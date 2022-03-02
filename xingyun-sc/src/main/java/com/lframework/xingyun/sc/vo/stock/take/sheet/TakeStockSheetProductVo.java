package com.lframework.xingyun.sc.vo.stock.take.sheet;

import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TakeStockSheetProductVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @NotBlank(message = "商品ID不能为空！")
    private String productId;

    /**
     * 盘点数量
     */
    @NotNull(message = "盘点数量不能为空！")
    @TypeMismatch(message = "盘点数量格式有误！")
    private Integer takeNum;

    /**
     * 备注
     */
    private String description;
}
