package com.lframework.xingyun.settle.api.bo.item.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.settle.facade.entity.SettleInItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySettleInItemBo extends BaseBo<SettleInItem> {

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

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @ApiModelProperty("修改人ID")
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;

    public QuerySettleInItemBo() {

    }

    public QuerySettleInItemBo(SettleInItem dto) {

        super(dto);
    }

    @Override
    protected void afterInit(SettleInItem dto) {

        IUserService userService = ApplicationUtil.getBean(IUserService.class);

        UserDto createBy = userService.findById(this.getCreateBy());
        UserDto updateBy = userService.findById(this.getUpdateBy());
        this.setCreateBy(createBy.getName());
        this.setUpdateBy(updateBy.getName());
    }
}
