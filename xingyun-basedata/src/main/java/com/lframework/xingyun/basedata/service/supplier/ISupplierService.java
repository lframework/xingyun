package com.lframework.xingyun.basedata.service.supplier;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.vo.supplier.CreateSupplierVo;
import com.lframework.xingyun.basedata.vo.supplier.QuerySupplierSelectorVo;
import com.lframework.xingyun.basedata.vo.supplier.QuerySupplierVo;
import com.lframework.xingyun.basedata.vo.supplier.UpdateSupplierVo;
import java.util.Collection;
import java.util.List;

public interface ISupplierService extends BaseMpService<Supplier> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<SupplierDto> query(Integer pageIndex, Integer pageSize, QuerySupplierVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SupplierDto> query(QuerySupplierVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SupplierDto getById(String id);

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
  String create(CreateSupplierVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSupplierVo vo);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<SupplierDto> selector(Integer pageIndex, Integer pageSize, QuerySupplierSelectorVo vo);
}
