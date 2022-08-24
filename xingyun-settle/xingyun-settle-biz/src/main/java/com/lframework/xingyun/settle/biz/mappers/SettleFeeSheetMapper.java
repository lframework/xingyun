package com.lframework.xingyun.settle.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.settle.facade.dto.fee.SettleFeeSheetFullDto;
import com.lframework.xingyun.settle.facade.entity.SettleFeeSheet;
import com.lframework.xingyun.settle.facade.vo.fee.QuerySettleFeeSheetVo;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
    List<SettleFeeSheet> query(@Param("vo") QuerySettleFeeSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SettleFeeSheetFullDto getDetail(String id);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<SettleFeeSheetFullDto> queryFulls(@Param("vo") QuerySettleFeeSheetVo vo);

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
