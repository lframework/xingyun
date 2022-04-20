package com.lframework.xingyun.settle.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.dto.pre.SettlePreSheetFullDto;
import com.lframework.xingyun.settle.entity.SettlePreSheet;
import com.lframework.xingyun.settle.vo.pre.QuerySettlePreSheetVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-12-02
 */
public interface SettlePreSheetMapper extends BaseMapper<SettlePreSheet> {

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<SettlePreSheet> query(@Param("vo") QuerySettlePreSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SettlePreSheetFullDto getDetail(String id);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<SettlePreSheetFullDto> queryFulls(@Param("vo") QuerySettlePreSheetVo vo);

    /**
     * 查询已审核列表
     *
     * @param supplierId
     * @param startTime
     * @param endTime
     * @return
     */
    List<SettlePreSheet> getApprovedList(@Param("supplierId") String supplierId,
            @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime,
            @Param("settleStatus") SettleStatus settleStatus);
}
