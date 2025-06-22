package com.lframework.xingyun.sc.service.stock;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.dto.stock.ProductStockChangeDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import com.lframework.xingyun.sc.vo.stock.QueryProductStockVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;
import java.util.List;

public interface ProductStockService extends BaseMpService<ProductStock> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<ProductStock> query(Integer pageIndex, Integer pageSize, QueryProductStockVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductStock> query(QueryProductStockVo vo);

  /**
   * 根据商品ID、仓库ID查询
   *
   * @param productId
   * @param scId
   * @return
   */
  ProductStock getByProductIdAndScId(String productId, String scId);

  /**
   * 根据商品ID、仓库ID查询
   *
   * @param productIds
   * @param scId
   * @return
   */
  List<ProductStock> getByProductIdsAndScId(List<String> productIds, String scId,
      Integer productType);

  /**
   * 入库
   *
   * @param vo
   */
  ProductStockChangeDto addStock(AddProductStockVo vo);

  /**
   * 出库
   *
   * @param vo
   */
  ProductStockChangeDto subStock(SubProductStockVo vo);
}
