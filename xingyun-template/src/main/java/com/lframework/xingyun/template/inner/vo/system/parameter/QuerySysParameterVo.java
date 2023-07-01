package com.lframework.xingyun.template.inner.vo.system.parameter;

import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuerySysParameterVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 键
   */
  @ApiModelProperty("键")
  private String pmKey;

  /**
   * 创建时间 起始时间
   */
  @ApiModelProperty("创建时间 起始时间")
  @TypeMismatch(message = "创建时间起始时间格式有误！")
  private LocalDateTime createTimeStart;

  /**
   * 创建时间 截止时间
   */
  @ApiModelProperty("创建时间 截止时间")
  @TypeMismatch(message = "创建时间截止时间格式有误！")
  private LocalDateTime createTimeEnd;

}
