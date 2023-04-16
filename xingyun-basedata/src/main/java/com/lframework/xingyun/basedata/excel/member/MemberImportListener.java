package com.lframework.xingyun.basedata.excel.member;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.components.excel.ExcelImportListener;
import com.lframework.starter.mybatis.entity.DefaultSysUser;
import com.lframework.starter.mybatis.enums.Gender;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.service.shop.ShopService;
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
      ShopService shopService = ApplicationUtil.getBean(ShopService.class);
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
      UserService userService = ApplicationUtil.getBean(UserService.class);
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

    MemberService memberService = ApplicationUtil.getBean(MemberService.class);

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

      if (!StringUtil.isBlank(data.getTelephone())) {
        Wrapper<Member> checkWrapper = Wrappers.lambdaQuery(Member.class)
            .eq(Member::getTelephone, data.getTelephone())
            .ne(Member::getId, record.getId());
        if (memberService.count(checkWrapper) > 0) {
          throw new DefaultClientException(
              "第" + (i + 1) + "行“联系电话”重复，请重新输入");
        }
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

      memberService.saveOrUpdateAllColumn(record);

      this.setSuccessProcess(i);
    }
  }

  @Override
  protected void doComplete() {
    MemberService memberService = ApplicationUtil.getBean(MemberService.class);
    List<MemberImportModel> datas = this.getDatas();
    for (MemberImportModel data : datas) {
      memberService.cleanCacheByKey(data.getId());
    }
  }
}
