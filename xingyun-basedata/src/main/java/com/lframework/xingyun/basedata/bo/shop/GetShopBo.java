package com.lframework.xingyun.basedata.bo.shop;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.template.core.dto.DeptDto;
import com.lframework.xingyun.template.core.service.DeptService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>
 * 门店 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetShopBo extends BaseBo<Shop> {

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
   * 所属部门ID
   */
  @ApiModelProperty("所属部门ID")
  private String deptId;

  /**
   * 所属部门名称
   */
  @ApiModelProperty("所属部门名称")
  private String deptName;

  /**
   * 经度
   */
  @ApiModelProperty("经度")
  private BigDecimal lng;

  /**
   * 纬度
   */
  @ApiModelProperty("纬度")
  private BigDecimal lat;

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

  public GetShopBo() {

  }

  public GetShopBo(Shop dto) {

    super(dto);
  }

  @Override
  protected void afterInit(Shop dto) {
    if (!StringUtil.isBlank(dto.getDeptId())) {
      DeptService deptService = ApplicationUtil.getBean(DeptService.class);

      DeptDto dept = deptService.findById(dto.getDeptId());

      this.deptName = dept.getName();
    }
  }
}
