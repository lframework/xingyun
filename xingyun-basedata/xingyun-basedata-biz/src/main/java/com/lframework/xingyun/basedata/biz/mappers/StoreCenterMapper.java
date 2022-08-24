package com.lframework.xingyun.basedata.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.basedata.facade.vo.storecenter.QueryStoreCenterSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.storecenter.QueryStoreCenterVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-07
 */
public interface StoreCenterMapper extends BaseMapper<StoreCenter> {

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<StoreCenter> query(@Param("vo") QueryStoreCenterVo vo);

    /**
     * 选择器
     *
     * @param vo
     * @return
     */
    List<StoreCenter> selector(@Param("vo") QueryStoreCenterSelectorVo vo);
}
