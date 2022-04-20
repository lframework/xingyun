package com.lframework.xingyun.sc.service.stock;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.dto.stock.ProductLotWithStockDto;
import com.lframework.xingyun.sc.entity.ProductLot;
import com.lframework.xingyun.sc.vo.stock.lot.CreateProductLotVo;
import com.lframework.xingyun.sc.vo.stock.lot.QueryProductLotVo;
import java.util.List;

public interface IProductLotService extends BaseMpService<ProductLot> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<ProductLotWithStockDto> query(Integer pageIndex, Integer pageSize,
      QueryProductLotVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductLotWithStockDto> query(QueryProductLotVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductLot findById(String id);

  /**
   * 创建
   *
   * @param vo
   */
  String create(CreateProductLotVo vo);

  /**
   * 查询末次采购入库的批次信息
   *
   * @param productId
   * @param scId
   * @param supplierId null表示不限制供应商
   * @return null表示没有进行过采购入库
   */
  ProductLotWithStockDto getLastPurchaseLot(String productId, String scId, String supplierId);
}
