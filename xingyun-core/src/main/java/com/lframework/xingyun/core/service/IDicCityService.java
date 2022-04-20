package com.lframework.xingyun.core.service;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.entity.DicCity;
import java.util.List;

public interface IDicCityService extends BaseMpService<DicCity> {

  /**
   * 查询所有数据
   *
   * @return
   */
  List<DicCityDto> getAll();

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DicCityDto findById(String id);

  /**
   * 根据ID查询链路数据
   *
   * @param id
   * @return
   */
  List<DicCityDto> getChainById(String id);
}
