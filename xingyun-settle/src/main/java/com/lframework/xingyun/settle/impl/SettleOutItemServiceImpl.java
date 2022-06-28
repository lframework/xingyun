package com.lframework.xingyun.settle.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import com.lframework.xingyun.settle.mappers.SettleOutItemMapper;
import com.lframework.xingyun.settle.service.ISettleOutItemService;
import com.lframework.xingyun.settle.vo.item.out.CreateSettleOutItemVo;
import com.lframework.xingyun.settle.vo.item.out.QuerySettleOutItemVo;
import com.lframework.xingyun.settle.vo.item.out.SettleOutItemSelectorVo;
import com.lframework.xingyun.settle.vo.item.out.UpdateSettleOutItemVo;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettleOutItemServiceImpl extends BaseMpServiceImpl<SettleOutItemMapper, SettleOutItem>
        implements ISettleOutItemService {

    @Override
    public PageResult<SettleOutItem> query(Integer pageIndex, Integer pageSize, QuerySettleOutItemVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<SettleOutItem> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<SettleOutItem> query(QuerySettleOutItemVo vo) {

        return getBaseMapper().query(vo);
    }

    @Override
    public PageResult<SettleOutItem> selector(Integer pageIndex, Integer pageSize, SettleOutItemSelectorVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<SettleOutItem> datas = getBaseMapper().selector(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Cacheable(value = SettleOutItem.CACHE_NAME, key = "#id", unless = "#result == null")
    @Override
    public SettleOutItem findById(String id) {

        return getBaseMapper().selectById(id);
    }

    @OpLog(type = OpLogType.OTHER, name = "停用支出项目，ID：{}", params = "#ids", loopFormat = true)
    @Transactional
    @Override
    public void batchUnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        Wrapper<SettleOutItem> updateWrapper = Wrappers.lambdaUpdate(SettleOutItem.class)
                .set(SettleOutItem::getAvailable, Boolean.FALSE).in(SettleOutItem::getId, ids);
        getBaseMapper().update(updateWrapper);
    }

    @OpLog(type = OpLogType.OTHER, name = "启用支出项目，ID：{}", params = "#ids", loopFormat = true)
    @Transactional
    @Override
    public void batchEnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        Wrapper<SettleOutItem> updateWrapper = Wrappers.lambdaUpdate(SettleOutItem.class)
                .set(SettleOutItem::getAvailable, Boolean.TRUE).in(SettleOutItem::getId, ids);
        getBaseMapper().update(updateWrapper);
    }

    @OpLog(type = OpLogType.OTHER, name = "新增支出项目，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional
    @Override
    public String create(CreateSettleOutItemVo vo) {

        Wrapper<SettleOutItem> checkWrapper = Wrappers.lambdaQuery(SettleOutItem.class)
                .eq(SettleOutItem::getCode, vo.getCode());
        if (getBaseMapper().selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        SettleOutItem data = new SettleOutItem();
        data.setId(IdUtil.getId());
        data.setCode(vo.getCode());
        data.setName(vo.getName());
        data.setAvailable(Boolean.TRUE);
        data.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

        getBaseMapper().insert(data);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        return data.getId();
    }

    @OpLog(type = OpLogType.OTHER, name = "修改支出项目，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional
    @Override
    public void update(UpdateSettleOutItemVo vo) {

        SettleOutItem data = getBaseMapper().selectById(vo.getId());
        if (ObjectUtil.isNull(data)) {
            throw new DefaultClientException("支出项目不存在！");
        }

        Wrapper<SettleOutItem> checkWrapper = Wrappers.lambdaQuery(SettleOutItem.class)
                .eq(SettleOutItem::getCode, vo.getCode()).ne(SettleOutItem::getId, vo.getId());
        if (getBaseMapper().selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        LambdaUpdateWrapper<SettleOutItem> updateWrapper = Wrappers.lambdaUpdate(SettleOutItem.class)
                .set(SettleOutItem::getCode, vo.getCode()).set(SettleOutItem::getName, vo.getName())
                .set(SettleOutItem::getAvailable, vo.getAvailable()).set(SettleOutItem::getDescription,
                        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
                .eq(SettleOutItem::getId, vo.getId());

        getBaseMapper().update(updateWrapper);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);
    }

    @CacheEvict(value = SettleOutItem.CACHE_NAME, key = "#key")
    @Override
    public void cleanCacheByKey(Serializable key) {

    }
}
