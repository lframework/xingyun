package com.lframework.xingyun.crm.bo.followup;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.crm.entity.FollowUp;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryFollowUpBo extends BaseBo<FollowUp> {

  @Schema(description = "ID")
  private String id;

  @Schema(description = "跟进方式")
  private Integer followType;

  @Schema(description = "跟进内容")
  private String content;

  @Schema(description = "下次跟进日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate nextFollowDate;

  @Schema(description = "跟进人")
  private String createBy;

  @Schema(description = "跟进时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QueryFollowUpBo(FollowUp dto) {
    super(dto);
  }

  @Override
  public BaseBo<FollowUp> convert(FollowUp dto) {
    return super.convert(dto);
  }
}
