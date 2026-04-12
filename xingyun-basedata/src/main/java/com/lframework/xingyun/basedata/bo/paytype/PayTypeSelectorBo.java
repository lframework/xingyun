package com.lframework.xingyun.basedata.bo.paytype;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.PayType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PayTypeSelectorBo extends BaseBo<PayType> {

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
   * 是否记录内容
   */
  @Schema(description = "是否记录内容")
  private Boolean recText;

  public PayTypeSelectorBo() {

  }

  public PayTypeSelectorBo(PayType dto) {

    super(dto);
  }
}
