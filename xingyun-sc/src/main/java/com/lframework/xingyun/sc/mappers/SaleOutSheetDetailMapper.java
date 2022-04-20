package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.entity.SaleOutSheetDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-26
 */
public interface SaleOutSheetDetailMapper extends BaseMapper<SaleOutSheetDetail> {

  /**
   * 根据出库单ID查询
   *
   * @param sheetId
   * @return
   */
  List<SaleOutSheetDetail> getBySheetId(String sheetId);

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
