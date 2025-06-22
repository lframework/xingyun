package com.lframework.xingyun.basedata.bo.logistics.company;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.LogisticsCompany;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogisticsCompanySelectorBo extends BaseBo<LogisticsCompany> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 编号
     */
    @ApiModelProperty("编号")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Boolean available;

    public LogisticsCompanySelectorBo() {

    }

    public LogisticsCompanySelectorBo(LogisticsCompany dto) {

        super(dto);
    }
}
