package com.lframework.xingyun.settle.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.settle.facade.entity.SettleInItem;
import com.lframework.xingyun.settle.facade.vo.item.in.QuerySettleInItemVo;
import com.lframework.xingyun.settle.facade.vo.item.in.SettleInItemSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
