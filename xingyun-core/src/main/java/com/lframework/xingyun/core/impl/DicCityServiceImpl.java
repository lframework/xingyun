package com.lframework.xingyun.core.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.entity.DicCity;
import com.lframework.xingyun.core.mappers.DicCityMapper;
import com.lframework.xingyun.core.service.IDicCityService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DicCityServiceImpl extends BaseMpServiceImpl<DicCityMapper, DicCity> implements
    IDicCityService {

  @Cacheable(value = DicCityDto.SELECTOR_CACHE_NAME, key = "'all'")
  @Override
  public List<DicCityDto> getAll() {

    return getBaseMapper().getAll();
  }

  @Cacheable(value = DicCityDto.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public DicCityDto findById(String id) {

    return getBaseMapper().findById(id);
  }

  @Override
  public List<DicCityDto> getChainById(String id) {

    IDicCityService thisService = getThis(this.getClass());
    List<DicCityDto> all = thisService.getAll();
    List<DicCityDto> results = new ArrayList<>();
    DicCityDto current = all.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    if (current == null) {
      return results;
    }

    while (current != null) {
      results.add(current);

      String pId = current.getParentId();
      current = all.stream().filter(t -> t.getId().equals(pId)).findFirst().orElse(null);
    }

    Collections.reverse(results);

    return results;
  }

  @CacheEvict(value = DicCityDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

  }
}
