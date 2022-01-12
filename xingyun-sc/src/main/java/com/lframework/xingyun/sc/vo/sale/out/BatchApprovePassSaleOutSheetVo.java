package com.lframework.xingyun.sc.vo.sale.out;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class BatchApprovePassSaleOutSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 出库单ID
     */
    @NotEmpty(message = "出库单ID不能为空！")
    private List<String> ids;
}
