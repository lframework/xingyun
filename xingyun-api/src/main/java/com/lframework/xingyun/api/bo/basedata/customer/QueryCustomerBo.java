package com.lframework.xingyun.api.bo.basedata.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.customer.CustomerDto;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryCustomerBo extends BaseBo<CustomerDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人ID
   */
  private String createBy;

  /**
   * 创建时间
   */
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改人ID
   */
  private String updateBy;

  /**
   * 修改时间
   */
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public QueryCustomerBo() {

  }

  public QueryCustomerBo(CustomerDto dto) {

    super(dto);
  }

  @Override
  protected void afterInit(CustomerDto dto) {

    IUserService userService = ApplicationUtil.getBean(IUserService.class);

    UserDto createBy = userService.getById(this.getCreateBy());
    UserDto updateBy = userService.getById(this.getUpdateBy());
    this.setCreateBy(createBy.getName());
    this.setUpdateBy(updateBy.getName());
  }
}
