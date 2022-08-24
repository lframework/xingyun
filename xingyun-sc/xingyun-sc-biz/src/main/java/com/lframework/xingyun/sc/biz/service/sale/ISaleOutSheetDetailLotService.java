package com.lframework.xingyun.sc.biz.service.sale;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.dto.sale.out.SaleOutSheetDetailLotDto;
import com.lframework.xingyun.sc.facade.entity.SaleOutSheetDetailLot;

public interface ISaleOutSheetDetailLotService extends BaseMpService<SaleOutSheetDetailLot> {

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
