package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.vo.simpledb.QuerySimpleTableColumnVo;
import com.lframework.xingyun.template.gen.dto.simpledb.OriSimpleTableColumnDto;
import com.lframework.xingyun.template.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.web.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-05-28
 */
public interface GenSimpleTableColumnMapper extends BaseMapper<GenSimpleTableColumn> {

  /**
   * 根据创建Vo查询
   *
   * @param vo
   * @return
   */
  List<OriSimpleTableColumnDto> query(QuerySimpleTableColumnVo vo);
}
