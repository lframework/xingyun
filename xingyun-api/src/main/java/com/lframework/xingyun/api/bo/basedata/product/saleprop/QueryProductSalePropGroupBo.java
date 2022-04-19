package com.lframework.xingyun.api.bo.basedata.product.saleprop;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.saleprop.ProductSalePropGroupDto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductSalePropGroupBo extends BaseBo<ProductSalePropGroupDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 创建人ID
   */
  @ApiModelProperty("创建人ID")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改人ID
   */
  @ApiModelProperty("修改人ID")
  private String updateBy;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public QueryProductSalePropGroupBo() {

  }

  public QueryProductSalePropGroupBo(ProductSalePropGroupDto dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductSalePropGroupDto dto) {

    IUserService userService = ApplicationUtil.getBean(IUserService.class);

    UserDto createBy = userService.getById(this.getCreateBy());
    UserDto updateBy = userService.getById(this.getUpdateBy());
    this.setCreateBy(createBy.getName());
    this.setUpdateBy(updateBy.getName());
  }
}
