package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.PayType;
import com.lframework.xingyun.basedata.vo.paytype.PayTypeSelectorVo;
import com.lframework.xingyun.basedata.vo.paytype.QueryPayTypeVo;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-11
 */
public interface PayTypeMapper extends BaseMapper<PayType> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", alias = "tb", autoParse = true),
      @Sort(value = "name", alias = "tb", autoParse = true),
  })
  List<PayType> query(@Param("vo") QueryPayTypeVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<PayType> selector(@Param("vo") PayTypeSelectorVo vo);
}
