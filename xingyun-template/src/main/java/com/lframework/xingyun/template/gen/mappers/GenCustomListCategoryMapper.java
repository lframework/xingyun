package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.vo.custom.list.category.GenCustomListCategorySelectorVo;
import com.lframework.xingyun.template.gen.entity.GenCustomListCategory;
import com.lframework.starter.web.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 自定义列表分类 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface GenCustomListCategoryMapper extends BaseMapper<GenCustomListCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenCustomListCategory> query();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenCustomListCategory> selector(@Param("vo") GenCustomListCategorySelectorVo vo);
}
