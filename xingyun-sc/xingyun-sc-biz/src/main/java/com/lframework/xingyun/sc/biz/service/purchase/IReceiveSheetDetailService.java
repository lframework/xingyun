package com.lframework.xingyun.sc.biz.service.purchase;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.entity.ReceiveSheetDetail;
import java.util.List;

public interface IReceiveSheetDetailService extends BaseMpService<ReceiveSheetDetail> {

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
  void addReturnNum(String id, Integer num);

  /**
   * 减少退货数量
   *
   * @param id
   * @param num
   */
  void subReturnNum(String id, Integer num);
}
