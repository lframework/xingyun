package com.lframework.xingyun.api.excel.retail.out;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RetailOutSheetExportModel extends BaseBo<RetailOutSheet> implements ExcelModel {

    /**
     * 单号
     */
    @ExcelProperty("业务单据号")
    private String code;

    /**
     * 仓库编号
     */
    @ExcelProperty("仓库编号")
    private String scCode;

    /**
     * 仓库名称
     */
    @ExcelProperty("仓库名称")
    private String scName;

    /**
     * 客户编号
     */
    @ExcelProperty("会员编号")
    private String memberCode;

    /**
     * 客户名称
     */
    @ExcelProperty("会员名称")
    private String memberName;

    /**
     * 销售员姓名
     */
    @ExcelProperty("销售员")
    private String salerName;

    /**
     * 单据总金额
     */
    @ExcelProperty("单据总金额")
    private BigDecimal totalAmount;

    /**
     * 商品数量
     */
    @ExcelProperty("商品数量")
    private Integer receiveNum;

    /**
     * 赠品数量
     */
    @ExcelProperty("赠品数量")
    private Integer giftNum;

    /**
     * 操作时间
     */
    @ExcelProperty("操作时间")
    @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
    private Date createTime;

    /**
     * 操作人
     */
    @ExcelProperty("操作人")
    private String createBy;

    /**
     * 审核状态
     */
    @ExcelProperty("审核状态")
    private String status;

    /**
     * 审核时间
     */
    @ExcelProperty("审核时间")
    @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
    private Date approveTime;

    /**
     * 审核人
     */
    @ExcelProperty("审核人")
    private String approveBy;

    /**
     * 结算状态
     */
    @ExcelProperty("结算状态")
    private String settleStatus;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String description;

    public RetailOutSheetExportModel() {

    }

    public RetailOutSheetExportModel(RetailOutSheet dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<RetailOutSheet> convert(RetailOutSheet dto) {

        return this;
    }

    @Override
    protected void afterInit(RetailOutSheet dto) {

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());

        IMemberService memberService = ApplicationUtil.getBean(IMemberService.class);
        Member member = memberService.findById(dto.getMemberId());

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        UserDto saler = null;
        if (!StringUtil.isBlank(dto.getSalerId())) {
            saler = userService.findById(dto.getSalerId());
        }
        UserDto createBy = userService.findById(dto.getCreateBy());
        UserDto approveBy = null;
        if (!StringUtil.isBlank(dto.getApproveBy())) {
            approveBy = userService.findById(dto.getApproveBy());
        }

        this.setCode(dto.getCode());
        this.setScCode(sc.getCode());
        this.setScName(sc.getName());
        this.setMemberCode(member.getCode());
        this.setMemberName(member.getName());
        this.setSalerName(saler == null ? null : saler.getName());
        this.setTotalAmount(dto.getTotalAmount());
        this.setReceiveNum(dto.getTotalNum());
        this.setGiftNum(dto.getTotalGiftNum());
        this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
        this.setCreateBy(createBy.getName());
        this.setStatus(dto.getStatus().getDesc());
        if (dto.getApproveTime() != null) {
            this.setApproveTime(DateUtil.toDate(dto.getApproveTime()));
        }
        if (approveBy != null) {
            this.setApproveBy(approveBy.getName());
        }
        this.setSettleStatus(dto.getSettleStatus().getDesc());
        this.setDescription(dto.getDescription());
    }
}
