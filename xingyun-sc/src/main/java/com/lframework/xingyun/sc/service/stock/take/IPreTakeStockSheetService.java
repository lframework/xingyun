package com.lframework.xingyun.sc.service.stock.take;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetFullDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetSelectorDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.QueryPreTakeStockSheetProductDto;
import com.lframework.xingyun.sc.entity.PreTakeStockSheet;
import com.lframework.xingyun.sc.vo.stock.take.pre.CreatePreTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.PreTakeStockSheetSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.QueryPreTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.UpdatePreTakeStockSheetVo;
import java.util.List;

/**
 * 预先盘点单 Service
 *
 * @author zmj
 */
public interface IPreTakeStockSheetService extends BaseMpService<PreTakeStockSheet> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<PreTakeStockSheetDto> query(Integer pageIndex, Integer pageSize,
      QueryPreTakeStockSheetVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<PreTakeStockSheetDto> query(QueryPreTakeStockSheetVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<PreTakeStockSheetSelectorDto> selector(Integer pageIndex, Integer pageSize,
      PreTakeStockSheetSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PreTakeStockSheetDto getById(String id);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PreTakeStockSheetFullDto getDetail(String id);

  /**
   * 根据预先盘点单、盘点任务查询商品信息
   *
   * @param id
   * @param planId
   * @return
   */
  List<QueryPreTakeStockSheetProductDto> getProducts(String id, String planId);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreatePreTakeStockSheetVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdatePreTakeStockSheetVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);

  /**
   * 根据ID批量删除
   *
   * @param ids
   */
  void batchDelete(List<String> ids);

}
