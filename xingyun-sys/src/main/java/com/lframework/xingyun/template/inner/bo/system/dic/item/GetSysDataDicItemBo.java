package com.lframework.xingyun.template.inner.bo.system.dic.item;

import com.lframework.xingyun.template.inner.entity.SysDataDicItem;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetSysDataDicItemBo extends BaseBo<SysDataDicItem> {

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

  /**
   * 字典ID
   */
  @ApiModelProperty("字典ID")
  private String dicId;

  /**
   * 排序
   */
  @ApiModelProperty("排序")
  private Integer orderNo;


  public GetSysDataDicItemBo() {

  }

  public GetSysDataDicItemBo(SysDataDicItem dto) {

    super(dto);
  }
}
