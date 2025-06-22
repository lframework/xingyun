package com.lframework.xingyun.basedata.impl.paytype;

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
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.PayType;
import com.lframework.xingyun.basedata.mappers.PayTypeMapper;
import com.lframework.xingyun.basedata.service.paytype.PayTypeService;
import com.lframework.xingyun.basedata.vo.paytype.CreatePayTypeVo;
import com.lframework.xingyun.basedata.vo.paytype.PayTypeSelectorVo;
import com.lframework.xingyun.basedata.vo.paytype.QueryPayTypeVo;
import com.lframework.xingyun.basedata.vo.paytype.UpdatePayTypeVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayTypeServiceImpl extends BaseMpServiceImpl<PayTypeMapper, PayType>
    implements PayTypeService {

  @Override
  public PageResult<PayType> query(Integer pageIndex, Integer pageSize, QueryPayTypeVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<PayType> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<PayType> query(QueryPayTypeVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<PayType> selector(Integer pageIndex, Integer pageSize, PayTypeSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<PayType> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = PayType.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public PayType findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "新增支付方式，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreatePayTypeVo vo) {

    Wrapper<PayType> checkCodeWrapper = Wrappers.lambdaQuery(PayType.class)
        .eq(PayType::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<PayType> checkNameWrapper = Wrappers.lambdaQuery(PayType.class)
        .eq(PayType::getName, vo.getName());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    PayType data = new PayType();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    data.setRecText(vo.getRecText());

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = BaseDataOpLogType.class, name = "修改支付方式，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdatePayTypeVo vo) {

    PayType data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("支付方式不存在！");
    }

    Wrapper<PayType> checkWrapper = Wrappers.lambdaQuery(PayType.class)
        .eq(PayType::getCode, vo.getCode()).ne(PayType::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<PayType> checkNameWrapper = Wrappers.lambdaQuery(PayType.class)
        .eq(PayType::getName, vo.getName()).ne(PayType::getId, vo.getId());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    LambdaUpdateWrapper<PayType> updateWrapper = Wrappers.lambdaUpdate(PayType.class)
        .set(PayType::getCode, vo.getCode()).set(PayType::getName, vo.getName())
        .set(PayType::getAvailable, vo.getAvailable()).set(PayType::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(PayType::getRecText, vo.getRecText())
        .eq(PayType::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = PayType.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
