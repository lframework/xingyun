package com.lframework.xingyun.settle.vo.check.customer;

import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCustomerSettleCheckSheetVo implements BaseVo, Serializable {

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
  private List<CustomerSettleCheckSheetItemVo> items;

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

  public void validate() {

    int orderNo = 1;
    for (CustomerSettleCheckSheetItemVo item : this.items) {
      if (StringUtil.isBlank(item.getId())) {
        throw new InputErrorException("第" + orderNo + "行业务单据不能为空！");
      }

      if (item.getBizType() == null) {
        throw new InputErrorException("第" + orderNo + "行业务类型不能为空！");
      }

      if (item.getPayAmount() == null) {
        throw new InputErrorException("第" + orderNo + "行应收金额不能为空！");
      }

      orderNo++;
    }
  }
}
