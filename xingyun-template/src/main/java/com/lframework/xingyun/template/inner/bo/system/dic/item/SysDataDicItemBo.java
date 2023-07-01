package com.lframework.xingyun.template.inner.bo.system.dic.item;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.xingyun.template.inner.entity.SysDataDic;
import com.lframework.xingyun.template.inner.entity.SysDataDicItem;
import com.lframework.xingyun.template.inner.service.system.SysDataDicService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysDataDicItemBo extends BaseBo<SysDataDicItem> {

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

  public SysDataDicItemBo() {

  }

  public SysDataDicItemBo(SysDataDicItem dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysDataDicItem dto) {
    SysDataDicService sysDataDicService = ApplicationUtil.getBean(SysDataDicService.class);
    SysDataDic dataDic = sysDataDicService.findById(dto.getDicId());
    this.id = dataDic.getCode() + StringPool.DATA_DIC_SPLIT + this.code;
  }
}
