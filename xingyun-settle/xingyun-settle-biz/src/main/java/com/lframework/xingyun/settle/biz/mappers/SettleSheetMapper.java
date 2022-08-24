package com.lframework.xingyun.settle.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.settle.facade.dto.sheet.SettleSheetFullDto;
import com.lframework.xingyun.settle.facade.entity.SettleSheet;
import com.lframework.xingyun.settle.facade.vo.sheet.QuerySettleSheetVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
    List<SettleSheet> query(@Param("vo") QuerySettleSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SettleSheetFullDto getDetail(String id);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<SettleSheetFullDto> queryFulls(@Param("vo") QuerySettleSheetVo vo);
}
