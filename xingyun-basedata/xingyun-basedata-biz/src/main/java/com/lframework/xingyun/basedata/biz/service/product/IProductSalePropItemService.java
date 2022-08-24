package com.lframework.xingyun.basedata.biz.service.product;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropItem;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.item.CreateProductSalePropItemVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.item.QueryProductSalePropItemSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.item.QueryProductSalePropItemVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.item.UpdateProductSalePropItemVo;
import java.util.List;

public interface IProductSalePropItemService extends BaseMpService<ProductSalePropItem> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<ProductSalePropItem> query(Integer pageIndex, Integer pageSize,
      QueryProductSalePropItemVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<ProductSalePropItem> selector(Integer pageIndex, Integer pageSize,
      QueryProductSalePropItemSelectorVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductSalePropItem> query(QueryProductSalePropItemVo vo);

  /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    ProductSalePropItem findById(String id);

    /**
     * 创建
     *
     * @param vo
     * @return
     */
    String create(CreateProductSalePropItemVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void update(UpdateProductSalePropItemVo vo);

    /**
     * 根据销售属性组ID查询启用的销售属性
     *
     * @param groupId
     * @return
     */
    List<ProductSalePropItem> getEnablesByGroupId(String groupId);
}
