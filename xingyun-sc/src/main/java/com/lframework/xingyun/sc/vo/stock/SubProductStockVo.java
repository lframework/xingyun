package com.lframework.xingyun.sc.vo.stock;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SubProductStockVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @NotBlank(message = "商品ID不能为空！")
    private String productId;

    /**
     * 仓库ID
     */
    @NotBlank(message = "仓库ID不能为空！")
    private String scId;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 出库数量
     */
    @NotNull(message = "出库数量不能为空！")
    @Min(message = "出库数量必须大于0！", value = 1)
    private Integer stockNum;

    /**
     * 含税成本总金额
     */
    @Min(message = "含税成本总金额不能小于0！", value = 0)
    private BigDecimal taxAmount;

    /**
     * 税率（%）
     */
    @Min(message = "税率（%）不能小于0！", value = 0)
    private BigDecimal taxRate;

    /**
     * 出库时间
     */
    private LocalDateTime createTime = LocalDateTime.now();

    /**
     * 业务单据ID
     */
    private String bizId;

    /**
     * 业务单据明细ID
     */
    private String bizDetailId;

    /**
     * 业务单据号
     */
    private String bizCode;

    /**
     * 业务类型
     */
    @NotNull(message = "业务类型不能为空！")
    @IsEnum(message = "业务类型不正确！", enumClass = ProductStockBizType.class)
    private Integer bizType;
}
