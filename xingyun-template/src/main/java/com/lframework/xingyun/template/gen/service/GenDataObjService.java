package com.lframework.xingyun.template.gen.service;

import com.lframework.xingyun.template.gen.entity.GenDataObj;
import com.lframework.xingyun.template.gen.vo.data.obj.CreateGenDataObjVo;
import com.lframework.xingyun.template.gen.vo.data.obj.GenDataObjSelectorVo;
import com.lframework.xingyun.template.gen.vo.data.obj.QueryGenDataObjVo;
import com.lframework.xingyun.template.gen.vo.data.obj.UpdateGenDataObjVo;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import java.util.List;

public interface GenDataObjService extends BaseMpService<GenDataObj> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenDataObj> query(Integer pageIndex, Integer pageSize, QueryGenDataObjVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenDataObj> query(QueryGenDataObjVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenDataObj> selector(Integer pageIndex, Integer pageSize, GenDataObjSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenDataObj findById(String id);

  /**
   * 新增
   *
   * @param data
   * @return
   */
  String create(CreateGenDataObjVo data);

  /**
   * 修改
   *
   * @param data
   */
  void update(UpdateGenDataObjVo data);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void delete(String id);

  /**
   * 根据ID批量删除
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
   * 查询所有关联了数据实体的数据对象ID
   * @return
   */
  List<String> getRelaGenDataEntityIds(String entityId);
}
