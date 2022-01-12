package com.lframework.xingyun.sc.vo.sale.out;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDto;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetService;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UpdateSaleOutSheetVo extends CreateSaleOutSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 出库单ID
     */
    @NotBlank(message = "出库单ID不能为空！")
    private String id;

    @Override
    public void validate() {

        ISaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(ISaleOutSheetService.class);
        SaleOutSheetDto saleOutSheet = saleOutSheetService.getById(this.getId());

        this.validate(!StringUtil.isBlank(saleOutSheet.getSaleOrderId()));
    }
}
