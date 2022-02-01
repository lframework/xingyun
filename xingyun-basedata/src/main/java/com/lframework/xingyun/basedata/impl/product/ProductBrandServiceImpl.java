package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.*;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.xingyun.basedata.dto.product.brand.ProductBrandDto;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.mappers.ProductBrandMapper;
import com.lframework.xingyun.basedata.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.vo.product.brand.CreateProductBrandVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandSelectorVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandVo;
import com.lframework.xingyun.basedata.vo.product.brand.UpdateProductBrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class ProductBrandServiceImpl implements IProductBrandService {

    @Autowired
    private ProductBrandMapper productBrandMapper;

    @Override
    public PageResult<ProductBrandDto> query(Integer pageIndex, Integer pageSize, QueryProductBrandVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<ProductBrandDto> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<ProductBrandDto> query(QueryProductBrandVo vo) {

        return productBrandMapper.query(vo);
    }

    @Override
    public PageResult<ProductBrandDto> selector(Integer pageIndex, Integer pageSize, QueryProductBrandSelectorVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<ProductBrandDto> datas = productBrandMapper.selector(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Cacheable(value = ProductBrandDto.CACHE_NAME, key = "#id", unless = "#result == null")
    @Override
    public ProductBrandDto getById(String id) {

        return productBrandMapper.getById(id);
    }

    @OpLog(type = OpLogType.OTHER, name = "停用商品品牌，ID：{}", params = "#ids", loopFormat = true)
    @Transactional
    @Override
    public void batchUnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        Wrapper<ProductBrand> updateWrapper = Wrappers.lambdaUpdate(ProductBrand.class)
                .set(ProductBrand::getAvailable, Boolean.FALSE).in(ProductBrand::getId, ids);
        productBrandMapper.update(updateWrapper);

        IProductBrandService thisService = getThis(this.getClass());
        for (String id : ids) {
            thisService.cleanCacheByKey(id);
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "启用商品品牌，ID：{}", params = "#ids", loopFormat = true)
    @Transactional
    @Override
    public void batchEnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        Wrapper<ProductBrand> updateWrapper = Wrappers.lambdaUpdate(ProductBrand.class)
                .set(ProductBrand::getAvailable, Boolean.TRUE).in(ProductBrand::getId, ids);
        productBrandMapper.update(updateWrapper);

        IProductBrandService thisService = getThis(this.getClass());
        for (String id : ids) {
            thisService.cleanCacheByKey(id);
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "新增商品品牌，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional
    @Override
    public String create(CreateProductBrandVo vo) {

        Wrapper<ProductBrand> checkCodeWrapper = Wrappers.lambdaQuery(ProductBrand.class)
                .eq(ProductBrand::getCode, vo.getCode());
        if (productBrandMapper.selectCount(checkCodeWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        Wrapper<ProductBrand> checkNameWrapper = Wrappers.lambdaQuery(ProductBrand.class)
                .eq(ProductBrand::getName, vo.getName());
        if (productBrandMapper.selectCount(checkNameWrapper) > 0) {
            throw new DefaultClientException("名称重复，请重新输入！");
        }

        ProductBrand data = new ProductBrand();
        data.setId(IdUtil.getId());
        data.setCode(vo.getCode());
        data.setName(vo.getName());
        data.setShortName(StringUtil.isBlank(vo.getShortName()) ? StringPool.EMPTY_STR : vo.getShortName());
        data.setIntroduction(StringUtil.isBlank(vo.getIntroduction()) ? StringPool.EMPTY_STR : vo.getIntroduction());
        data.setAvailable(Boolean.TRUE);
        data.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

        productBrandMapper.insert(data);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        return data.getId();
    }

    @OpLog(type = OpLogType.OTHER, name = "修改商品品牌，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional
    @Override
    public void update(UpdateProductBrandVo vo) {

        ProductBrand data = productBrandMapper.selectById(vo.getId());
        if (ObjectUtil.isNull(data)) {
            throw new DefaultClientException("品牌不存在！");
        }

        Wrapper<ProductBrand> checkWrapper = Wrappers.lambdaQuery(ProductBrand.class)
                .eq(ProductBrand::getCode, vo.getCode()).ne(ProductBrand::getId, vo.getId());
        if (productBrandMapper.selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        Wrapper<ProductBrand> checkNameWrapper = Wrappers.lambdaQuery(ProductBrand.class)
                .eq(ProductBrand::getName, vo.getName()).ne(ProductBrand::getId, vo.getId());
        if (productBrandMapper.selectCount(checkNameWrapper) > 0) {
            throw new DefaultClientException("名称重复，请重新输入！");
        }

        LambdaUpdateWrapper<ProductBrand> updateWrapper = Wrappers.lambdaUpdate(ProductBrand.class)
                .set(ProductBrand::getCode, vo.getCode()).set(ProductBrand::getName, vo.getName())
                .set(ProductBrand::getShortName,
                        StringUtil.isBlank(vo.getShortName()) ? StringPool.EMPTY_STR : vo.getShortName())
                .set(ProductBrand::getIntroduction,
                        StringUtil.isBlank(vo.getIntroduction()) ? StringPool.EMPTY_STR : vo.getIntroduction())
                .set(ProductBrand::getAvailable, vo.getAvailable()).set(ProductBrand::getDescription,
                        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
                .eq(ProductBrand::getId, vo.getId());

        productBrandMapper.update(updateWrapper);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        IProductBrandService thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(data.getId());
    }

    @CacheEvict(value = ProductBrandDto.CACHE_NAME, key = "#key")
    @Override
    public void cleanCacheByKey(String key) {

    }
}
