package com.lframework.xingyun.api.bo.retail.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.member.MemberDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryRetailOutSheetWithReturnBo extends BaseBo<RetailOutSheetDto> {

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

  public QueryRetailOutSheetWithReturnBo(RetailOutSheetDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<RetailOutSheetDto> convert(RetailOutSheetDto dto) {

    return super.convert(dto);
  }

  @Override
  protected void afterInit(RetailOutSheetDto dto) {

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    IMemberService memberService = ApplicationUtil.getBean(IMemberService.class);
    MemberDto member = memberService.getById(dto.getMemberId());
    this.memberCode = member.getCode();
    this.memberName = member.getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);

    this.createBy = userService.getById(dto.getCreateBy()).getName();
  }
}
