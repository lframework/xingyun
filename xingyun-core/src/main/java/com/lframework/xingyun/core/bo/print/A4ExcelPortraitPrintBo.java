package com.lframework.xingyun.core.bo.print;

import com.lframework.starter.web.bo.BasePrintBo;
import com.lframework.starter.web.bo.BasePrintDataBo;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Excel文件的A4纸纵向打印的默认设置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class A4ExcelPortraitPrintBo<T extends BasePrintDataBo<? extends BaseDto>> extends
    BasePrintBo<T> {

  public A4ExcelPortraitPrintBo(String templateName) {

    this(templateName, null);
  }

  public A4ExcelPortraitPrintBo(String templateName, T data) {

    super(templateName, data);

    this.setOrient(2);
    this.setPageHeight(2970);
    this.setPageWidth(2100);
    this.setPageName("A4");

    this.setMarginTop(19.1D);
    this.setMarginRight(17.8D);
    this.setMarginBottom(19.1D);
    this.setMarginLeft(17.8D);
  }
}
