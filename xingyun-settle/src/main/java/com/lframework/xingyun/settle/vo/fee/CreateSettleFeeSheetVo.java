package com.lframework.xingyun.settle.vo.fee;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.settle.enums.SettleFeeSheetType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class CreateSettleFeeSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商ID
     */
    @NotNull(message = "供应商ID不能为空！")
    private String supplierId;

    /**
     * 收支方式
     */
    @NotNull(message = "收支方式不能为空！")
    @IsEnum(message = "收支方式不能为空！", enumClass = SettleFeeSheetType.class)
    private Integer sheetType;

    /**
     * 项目
     */
    @NotEmpty(message = "项目不能为空！")
    private List<SettleFeeSheetItemVo> items;

    /**
     * 备注
     */
    private String description;

    @Override
    public void validate() {

        int orderNo = 1;
        for (SettleFeeSheetItemVo item : this.items) {
            if (StringUtil.isBlank(item.getId())) {
                throw new InputErrorException("第" + orderNo + "行项目不能为空！");
            }

            if (item.getAmount() == null) {
                throw new InputErrorException("第" + orderNo + "行金额不能为空！");
            }

            if (NumberUtil.le(item.getAmount(), 0D)) {
                throw new InputErrorException("第" + orderNo + "行金额必须大于0！");
            }

            if (!NumberUtil.isNumberPrecision(item.getAmount(), 2)) {
                throw new InputErrorException("第" + orderNo + "行金额最多允许2位小数！");
            }

            if (this.items.stream().filter(t -> t.getId().equals(item.getId())).count() > 1) {
                throw new InputErrorException("第" + orderNo + "行项目与其他行重复，请检查！");
            }

            orderNo++;
        }
    }
}
