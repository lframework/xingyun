package com.lframework.xingyun.basedata.biz.impl.product;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.facade.entity.ProductSale;
import com.lframework.xingyun.basedata.biz.mappers.ProductSaleMapper;
import com.lframework.xingyun.basedata.biz.service.product.IProductSaleService;
import com.lframework.xingyun.basedata.facade.vo.product.sale.CreateProductSaleVo;
import com.lframework.xingyun.basedata.facade.vo.product.sale.UpdateProductSaleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSaleServiceImpl extends BaseMpServiceImpl<ProductSaleMapper, ProductSale>
    implements IProductSaleService {

  @OpLog(type = OpLogType.OTHER, name = "设置商品销售价，ID：{}, 销售价：{}", params = {"#vo.id", "#vo.price"})
  @Transactional
  @Override
  public String create(CreateProductSaleVo vo) {

    ProductSale data = new ProductSale();
    data.setId(IdUtil.getId());
    if (!StringUtil.isBlank(vo.getId())) {
      data.setId(vo.getId());
    }

    data.setPrice(vo.getPrice());

    getBaseMapper().insert(data);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "设置商品销售价，ID：{}, 销售价：{}", params = {"#vo.id", "#vo.price"})
  @Transactional
  @Override
  public void update(UpdateProductSaleVo vo) {

    if (vo.getPrice() == null) {
      throw new InputErrorException("销售价不能为空！");
    }

    if (vo.getPrice().doubleValue() < 0D) {
      throw new InputErrorException("销售价必须大于0！");
    }

    getBaseMapper().deleteById(vo.getId());

    CreateProductSaleVo createVo = new CreateProductSaleVo();
    createVo.setId(vo.getId());
    createVo.setPrice(vo.getPrice());

    this.create(createVo);
  }
}
