package com.lframework.xingyun.sc.biz.service.retail;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.dto.retail.out.RetailOutSheetDetailLotDto;
import com.lframework.xingyun.sc.facade.entity.RetailOutSheetDetailLot;

public interface IRetailOutSheetDetailLotService extends BaseMpService<RetailOutSheetDetailLot> {

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
