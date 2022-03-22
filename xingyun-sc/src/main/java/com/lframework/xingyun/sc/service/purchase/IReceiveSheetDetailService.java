package com.lframework.xingyun.sc.service.purchase;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetDetailDto;
import java.util.List;

public interface IReceiveSheetDetailService extends BaseService {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ReceiveSheetDetailDto getById(String id);

  /**
   * 根据收货单ID查询
   *
   * @param sheetId
   * @return
   */
  List<ReceiveSheetDetailDto> getBySheetId(String sheetId);

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
