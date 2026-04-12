package com.lframework.xingyun.comp.vo.sw.filebox;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadFileBoxVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 文件
   */
  @Schema(description = "文件", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "文件不能为空！")
  private MultipartFile file;

  @Schema(description = "路径", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "路径不能为空！")
  private String path;
}
