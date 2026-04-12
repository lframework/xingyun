package com.lframework.xingyun.basedata.vo.supplier;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import com.lframework.xingyun.basedata.enums.ManageType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class QuerySupplierSelectorVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

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
   * 经营方式
   */
  @Schema(description = "经营方式")
  @IsEnum(message = "经营方式格式不正确！", enumClass = ManageType.class)
  private Integer manageType;

}
