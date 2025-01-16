package com.lframework.xingyun.settle.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.settle.dto.check.customer.CustomerSettleCheckSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettleCheckSheet;
import com.lframework.xingyun.settle.vo.check.customer.QueryCustomerSettleCheckSheetVo;
import com.lframework.xingyun.core.annotations.permission.DataPermission;
import com.lframework.xingyun.core.annotations.permission.DataPermissions;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import com.lframework.xingyun.core.components.permission.SysDataPermissionDataPermissionType;
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
public interface CustomerSettleCheckSheetMapper extends BaseMapper<CustomerSettleCheckSheet> {

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
    List<CustomerSettleCheckSheet> query(@Param("vo") QueryCustomerSettleCheckSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    CustomerSettleCheckSheetFullDto getDetail(String id);

    /**
     * 查询已审核列表
     *
     * @param customerId
     * @param startTime
     * @param endTime
     * @return
     */
    List<CustomerSettleCheckSheet> getApprovedList(@Param("customerId") String customerId,
        @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
