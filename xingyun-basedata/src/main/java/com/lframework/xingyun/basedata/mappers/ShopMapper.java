package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.vo.shop.QueryShopVo;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
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
  @Sorts({
      @Sort(value = "code", alias = "tb", autoParse = true),
      @Sort(value = "name", alias = "tb", autoParse = true),
      @Sort(value = "createTime", alias = "tb", autoParse = true),
  })
  List<Shop> query(@Param("vo") QueryShopVo vo);
}
