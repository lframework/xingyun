package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterSelectorVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterVo;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
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
    @Sorts({
        @Sort(value = "code", autoParse = true),
        @Sort(value = "name", autoParse = true),
        @Sort(value = "createTime", autoParse = true),
        @Sort(value = "updateTime", autoParse = true),
    })
    List<StoreCenter> query(@Param("vo") QueryStoreCenterVo vo);

    /**
     * 选择器
     *
     * @param vo
     * @return
     */
    List<StoreCenter> selector(@Param("vo") QueryStoreCenterSelectorVo vo);
}
