package com.lframework.xingyun.basedata.service.customer;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.basedata.dto.customer.CustomerDto;
import com.lframework.xingyun.basedata.vo.customer.CreateCustomerVo;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerSelectorVo;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerVo;
import com.lframework.xingyun.basedata.vo.customer.UpdateCustomerVo;
import java.util.Collection;
import java.util.List;

public interface ICustomerService extends BaseService {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<CustomerDto> query(Integer pageIndex, Integer pageSize, QueryCustomerVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<CustomerDto> query(QueryCustomerVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  CustomerDto getById(String id);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<CustomerDto> selector(Integer pageIndex, Integer pageSize, QueryCustomerSelectorVo vo);

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
