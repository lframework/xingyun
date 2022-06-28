package com.lframework.xingyun.api.excel.basedata.member;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.PatternPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.RegUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.components.excel.ExcelImportListener;
import com.lframework.starter.mybatis.entity.DefaultSysUser;
import com.lframework.starter.mybatis.enums.Gender;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.service.shop.IShopService;
import com.lframework.xingyun.core.events.member.CreateMemberEvent;
import com.lframework.xingyun.core.events.member.UpdateMemberEvent;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberImportListener extends ExcelImportListener<MemberImportModel> {

  @Override
  protected void doInvoke(MemberImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getCode())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“编号”不能为空");
    }
    if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“编号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }
    if (StringUtil.isBlank(data.getName())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“名称”不能为空");
    }
    if (StringUtil.isBlank(data.getGender())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“性别”不能为空");
    }

    Gender gender = EnumUtil.getByDesc(Gender.class, data.getGender());
    if (gender == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“性别”只能填写“" + CollectionUtil.join(
              EnumUtil.getDescs(Gender.class), "、") + "”");
    }
    data.setGenderEnum(gender);

    if (!StringUtil.isEmpty(data.getEmail())) {
      if (!RegUtil.isMatch(PatternPool.EMAIL, data.getEmail())) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“电子邮箱”格式有误");
      }
    }

    if (!StringUtil.isEmpty(data.getShopCode())) {
      IShopService shopService = ApplicationUtil.getBean(IShopService.class);
      Wrapper<Shop> queryWrapper = Wrappers.lambdaQuery(Shop.class)
          .eq(Shop::getCode, data.getShopCode());
      Shop shop = shopService.getOne(queryWrapper);
      if (shop == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“所属门店编号”不存在");
      }
      data.setShopId(shop.getId());
    }

    if (!StringUtil.isEmpty(data.getGuiderCode())) {
      IUserService userService = ApplicationUtil.getBean(IUserService.class);
      Wrapper<DefaultSysUser> queryWrapper = Wrappers.lambdaQuery(DefaultSysUser.class)
          .eq(DefaultSysUser::getCode, data.getGuiderCode());
      DefaultSysUser guider = userService.getOne(queryWrapper);
      if (guider == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“所属导购编号”不存在");
      }
      data.setGuiderId(guider.getId());
    }

    if (data.getJoinDay() == null) {
      data.setJoinDay(new Date());
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    IMemberService memberService = ApplicationUtil.getBean(IMemberService.class);

    List<MemberImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      MemberImportModel data = datas.get(i);

      boolean isInsert = false;
      Wrapper<Member> queryWrapper = Wrappers.lambdaQuery(Member.class)
          .eq(Member::getCode, data.getCode());
      Member record = memberService.getOne(queryWrapper);
      if (record == null) {
        record = new Member();
        record.setId(IdUtil.getId());
        isInsert = true;
      }

      record.setCode(data.getCode());
      record.setName(data.getName());
      record.setGender(data.getGenderEnum());
      record.setTelephone(data.getTelephone());
      record.setEmail(data.getEmail());
      record.setBirthday(DateUtil.toLocalDate(data.getBirthday()));
      record.setJoinDay(DateUtil.toLocalDate(data.getJoinDay()));
      record.setShopId(data.getShopId());
      record.setGuiderId(data.getGuiderId());
      record.setDescription(data.getDescription());
      if (isInsert) {
        record.setAvailable(Boolean.TRUE);
      }

      data.setId(record.getId());
      data.setIsInsert(isInsert);

      memberService.saveOrUpdate(record);

      this.setSuccessProcess(i);
    }
  }

  @Override
  protected void doComplete() {
    IMemberService memberService = ApplicationUtil.getBean(IMemberService.class);
    List<MemberImportModel> datas = this.getDatas();
    for (MemberImportModel data : datas) {
      memberService.cleanCacheByKey(data.getId());
    }
    for (MemberImportModel data : datas) {
      if (data.getIsInsert()) {
        CreateMemberEvent event = new CreateMemberEvent(this);
        event.setId(data.getId());
        ApplicationUtil.publishEvent(event);
      } else {
        UpdateMemberEvent event = new UpdateMemberEvent(this);
        event.setId(data.getId());
        ApplicationUtil.publishEvent(event);
      }
    }
  }
}
