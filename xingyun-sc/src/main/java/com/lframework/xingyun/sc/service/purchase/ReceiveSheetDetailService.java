package com.lframework.xingyun.sc.service.purchase;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.sc.entity.ReceiveSheetDetail;
import java.math.BigDecimal;
import java.util.List;

public interface ReceiveSheetDetailService extends BaseMpService<ReceiveSheetDetail> {

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
   */
  void addReturnNum(String id, BigDecimal num);

  /**
   * 减少退货数量
   *
   * @param id
   * @param num
   */
  void subReturnNum(String id, BigDecimal num);
}
