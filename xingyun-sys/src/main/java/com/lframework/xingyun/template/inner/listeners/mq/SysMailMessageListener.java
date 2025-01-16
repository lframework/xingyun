package com.lframework.xingyun.template.inner.listeners.mq;

import cn.hutool.extra.mail.MailException;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.service.MailService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.core.dto.message.SysMailMessageDto;
import com.lframework.xingyun.template.inner.entity.SysUser;
import com.lframework.xingyun.core.queue.MqStringPool;
import com.lframework.xingyun.template.inner.entity.SysMailMessage;
import com.lframework.xingyun.template.inner.enums.system.SysMailMessageSendStatus;
import com.lframework.xingyun.template.inner.service.system.SysMailMessageService;
import com.lframework.xingyun.template.inner.service.system.SysUserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.mail.Address;
import javax.mail.SendFailedException;
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
public class SysMailMessageListener {

  @Autowired
  private SysMailMessageService sysMailMessageService;

  @Autowired
  private SysUserService sysUserService;

  @RabbitListener(bindings = {
      @QueueBinding(value = @Queue(value = MqStringPool.SYS_MAIL_MESSAGE_QUEUE), key = MqStringPool.SYS_MAIL_MESSAGE_ROUTING_KEY, exchange = @Exchange(value = MqStringPool.SYS_MAIL_MESSAGE_EXCHANGE))})
  public void execute(Message<SysMailMessageDto> message) {

    SysMailMessageDto dto = message.getPayload();
    log.info("接收到发送邮件消息 {}", dto);

    if (CollectionUtil.isNotEmpty(dto.getMailList())) {
      dto.setMailList(dto.getMailList().stream().filter(t -> RegUtil.isMatch(PatternPool.EMAIL, t)).collect(
          Collectors.toList()));
    }

    if (CollectionUtil.isEmpty(dto.getMailList())) {
      log.info("邮箱地址为空，不发送");
      return;
    }

    String title = dto.getTitle();
    String content = dto.getContent();
    if (StringUtil.isBlank(title) || StringUtil.isBlank(content) || StringUtil.isBlank(
        dto.getBizKey())) {
      log.info("标题、内容、业务键不能为空，不发送");
      return;
    }

    SysUser createBy = StringUtil.isBlank(dto.getCreateUserId()) ? null
        : sysUserService.findById(dto.getCreateUserId());

    List<SysMailMessage> recordList = dto.getMailList().stream().distinct().map(t -> {
      SysMailMessage record = new SysMailMessage();
      record.setId(IdUtil.getId());
      record.setTitle(title);
      record.setContent(content);
      record.setMail(t);
      record.setBizKey(dto.getBizKey());
      record.setSendStatus(SysMailMessageSendStatus.UN_SEND);
      if (createBy != null) {
        record.setCreateById(createBy.getId());
        record.setCreateBy(createBy.getName());
        record.setUpdateBy(createBy.getName());
        record.setUpdateById(createBy.getId());
      }

      return record;
    }).collect(Collectors.toList());

    sysMailMessageService.saveBatch(recordList);

    try {
      MailService mailService = ApplicationUtil.getBean(MailService.class);
      mailService.send(
          recordList.stream().map(SysMailMessage::getMail).collect(Collectors.toList()),
          title, content);

      // 全部成功
      recordList.forEach(t -> t.setSendStatus(SysMailMessageSendStatus.SENDED));

    } catch (MailException e) {
      Throwable throwable = e.getCause();
      if (throwable instanceof SendFailedException) {
        //部分失败
        Address[] invalidAddresses = ((SendFailedException) throwable).getInvalidAddresses();
        for (Address invalidAddress : invalidAddresses) {
          String mail = invalidAddress.toString();
          recordList.stream().filter(t -> mail.equals(t.getMail()))
              .findFirst().ifPresent(record -> record.setSendStatus(SysMailMessageSendStatus.FAIL));
          recordList.stream().filter(t -> t.getSendStatus() == SysMailMessageSendStatus.UN_SEND
          ).forEach(record -> record.setSendStatus(SysMailMessageSendStatus.SENDED));
        }
      } else {
        // 全部失败
        recordList.forEach(t -> t.setSendStatus(SysMailMessageSendStatus.FAIL));
        log.error(e.getMessage(), e);
      }
    }

    sysMailMessageService.updateBatchById(recordList.stream().map(t -> {
      SysMailMessage record = new SysMailMessage();
      record.setId(t.getId());
      record.setSendStatus(t.getSendStatus());
      record.setCreateById(t.getCreateById());
      record.setCreateBy(t.getCreateBy());
      record.setUpdateById(t.getUpdateById());
      record.setUpdateBy(t.getUpdateBy());

      return record;
    }).collect(Collectors.toList()));
  }
}
