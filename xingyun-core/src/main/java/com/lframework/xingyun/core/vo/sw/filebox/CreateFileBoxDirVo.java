package com.lframework.xingyun.core.vo.sw.filebox;

import com.lframework.starter.web.components.validation.Pattern;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFileBoxDirVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父级目录", required = true)
    @NotBlank(message = "父级目录不能为空！")
    private String parentPath;

    @ApiModelProperty(value = "文件夹名称", required = true)
    @Pattern(regexp = "^(?!^\\.)[^\\/:*?\"<>|\\s].*$", message = "文件夹名称不合法！")
    @NotBlank(message = "文件夹名称不能为空！")
    private String name;
}
