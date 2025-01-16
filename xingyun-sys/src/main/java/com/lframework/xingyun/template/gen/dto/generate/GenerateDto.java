package com.lframework.xingyun.template.gen.dto.generate;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class GenerateDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 文件路径
   */
  private String path;

  /**
   * 文件名
   */
  private String fileName;

  /**
   * 文件内容
   */
  private String content;
}
