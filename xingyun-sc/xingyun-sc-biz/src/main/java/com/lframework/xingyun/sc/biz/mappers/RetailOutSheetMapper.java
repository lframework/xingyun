package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.facade.dto.retail.out.RetailOutSheetFullDto;
import com.lframework.xingyun.sc.facade.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.xingyun.sc.facade.entity.RetailOutSheet;
import com.lframework.xingyun.sc.facade.vo.retail.out.QueryRetailOutSheetVo;
import com.lframework.xingyun.sc.facade.vo.retail.out.QueryRetailOutSheetWithReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.out.RetailOutSheetSelectorVo;
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
  List<RetailOutSheet> query(@Param("vo") QueryRetailOutSheetVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<RetailOutSheet> selector(@Param("vo") RetailOutSheetSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  RetailOutSheetFullDto getDetail(String id);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<RetailOutSheetFullDto> queryFulls(@Param("vo") QueryRetailOutSheetVo vo);

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
      @Param("multipleRelate") boolean multipleRelate);
}
