package com.lframework.xingyun.template.gen.service;

import com.lframework.xingyun.template.gen.entity.GenCustomSelector;
import com.lframework.xingyun.template.gen.vo.custom.selector.CreateGenCustomSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.selector.GenCustomSelectorSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.selector.QueryGenCustomSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.selector.UpdateGenCustomSelectorVo;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import java.util.List;

public interface GenCustomSelectorService extends BaseMpService<GenCustomSelector> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenCustomSelector> query(Integer pageIndex, Integer pageSize,
      QueryGenCustomSelectorVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenCustomSelector> query(QueryGenCustomSelectorVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenCustomSelector> selector(Integer pageIndex, Integer pageSize,
      GenCustomSelectorSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenCustomSelector findById(String id);

  /**
   * 新增
   *
   * @param data
   * @return
   */
  String create(CreateGenCustomSelectorVo data);

  /**
   * 修改
   *
   * @param data
   */
  void update(UpdateGenCustomSelectorVo data);

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

  /**
   * 查询所有关联了自定义列表的自定义选择器ID
   *
   * @return
   */
  List<String> getRelaGenCustomListIds(String customListId);
}
