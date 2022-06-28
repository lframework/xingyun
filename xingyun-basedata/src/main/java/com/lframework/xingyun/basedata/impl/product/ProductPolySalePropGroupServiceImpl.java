package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.basedata.entity.ProductPolySalePropGroup;
import com.lframework.xingyun.basedata.mappers.ProductPolySalePropGroupMapper;
import com.lframework.xingyun.basedata.service.product.IProductPolySalePropGroupService;
import com.lframework.xingyun.basedata.vo.product.poly.saleprop.CreateProductPolySalePropGroupVo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductPolySalePropGroupServiceImpl extends
    BaseMpServiceImpl<ProductPolySalePropGroupMapper, ProductPolySalePropGroup> implements
    IProductPolySalePropGroupService {

  @Transactional
  @Override
  public void create(CreateProductPolySalePropGroupVo data) {
    if (CollectionUtil.isEmpty(data.getSalePropGroupIds())) {
      return;
    }

    int orderNo = 1;
    for (String salePropGroupId : data.getSalePropGroupIds()) {
      ProductPolySalePropGroup record = new ProductPolySalePropGroup();
      record.setId(IdUtil.getId());
      record.setPolyId(data.getPolyId());
      record.setSalePropGroupId(salePropGroupId);
      record.setOrderNo(orderNo);

      this.save(record);

      orderNo++;
    }
  }

  @Override
  public List<ProductPolySalePropGroup> getByPolyId(String polyId) {
    Wrapper<ProductPolySalePropGroup> queryWrapper = Wrappers.lambdaQuery(
        ProductPolySalePropGroup.class);
    return this.list(queryWrapper);
  }
}
