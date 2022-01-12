package com.lframework.xingyun.core.service;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;

import java.util.List;

public interface IDicCityService extends BaseService {

    /**
     * 选择器
     * @return
     */
    List<DicCityDto> selector();

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    DicCityDto getById(String id);
}
