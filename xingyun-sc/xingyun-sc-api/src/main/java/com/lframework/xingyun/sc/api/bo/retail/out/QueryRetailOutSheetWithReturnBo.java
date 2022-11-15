package com.lframework.xingyun.sc.api.bo.retail.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.MemberFeignClient;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.entity.Member;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.sc.facade.entity.RetailOutSheet;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryRetailOutSheetWithReturnBo extends BaseBo<RetailOutSheet> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 单号
   */
  @ApiModelProperty("单号")
  private String code;

  /**
   * 仓库编号
   */
  @ApiModelProperty("仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @ApiModelProperty("仓库名称")
  private String scName;

  /**
   * 会员编号
   */
  @ApiModelProperty("会员编号")
  private String memberCode;

  /**
   * 会员名称
   */
  @ApiModelProperty("会员名称")
  private String memberName;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QueryRetailOutSheetWithReturnBo() {

  }

  public QueryRetailOutSheetWithReturnBo(RetailOutSheet dto) {

    super(dto);
  }

  @Override
  public BaseBo<RetailOutSheet> convert(RetailOutSheet dto) {

    return super.convert(dto);
  }

  @Override
  protected void afterInit(RetailOutSheet dto) {

    StoreCenterFeignClient storeCenterFeignClient = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    StoreCenter sc = storeCenterFeignClient.findById(dto.getScId()).getData();
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    if (!StringUtil.isBlank(dto.getMemberId())) {
      MemberFeignClient memberFeignClient = ApplicationUtil.getBean(MemberFeignClient.class);
      Member member = memberFeignClient.findById(dto.getMemberId()).getData();
      this.memberCode = member.getCode();
      this.memberName = member.getName();
    }
  }
}
