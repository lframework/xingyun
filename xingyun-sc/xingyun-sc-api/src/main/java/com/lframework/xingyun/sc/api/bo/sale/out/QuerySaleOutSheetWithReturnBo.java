package com.lframework.xingyun.sc.api.bo.sale.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.CustomerFeignClient;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.entity.Customer;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.sc.facade.entity.SaleOutSheet;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySaleOutSheetWithReturnBo extends BaseBo<SaleOutSheet> {

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
   * 客户编号
   */
  @ApiModelProperty("客户编号")
  private String customerCode;

  /**
   * 客户名称
   */
  @ApiModelProperty("客户名称")
  private String customerName;

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

  public QuerySaleOutSheetWithReturnBo() {

  }

  public QuerySaleOutSheetWithReturnBo(SaleOutSheet dto) {

    super(dto);
  }

  @Override
  public BaseBo<SaleOutSheet> convert(SaleOutSheet dto) {

    return super.convert(dto);
  }

  @Override
  protected void afterInit(SaleOutSheet dto) {

    StoreCenterFeignClient storeCenterFeignClient = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    StoreCenter sc = storeCenterFeignClient.findById(dto.getScId()).getData();
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    CustomerFeignClient customerFeignClient = ApplicationUtil.getBean(CustomerFeignClient.class);
    Customer customer = customerFeignClient.findById(dto.getCustomerId()).getData();
    this.customerCode = customer.getCode();
    this.customerName = customer.getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);

    this.createBy = userService.findById(dto.getCreateBy()).getName();
  }
}
