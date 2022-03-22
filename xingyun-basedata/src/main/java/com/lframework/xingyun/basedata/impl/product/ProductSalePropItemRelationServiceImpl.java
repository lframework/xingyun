package com.lframework.xingyun.basedata.impl.product;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.IdUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.ProductSalePropItemDto;
import com.lframework.xingyun.basedata.entity.ProductSalePropItemRelation;
import com.lframework.xingyun.basedata.mappers.ProductSalePropItemRelationMapper;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemRelationService;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.saleprop.CreateProductSalePropItemRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSalePropItemRelationServiceImpl implements IProductSalePropItemRelationService {

  @Autowired
  private ProductSalePropItemRelationMapper productSalePropItemRelationMapper;

  @Autowired
  private IProductService productService;

  @Autowired
  private IProductSalePropItemService productSalePropItemService;

  @Transactional
  @Override
  public void create(CreateProductSalePropItemRelationVo vo) {

    ProductDto product = productService.getById(vo.getProductId());
    if (product == null) {
      throw new DefaultClientException("商品不存在！");
    }

    int orderNo = 1;
    for (String salePropItemId : vo.getSalePropItemIds()) {

      ProductSalePropItemDto salePropItem = productSalePropItemService.getById(salePropItemId);
      if (salePropItem == null) {
        throw new DefaultClientException("销售属性不存在！");
      }

      ProductSalePropItemRelation data = new ProductSalePropItemRelation();
      data.setId(IdUtil.getId());
      data.setProductId(product.getId());
      data.setSalePropItemId(salePropItemId);
      data.setOrderNo(orderNo);

      productSalePropItemRelationMapper.insert(data);

      orderNo++;
    }
  }
}
