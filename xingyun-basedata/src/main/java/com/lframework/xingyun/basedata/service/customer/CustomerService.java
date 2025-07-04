package com.lframework.xingyun.basedata.service.customer;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.vo.customer.CreateCustomerVo;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerSelectorVo;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerVo;
import com.lframework.xingyun.basedata.vo.customer.UpdateCustomerVo;
import java.util.List;

public interface CustomerService extends BaseMpService<Customer> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<Customer> query(Integer pageIndex, Integer pageSize, QueryCustomerVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<Customer> query(QueryCustomerVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  Customer findById(String id);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<Customer> selector(Integer pageIndex, Integer pageSize, QueryCustomerSelectorVo vo);

  /**
   * 根据ID停用
   *
   * @param id
   */
  void unable(String id);

  /**
   * 根据ID启用
   *
   * @param id
   */
  void enable(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateCustomerVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateCustomerVo vo);
}
