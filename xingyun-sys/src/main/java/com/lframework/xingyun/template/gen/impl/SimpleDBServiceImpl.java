package com.lframework.xingyun.template.gen.impl;

import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.xingyun.template.gen.entity.GenSimpleTableColumn;
import com.lframework.xingyun.template.gen.mappers.DBMapper;
import com.lframework.xingyun.template.gen.mappers.SimpleDBMapper;
import com.lframework.xingyun.template.gen.service.SimpleDBService;
import com.lframework.xingyun.template.gen.service.SimpleTableColumnService;
import com.lframework.xingyun.template.gen.vo.simpledb.QuerySimpleTableColumnVo;
import com.lframework.xingyun.template.gen.vo.simpledb.SimpleTableSelectorVo;
import com.lframework.xingyun.template.gen.dto.simpledb.OriSimpleTableDto;
import com.lframework.xingyun.template.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleDBServiceImpl implements SimpleDBService {

  @Autowired
  private SimpleDBMapper simpleDBMapper;

  @Autowired
  private DBMapper dbMapper;

  @Autowired
  private SimpleTableColumnService simpleTableColumnService;

  @Override
  public String getCurrentDBName() {
    return dbMapper.getCurrentDBName();
  }

  @Override
  public PageResult<SimpleDBDto> selector(Integer pageIndex, Integer pageSize,
      SimpleTableSelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<SimpleDBDto> datas = simpleDBMapper.selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public OriSimpleTableDto getByTableName(String tableName) {
    OriSimpleTableDto table = simpleDBMapper.getByTableName(tableName);
    if (ObjectUtil.isNull(table)) {
      return table;
    }

    QuerySimpleTableColumnVo queryVo = new QuerySimpleTableColumnVo();
    queryVo.setTableName(tableName);

    List<GenSimpleTableColumn> columns = simpleTableColumnService.query(queryVo);
    table.setColumns(columns);

    return table;
  }

  @Override
  public List<SimpleDBDto> listByIds(List<String> tableNames) {
    return simpleDBMapper.listByIds(tableNames);
  }
}
