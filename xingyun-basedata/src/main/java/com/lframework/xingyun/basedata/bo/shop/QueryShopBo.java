package com.lframework.xingyun.basedata.bo.shop;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.starter.web.inner.entity.SysDept;
import com.lframework.starter.web.inner.service.system.SysDeptService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 门店 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QueryShopBo extends BaseBo<Shop> {

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
   * 所属部门名称
   */
  @Schema(description = "所属部门名称")
  private String deptName;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QueryShopBo() {

  }

  public QueryShopBo(Shop dto) {

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
