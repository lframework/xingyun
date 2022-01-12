package com.lframework.xingyun.settle.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.settle.dto.item.in.SettleInItemDto;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.vo.item.in.QuerySettleInItemVo;
import com.lframework.xingyun.settle.vo.item.in.SettleInItemSelectorVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-11-28
 */
public interface SettleInItemMapper extends BaseMapper<SettleInItem> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SettleInItemDto> query(@Param("vo") QuerySettleInItemVo vo);

    /**
     * 选择器
     * @param vo
     * @return
     */
    List<SettleInItemDto> selector(@Param("vo") SettleInItemSelectorVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SettleInItemDto getById(String id);
}
