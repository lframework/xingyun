package com.lframework.xingyun.template.gen.service;

import com.lframework.xingyun.template.gen.entity.GenCustomForm;
import com.lframework.xingyun.template.gen.vo.custom.form.CreateGenCustomFormVo;
import com.lframework.xingyun.template.gen.vo.custom.form.GenCustomFormSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.form.QueryGenCustomFormVo;
import com.lframework.xingyun.template.gen.vo.custom.form.UpdateGenCustomFormVo;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import java.util.List;

public interface GenCustomFormService extends BaseMpService<GenCustomForm> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenCustomForm> query(Integer pageIndex, Integer pageSize,
      QueryGenCustomFormVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenCustomForm> query(QueryGenCustomFormVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenCustomForm> selector(Integer pageIndex, Integer pageSize,
      GenCustomFormSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenCustomForm findById(String id);

  /**
   * 新增
   *
   * @param data
   * @return
   */
  String create(CreateGenCustomFormVo data);

  /**
   * 修改
   *
   * @param data
   */
  void update(UpdateGenCustomFormVo data);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void delete(String id);

  /**
   * 批量删除
   *
   * @param ids
   */
  void batchDelete(List<String> ids);

  /**
   * 批量启用
   *
   * @param ids
   */
  void batchEnable(List<String> ids);

  /**
   * 批量停用
   *
   * @param ids
   */
  void batchUnable(List<String> ids);
}
