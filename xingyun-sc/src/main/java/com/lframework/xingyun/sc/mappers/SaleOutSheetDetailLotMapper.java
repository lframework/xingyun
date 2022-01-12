package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDetailLotDto;
import com.lframework.xingyun.sc.entity.SaleOutSheetDetailLot;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-11-03
 */
public interface SaleOutSheetDetailLotMapper extends BaseMapper<SaleOutSheetDetailLot> {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleOutSheetDetailLotDto getById(String id);

    /**
     * 增加退货数量
     * @param id
     * @param num
     * @return
     */
    int addReturnNum(@Param("id") String id, @Param("num") Integer num);

    /**
     * 减少退货数量
     * @param id
     * @param num
     * @return
     */
    int subReturnNum(@Param("id") String id, @Param("num") Integer num);
}
