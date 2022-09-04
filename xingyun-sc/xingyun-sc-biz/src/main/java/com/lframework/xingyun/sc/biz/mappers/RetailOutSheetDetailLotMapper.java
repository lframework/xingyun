package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.facade.dto.retail.out.RetailOutSheetDetailLotDto;
import com.lframework.xingyun.sc.facade.entity.RetailOutSheetDetailLot;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-11-03
 */
public interface RetailOutSheetDetailLotMapper extends BaseMapper<RetailOutSheetDetailLot> {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  RetailOutSheetDetailLotDto findById(String id);

  /**
   * 增加退货数量
   *
   * @param id
   * @param num
   * @return
   */
  int addReturnNum(@Param("id") String id, @Param("num") Integer num);

  /**
   * 减少退货数量
   *
   * @param id
   * @param num
   * @return
   */
  int subReturnNum(@Param("id") String id, @Param("num") Integer num);
}