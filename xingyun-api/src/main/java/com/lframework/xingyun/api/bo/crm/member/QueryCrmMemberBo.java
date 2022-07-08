package com.lframework.xingyun.api.bo.crm.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.service.shop.IShopService;
import com.lframework.xingyun.crm.entity.CrmMember;
import com.lframework.xingyun.crm.entity.MemberLevel;
import com.lframework.xingyun.crm.service.member.IMemberLevelService;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryCrmMemberBo extends BaseBo<CrmMember> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 性别
   */
  @ApiModelProperty("性别")
  private Integer gender;

  /**
   * 联系电话
   */
  @ApiModelProperty("联系电话")
  private String telephone;

  /**
   * 入会日期
   */
  @ApiModelProperty("入会日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate joinDay;

  /**
   * 所属门店名称
   */
  @ApiModelProperty("所属门店名称")
  private String shopName;

  /**
   * 所属导购名称
   */
  @ApiModelProperty("所属导购名称")
  private String guiderName;

  /**
   * 会员等级名称
   */
  @ApiModelProperty("会员等级名称")
  private String levelName;

  public QueryCrmMemberBo() {

  }

  public QueryCrmMemberBo(CrmMember dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<CrmMember> convert(CrmMember dto) {
    return super.convert(dto, QueryCrmMemberBo::getGender);
  }

  @Override
  protected void afterInit(CrmMember dto) {
    this.gender = dto.getGender().getCode();

    if (!StringUtil.isBlank(dto.getShopId())) {
      IShopService shopService = ApplicationUtil.getBean(IShopService.class);
      Shop shop = shopService.findById(dto.getShopId());
      this.shopName = shop.getName();
    }

    if (!StringUtil.isBlank(dto.getGuiderId())) {
      IUserService userService = ApplicationUtil.getBean(IUserService.class);
      UserDto guider = userService.findById(dto.getGuiderId());
      this.guiderName = guider.getName();
    }

    IMemberLevelService memberLevelService = ApplicationUtil.getBean(IMemberLevelService.class);
    MemberLevel level = memberLevelService.findById(dto.getLevelId());
    this.levelName = level.getName();
  }
}
