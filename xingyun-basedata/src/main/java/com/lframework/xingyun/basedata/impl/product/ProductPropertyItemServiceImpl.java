package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.mappers.ProductPropertyItemMapper;
import com.lframework.xingyun.basedata.service.product.ProductPropertyItemService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyService;
import com.lframework.xingyun.basedata.vo.product.property.item.CreateProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.QueryProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.UpdateProductPropertyItemVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductPropertyItemServiceImpl extends BaseMpServiceImpl<ProductPropertyItemMapper, ProductPropertyItem>
        implements ProductPropertyItemService {

    @Autowired
    private ProductPropertyService productPropertyService;

    @Override
    public PageResult<ProductPropertyItem> query(Integer pageIndex, Integer pageSize, QueryProductPropertyItemVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<ProductPropertyItem> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<ProductPropertyItem> query(QueryProductPropertyItemVo vo) {

        return getBaseMapper().query(vo);
    }

    @Override
    public List<ProductPropertyItem> getByPropertyId(String propertyId) {

        return getBaseMapper().getByPropertyId(propertyId);
    }

    @Cacheable(value = ProductPropertyItem.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
    @Override
    public ProductPropertyItem findById(String id) {

        return getBaseMapper().selectById(id);
    }

    @OpLog(type = DefaultOpLogType.OTHER, name = "新增商品属性值，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(CreateProductPropertyItemVo vo) {

        ProductProperty property = productPropertyService.findById(vo.getPropertyId());
        if (ObjectUtil.isNull(property)) {
            throw new DefaultClientException("属性不存在！");
        }

        Wrapper<ProductPropertyItem> checkWrapper = Wrappers.lambdaQuery(ProductPropertyItem.class)
                .eq(ProductPropertyItem::getPropertyId, vo.getPropertyId())
                .eq(ProductPropertyItem::getCode, vo.getCode());
        if (getBaseMapper().selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        Wrapper<ProductPropertyItem> checkNameWrapper = Wrappers.lambdaQuery(ProductPropertyItem.class)
                .eq(ProductPropertyItem::getPropertyId, vo.getPropertyId())
                .eq(ProductPropertyItem::getName, vo.getName());
        if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
            throw new DefaultClientException("名称重复，请重新输入！");
        }

        ProductPropertyItem data = new ProductPropertyItem();
        data.setId(IdUtil.getId());
        data.setCode(vo.getCode());
        data.setName(vo.getName());
        data.setPropertyId(vo.getPropertyId());
        data.setAvailable(Boolean.TRUE);
        data.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

        getBaseMapper().insert(data);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        return data.getId();
    }

    @OpLog(type = DefaultOpLogType.OTHER, name = "修改商品属性值，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UpdateProductPropertyItemVo vo) {

        ProductPropertyItem data = this.findById(vo.getId());
        if (data == null) {
            throw new DefaultClientException("属性值不存在！");
        }

        Wrapper<ProductPropertyItem> checkWrapper = Wrappers.lambdaQuery(ProductPropertyItem.class)
                .eq(ProductPropertyItem::getPropertyId, data.getPropertyId())
                .eq(ProductPropertyItem::getCode, vo.getCode()).ne(ProductPropertyItem::getId, vo.getId());
        if (getBaseMapper().selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        Wrapper<ProductPropertyItem> checkNameWrapper = Wrappers.lambdaQuery(ProductPropertyItem.class)
                .eq(ProductPropertyItem::getPropertyId, data.getPropertyId())
                .eq(ProductPropertyItem::getName, vo.getName()).ne(ProductPropertyItem::getId, vo.getId());
        if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
            throw new DefaultClientException("名称重复，请重新输入！");
        }

        Wrapper<ProductPropertyItem> updateWrapper = Wrappers.lambdaUpdate(ProductPropertyItem.class)
                .set(ProductPropertyItem::getCode, vo.getCode()).set(ProductPropertyItem::getName, vo.getName())
                .set(ProductPropertyItem::getAvailable, vo.getAvailable()).eq(ProductPropertyItem::getId, vo.getId())
                .set(ProductPropertyItem::getDescription,
                        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
        getBaseMapper().update(updateWrapper);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);
    }

    @CacheEvict(value = ProductPropertyItem.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
    @Override
    public void cleanCacheByKey(Serializable key) {

    }
}
