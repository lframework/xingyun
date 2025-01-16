package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerSelectorVo;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerVo;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-10
 */
public interface CustomerMapper extends BaseMapper<Customer> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", autoParse = true),
      @Sort(value = "name", autoParse = true),
      @Sort(value = "createTime", autoParse = true),
      @Sort(value = "updateTime", autoParse = true),
  })
  List<Customer> query(@Param("vo") QueryCustomerVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<Customer> selector(@Param("vo") QueryCustomerSelectorVo vo);
}
