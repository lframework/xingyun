package com.lframework.xingyun.settle.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.core.annotations.permission.DataPermission;
import com.lframework.starter.web.core.annotations.permission.DataPermissions;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.inner.components.permission.OrderDataPermissionDataPermissionType;
import com.lframework.xingyun.settle.dto.sheet.customer.CustomerSettleSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettleSheet;
import com.lframework.xingyun.settle.vo.sheet.customer.QueryCustomerSettleSheetVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-12-05
 */
public interface CustomerSettleSheetMapper extends BaseMapper<CustomerSettleSheet> {

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
    List<CustomerSettleSheet> query(@Param("vo") QueryCustomerSettleSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    CustomerSettleSheetFullDto getDetail(String id);
}
