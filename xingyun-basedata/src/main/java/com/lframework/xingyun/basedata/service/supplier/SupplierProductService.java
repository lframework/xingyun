package com.lframework.xingyun.basedata.service.supplier;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.SupplierProduct;
import com.lframework.xingyun.basedata.vo.supplier.product.CreateSupplierProductVo;
import com.lframework.xingyun.basedata.vo.supplier.product.QuerySupplierProductVo;
import com.lframework.xingyun.basedata.vo.supplier.product.UpdateSupplierProductVo;
import java.util.List;

public interface SupplierProductService extends BaseMpService<SupplierProduct> {

  /**
   * 查询列表
   */
  PageResult<SupplierProduct> query(Integer pageIndex, Integer pageSize, QuerySupplierProductVo vo);

  /**
   * 根据供应商ID查询关联商品
   */
  List<SupplierProduct> listBySupplierId(String supplierId);

  /**
   * 根据商品ID查询关联供应商
   */
  List<SupplierProduct> listByProductId(String productId);

  /**
   * 根据ID查询
   */
  SupplierProduct findById(String id);

  /**
   * 创建
   */
  String create(CreateSupplierProductVo vo);

  /**
   * 修改
   */
  void update(UpdateSupplierProductVo vo);

  /**
   * 删除
   */
  void deleteById(String id);
}