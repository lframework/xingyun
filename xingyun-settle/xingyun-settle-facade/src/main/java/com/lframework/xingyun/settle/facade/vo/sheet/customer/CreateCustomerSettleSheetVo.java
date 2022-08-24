package com.lframework.xingyun.settle.facade.vo.sheet.customer;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCustomerSettleSheetVo implements BaseVo, Serializable {

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
  private List<CustomerSettleSheetItemVo> items;

  /**
   * 起始日期
   */
  @ApiModelProperty(value = "起始日期", required = true)
  @NotNull(message = "起始日期不能为空！")
  private LocalDate startDate;

  /**
   * 截止日期
   */
  @ApiModelProperty(value = "截止日期", required = true)
  @NotNull(message = "截止日期不能为空！")
  private LocalDate endDate;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  @Override
  public void validate() {

    int orderNo = 1;
    for (CustomerSettleSheetItemVo item : this.items) {
      if (StringUtil.isBlank(item.getId())) {
        throw new InputErrorException("第" + orderNo + "行对账单不能为空！");
      }

      if (item.getPayAmount() == null) {
        throw new InputErrorException("第" + orderNo + "行实收金额不能为空！");
      }

      if (item.getDiscountAmount() == null) {
        throw new InputErrorException("第" + orderNo + "行优惠金额不能为空！");
      }

      orderNo++;
    }
  }
}
