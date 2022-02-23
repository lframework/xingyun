package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetFullDto;
import com.lframework.xingyun.sc.entity.PreTakeStockSheet;
import com.lframework.xingyun.sc.vo.stock.take.pre.QueryPreTakeStockSheetVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 预先盘点单 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface PreTakeStockSheetMapper extends BaseMapper<PreTakeStockSheet> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<PreTakeStockSheetDto> query(@Param("vo") QueryPreTakeStockSheetVo vo);

    /**
     * 根据ID查询
     */
    PreTakeStockSheetDto getById(@Param("id") String id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    PreTakeStockSheetFullDto getDetail(@Param("id") String id);
}
