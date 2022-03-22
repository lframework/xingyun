package com.lframework.xingyun.settle.vo.check;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSettleCheckSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 供应商ID
   */
  @NotNull(message = "供应商ID不能为空！")
  private String supplierId;

  /**
   * 项目
   */
  @NotEmpty(message = "项目不能为空！")
  private List<SettleCheckSheetItemVo> items;

  /**
   * 起始日期
   */
  @NotNull(message = "起始日期不能为空！")
  private LocalDate startDate;

  /**
   * 截止日期
   */
  @NotNull(message = "截止日期不能为空！")
  private LocalDate endDate;

  /**
   * 备注
   */
  private String description;

  @Override
  public void validate() {

    int orderNo = 1;
    for (SettleCheckSheetItemVo item : this.items) {
      if (StringUtil.isBlank(item.getId())) {
        throw new InputErrorException("第" + orderNo + "行业务单据不能为空！");
      }

      if (item.getBizType() == null) {
        throw new InputErrorException("第" + orderNo + "行业务类型不能为空！");
      }

      if (item.getPayAmount() == null) {
        throw new InputErrorException("第" + orderNo + "行应付金额不能为空！");
      }

      orderNo++;
    }
  }
}
