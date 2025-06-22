package com.lframework.xingyun.comp.vo.sw.excel;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BatchSendOnlineExcelVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotEmpty(message = "ID不能为空！")
  private List<String> ids;

  /**
   * 用户ID
   */
  @ApiModelProperty(value = "用户ID", required = true)
  @NotBlank(message = "用户ID不能为空！")
  private String userId;

  /**
   * 是否留存副本
   */
  @ApiModelProperty(value = "是否留存副本", required = true)
  @NotNull(message = "是否留存副本不能为空！")
  @TypeMismatch(message = "是否留存副本格式错误！")
  private Boolean selfSave;
}
