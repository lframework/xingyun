package com.lframework.xingyun.template.inner.listeners.mq;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.mq.core.producer.MqProducer;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.core.components.notify.SysNotifyRuleEmail;
import com.lframework.xingyun.core.components.notify.SysNotifyRuleSys;
import com.lframework.xingyun.core.dto.message.SysMailMessageDto;
import com.lframework.xingyun.core.dto.message.SysSiteMessageDto;
import com.lframework.xingyun.core.dto.notify.SysNotifyDto;
import com.lframework.xingyun.core.dto.notify.SysNotifyParamsDto;
import com.lframework.xingyun.template.inner.entity.SysUser;
import com.lframework.xingyun.core.queue.MqConstants;
import com.lframework.xingyun.core.queue.MqStringPool;
import com.lframework.xingyun.template.inner.entity.SysNotifyGroup;
import com.lframework.xingyun.template.inner.enums.system.SysNotifyMessageType;
import com.lframework.xingyun.template.inner.service.system.SysNotifyGroupService;
import com.lframework.xingyun.template.inner.service.system.SysUserService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SysNotifyListener {

  @Autowired
  private SysNotifyGroupService sysNotifyGroupService;

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private MqProducer mqProducer;

  private static final int BATCH_SIZE = 100;

  @RabbitListener(bindings = {
      @QueueBinding(value = @Queue(value = MqStringPool.SYS_NOTIFY_QUEUE), key = MqStringPool.SYS_NOTIFY_ROUTING_KEY, exchange = @Exchange(value = MqStringPool.SYS_NOTIFY_EXCHANGE))})
  public void execute(Message<SysNotifyDto> message) {
    SysNotifyDto dto = message.getPayload();
    log.debug("接收到消息：{}", dto);

    SysNotifyGroup notifyGroup = sysNotifyGroupService.findById(dto.getNotifyGroupId());
    if (notifyGroup == null) {
      log.info("消息通知组不存在，通知组ID：{}", dto.getNotifyGroupId());
      return;
    }
    if (!notifyGroup.getAvailable()) {
      log.info("消息通知组不可用，通知组ID：{}", dto.getNotifyGroupId());
      return;
    }

    Set<String> userIds = sysNotifyGroupService.getReceiveUserIds(notifyGroup.getId());
    if (CollectionUtil.isEmpty(userIds)) {
      log.info("消息通知组不存在接收人，通知组ID：{}", dto.getNotifyGroupId());
      return;
    }

    List<Integer> messageTypeCodes = Arrays.stream(
        notifyGroup.getMessageType().split(StringPool.STR_SPLIT)).map(Integer::valueOf).collect(
        Collectors.toList());
    for (Integer messageTypeCode : messageTypeCodes) {
      SysNotifyMessageType messageType = EnumUtil.getByCode(SysNotifyMessageType.class,
          messageTypeCode);
      switch (messageType) {
        case EMAIL: {
          Map<String, SysNotifyRuleEmail> ruleMap = ApplicationUtil.getBeansOfType(
              SysNotifyRuleEmail.class);

          for (SysNotifyRuleEmail rule : ruleMap.values()) {
            if (!rule.match(dto.getBizType())) {
              continue;
            }

            log.info("匹配到rule，开始发送邮件");
            Wrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .select(SysUser::getEmail)
                .in(SysUser::getId, userIds)
                .eq(SysUser::getAvailable, true)
                .isNotNull(SysUser::getEmail)
                .ne(SysUser::getEmail, StringPool.EMPTY_STR);
            List<SysUser> userList = sysUserService.list(queryWrapper);
            if (CollectionUtil.isNotEmpty(userList)) {
              SysNotifyParamsDto paramsDto = new SysNotifyParamsDto();
              paramsDto.setVariables(dto.getVariables());

              String title = rule.getTitle(paramsDto);
              String content = rule.getContent(paramsDto);

              List<String> allMailList = userList.stream().map(SysUser::getEmail).distinct()
                  .collect(
                      Collectors.toList());
              List<List<String>> splitMailList = CollectionUtil.split(allMailList, BATCH_SIZE);
              for (List<String> mailList : splitMailList) {
                SysMailMessageDto messageDto = new SysMailMessageDto();
                messageDto.setMailList(mailList);
                messageDto.setTitle(title);
                messageDto.setContent(content);
                messageDto.setBizKey(IdUtil.getId());
                messageDto.setCreateUserId(dto.getCreateUserId());

                mqProducer.sendMessage(MqConstants.SYS_MAIL_MESSAGE, messageDto);
              }
            }
            break;
          }
          break;
        }

        case SYS: {
          Map<String, SysNotifyRuleSys> ruleMap = ApplicationUtil.getBeansOfType(
              SysNotifyRuleSys.class);

          for (SysNotifyRuleSys rule : ruleMap.values()) {
            if (!rule.match(dto.getBizType())) {
              continue;
            }

            log.info("匹配到rule，开始发送站内信");
            Wrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .select(SysUser::getId)
                .in(SysUser::getId, userIds)
                .eq(SysUser::getAvailable, true);
            List<SysUser> userList = sysUserService.list(queryWrapper);
            if (CollectionUtil.isNotEmpty(userList)) {
              SysNotifyParamsDto paramsDto = new SysNotifyParamsDto();
              paramsDto.setVariables(dto.getVariables());

              String title = rule.getTitle(paramsDto);
              String content = rule.getContent(paramsDto);

              List<String> allUserIdList = userList.stream().map(SysUser::getId).distinct()
                  .collect(
                      Collectors.toList());
              List<List<String>> splitUserIdList = CollectionUtil.split(allUserIdList, BATCH_SIZE);
              for (List<String> userIdList : splitUserIdList) {
                SysSiteMessageDto messageDto = new SysSiteMessageDto();
                messageDto.setUserIdList(userIdList);
                messageDto.setTitle(title);
                messageDto.setContent(content);
                messageDto.setBizKey(IdUtil.getId());
                messageDto.setCreateUserId(dto.getCreateUserId());

                mqProducer.sendMessage(MqConstants.SYS_SITE_MESSAGE, messageDto);
              }
            }
            break;
          }
          break;
        }

        default: {
          throw new DefaultSysException("未知的MessageType: " + messageTypeCode);
        }
      }
    }
  }
}
