package com.lframework.xingyun.settle.vo.pre.customer;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCustomerSettlePreSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 客户ID
   */
  @ApiModelProperty(value = "客户ID", required = true)
  @NotNull(message = "客户ID不能为空！")
  private String customerId;

  /**
   * 项目
   */
  @ApiModelProperty(value = "项目", required = true)
  @NotEmpty(message = "项目不能为空！")
  private List<CustomerSettlePreSheetItemVo> items;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  @Override
  public void validate() {

    int orderNo = 1;
    for (CustomerSettlePreSheetItemVo item : this.items) {
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
