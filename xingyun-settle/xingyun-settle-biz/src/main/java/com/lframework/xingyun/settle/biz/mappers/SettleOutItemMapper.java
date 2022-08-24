package com.lframework.xingyun.settle.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.settle.facade.entity.SettleOutItem;
import com.lframework.xingyun.settle.facade.vo.item.out.QuerySettleOutItemVo;
import com.lframework.xingyun.settle.facade.vo.item.out.SettleOutItemSelectorVo;
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
