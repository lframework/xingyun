package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.retail.RetailProductDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetFullDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetWithReturnVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailProductVo;
import com.lframework.xingyun.sc.vo.retail.out.RetailOutSheetSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-26
 */
public interface RetailOutSheetMapper extends BaseMapper<RetailOutSheet> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<RetailOutSheet> query(@Param("vo") QueryRetailOutSheetVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<RetailOutSheet> selector(@Param("vo") RetailOutSheetSelectorVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  RetailOutSheetFullDto getDetail(String id);

  /**
   * 根据ID查询（销售退货业务）
   *
   * @param id
   * @return
   */
  RetailOutSheetWithReturnDto getWithReturn(@Param("id") String id,
      @Param("requireOut") Boolean requireOut);

  /**
   * 查询列表（销售退货业务）
   *
   * @param vo
   * @return
   */
  List<RetailOutSheet> queryWithReturn(@Param("vo") QueryRetailOutSheetWithReturnVo vo,
      @Param("multipleRelate") boolean multipleRelate,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据关键字零售采购商品信息
   *
   * @param condition
   * @return
   */
  List<RetailProductDto> queryRetailByCondition(
      @Param("condition") String condition, @Param("isReturn") Boolean isReturn, @Param("dataPermission") String dataPermission);

  /**
   * 查询可零售商品信息
   *
   * @param vo
   * @return
   */
  List<RetailProductDto> queryRetailList(@Param("vo") QueryRetailProductVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  RetailProductDto getRetailById(String id);
}
