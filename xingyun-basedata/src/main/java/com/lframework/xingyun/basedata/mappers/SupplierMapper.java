package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.vo.supplier.QuerySupplierSelectorVo;
import com.lframework.xingyun.basedata.vo.supplier.QuerySupplierVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-11
 */
public interface SupplierMapper extends BaseMapper<Supplier> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SupplierDto> query(@Param("vo") QuerySupplierVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SupplierDto getById(String id);

    /**
     * 选择器
     * @param vo
     * @return
     */
    List<SupplierDto> selector(@Param("vo") QuerySupplierSelectorVo vo);
}
