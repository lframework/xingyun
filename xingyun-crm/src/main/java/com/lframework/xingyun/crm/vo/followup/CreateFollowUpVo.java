package com.lframework.xingyun.crm.vo.followup;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFollowUpVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "关联线索ID（与opportunityId二选一）")
  private String clueId;

  @Schema(description = "关联商机ID（与clueId二选一）")
  private String opportunityId;

  @Schema(description = "跟进方式（0-电话 1-邮件 2-上门拜访 3-微信/企业微信 4-其他）")
  private Integer followType;

  @Schema(description = "跟进内容", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "跟进内容不能为空！")
  private String content;

  @Schema(description = "下次跟进日期")
  private LocalDate nextFollowDate;
}
