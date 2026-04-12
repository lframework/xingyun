package com.lframework.xingyun.basedata.bo.stockcell;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.stockcell.StockCellDto;
import com.lframework.xingyun.basedata.entity.StockCell;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryStockCellBo extends BaseBo<StockCellDto> {

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

    /**
     * 仓库编号
     */
    @Schema(description = "仓库编号")
    private String scCode;

    /**
     * 仓库名称
     */
    @Schema(description = "仓库名称")
    private String scName;

    /**
     * 仓位类别
     */
    @Schema(description = "仓位类别")
    @EnumConvert
    private Integer cellType;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private String createBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private String updateBy;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;

    public QueryStockCellBo() {

    }

    public QueryStockCellBo(StockCellDto dto) {

        super(dto);
    }

    @Override
    protected void afterInit(StockCellDto dto) {
    }
}
