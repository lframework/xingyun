package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.entity.GenDataObjCategory;
import com.lframework.xingyun.template.gen.vo.data.obj.category.GenDataObjCategorySelectorVo;
import com.lframework.starter.web.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据对象分类 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface GenDataObjCategoryMapper extends BaseMapper<GenDataObjCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenDataObjCategory> query();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenDataObjCategory> selector(@Param("vo") GenDataObjCategorySelectorVo vo);
}
