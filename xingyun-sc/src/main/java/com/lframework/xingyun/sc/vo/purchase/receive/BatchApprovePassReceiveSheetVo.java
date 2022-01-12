package com.lframework.xingyun.sc.vo.purchase.receive;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class BatchApprovePassReceiveSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收货单ID
     */
    @NotEmpty(message = "收货单ID不能为空！")
    private List<String> ids;
}
