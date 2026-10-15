package com.lframework.xingyun.crm.bo.clue;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.service.system.SysUserService;
import com.lframework.xingyun.crm.entity.Clue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryClueBo extends BaseBo<Clue> {

  @Schema(description = "ID")
  private String id;

  @Schema(description = "线索编号")
  private String code;

  @Schema(description = "联系人姓名")
  private String contactName;

  @Schema(description = "联系电话")
  private String contactPhone;

  @Schema(description = "公司名称")
  private String companyName;

  @Schema(description = "线索来源")
  private Integer source;

  @Schema(description = "负责销售员姓名")
  private String ownerName;

  @Schema(description = "状态")
  private Integer status;

  @Schema(description = "备注")
  private String description;

  @Schema(description = "创建人")
  private String createBy;

  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QueryClueBo(Clue dto) {
    super(dto);
  }

  @Override
  public BaseBo<Clue> convert(Clue dto) {
    return super.convert(dto, QueryClueBo::getStatus);
  }

  @Override
  protected void afterInit(Clue dto) {
    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getOwnerId())) {
      this.ownerName = userService.findById(dto.getOwnerId()).getName();
    }
    this.status = dto.getStatus().getCode();
  }
}
