package com.lframework.xingyun.sc.vo.retail.out;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ApprovePassRetailOutSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 出库单ID
     */
    @NotBlank(message = "出库单ID不能为空！")
    private String id;

    /**
     * 备注
     */
    private String description;
}
