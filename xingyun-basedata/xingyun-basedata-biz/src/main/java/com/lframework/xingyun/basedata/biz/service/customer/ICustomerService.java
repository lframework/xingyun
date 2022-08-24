package com.lframework.xingyun.basedata.biz.service.customer;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.facade.entity.Customer;
import com.lframework.xingyun.basedata.facade.vo.customer.CreateCustomerVo;
import com.lframework.xingyun.basedata.facade.vo.customer.QueryCustomerSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.customer.QueryCustomerVo;
import com.lframework.xingyun.basedata.facade.vo.customer.UpdateCustomerVo;
import java.util.Collection;
import java.util.List;

public interface ICustomerService extends BaseMpService<Customer> {

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
   * @param ids
   */
  void batchUnable(Collection<String> ids);

  /**
   * 根据ID启用
   *
   * @param ids
   */
  void batchEnable(Collection<String> ids);

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
