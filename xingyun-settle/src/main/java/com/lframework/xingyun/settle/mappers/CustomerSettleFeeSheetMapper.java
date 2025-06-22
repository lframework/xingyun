package com.lframework.xingyun.settle.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.core.annotations.permission.DataPermission;
import com.lframework.starter.web.core.annotations.permission.DataPermissions;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.inner.components.permission.OrderDataPermissionDataPermissionType;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.dto.fee.customer.CustomerSettleFeeSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettleFeeSheet;
import com.lframework.xingyun.settle.vo.fee.customer.QueryCustomerSettleFeeSheetVo;
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
public interface CustomerSettleFeeSheetMapper extends BaseMapper<CustomerSettleFeeSheet> {

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
    @DataPermissions(type = OrderDataPermissionDataPermissionType.class, value = {
        @DataPermission(template = "order", alias = "s")
    })
    List<CustomerSettleFeeSheet> query(@Param("vo") QueryCustomerSettleFeeSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    CustomerSettleFeeSheetFullDto getDetail(String id);

    /**
     * 查询已审核列表
     *
     * @param customerId
     * @param startTime
     * @param endTime
     * @return
     */
    List<CustomerSettleFeeSheet> getApprovedList(@Param("customerId") String customerId,
        @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime,
        @Param("settleStatus") SettleStatus settleStatus);
}
