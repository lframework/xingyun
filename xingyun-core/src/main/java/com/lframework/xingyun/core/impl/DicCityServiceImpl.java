package com.lframework.xingyun.core.impl;

import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.mappers.DicCityMapper;
import com.lframework.xingyun.core.service.IDicCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicCityServiceImpl implements IDicCityService {

    @Autowired
    private DicCityMapper dicCityMapper;

    @Override
    public List<DicCityDto> selector() {

        return dicCityMapper.selector();
    }

    @Cacheable(value = DicCityDto.CACHE_NAME, key = "#id", unless = "#result == null")
    @Override
    public DicCityDto getById(String id) {

        return dicCityMapper.getById(id);
    }

    @CacheEvict(value = DicCityDto.CACHE_NAME, key = "#key")
    @Override
    public void cleanCacheByKey(String key) {

    }
}
