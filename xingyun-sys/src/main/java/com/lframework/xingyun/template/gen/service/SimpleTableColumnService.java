package com.lframework.xingyun.template.gen.service;

import com.lframework.xingyun.template.gen.vo.simpledb.QuerySimpleTableColumnVo;
import com.lframework.xingyun.template.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.web.service.BaseMpService;
import java.util.List;

public interface SimpleTableColumnService extends BaseMpService<GenSimpleTableColumn> {

  /**
   * 查询列信息
   *
   * @param vo
   * @return
   */
  List<GenSimpleTableColumn> query(QuerySimpleTableColumnVo vo);
}
