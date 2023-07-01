package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.sc.entity.SaleOutSheetDetail;
import java.math.BigDecimal;
import java.util.List;

public interface SaleOutSheetDetailService extends BaseMpService<SaleOutSheetDetail> {

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

  /**
   * 根据出库单ID查询商品总重量
   * @param sheetIds
   * @return
   */
  BigDecimal getTotalWeightBySheetIds(List<String> sheetIds);

  /**
   * 根据出库单ID查询商品总体积
   * @param sheetIds
   * @return
   */
  BigDecimal getTotalVolumeBySheetIds(List<String> sheetIds);
}
