package com.lframework.xingyun.sc.service.logistics;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.dto.logistics.LogisticsSheetBizOrderDto;
import com.lframework.xingyun.sc.dto.logistics.LogisticsSheetFullDto;
import com.lframework.xingyun.sc.entity.LogisticsSheet;
import com.lframework.xingyun.sc.vo.logistics.CreateLogisticsSheetVo;
import com.lframework.xingyun.sc.vo.logistics.DeliveryLogisticsSheetVo;
import com.lframework.xingyun.sc.vo.logistics.LogisticsSheetSelectorVo;
import com.lframework.xingyun.sc.vo.logistics.QueryLogisticsSheetBizOrderVo;
import com.lframework.xingyun.sc.vo.logistics.QueryLogisticsSheetVo;
import com.lframework.xingyun.sc.vo.logistics.UpdateLogisticsSheetVo;
import java.util.List;

public interface LogisticsSheetService extends BaseMpService<LogisticsSheet> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<LogisticsSheet> query(Integer pageIndex, Integer pageSize, QueryLogisticsSheetVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<LogisticsSheet> query(QueryLogisticsSheetVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<LogisticsSheet> selector(Integer pageIndex, Integer pageSize,
      LogisticsSheetSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  LogisticsSheetFullDto getDetail(String id);

  /**
   * 查询业务单据列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<LogisticsSheetBizOrderDto> queryBizOrder(Integer pageIndex, Integer pageSize,
      QueryLogisticsSheetBizOrderVo vo);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateLogisticsSheetVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateLogisticsSheetVo vo);

  /**
   * 发货
   *
   * @param vo
   */
  void delivery(DeliveryLogisticsSheetVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);

  /**
   * 根据IDs删除
   *
   * @param ids
   */
  void deleteByIds(List<String> ids);
}
