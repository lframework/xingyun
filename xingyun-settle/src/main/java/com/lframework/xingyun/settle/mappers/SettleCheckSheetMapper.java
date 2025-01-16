package com.lframework.xingyun.settle.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.settle.dto.check.SettleCheckSheetFullDto;
import com.lframework.xingyun.settle.entity.SettleCheckSheet;
import com.lframework.xingyun.settle.vo.check.QuerySettleCheckSheetVo;
import com.lframework.xingyun.core.annotations.permission.DataPermission;
import com.lframework.xingyun.core.annotations.permission.DataPermissions;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import com.lframework.xingyun.core.components.permission.SysDataPermissionDataPermissionType;
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
public interface SettleCheckSheetMapper extends BaseMapper<SettleCheckSheet> {

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @Sorts({
        @Sort(value = "code", alias = "s", autoParse = true),
        @Sort(value = "createTime", alias = "s", autoParse = true),
        @Sort(value = "approveTime", alias = "s", autoParse = true),
    })
    @DataPermissions(type = SysDataPermissionDataPermissionType.ORDER, value = {
        @DataPermission(template = "order", alias = "s")
    })
    List<SettleCheckSheet> query(@Param("vo") QuerySettleCheckSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SettleCheckSheetFullDto getDetail(String id);

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
