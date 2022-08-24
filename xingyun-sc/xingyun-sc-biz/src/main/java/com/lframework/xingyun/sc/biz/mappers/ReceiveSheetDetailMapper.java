package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.facade.entity.ReceiveSheetDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-09
 */
public interface ReceiveSheetDetailMapper extends BaseMapper<ReceiveSheetDetail> {

  /**
   * 根据收货单ID查询
   *
   * @param sheetId
   * @return
   */
  List<ReceiveSheetDetail> getBySheetId(String sheetId);

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
