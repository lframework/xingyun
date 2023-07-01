package com.lframework.xingyun.template.gen.service;

import com.lframework.xingyun.template.gen.entity.GenDataEntityCategory;
import com.lframework.xingyun.template.gen.vo.data.entity.category.CreateGenDataEntityCategoryVo;
import com.lframework.xingyun.template.gen.vo.data.entity.category.GenDataEntityCategorySelectorVo;
import com.lframework.xingyun.template.gen.vo.data.entity.category.UpdateGenDataEntityCategoryVo;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import java.util.List;

/**
 * 数据实体分类 Service
 *
 * @author zmj
 */
public interface GenDataEntityCategoryService extends BaseMpService<GenDataEntityCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenDataEntityCategory> queryList();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<GenDataEntityCategory> selector(Integer pageIndex, Integer pageSize,
      GenDataEntityCategorySelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenDataEntityCategory findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateGenDataEntityCategoryVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateGenDataEntityCategoryVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
