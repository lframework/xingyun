package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterSelectorVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-07
 */
public interface StoreCenterMapper extends BaseMapper<StoreCenter> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<StoreCenterDto> query(@Param("vo") QueryStoreCenterVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  StoreCenterDto getById(String id);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<StoreCenterDto> selector(@Param("vo") QueryStoreCenterSelectorVo vo);
}
