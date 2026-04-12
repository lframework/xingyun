package com.lframework.xingyun.sc.bo.logistics;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.dto.logistics.LogisticsSheetBizOrderDto;
import com.lframework.xingyun.sc.enums.LogisticsSheetDetailBizType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryLogisticsSheetBizOrderBo extends BaseBo<LogisticsSheetBizOrderDto> {

  /**
   * 业务单据ID
   */
  @Schema(description = "业务单据ID")
  private String bizId;

  /**
   * 业务单据号
   */
  @Schema(description = "业务单据号")
  private String bizCode;

  /**
   * 业务类型
   */
  @Schema(description = "业务类型")
  @EnumConvert
  private Integer bizType;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID")
  private String scId;

  /**
   * 仓库名称
   */
  @Schema(description = "仓库名称")
  private String scName;

  /**
   * 收货人ID
   */
  @Schema(description = "收货人ID")
  private String receiverId;

  /**
   * 收货人姓名
   */
  @Schema(description = "收货人姓名")
  private String receiverName;

  /**
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  private LocalDateTime createTime;

  public QueryLogisticsSheetBizOrderBo() {
  }

  public QueryLogisticsSheetBizOrderBo(LogisticsSheetBizOrderDto dto) {
    super(dto);
  }

  @Override
  protected void afterInit(LogisticsSheetBizOrderDto dto) {
    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scName = sc.getName();

    if (dto.getBizType() == LogisticsSheetDetailBizType.SALE_OUT_SHEET) {
      CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
      Customer customer = customerService.findById(dto.getReceiverId());
      this.receiverName = customer.getName();
    } else if (dto.getBizType() == LogisticsSheetDetailBizType.RETAIL_OUT_SHEET) {
      if (StringUtil.isNotBlank(dto.getReceiverId())) {
        MemberService memberService = ApplicationUtil.getBean(MemberService.class);
        Member member = memberService.findById(dto.getReceiverId());
        this.receiverName = member.getName();
      }
    }
  }
}
