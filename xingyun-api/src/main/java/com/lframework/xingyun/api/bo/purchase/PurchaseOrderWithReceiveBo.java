package com.lframework.xingyun.api.bo.purchase;

import com.lframework.common.functions.SFunction;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.PurchaseProductDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderWithReceiveDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class PurchaseOrderWithReceiveBo extends BaseBo<PurchaseOrderWithReceiveDto> {

    /**
     * 订单ID
     */
    private String id;

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 仓库名称
     */
    private String scName;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 采购员ID
     */
    private String purchaserId;

    /**
     * 采购员姓名
     */
    private String purchaserName;

    /**
     * 订单明细
     */
    private List<DetailBo> details;

    public PurchaseOrderWithReceiveBo() {

    }

    public PurchaseOrderWithReceiveBo(PurchaseOrderWithReceiveDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<PurchaseOrderWithReceiveDto> convert(PurchaseOrderWithReceiveDto dto) {

        return super.convert(dto, PurchaseOrderWithReceiveBo::getPurchaserId, PurchaseOrderWithReceiveBo::getDetails);
    }

    @Override
    protected void afterInit(PurchaseOrderWithReceiveDto dto) {

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenterDto sc = storeCenterService.getById(dto.getScId());
        this.scName = sc.getName();

        ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
        SupplierDto supplier = supplierService.getById(dto.getSupplierId());
        this.supplierName = supplier.getName();

        if (!StringUtil.isBlank(dto.getPurchaserId())) {
            IUserService userService = ApplicationUtil.getBean(IUserService.class);
            UserDto purchaser = userService.getById(dto.getPurchaserId());

            this.purchaserId = purchaser.getId();
            this.purchaserName = purchaser.getName();
        }

        if (!CollectionUtil.isEmpty(dto.getDetails())) {
            this.details = dto.getDetails().stream().map(DetailBo::new).collect(Collectors.toList());
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DetailBo extends BaseBo<PurchaseOrderWithReceiveDto.DetailDto> {

        /**
         * ID
         */
        private String id;

        /**
         * 商品ID
         */
        private String productId;

        /**
         * 商品编号
         */
        private String productCode;

        /**
         * 商品名称
         */
        private String productName;

        /**
         * SKU编号
         */
        private String skuCode;

        /**
         * 外部编号
         */
        private String externalCode;

        /**
         * 单位
         */
        private String unit;

        /**
         * 规格
         */
        private String spec;

        /**
         * 类目名称
         */
        private String categoryName;

        /**
         * 品牌名称
         */
        private String brandName;

        /**
         * 销售属性1
         */
        private String salePropItemName1;

        /**
         * 销售属性2
         */
        private String salePropItemName2;

        /**
         * 采购数量
         */
        private Integer orderNum;

        /**
         * 采购价
         */
        private BigDecimal purchasePrice;

        /**
         * 剩余收货数量
         */
        private Integer remainNum;

        /**
         * 是否赠品
         */
        private Boolean isGift;

        /**
         * 税率（%）
         */
        private BigDecimal taxRate;

        /**
         * 备注
         */
        private String description;

        public DetailBo() {

        }

        public DetailBo(PurchaseOrderWithReceiveDto.DetailDto dto) {

            super(dto);
        }

        @Override
        public BaseBo<PurchaseOrderWithReceiveDto.DetailDto> convert(PurchaseOrderWithReceiveDto.DetailDto dto) {

            return this;
        }

        @Override
        public <A> BaseBo<PurchaseOrderWithReceiveDto.DetailDto> convert(PurchaseOrderWithReceiveDto.DetailDto dto,
                SFunction<A, ?>... columns) {

            return this;
        }

        @Override
        protected void afterInit(PurchaseOrderWithReceiveDto.DetailDto dto) {

            IProductService productService = ApplicationUtil.getBean(IProductService.class);
            PurchaseProductDto product = productService.getPurchaseById(dto.getProductId());

            this.id = dto.getId();
            this.productId = product.getId();
            this.productCode = product.getCode();
            this.productName = product.getName();
            this.skuCode = product.getSkuCode();
            this.externalCode = product.getExternalCode();
            this.unit = product.getUnit();
            this.spec = product.getSpec();
            this.categoryName = product.getCategoryName();
            this.brandName = product.getBrandName();
            if (!CollectionUtil.isEmpty(product.getSaleProps())) {
                if (product.getSaleProps().size() > 0) {
                    this.salePropItemName1 = product.getSaleProps().get(0).getName();
                }

                if (product.getSaleProps().size() > 1) {
                    this.salePropItemName2 = product.getSaleProps().get(1).getName();
                }
            }

            this.orderNum = dto.getOrderNum();
            this.purchasePrice = dto.getTaxPrice();
            this.remainNum = NumberUtil.sub(dto.getOrderNum(), dto.getReceiveNum()).intValue();
            this.isGift = dto.getIsGift();
            this.taxRate = dto.getTaxRate();
            this.description = dto.getDescription();
        }
    }
}
