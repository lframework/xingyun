package com.lframework.xingyun.settle.vo.check.customer;

import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCustomerSettleCheckSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 客户ID
   */
  @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "客户ID不能为空！")
  private String customerId;

  /**
   * 项目
   */
  @Schema(description = "项目", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "项目不能为空！")
  private List<CustomerSettleCheckSheetItemVo> items;

  /**
   * 起始日期
   */
  @Schema(description = "起始日期", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "起始日期不能为空！")
  private LocalDate startDate;

  /**
   * 截止日期
   */
  @Schema(description = "截止日期", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "截止日期不能为空！")
  private LocalDate endDate;

  /**
   * 备注
   */
  @Schema(description = "备注")
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
