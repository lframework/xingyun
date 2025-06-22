package com.lframework.xingyun.basedata.impl.print;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.PrintTemplateComp;
import com.lframework.xingyun.basedata.mappers.PrintTemplateCompMapper;
import com.lframework.xingyun.basedata.service.print.PrintTemplateCompService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PrintTemplateCompServiceImpl extends
    BaseMpServiceImpl<PrintTemplateCompMapper, PrintTemplateComp> implements
    PrintTemplateCompService {

  @Override
  public List<String> getCompJsonByTemplateId(Integer templateId) {

    Wrapper<PrintTemplateComp> queryWrapper = Wrappers.lambdaQuery(PrintTemplateComp.class)
        .eq(PrintTemplateComp::getTemplateId, templateId);
    List<PrintTemplateComp> datas = getBaseMpMapper().selectList(queryWrapper);

    return datas.stream().map(PrintTemplateComp::getCompJson).collect(Collectors.toList());
  }
}
