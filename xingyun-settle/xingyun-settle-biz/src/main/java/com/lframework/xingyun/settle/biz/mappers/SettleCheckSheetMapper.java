package com.lframework.xingyun.settle.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.settle.facade.dto.check.SettleCheckSheetFullDto;
import com.lframework.xingyun.settle.facade.entity.SettleCheckSheet;
import com.lframework.xingyun.settle.facade.vo.check.QuerySettleCheckSheetVo;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-12-02
 */
public interface SettleCheckSheetMapper extends BaseMapper<SettleCheckSheet> {

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<SettleCheckSheet> query(@Param("vo") QuerySettleCheckSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SettleCheckSheetFullDto getDetail(String id);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<SettleCheckSheetFullDto> queryFulls(@Param("vo") QuerySettleCheckSheetVo vo);

    /**
     * 查询已审核列表
     *
     * @param supplierId
     * @param startTime
     * @param endTime
     * @return
     */
    List<SettleCheckSheet> getApprovedList(@Param("supplierId") String supplierId,
            @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
