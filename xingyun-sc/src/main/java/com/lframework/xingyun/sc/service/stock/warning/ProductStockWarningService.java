package com.lframework.xingyun.sc.service.stock.warning;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.sc.entity.ProductStockWarning;
import com.lframework.xingyun.sc.vo.stock.warning.CreateProductStockWarningVo;
import com.lframework.xingyun.sc.vo.stock.warning.QueryProductStockWarningVo;
import com.lframework.xingyun.sc.vo.stock.warning.UpdateProductStockWarningVo;
import java.util.List;

public interface ProductStockWarningService extends BaseMpService<ProductStockWarning> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<ProductStockWarning> query(Integer pageIndex, Integer pageSize,
      QueryProductStockWarningVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductStockWarning> query(QueryProductStockWarningVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductStockWarning findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateProductStockWarningVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateProductStockWarningVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);

  /**
   * 根据ID删除
   *
   * @param ids
   */
  void deleteByIds(List<String> ids);
}
