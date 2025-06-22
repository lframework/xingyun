package com.lframework.xingyun.settle.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import com.lframework.xingyun.settle.vo.item.out.QuerySettleOutItemVo;
import com.lframework.xingyun.settle.vo.item.out.SettleOutItemSelectorVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-11-28
 */
public interface SettleOutItemMapper extends BaseMapper<SettleOutItem> {

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<SettleOutItem> query(@Param("vo") QuerySettleOutItemVo vo);

    /**
     * 选择器
     *
     * @param vo
     * @return
     */
    List<SettleOutItem> selector(@Param("vo") SettleOutItemSelectorVo vo);
}
