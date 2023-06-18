package com.lframework.xingyun.sc.bo.stock.take.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TakeStockPlanSelectorBo extends BaseBo<TakeStockPlan> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 业务单据号
     */
    @ApiModelProperty("业务单据号")
    private String code;

    /**
     * 仓库编号
     */
    @ApiModelProperty("仓库编号")
    private String scCode;

    /**
     * 仓库名称
     */
    @ApiModelProperty("仓库名称")
    private String scName;

    /**
     * 盘点类别
     */
    @ApiModelProperty("盘点类别")
    private Integer takeType;

    /**
     * 盘点状态
     */
    @ApiModelProperty("盘点状态")
    private Integer takeStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;

    public TakeStockPlanSelectorBo(TakeStockPlan dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<TakeStockPlan> convert(TakeStockPlan dto) {

        return super.convert(dto, TakeStockPlanSelectorBo::getTakeType, TakeStockPlanSelectorBo::getTakeStatus);
    }

    @Override
    protected void afterInit(TakeStockPlan dto) {

        this.takeType = dto.getTakeType().getCode();
        this.takeStatus = dto.getTakeStatus().getCode();

        StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();
    }
}
