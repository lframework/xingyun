package com.lframework.xingyun.sc.vo.retail.returned;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class BatchApprovePassRetailReturnVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退单ID
     */
    @NotEmpty(message = "退单ID不能为空！")
    private List<String> ids;
}
