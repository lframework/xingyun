package com.lframework.xingyun.sc.bo.stock.take.sheet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.entity.TakeStockSheet;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanService;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 盘点单 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QueryTakeStockSheetBo extends BaseBo<TakeStockSheet> {

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
     * 盘点任务号
     */
    @ApiModelProperty("盘点任务号")
    private String planCode;

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
     * 盘点任务-盘点类别
     */
    @ApiModelProperty("盘点任务-盘点类别")
    private Integer takeType;

    /**
     * 盘点任务-盘点状态
     */
    @ApiModelProperty("盘点任务-盘点状态")
    private Integer takeStatus;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;

    /**
     * 审核人
     */
    @ApiModelProperty("审核人")
    private String approveBy;

    /**
     * 审核时间
     */
    @ApiModelProperty("审核时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime approveTime;

    public QueryTakeStockSheetBo() {

    }

    public QueryTakeStockSheetBo(TakeStockSheet dto) {

        super(dto);
    }

    @Override
    public BaseBo<TakeStockSheet> convert(TakeStockSheet dto) {

        return super.convert(dto, QueryTakeStockSheetBo::getTakeStatus, QueryTakeStockSheetBo::getStatus);
    }

    @Override
    protected void afterInit(TakeStockSheet dto) {

        this.status = dto.getStatus().getCode();

        TakeStockPlanService takeStockPlanService = ApplicationUtil.getBean(TakeStockPlanService.class);
        TakeStockPlan takeStockPlan = takeStockPlanService.getById(dto.getPlanId());

        this.planCode = takeStockPlan.getCode();
        this.takeStatus = takeStockPlan.getTakeStatus().getCode();

        StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();

        UserService userService = ApplicationUtil.getBean(UserService.class);

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
        }

        this.takeType = takeStockPlan.getTakeType().getCode();
    }
}
