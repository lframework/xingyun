package com.lframework.xingyun.sc.impl.stock.adjust;

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
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.xingyun.sc.entity.StockAdjustReason;
import com.lframework.xingyun.sc.enums.StockAdjustOpLogType;
import com.lframework.xingyun.sc.mappers.StockAdjustReasonMapper;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustReasonService;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.CreateStockAdjustReasonVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.QueryStockAdjustReasonVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.StockAdjustReasonSelectorVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.UpdateStockAdjustReasonVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockAdjustReasonServiceImpl extends
    BaseMpServiceImpl<StockAdjustReasonMapper, StockAdjustReason>
    implements StockAdjustReasonService {

  @Override
  public PageResult<StockAdjustReason> query(Integer pageIndex, Integer pageSize,
      QueryStockAdjustReasonVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<StockAdjustReason> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<StockAdjustReason> query(QueryStockAdjustReasonVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<StockAdjustReason> selector(Integer pageIndex, Integer pageSize,
      StockAdjustReasonSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<StockAdjustReason> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = StockAdjustReason.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public StockAdjustReason findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = StockAdjustOpLogType.class, name = "停用库存调整原因，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void unable(String id) {

    Wrapper<StockAdjustReason> updateWrapper = Wrappers.lambdaUpdate(StockAdjustReason.class)
        .set(StockAdjustReason::getAvailable, Boolean.FALSE).eq(StockAdjustReason::getId, id);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = StockAdjustOpLogType.class, name = "启用库存调整原因，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void enable(String id) {

    Wrapper<StockAdjustReason> updateWrapper = Wrappers.lambdaUpdate(StockAdjustReason.class)
        .set(StockAdjustReason::getAvailable, Boolean.TRUE).eq(StockAdjustReason::getId, id);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = StockAdjustOpLogType.class, name = "新增库存调整原因，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateStockAdjustReasonVo vo) {

    Wrapper<StockAdjustReason> checkWrapper = Wrappers.lambdaQuery(StockAdjustReason.class)
        .eq(StockAdjustReason::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    StockAdjustReason data = new StockAdjustReason();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = StockAdjustOpLogType.class, name = "修改库存调整原因，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateStockAdjustReasonVo vo) {

    StockAdjustReason data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("库存调整原因不存在！");
    }

    Wrapper<StockAdjustReason> checkWrapper = Wrappers.lambdaQuery(StockAdjustReason.class)
        .eq(StockAdjustReason::getCode, vo.getCode()).ne(StockAdjustReason::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    LambdaUpdateWrapper<StockAdjustReason> updateWrapper = Wrappers.lambdaUpdate(
            StockAdjustReason.class)
        .set(StockAdjustReason::getCode, vo.getCode()).set(StockAdjustReason::getName, vo.getName())
        .set(StockAdjustReason::getAvailable, vo.getAvailable())
        .set(StockAdjustReason::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(StockAdjustReason::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = StockAdjustReason.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
