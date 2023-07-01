package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDetailLotDto;
import com.lframework.xingyun.sc.entity.SaleOutSheetDetailLot;

public interface SaleOutSheetDetailLotService extends BaseMpService<SaleOutSheetDetailLot> {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleOutSheetDetailLotDto findById(String id);

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
