package com.lframework.xingyun.api.bo.retail.config;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.sc.entity.RetailConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetRetailConfigBo extends BaseBo<RetailConfig> {

    /**
     * 零售退货单是否关联零售出库单
     */
    @ApiModelProperty("零售退货单是否关联零售出库单")
    private Boolean retailReturnRequireOutStock;

    /**
     * 零售退货单是否多次关联零售出库单
     */
    @ApiModelProperty("零售退货单是否多次关联零售出库单")
    private Boolean retailReturnMultipleRelateOutStock;

    public GetRetailConfigBo() {

    }

    public GetRetailConfigBo(RetailConfig dto) {

        super(dto);
    }
}
