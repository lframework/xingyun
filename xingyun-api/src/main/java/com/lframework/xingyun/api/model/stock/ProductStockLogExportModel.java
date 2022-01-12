package com.lframework.xingyun.api.model.stock;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.dto.stock.ProductLotDto;
import com.lframework.xingyun.sc.dto.stock.ProductStockLogDto;
import com.lframework.xingyun.sc.service.stock.IProductLotService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductStockLogExportModel extends BaseBo<ProductStockLogDto> implements ExcelModel {

    /**
     * 仓库编号
     */
    @ExcelProperty("仓库编号")
    private String scCode;

    /**
     * 仓库名称
     */
    @ExcelProperty("仓库名称")
    private String scName;

    /**
     * 供应商编号
     */
    @ExcelProperty("供应商编号")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @ExcelProperty("供应商名称")
    private String supplierName;

    /**
     * 批次号
     */
    @ExcelProperty("批次号")
    private String lotCode;

    /**
     * 商品编号
     */
    @ExcelProperty("商品编号")
    private String productCode;

    /**
     * 商品名称
     */
    @ExcelProperty("商品名称")
    private String productName;

    /**
     * 商品类目
     */
    @ExcelProperty("商品类目")
    private String categoryName;

    /**
     * 商品品牌
     */
    @ExcelProperty("商品品牌")
    private String brandName;

    /**
     * 销售属性1
     */
    @ExcelProperty("销售属性1")
    private String salePropItem1;

    /**
     * 销售属性2
     */
    @ExcelProperty("销售属性2")
    private String salePropItem2;

    /**
     * 原库存数量
     */
    @ExcelProperty("变动前库存数量")
    private Integer oriStockNum;

    /**
     * 现库存数量
     */
    @ExcelProperty("变动后库存数量")
    private Integer curStockNum;

    /**
     * 库存数量
     */
    @ExcelProperty("变动库存数量")
    private Integer stockNum;

    /**
     * 原含税成本价
     */
    @ExcelProperty("变动前含税成本价")
    private BigDecimal oriTaxPrice;

    /**
     * 现含税成本价
     */
    @ExcelProperty("变动后含税成本价")
    private BigDecimal curTaxPrice;

    /**
     * 原无税成本价
     */
    @ExcelProperty("变动前无税成本价")
    private BigDecimal oriUnTaxPrice;

    /**
     * 现无税成本价
     */
    @ExcelProperty("变动后无税成本价")
    private BigDecimal curUnTaxPrice;

    /**
     * 含税金额
     */
    @ExcelProperty("变动含税金额")
    private BigDecimal taxAmount;

    /**
     * 无税金额
     */
    @ExcelProperty("变动无税金额")
    private BigDecimal unTaxAmount;

    /**
     * 创建时间
     */
    @ExcelProperty("操作时间")
    @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
    private Date createTime;

    /**
     * 创建人
     */
    @ExcelProperty("操作人")
    private String createBy;

    /**
     * 业务单据号
     */
    @ExcelProperty("业务单据号")
    private String bizCode;

    /**
     * 业务类型
     */
    @ExcelProperty("业务类型")
    private String bizType;

    public ProductStockLogExportModel() {

    }

    public ProductStockLogExportModel(ProductStockLogDto dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<ProductStockLogDto> convert(ProductStockLogDto dto) {

        return this;
    }

    @Override
    protected void afterInit(ProductStockLogDto dto) {

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenterDto sc = storeCenterService.getById(dto.getScId());
        this.setScCode(sc.getCode());
        this.setScName(sc.getName());

        IProductLotService productLotService = ApplicationUtil.getBean(IProductLotService.class);
        ProductLotDto lot = productLotService.getById(dto.getLotId());
        this.setLotCode(lot.getLotCode());

        ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
        SupplierDto supplier = supplierService.getById(lot.getSupplierId());
        this.setSupplierCode(supplier.getCode());
        this.setSupplierName(supplier.getName());

        IProductService productService = ApplicationUtil.getBean(IProductService.class);
        IProductSalePropItemService productSalePropItemService = ApplicationUtil
                .getBean(IProductSalePropItemService.class);

        ProductDto product = productService.getById(dto.getProductId());
        this.setProductCode(product.getCode());
        this.setProductName(product.getName());
        this.setCategoryName(product.getPoly().getCategoryName());
        this.setBrandName(product.getPoly().getBrandName());
        if (product.getPoly().getMultiSaleProp()) {
            List<SalePropItemByProductDto> saleProps = productSalePropItemService.getByProductId(product.getId());
            if (!CollectionUtil.isEmpty(saleProps)) {
                this.setSalePropItem1(saleProps.get(0).getName());
            }
            if (saleProps.size() > 1) {
                this.setSalePropItem2(saleProps.get(1).getName());
            }
        }

        this.setOriStockNum(dto.getOriStockNum());
        this.setCurStockNum(dto.getCurStockNum());
        this.setStockNum(dto.getStockNum());
        this.setOriTaxPrice(NumberUtil.getNumber(dto.getOriTaxPrice(), 2));
        this.setCurTaxPrice(NumberUtil.getNumber(dto.getCurTaxPrice(), 2));
        this.setOriUnTaxPrice(NumberUtil.getNumber(dto.getOriUnTaxPrice(), 2));
        this.setCurUnTaxPrice(NumberUtil.getNumber(dto.getCurUnTaxPrice(), 2));
        this.setTaxAmount(NumberUtil.getNumber(dto.getTaxAmount(), 2));
        this.setUnTaxAmount(NumberUtil.getNumber(dto.getUnTaxAmount(), 2));

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        UserDto createBy = userService.getById(dto.getCreateBy());
        this.setCreateBy(createBy.getName());

        this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
        this.setBizCode(dto.getBizCode());
        this.setBizType(dto.getBizType().getDesc());
    }
}
