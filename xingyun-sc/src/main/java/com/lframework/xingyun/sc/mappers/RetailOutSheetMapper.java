package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.retail.RetailProductDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetFullDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetWithReturnVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailProductVo;
import com.lframework.xingyun.sc.vo.retail.out.RetailOutSheetSelectorVo;
import com.lframework.xingyun.core.annotations.permission.DataPermission;
import com.lframework.xingyun.core.annotations.permission.DataPermissions;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import com.lframework.xingyun.core.components.permission.SysDataPermissionDataPermissionType;
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
  @Sorts({
      @Sort(value = "code", alias = "s", autoParse = true),
      @Sort(value = "createTime", alias = "s", autoParse = true),
      @Sort(value = "approveTime", alias = "s", autoParse = true),
  })
  @DataPermissions(type = SysDataPermissionDataPermissionType.ORDER, value = {
      @DataPermission(template = "order", alias = "s")
  })
  List<RetailOutSheet> query(@Param("vo") QueryRetailOutSheetVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.ORDER, value = {
      @DataPermission(template = "order", alias = "s")
  })
  List<RetailOutSheet> selector(@Param("vo") RetailOutSheetSelectorVo vo);

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
  @DataPermissions(type = SysDataPermissionDataPermissionType.ORDER, value = {
      @DataPermission(template = "order", alias = "s")
  })
  List<RetailOutSheet> queryWithReturn(@Param("vo") QueryRetailOutSheetWithReturnVo vo,
      @Param("multipleRelate") boolean multipleRelate);

  /**
   * 根据关键字零售采购商品信息
   *
   * @param condition
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.PRODUCT, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<RetailProductDto> queryRetailByCondition(
      @Param("condition") String condition, @Param("isReturn") Boolean isReturn);

  /**
   * 查询可零售商品信息
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.PRODUCT, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<RetailProductDto> queryRetailList(@Param("vo") QueryRetailProductVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  RetailProductDto getRetailById(String id);
}
