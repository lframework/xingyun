package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.entity.GenCustomForm;
import com.lframework.xingyun.template.gen.vo.custom.form.GenCustomFormSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.form.QueryGenCustomFormVo;
import com.lframework.starter.web.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 自定义表单 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2022-09-17
 */
public interface GenCustomFormMapper extends BaseMapper<GenCustomForm> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenCustomForm> query(@Param("vo") QueryGenCustomFormVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenCustomForm> selector(@Param("vo") GenCustomFormSelectorVo vo);

}
