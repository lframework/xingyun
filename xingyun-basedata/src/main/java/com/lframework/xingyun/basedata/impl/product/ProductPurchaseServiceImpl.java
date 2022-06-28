package com.lframework.xingyun.basedata.impl.product;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.ProductPurchase;
import com.lframework.xingyun.basedata.mappers.ProductPurchaseMapper;
import com.lframework.xingyun.basedata.service.product.IProductPurchaseService;
import com.lframework.xingyun.basedata.vo.product.purchase.CreateProductPurchaseVo;
import com.lframework.xingyun.basedata.vo.product.purchase.UpdateProductPurchaseVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductPurchaseServiceImpl extends
    BaseMpServiceImpl<ProductPurchaseMapper, ProductPurchase>
    implements IProductPurchaseService {

  @OpLog(type = OpLogType.OTHER, name = "设置商品采购价，ID：{}, 采购价：{}", params = {"#vo.id", "#vo.price"})
  @Transactional
  @Override
  public String create(CreateProductPurchaseVo vo) {

    ProductPurchase data = new ProductPurchase();
    data.setId(IdUtil.getId());
    if (!StringUtil.isBlank(vo.getId())) {
      data.setId(vo.getId());
    }

    data.setPrice(vo.getPrice());

    getBaseMapper().insert(data);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "设置商品采购价，ID：{}, 采购价：{}", params = {"#vo.id", "#vo.price"})
  @Transactional
  @Override
  public void update(UpdateProductPurchaseVo vo) {

    if (vo.getPrice() == null) {
      throw new InputErrorException("采购价不能为空！");
    }

    if (vo.getPrice().doubleValue() < 0D) {
      throw new InputErrorException("采购价必须大于0！");
    }

    getBaseMapper().deleteById(vo.getId());

    CreateProductPurchaseVo createVo = new CreateProductPurchaseVo();
    createVo.setId(vo.getId());
    createVo.setPrice(vo.getPrice());

    this.create(createVo);
  }
}
