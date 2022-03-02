package com.lframework.xingyun.api.bo.stock.take.pre;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.PreTakeStockProductDto;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetFullDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 预先盘点单 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetPreTakeStockSheetBo extends BaseBo<PreTakeStockSheetFullDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 业务单据号
     */
    private String code;

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 仓库名称
     */
    private String scName;

    /**
     * 盘点状态
     */
    private Integer takeStatus;

    /**
     * 备注
     */
    private String description;

    /**
     * 操作人
     */
    private String updateBy;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;

    /**
     * 明细
     */
    private List<SheetDetailBo> details;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SheetDetailBo extends BaseBo<PreTakeStockSheetFullDto.SheetDetailDto> {

        /**
         * ID
         */
        private String id;

        /**
         * 商品ID
         */
        private String productId;

        /**
         * 编号
         */
        private String productCode;

        /**
         * 名称
         */
        private String productName;

        /**
         * 类目名称
         */
        private String categoryName;

        /**
         * 品牌名称
         */
        private String brandName;

        /**
         * SKU
         */
        private String skuCode;

        /**
         * 外部编号
         */
        private String externalCode;

        /**
         * 规格
         */
        private String spec;

        /**
         * 单位
         */
        private String unit;

        /**
         * 初盘数量
         */
        private Integer firstNum;

        /**
         * 复盘数量
         */
        private Integer secondNum;

        /**
         * 抽盘数量
         */
        private Integer randNum;

        public SheetDetailBo(PreTakeStockSheetFullDto.SheetDetailDto dto) {
            super(dto);
        }

        @Override
        protected void afterInit(PreTakeStockSheetFullDto.SheetDetailDto dto) {

            IProductService productService = ApplicationUtil.getBean(IProductService.class);

            ProductDto product = productService.getById(dto.getProductId());

            this.productCode = product.getCode();
            this.productName = product.getName();
            this.brandName = product.getPoly().getBrandName();
            this.categoryName = product.getPoly().getCategoryName();
            this.skuCode = product.getSkuCode();
            this.externalCode = product.getExternalCode();
            this.spec = product.getSpec();
            this.unit = product.getUnit();
        }
    }

    public GetPreTakeStockSheetBo() {

    }

    public GetPreTakeStockSheetBo(PreTakeStockSheetFullDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<PreTakeStockSheetFullDto> convert(PreTakeStockSheetFullDto dto) {

        return super.convert(dto, GetPreTakeStockSheetBo::getTakeStatus);
    }

    @Override
    protected void afterInit(PreTakeStockSheetFullDto dto) {

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenterDto sc = storeCenterService.getById(dto.getScId());

        this.scId = sc.getId();
        this.scName = sc.getName();

        this.takeStatus = dto.getTakeStatus().getCode();

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        this.updateBy = userService.getById(dto.getUpdateBy()).getName();

        this.details = dto.getDetails().stream().map(SheetDetailBo::new).collect(Collectors.toList());
    }
}
