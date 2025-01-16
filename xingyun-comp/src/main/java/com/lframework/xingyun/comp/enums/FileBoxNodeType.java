package com.lframework.xingyun.comp.enums;

import com.lframework.xingyun.core.enums.NodeType;
import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public final class FileBoxNodeType implements NodeType, Serializable {

  private static final long serialVersionUID = 1L;

  @Override
  public Integer getCode() {

    return 3;
  }

  @Override
  public String getDesc() {

    return "文件收纳箱";
  }
}
