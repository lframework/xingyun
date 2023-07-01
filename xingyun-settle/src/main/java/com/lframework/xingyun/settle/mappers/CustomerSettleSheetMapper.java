package com.lframework.xingyun.settle.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
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
    List<CustomerSettleSheet> query(@Param("vo") QueryCustomerSettleSheetVo vo,
        @Param("dataPermission") String dataPermission);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    CustomerSettleSheetFullDto getDetail(String id);
}
