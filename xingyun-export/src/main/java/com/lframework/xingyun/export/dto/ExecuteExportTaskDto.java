package com.lframework.xingyun.export.dto;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class ExecuteExportTaskDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  private String taskId;

  private String token;
}
