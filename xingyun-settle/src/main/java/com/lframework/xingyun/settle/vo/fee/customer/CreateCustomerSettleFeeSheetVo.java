package com.lframework.xingyun.settle.vo.fee.customer;

import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.settle.enums.CustomerSettleFeeSheetType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCustomerSettleFeeSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 客户ID
   */
  @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "客户ID不能为空！")
  private String customerId;

  /**
   * 收支方式
   */
  @Schema(description = "收支方式", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "收支方式不能为空！")
  @IsEnum(message = "收支方式不能为空！", enumClass = CustomerSettleFeeSheetType.class)
  private Integer sheetType;

  /**
   * 项目
   */
  @Schema(description = "项目", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "项目不能为空！")
  private List<CustomerSettleFeeSheetItemVo> items;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public void validate() {

    int orderNo = 1;
    for (CustomerSettleFeeSheetItemVo item : this.items) {
      if (StringUtil.isBlank(item.getId())) {
        throw new InputErrorException("第" + orderNo + "行项目不能为空！");
      }

      if (item.getAmount() == null) {
        throw new InputErrorException("第" + orderNo + "行金额不能为空！");
      }

      if (NumberUtil.le(item.getAmount(), 0D)) {
        throw new InputErrorException("第" + orderNo + "行金额必须大于0！");
      }

      if (!NumberUtil.isNumberPrecision(item.getAmount(), 2)) {
        throw new InputErrorException("第" + orderNo + "行金额最多允许2位小数！");
      }

      if (this.items.stream().filter(t -> t.getId().equals(item.getId())).count() > 1) {
        throw new InputErrorException("第" + orderNo + "行项目与其他行重复，请检查！");
      }

      orderNo++;
    }
  }
}
