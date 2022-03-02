package com.lframework.xingyun.sc.vo.stock.take.sheet;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class UpdateTakeStockSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotBlank(message = "id不能为空！")
    private String id;

    /**
     * 备注
     */
    private String description;

    /**
     * 商品信息
     */
    @Valid
    @NotEmpty(message = "请录入商品！")
    private List<TakeStockSheetProductVo> products;
}
