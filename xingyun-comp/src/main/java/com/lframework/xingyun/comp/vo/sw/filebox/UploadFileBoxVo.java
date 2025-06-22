package com.lframework.xingyun.comp.vo.sw.filebox;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadFileBoxVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 文件
   */
  @ApiModelProperty(value = "文件", required = true)
  @NotNull(message = "文件不能为空！")
  private MultipartFile file;

  @ApiModelProperty(value = "路径", required = true)
  @NotBlank(message = "路径不能为空！")
  private String path;
}
