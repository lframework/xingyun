package com.lframework.xingyun.crm.vo.clue;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateClueVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "联系人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "联系人姓名不能为空！")
  private String contactName;

  @Schema(description = "联系电话", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "联系电话不能为空！")
  private String contactPhone;

  @Schema(description = "公司名称")
  private String companyName;

  @Schema(description = "线索来源（0-手动录入 1-电话咨询 2-网络推广 3-展会 4-转介绍 5-其他）")
  private Integer source;

  @Schema(description = "负责销售员ID")
  private String ownerId;

  @Schema(description = "备注")
  private String description;
}
