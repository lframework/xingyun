package com.lframework.xingyun.basedata.impl.stockcell;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.event.DataChangeEventBuilder;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.dto.stockcell.StockCellDto;
import com.lframework.xingyun.basedata.entity.StockCell;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.enums.StockCellType;
import com.lframework.xingyun.basedata.events.DeleteStockCellEvent;
import com.lframework.xingyun.basedata.mappers.StockCellMapper;
import com.lframework.xingyun.basedata.service.stockcell.StockCellService;
import com.lframework.xingyun.basedata.vo.stockcell.CreateStockCellVo;
import com.lframework.xingyun.basedata.vo.stockcell.QueryStockCellSelectorVo;
import com.lframework.xingyun.basedata.vo.stockcell.QueryStockCellVo;
import com.lframework.xingyun.basedata.vo.stockcell.UpdateStockCellVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockCellServiceImpl extends BaseMpServiceImpl<StockCellMapper, StockCell>
    implements StockCellService {

  @Override
  public PageResult<StockCellDto> query(Integer pageIndex, Integer pageSize, QueryStockCellVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<StockCellDto> datas = getBaseMapper().query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = StockCell.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public StockCellDto findById(String id) {

    return getBaseMapper().findById(id);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "删除仓位，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Wrapper<StockCell> updateWrapper = Wrappers.lambdaUpdate(StockCell.class)
        .set(StockCell::getAvailable, Boolean.FALSE)
        .eq(StockCell::getId, id);
    getBaseMapper().update(updateWrapper);

    StockCell record = this.getById(id);

    DataChangeEventBuilder.publishLogicDelete(this, DeleteStockCellEvent.class, record);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "新增仓位，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateStockCellVo vo) {

    Wrapper<StockCell> checkWrapper = Wrappers.lambdaQuery(StockCell.class)
        .eq(StockCell::getScId, vo.getScId())
        .eq(StockCell::getCode, vo.getCode())
        .eq(StockCell::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(StockCell.class)
        .eq(StockCell::getScId, vo.getScId())
        .eq(StockCell::getName, vo.getName())
        .eq(StockCell::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    StockCell data = new StockCell();
    data.setId(IdUtil.getId());
    data.setScId(vo.getScId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setCellType(EnumUtil.getByCode(StockCellType.class, vo.getCellType()));
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = BaseDataOpLogType.class, name = "修改仓位，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateStockCellVo vo) {

    StockCell data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("仓位不存在！");
    }

    Wrapper<StockCell> checkWrapper = Wrappers.lambdaQuery(StockCell.class)
        .eq(StockCell::getScId, data.getScId())
        .eq(StockCell::getCode, vo.getCode())
        .eq(StockCell::getAvailable, Boolean.TRUE)
        .ne(StockCell::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(StockCell.class)
        .eq(StockCell::getScId, data.getScId())
        .eq(StockCell::getName, vo.getName())
        .eq(StockCell::getAvailable, Boolean.TRUE)
        .ne(StockCell::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    LambdaUpdateWrapper<StockCell> updateWrapper = Wrappers.lambdaUpdate(StockCell.class)
        .set(StockCell::getCode, vo.getCode()).set(StockCell::getName, vo.getName())
        .set(StockCell::getCellType, EnumUtil.getByCode(StockCellType.class, vo.getCellType()))
        .set(StockCell::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(StockCell::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public PageResult<StockCellDto> selector(Integer pageIndex, Integer pageSize,
      QueryStockCellSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<StockCellDto> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @CacheEvict(value = StockCell.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
