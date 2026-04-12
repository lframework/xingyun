package com.lframework.xingyun.basedata.bo.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.service.shop.ShopService;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;

@Data
public class GetMemberBo extends BaseBo<Member> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 性别
   */
  @Schema(description = "性别")
  private Integer gender;

  /**
   * 联系电话
   */
  @Schema(description = "联系电话")
  private String telephone;

  /**
   * 电子邮箱
   */
  @Schema(description = "电子邮箱")
  private String email;

  /**
   * 出生日期
   */
  @Schema(description = "出生日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate birthday;

  /**
   * 入会日期
   */
  @Schema(description = "入会日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate joinDay;

  /**
   * 所属门店ID
   */
  @Schema(description = "所属门店ID")
  private String shopId;

  /**
   * 所属门店名称
   */
  @Schema(description = "所属门店名称")
  private String shopName;

  /**
   * 所属导购ID
   */
  @Schema(description = "所属导购ID")
  private String guiderId;

  /**
   * 所属导购名称
   */
  @Schema(description = "所属导购名称")
  private String guiderName;

  /**
   * 备注
   */
  @Schema(description = "备注")
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
      SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
      SysUser guider = userService.findById(dto.getGuiderId());
      this.guiderName = guider.getName();
    }
  }
}
