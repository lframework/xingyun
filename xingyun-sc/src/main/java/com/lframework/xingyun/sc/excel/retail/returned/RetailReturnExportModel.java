package com.lframework.xingyun.sc.excel.retail.returned;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.entity.RetailReturn;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetService;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class RetailReturnExportModel extends BaseBo<RetailReturn> implements ExcelModel {

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
     * 会员编号
     */
    @ExcelProperty("会员编号")
    private String memberCode;

    /**
     * 会员名称
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
    private Integer totalNum;

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

    /**
     * 零售出库单号
     */
    @ExcelProperty("零售出库单号")
    private String outSheetCode;

    public RetailReturnExportModel() {

    }

    public RetailReturnExportModel(RetailReturn dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<RetailReturn> convert(RetailReturn dto) {

        return this;
    }

    @Override
    protected void afterInit(RetailReturn dto) {

        StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());

        MemberService memberService = ApplicationUtil.getBean(MemberService.class);
        Member member = memberService.findById(dto.getMemberId());

        UserService userService = ApplicationUtil.getBean(UserService.class);
        UserDto saler = null;
        if (!StringUtil.isBlank(dto.getSalerId())) {
            saler = userService.findById(dto.getSalerId());
        }
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
        this.setTotalNum(dto.getTotalNum());
        this.setGiftNum(dto.getTotalGiftNum());
        this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
        this.setStatus(dto.getStatus().getDesc());
        if (dto.getApproveTime() != null) {
            this.setApproveTime(DateUtil.toDate(dto.getApproveTime()));
        }
        if (approveBy != null) {
            this.setApproveBy(approveBy.getName());
        }
        this.setSettleStatus(dto.getSettleStatus().getDesc());
        this.setDescription(dto.getDescription());

        if (!StringUtil.isBlank(dto.getOutSheetId())) {
            RetailOutSheetService retailOutSheetService = ApplicationUtil.getBean(
                RetailOutSheetService.class);
            RetailOutSheet retailOutSheet = retailOutSheetService.getById(dto.getOutSheetId());
            this.setOutSheetCode(retailOutSheet.getCode());
        }
    }
}
