package com.lframework.xingyun.settle.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.settle.dto.sheet.SettleSheetFullDto;
import com.lframework.xingyun.settle.entity.SettleSheet;
import com.lframework.xingyun.settle.vo.sheet.QuerySettleSheetVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-12-05
 */
public interface SettleSheetMapper extends BaseMapper<SettleSheet> {

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<SettleSheet> query(@Param("vo") QuerySettleSheetVo vo,
        @Param("dataPermission") String dataPermission);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SettleSheetFullDto getDetail(String id);
}
