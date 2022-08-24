package com.lframework.xingyun.settle.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.settle.facade.dto.check.customer.CustomerSettleCheckSheetFullDto;
import com.lframework.xingyun.settle.facade.entity.CustomerSettleCheckSheet;
import com.lframework.xingyun.settle.facade.vo.check.customer.QueryCustomerSettleCheckSheetVo;
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
    List<CustomerSettleCheckSheet> query(@Param("vo") QueryCustomerSettleCheckSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    CustomerSettleCheckSheetFullDto getDetail(String id);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<CustomerSettleCheckSheetFullDto> queryFulls(
        @Param("vo") QueryCustomerSettleCheckSheetVo vo);

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
