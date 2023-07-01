package com.lframework.xingyun.settle.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.dto.fee.SettleFeeSheetFullDto;
import com.lframework.xingyun.settle.entity.SettleFeeSheet;
import com.lframework.xingyun.settle.vo.fee.QuerySettleFeeSheetVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-11-30
 */
public interface SettleFeeSheetMapper extends BaseMapper<SettleFeeSheet> {

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<SettleFeeSheet> query(@Param("vo") QuerySettleFeeSheetVo vo,
        @Param("dataPermission") String dataPermission);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SettleFeeSheetFullDto getDetail(String id);

    /**
     * 查询已审核列表
     *
     * @param supplierId
     * @param startTime
     * @param endTime
     * @return
     */
    List<SettleFeeSheet> getApprovedList(@Param("supplierId") String supplierId,
            @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime,
            @Param("settleStatus") SettleStatus settleStatus);
}
