package com.lframework.xingyun.produce.service.assemble;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.produce.entity.AssembleSheetDetail;
import java.util.List;

/**
 * 组装单明细 Service
 *
 * @author zmj
 */
public interface AssembleSheetDetailService extends BaseMpService<AssembleSheetDetail> {

  /**
   * 根据单据ID查询明细
   */
  List<AssembleSheetDetail> listBySheetId(String sheetId);

  /**
   * 根据单据ID删除明细
   */
  void deleteBySheetId(String sheetId);
}
