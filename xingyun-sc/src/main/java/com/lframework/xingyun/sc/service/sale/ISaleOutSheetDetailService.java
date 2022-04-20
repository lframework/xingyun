package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.entity.SaleOutSheetDetail;
import java.util.List;

public interface ISaleOutSheetDetailService extends BaseMpService<SaleOutSheetDetail> {

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
   */
  void addReturnNum(String id, Integer num);

  /**
   * 减少退货数量
   *
   * @param id
   * @param num
   */
  void subReturnNum(String id, Integer num);
}
