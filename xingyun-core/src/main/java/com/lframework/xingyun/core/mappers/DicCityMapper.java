package com.lframework.xingyun.core.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.entity.DicCity;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-07
 */
public interface DicCityMapper extends BaseMapper<DicCity> {

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
