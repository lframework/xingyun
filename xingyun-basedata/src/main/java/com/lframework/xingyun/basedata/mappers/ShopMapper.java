package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.vo.shop.QueryShopVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 门店 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface ShopMapper extends BaseMapper<Shop> {

  /**
   * 查询列表
   * @param vo
   * @return
   */
  List<Shop> query(@Param("vo") QueryShopVo vo);
}
