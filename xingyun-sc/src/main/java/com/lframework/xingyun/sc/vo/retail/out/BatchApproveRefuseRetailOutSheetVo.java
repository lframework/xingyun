package com.lframework.xingyun.sc.vo.retail.out;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class BatchApproveRefuseRetailOutSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 出库单ID
     */
    @NotEmpty(message = "出库单ID不能为空！")
    private List<String> ids;

    /**
     * 拒绝理由
     */
    @NotBlank(message = "拒绝理由不能为空！")
    private String refuseReason;
}
