package com.lframework.xingyun.basedata.service.storecenter;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.vo.storecenter.CreateStoreCenterVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterSelectorVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterVo;
import com.lframework.xingyun.basedata.vo.storecenter.UpdateStoreCenterVo;
import java.util.Collection;

public interface IStoreCenterService extends BaseMpService<StoreCenter> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<StoreCenterDto> query(Integer pageIndex, Integer pageSize, QueryStoreCenterVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  StoreCenterDto getById(String id);

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
  String create(CreateStoreCenterVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateStoreCenterVo vo);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<StoreCenterDto> selector(Integer pageIndex, Integer pageSize,
      QueryStoreCenterSelectorVo vo);
}
