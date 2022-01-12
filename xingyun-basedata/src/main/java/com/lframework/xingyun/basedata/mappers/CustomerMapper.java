package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.customer.CustomerDto;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerSelectorVo;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-10
 */
public interface CustomerMapper extends BaseMapper<Customer> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<CustomerDto> query(@Param("vo") QueryCustomerVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    CustomerDto getById(String id);

    /**
     * 选择器
     * @param vo
     * @return
     */
    List<CustomerDto> selector(@Param("vo") QueryCustomerSelectorVo vo);
}
