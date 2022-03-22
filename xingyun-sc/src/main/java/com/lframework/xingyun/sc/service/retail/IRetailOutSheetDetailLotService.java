package com.lframework.xingyun.sc.service.retail;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDetailLotDto;

public interface IRetailOutSheetDetailLotService extends BaseService {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  RetailOutSheetDetailLotDto getById(String id);

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
