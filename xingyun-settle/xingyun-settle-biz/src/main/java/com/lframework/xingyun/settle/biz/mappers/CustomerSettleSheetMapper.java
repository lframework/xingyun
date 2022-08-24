package com.lframework.xingyun.settle.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.settle.facade.dto.sheet.customer.CustomerSettleSheetFullDto;
import com.lframework.xingyun.settle.facade.entity.CustomerSettleSheet;
import com.lframework.xingyun.settle.facade.vo.sheet.customer.QueryCustomerSettleSheetVo;
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
    List<CustomerSettleSheet> query(@Param("vo") QueryCustomerSettleSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    CustomerSettleSheetFullDto getDetail(String id);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<CustomerSettleSheetFullDto> queryFulls(@Param("vo") QueryCustomerSettleSheetVo vo);
}
