package com.lframework.xingyun.comp.vo.sw.filebox;

import com.lframework.starter.web.core.components.validation.Regex;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFileBoxDirVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "父级目录", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "父级目录不能为空！")
    private String parentPath;

    @Schema(description = "文件夹名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Regex(regexp = "^(?!^\\.)[^\\/:*?\"<>|\\s].*$", message = "文件夹名称不合法！")
    @NotBlank(message = "文件夹名称不能为空！")
    private String name;
}
