package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.ClientException;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.basedata.dto.product.brand.ProductBrandDto;
import com.lframework.xingyun.basedata.dto.product.category.ProductCategoryDto;
import com.lframework.xingyun.basedata.dto.product.poly.ProductPolyDto;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyDto;
import com.lframework.xingyun.basedata.dto.product.property.item.ProductPropertyItemDto;
import com.lframework.xingyun.basedata.entity.ProductPoly;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.mappers.ProductPolyMapper;
import com.lframework.xingyun.basedata.service.product.*;
import com.lframework.xingyun.basedata.vo.product.info.CreateProductVo;
import com.lframework.xingyun.basedata.vo.product.poly.CreateProductPolyVo;
import com.lframework.xingyun.basedata.vo.product.poly.property.CreateProductPolyPropertyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductPolyServiceImpl implements IProductPolyService {

    @Autowired
    private ProductPolyMapper productPolyMapper;

    @Autowired
    private IProductPolyPropertyService productPolyPropertyService;

    @Autowired
    private IProductCategoryService productCategoryService;

    @Autowired
    private IProductBrandService productBrandService;

    @Autowired
    private IProductPropertyService productPropertyService;

    @Autowired
    private IProductPropertyItemService productPropertyItemService;

    @Autowired
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Cacheable(value = ProductPolyDto.CACHE_NAME, key = "#id", unless = "#result == null")
    @Override
    public ProductPolyDto getById(String id) {

        ProductPolyDto data = productPolyMapper.getById(id);
        if (data == null) {
            return data;
        }

        return convertDto(data);
    }

    @Override
    public List<String> getIdNotInPolyProperty(String propertyId) {

        return productPolyMapper.getIdNotInPolyProperty(propertyId);
    }

    @Override
    public List<String> getIdByCategoryId(String categoryId) {

        return productPolyMapper.getIdByCategoryId(categoryId);
    }

    @OpLog(type = OpLogType.OTHER, name = "新增商品SPU，ID：{}, 货号：{}", params = {"#id", "#code"})
    @Transactional
    @Override
    public String create(CreateProductPolyVo vo) {

        Wrapper<ProductPoly> checkCodeWrapper = Wrappers.lambdaQuery(ProductPoly.class)
                .eq(ProductPoly::getCode, vo.getCode());
        if (productPolyMapper.selectCount(checkCodeWrapper) > 0) {
            throw new DefaultClientException("商品货号重复，请重新输入！");
        }

        Wrapper<ProductPoly> checkNameWrapper = Wrappers.lambdaQuery(ProductPoly.class)
                .eq(ProductPoly::getName, vo.getName());
        if (productPolyMapper.selectCount(checkNameWrapper) > 0) {
            throw new DefaultClientException("商品名称重复，请重新输入！");
        }

        ProductPoly poly = new ProductPoly();
        poly.setId(IdUtil.getId());
        poly.setCode(vo.getCode());
        poly.setName(vo.getName());
        poly.setShortName(StringUtil.isBlank(vo.getShortName()) ? StringPool.EMPTY_STR : vo.getShortName());

        ProductCategoryDto productCategory = productCategoryService.getById(vo.getCategoryId());
        if (productCategory == null) {
            throw new DefaultClientException("商品类目不存在！");
        }
        poly.setCategoryId(vo.getCategoryId());

        ProductBrandDto productBrand = productBrandService.getById(vo.getBrandId());
        if (productBrand == null) {
            throw new DefaultClientException("商品品牌不存在！");
        }
        poly.setBrandId(vo.getBrandId());
        poly.setMultiSaleprop(vo.getMultipleSaleProp());
        poly.setTaxRate(vo.getTaxRate());
        poly.setSaleTaxRate(vo.getSaleTaxRate());

        productPolyMapper.insert(poly);

        //建立poly和属性值的关系
        if (!CollectionUtil.isEmpty(vo.getProperties())) {
            for (CreateProductPolyVo.PropertyVo property : vo.getProperties()) {
                ProductPropertyDto productProperty = productPropertyService.getById(property.getId());
                if (productProperty == null) {
                    throw new DefaultClientException("商品属性不存在！");
                }
                if (productProperty.getColumnType() == ColumnType.SINGLE) {
                    ProductPropertyItemDto propertyItem = productPropertyItemService.getById(property.getText());
                    if (propertyItem == null) {
                        throw new DefaultClientException("商品属性值不存在！");
                    }

                    CreateProductPolyPropertyVo createProductPolyPropertyVo = new CreateProductPolyPropertyVo();
                    createProductPolyPropertyVo.setPolyId(poly.getId());
                    createProductPolyPropertyVo.setPropertyId(productProperty.getId());
                    createProductPolyPropertyVo.setPropertyItemId(propertyItem.getId());

                    productPolyPropertyService.create(createProductPolyPropertyVo);
                } else if (productProperty.getColumnType() == ColumnType.MULTIPLE) {

                    List<String> propertyItemIds = JsonUtil.parseList(property.getText(), String.class);
                    for (String propertyItemId : propertyItemIds) {
                        CreateProductPolyPropertyVo createProductPolyPropertyVo = new CreateProductPolyPropertyVo();
                        createProductPolyPropertyVo.setPolyId(poly.getId());
                        createProductPolyPropertyVo.setPropertyId(productProperty.getId());
                        createProductPolyPropertyVo.setPropertyItemId(propertyItemId);

                        productPolyPropertyService.create(createProductPolyPropertyVo);
                    }

                } else if (productProperty.getColumnType() == ColumnType.CUSTOM) {

                    CreateProductPolyPropertyVo createProductPolyPropertyVo = new CreateProductPolyPropertyVo();
                    createProductPolyPropertyVo.setPolyId(poly.getId());
                    createProductPolyPropertyVo.setPropertyId(productProperty.getId());
                    createProductPolyPropertyVo.setPropertyText(property.getText());
                    productPolyPropertyService.create(createProductPolyPropertyVo);
                } else {
                    throw new DefaultClientException("商品属性字段类型不存在！");
                }
            }
        }

        //创建商品
        int orderNo = 1;
        for (CreateProductPolyVo.ProductVo product : vo.getProducts()) {
            CreateProductVo createProductVo = new CreateProductVo();
            createProductVo.setCode(product.getCode());
            createProductVo.setName(product.getName());
            createProductVo.setPolyId(poly.getId());
            createProductVo.setSkuCode(product.getSkuCode());
            createProductVo.setExternalCode(product.getExternalCode());
            createProductVo.setSpec(product.getSpec());
            createProductVo.setUnit(product.getUnit());
            createProductVo.setPurchasePrice(product.getPurchasePrice());
            createProductVo.setSalePrice(product.getSalePrice());
            createProductVo.setRetailPrice(product.getRetailPrice());

            if (vo.getMultipleSaleProp()) {
                List<String> salePropItems = new ArrayList<>();
                salePropItems.add(product.getSalePropItemId1());
                if (!StringUtil.isBlank(product.getSalePropItemId2())) {
                    salePropItems.add(product.getSalePropItemId2());
                }

                createProductVo.setSalePropItems(salePropItems);
            }

            try {
                productService.create(createProductVo);
            } catch (ClientException e) {
                throw new DefaultClientException("第" + orderNo + "行商品新增失败，具体原因：" + e.getMsg());
            }

            orderNo++;
        }

        OpLogUtil.setVariable("id", poly.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        return poly.getId();
    }

    private ProductPolyDto convertDto(ProductPolyDto dto) {

        if (dto == null) {
            return dto;
        }

        dto.setProperties(productPolyPropertyService.getByPolyId(dto.getId()));

        return dto;
    }

    @CacheEvict(value = ProductPolyDto.CACHE_NAME, key = "#key")
    @Override
    public void cleanCacheByKey(String key) {

    }
}
