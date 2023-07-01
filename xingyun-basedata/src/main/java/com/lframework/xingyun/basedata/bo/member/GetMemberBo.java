package com.lframework.xingyun.basedata.bo.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.service.shop.ShopService;
import com.lframework.xingyun.template.core.dto.UserDto;
import com.lframework.xingyun.template.core.service.UserService;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.Data;

@Data
public class GetMemberBo extends BaseBo<Member> {

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
   * 电子邮箱
   */
  @ApiModelProperty("电子邮箱")
  private String email;

  /**
   * 出生日期
   */
  @ApiModelProperty("出生日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate birthday;

  /**
   * 入会日期
   */
  @ApiModelProperty("入会日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate joinDay;

  /**
   * 所属门店ID
   */
  @ApiModelProperty("所属门店ID")
  private String shopId;

  /**
   * 所属门店名称
   */
  @ApiModelProperty("所属门店名称")
  private String shopName;

  /**
   * 所属导购ID
   */
  @ApiModelProperty("所属导购ID")
  private String guiderId;

  /**
   * 所属导购名称
   */
  @ApiModelProperty("所属导购名称")
  private String guiderName;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetMemberBo() {

  }

  public GetMemberBo(Member dto) {

    super(dto);
  }

  @Override
  public BaseBo<Member> convert(Member dto) {

    return super.convert(dto, GetMemberBo::getGender);
  }

  @Override
  protected void afterInit(Member dto) {

    this.gender = dto.getGender().getCode();

    if (!StringUtil.isBlank(dto.getShopId())) {
      ShopService shopService = ApplicationUtil.getBean(ShopService.class);
      Shop shop = shopService.findById(dto.getShopId());
      this.shopName = shop.getName();
    }

    if (!StringUtil.isBlank(dto.getGuiderId())) {
      UserService userService = ApplicationUtil.getBean(UserService.class);
      UserDto guider = userService.findById(dto.getGuiderId());
      this.guiderName = guider.getName();
    }
  }
}
