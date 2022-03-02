package com.lframework.xingyun.sc.vo.stock.take.sheet;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class BatchApproveRefuseTakeStockSheetVo implements BaseVo, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotEmpty(message = "ID不能为空！")
    private List<String> ids;

    /**
     * 拒绝原因
     */
    @NotBlank(message = "拒绝理由不能为空！")
    private String refuseReason;
}
