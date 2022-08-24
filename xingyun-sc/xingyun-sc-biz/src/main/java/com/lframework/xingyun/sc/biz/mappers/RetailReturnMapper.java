package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.facade.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.facade.entity.RetailReturn;
import com.lframework.xingyun.sc.facade.vo.retail.returned.QueryRetailReturnVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-11-04
 */
public interface RetailReturnMapper extends BaseMapper<RetailReturn> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<RetailReturn> query(@Param("vo") QueryRetailReturnVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  RetailReturnFullDto getDetail(String id);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<RetailReturnFullDto> queryFulls(@Param("vo") QueryRetailReturnVo vo);
}
