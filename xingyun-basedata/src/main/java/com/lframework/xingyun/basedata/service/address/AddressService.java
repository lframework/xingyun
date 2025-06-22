package com.lframework.xingyun.basedata.service.address;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.Address;
import com.lframework.xingyun.basedata.vo.address.AddressSelectorVo;
import com.lframework.xingyun.basedata.vo.address.CreateAddressVo;
import com.lframework.xingyun.basedata.vo.address.QueryAddressVo;
import com.lframework.xingyun.basedata.vo.address.UpdateAddressVo;
import java.util.List;

/**
 * 地址库 Service
 *
 * @author zmj
 */
public interface AddressService extends BaseMpService<Address> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<Address> query(Integer pageIndex, Integer pageSize, QueryAddressVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<Address> query(QueryAddressVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<Address> selector(Integer pageIndex, Integer pageSize,
      AddressSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  Address findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateAddressVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateAddressVo vo);

  /**
   * 查询默认地址
   *
   * @param entityId
   * @param entityType
   * @param addressType
   * @return
   */
  Address getDefaultAddress(String entityId, Integer entityType, Integer addressType);

}
