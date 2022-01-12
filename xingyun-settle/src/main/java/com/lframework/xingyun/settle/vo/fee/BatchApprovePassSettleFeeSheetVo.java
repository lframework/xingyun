package com.lframework.xingyun.settle.vo.fee;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class BatchApprovePassSettleFeeSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "费用单ID不能为空！")
    private List<String> ids;
}
