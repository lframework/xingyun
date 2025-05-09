package com.lframework.xingyun.basedata.service.print;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.PrintTemplate;
import com.lframework.xingyun.basedata.vo.print.CreatePrintTemplateVo;
import com.lframework.xingyun.basedata.vo.print.QueryPrintTemplateVo;
import com.lframework.xingyun.basedata.vo.print.UpdatePrintTemplateDemoDataVo;
import com.lframework.xingyun.basedata.vo.print.UpdatePrintTemplateSettingVo;
import com.lframework.xingyun.basedata.vo.print.UpdatePrintTemplateVo;
import java.util.List;

public interface PrintTemplateService extends BaseMpService<PrintTemplate> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<PrintTemplate> query(Integer pageIndex, Integer pageSize, QueryPrintTemplateVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<PrintTemplate> query(QueryPrintTemplateVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PrintTemplate findById(Integer id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  Integer create(CreatePrintTemplateVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdatePrintTemplateVo vo);

  /**
   * 保存设置
   *
   * @param vo
   */
  void updateSetting(UpdatePrintTemplateSettingVo vo);

  /**
   * 保存示例数据
   *
   * @param vo
   */
  void updateDemoData(UpdatePrintTemplateDemoDataVo vo);
}
