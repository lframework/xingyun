package com.lframework.xingyun.basedata.bo.shop;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.starter.web.inner.entity.SysDept;
import com.lframework.starter.web.inner.service.system.SysDeptService;
import io.swagger.v3.oas.annotations.media.Schema;
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
   * 所属部门ID
   */
  @Schema(description = "所属部门ID")
  private String deptId;

  /**
   * 所属部门名称
   */
  @Schema(description = "所属部门名称")
  private String deptName;

  /**
   * 经度
   */
  @Schema(description = "经度")
  private BigDecimal lng;

  /**
   * 纬度
   */
  @Schema(description = "纬度")
  private BigDecimal lat;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public GetShopBo() {

  }

  public GetShopBo(Shop dto) {

    super(dto);
  }

  @Override
  protected void afterInit(Shop dto) {
    if (!StringUtil.isBlank(dto.getDeptId())) {
      SysDeptService sysDeptService = ApplicationUtil.getBean(SysDeptService.class);

      SysDept dept = sysDeptService.findById(dto.getDeptId());

      this.deptName = dept.getName();
    }
  }
}
