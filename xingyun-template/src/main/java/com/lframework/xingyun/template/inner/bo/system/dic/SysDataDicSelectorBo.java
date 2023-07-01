package com.lframework.xingyun.template.inner.bo.system.dic;

import com.lframework.xingyun.template.inner.entity.SysDataDic;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysDataDicSelectorBo extends BaseBo<SysDataDic> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  public SysDataDicSelectorBo() {
  }

  public SysDataDicSelectorBo(SysDataDic dto) {
    super(dto);
  }
}
