package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.mappers.ProductBrandMapper;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.vo.product.brand.CreateProductBrandVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandSelectorVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandVo;
import com.lframework.xingyun.basedata.vo.product.brand.UpdateProductBrandVo;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductBrandServiceImpl extends BaseMpServiceImpl<ProductBrandMapper, ProductBrand>
        implements ProductBrandService {

    @Override
    public PageResult<ProductBrand> query(Integer pageIndex, Integer pageSize, QueryProductBrandVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<ProductBrand> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<ProductBrand> query(QueryProductBrandVo vo) {

        return getBaseMapper().query(vo);
    }

    @Override
    public PageResult<ProductBrand> selector(Integer pageIndex, Integer pageSize, QueryProductBrandSelectorVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<ProductBrand> datas = getBaseMapper().selector(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Cacheable(value = ProductBrand.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
    @Override
    public ProductBrand findById(String id) {

        return getBaseMapper().selectById(id);
    }

    @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "停用商品品牌，ID：{}", params = "#ids", loopFormat = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchUnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        Wrapper<ProductBrand> updateWrapper = Wrappers.lambdaUpdate(ProductBrand.class)
                .set(ProductBrand::getAvailable, Boolean.FALSE).in(ProductBrand::getId, ids);
        getBaseMapper().update(updateWrapper);
    }

    @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "启用商品品牌，ID：{}", params = "#ids", loopFormat = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchEnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        Wrapper<ProductBrand> updateWrapper = Wrappers.lambdaUpdate(ProductBrand.class)
                .set(ProductBrand::getAvailable, Boolean.TRUE).in(ProductBrand::getId, ids);
        getBaseMapper().update(updateWrapper);
    }

    @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "新增商品品牌，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(CreateProductBrandVo vo) {

        Wrapper<ProductBrand> checkCodeWrapper = Wrappers.lambdaQuery(ProductBrand.class)
                .eq(ProductBrand::getCode, vo.getCode());
        if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        Wrapper<ProductBrand> checkNameWrapper = Wrappers.lambdaQuery(ProductBrand.class)
                .eq(ProductBrand::getName, vo.getName());
        if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
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
        if (!StringUtil.isBlank(vo.getLogo())) {
            data.setLogo(vo.getLogo());
        }

        getBaseMapper().insert(data);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        return data.getId();
    }

    @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "修改商品品牌，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UpdateProductBrandVo vo) {

        ProductBrand data = getBaseMapper().selectById(vo.getId());
        if (ObjectUtil.isNull(data)) {
            throw new DefaultClientException("品牌不存在！");
        }

        Wrapper<ProductBrand> checkWrapper = Wrappers.lambdaQuery(ProductBrand.class)
                .eq(ProductBrand::getCode, vo.getCode()).ne(ProductBrand::getId, vo.getId());
        if (getBaseMapper().selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        Wrapper<ProductBrand> checkNameWrapper = Wrappers.lambdaQuery(ProductBrand.class)
                .eq(ProductBrand::getName, vo.getName()).ne(ProductBrand::getId, vo.getId());
        if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
            throw new DefaultClientException("名称重复，请重新输入！");
        }

        LambdaUpdateWrapper<ProductBrand> updateWrapper = Wrappers.lambdaUpdate(ProductBrand.class)
                .set(ProductBrand::getCode, vo.getCode()).set(ProductBrand::getName, vo.getName())
                .set(ProductBrand::getShortName,
                        StringUtil.isBlank(vo.getShortName()) ? StringPool.EMPTY_STR : vo.getShortName())
                .set(ProductBrand::getLogo, StringUtil.isBlank(vo.getLogo()) ? null : vo.getLogo())
                .set(ProductBrand::getIntroduction,
                        StringUtil.isBlank(vo.getIntroduction()) ? StringPool.EMPTY_STR : vo.getIntroduction())
                .set(ProductBrand::getAvailable, vo.getAvailable()).set(ProductBrand::getDescription,
                        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
                .eq(ProductBrand::getId, vo.getId());

        getBaseMapper().update(updateWrapper);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);
    }

    @CacheEvict(value = ProductBrand.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
    @Override
    public void cleanCacheByKey(Serializable key) {

    }
}
