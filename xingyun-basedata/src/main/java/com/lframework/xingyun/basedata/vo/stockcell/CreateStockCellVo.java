package com.lframework.xingyun.basedata.vo.stockcell;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.StockCellType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateStockCellVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 仓位类别
   */
  @Schema(description = "仓位类别", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择仓位类别！")
  @IsEnum(enumClass = StockCellType.class, message = "仓位类别格式不正确！")
  private Integer cellType;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
