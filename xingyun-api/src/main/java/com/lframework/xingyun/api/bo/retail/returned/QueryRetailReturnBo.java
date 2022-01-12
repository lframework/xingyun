package com.lframework.xingyun.api.bo.retail.returned;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.member.MemberDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDto;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnDto;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryRetailReturnBo extends BaseBo<RetailReturnDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 单号
     */
    private String code;

    /**
     * 仓库编号
     */
    private String scCode;

    /**
     * 仓库名称
     */
    private String scName;

    /**
     * 会员编号
     */
    private String memberCode;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 销售员姓名
     */
    private String salerName;

    /**
     * 销售出库单ID
     */
    private String outSheetId;

    /**
     * 销售出库单号
     */
    private String outSheetCode;

    /**
     * 退货数量
     */
    private Integer totalNum;

    /**
     * 赠品数量
     */
    private Integer totalGiftNum;

    /**
     * 退货金额
     */
    private BigDecimal totalAmount;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 审核人
     */
    private String approveBy;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime approveTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 拒绝原因
     */
    private String refuseReason;

    /**
     * 结算状态
     */
    private Integer settleStatus;

    public QueryRetailReturnBo(RetailReturnDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<RetailReturnDto> convert(RetailReturnDto dto) {

        return super.convert(dto, QueryRetailReturnBo::getStatus, QueryRetailReturnBo::getSettleStatus);
    }

    @Override
    protected void afterInit(RetailReturnDto dto) {

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenterDto sc = storeCenterService.getById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();

        IMemberService memberService = ApplicationUtil.getBean(IMemberService.class);
        MemberDto member = memberService.getById(dto.getMemberId());
        this.memberCode = member.getCode();
        this.memberName = member.getName();

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        if (!StringUtil.isBlank(dto.getSalerId())) {
            this.salerName = userService.getById(dto.getSalerId()).getName();
        }

        this.createBy = userService.getById(dto.getCreateBy()).getName();

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.getById(dto.getApproveBy()).getName();
        }

        this.status = dto.getStatus().getCode();
        this.settleStatus = dto.getSettleStatus().getCode();

        if (!StringUtil.isBlank(dto.getOutSheetId())) {
            IRetailOutSheetService retailOutSheetService = ApplicationUtil.getBean(IRetailOutSheetService.class);
            RetailOutSheetDto outSheet = retailOutSheetService.getById(dto.getOutSheetId());
            this.outSheetCode = outSheet.getCode();
        }
    }
}
