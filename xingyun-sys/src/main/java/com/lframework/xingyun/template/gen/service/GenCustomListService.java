package com.lframework.xingyun.template.gen.service;

import com.lframework.xingyun.template.gen.entity.GenCustomList;
import com.lframework.xingyun.template.gen.vo.custom.list.CreateGenCustomListVo;
import com.lframework.xingyun.template.gen.vo.custom.list.GenCustomListSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.list.QueryGenCustomListVo;
import com.lframework.xingyun.template.gen.vo.custom.list.UpdateGenCustomListVo;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import java.util.List;

public interface GenCustomListService extends BaseMpService<GenCustomList> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenCustomList> query(Integer pageIndex, Integer pageSize, QueryGenCustomListVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenCustomList> query(QueryGenCustomListVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenCustomList> selector(Integer pageIndex, Integer pageSize,
      GenCustomListSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenCustomList findById(String id);

  /**
   * 新增
   *
   * @param data
   * @return
   */
  String create(CreateGenCustomListVo data);

  /**
   * 修改
   *
   * @param data
   */
  void update(UpdateGenCustomListVo data);

  /**
   * 根据ID删除
   * @param id
   */
  void delete(String id);

  /**
   * 启用
   *
   * @param id
   */
  void enable(String id);

  /**
   * 停用
   *
   * @param id
   */
  void unable(String id);

  /**
   * 查询所有关联了数据对象的自定义列表ID
   * @return
   */
  List<String> getRelaGenDataObjIds(String objId);

  /**
   * 查询所有关联了数据实体的自定义列表ID
   * @return
   */
  List<String> getRelaGenDataEntityIds(String entityId);
}
