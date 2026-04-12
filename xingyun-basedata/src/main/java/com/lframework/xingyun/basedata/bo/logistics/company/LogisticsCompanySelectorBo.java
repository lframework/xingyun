package com.lframework.xingyun.basedata.bo.logistics.company;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.LogisticsCompany;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LogisticsCompanySelectorBo extends BaseBo<LogisticsCompany> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private String code;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    public LogisticsCompanySelectorBo() {

    }

    public LogisticsCompanySelectorBo(LogisticsCompany dto) {

        super(dto);
    }
}
