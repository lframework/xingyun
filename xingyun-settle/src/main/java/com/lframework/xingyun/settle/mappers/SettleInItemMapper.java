package com.lframework.xingyun.settle.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.vo.item.in.QuerySettleInItemVo;
import com.lframework.xingyun.settle.vo.item.in.SettleInItemSelectorVo;
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
public interface SettleInItemMapper extends BaseMapper<SettleInItem> {

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<SettleInItem> query(@Param("vo") QuerySettleInItemVo vo);

    /**
     * 选择器
     *
     * @param vo
     * @return
     */
    List<SettleInItem> selector(@Param("vo") SettleInItemSelectorVo vo);
}
