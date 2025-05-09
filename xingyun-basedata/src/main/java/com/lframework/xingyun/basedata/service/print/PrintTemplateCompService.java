package com.lframework.xingyun.basedata.service.print;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.PrintTemplateComp;

import java.util.List;

public interface PrintTemplateCompService extends BaseMpService<PrintTemplateComp> {

    List<String> getCompJsonByTemplateId(Integer templateId);
}
