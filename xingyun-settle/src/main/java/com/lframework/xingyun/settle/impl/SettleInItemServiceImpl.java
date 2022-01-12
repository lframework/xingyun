package com.lframework.xingyun.settle.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.*;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.xingyun.settle.dto.item.in.SettleInItemDto;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.mappers.SettleInItemMapper;
import com.lframework.xingyun.settle.service.ISettleInItemService;
import com.lframework.xingyun.settle.vo.item.in.CreateSettleInItemVo;
import com.lframework.xingyun.settle.vo.item.in.QuerySettleInItemVo;
import com.lframework.xingyun.settle.vo.item.in.SettleInItemSelectorVo;
import com.lframework.xingyun.settle.vo.item.in.UpdateSettleInItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class SettleInItemServiceImpl implements ISettleInItemService {

    @Autowired
    private SettleInItemMapper settleInItemMapper;

    @Override
    public PageResult<SettleInItemDto> query(Integer pageIndex, Integer pageSize, QuerySettleInItemVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<SettleInItemDto> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<SettleInItemDto> query(QuerySettleInItemVo vo) {

        return settleInItemMapper.query(vo);
    }

    @Override
    public PageResult<SettleInItemDto> selector(Integer pageIndex, Integer pageSize, SettleInItemSelectorVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<SettleInItemDto> datas = settleInItemMapper.selector(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Cacheable(value = SettleInItemDto.CACHE_NAME, key = "#id", unless = "#result == null")
    @Override
    public SettleInItemDto getById(String id) {

        return settleInItemMapper.getById(id);
    }

    @OpLog(type = OpLogType.OTHER, name = "停用收入项目，ID：{}", params = "#ids", loopFormat = true)
    @Transactional
    @Override
    public void batchUnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        Wrapper<SettleInItem> updateWrapper = Wrappers.lambdaUpdate(SettleInItem.class)
                .set(SettleInItem::getAvailable, Boolean.FALSE).in(SettleInItem::getId, ids);
        settleInItemMapper.update(updateWrapper);

        ISettleInItemService thisService = getThis(this.getClass());
        for (String id : ids) {
            thisService.cleanCacheByKey(id);
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "启用收入项目，ID：{}", params = "#ids", loopFormat = true)
    @Transactional
    @Override
    public void batchEnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        Wrapper<SettleInItem> updateWrapper = Wrappers.lambdaUpdate(SettleInItem.class)
                .set(SettleInItem::getAvailable, Boolean.TRUE).in(SettleInItem::getId, ids);
        settleInItemMapper.update(updateWrapper);

        ISettleInItemService thisService = getThis(this.getClass());
        for (String id : ids) {
            thisService.cleanCacheByKey(id);
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "新增收入项目，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional
    @Override
    public String create(CreateSettleInItemVo vo) {

        Wrapper<SettleInItem> checkWrapper = Wrappers.lambdaQuery(SettleInItem.class)
                .eq(SettleInItem::getCode, vo.getCode());
        if (settleInItemMapper.selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        SettleInItem data = new SettleInItem();
        data.setId(IdUtil.getId());
        data.setCode(vo.getCode());
        data.setName(vo.getName());
        data.setAvailable(Boolean.TRUE);
        data.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

        settleInItemMapper.insert(data);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        return data.getId();
    }

    @OpLog(type = OpLogType.OTHER, name = "修改收入项目，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional
    @Override
    public void update(UpdateSettleInItemVo vo) {

        SettleInItem data = settleInItemMapper.selectById(vo.getId());
        if (ObjectUtil.isNull(data)) {
            throw new DefaultClientException("收入项目不存在！");
        }

        Wrapper<SettleInItem> checkWrapper = Wrappers.lambdaQuery(SettleInItem.class)
                .eq(SettleInItem::getCode, vo.getCode()).ne(SettleInItem::getId, vo.getId());
        if (settleInItemMapper.selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        LambdaUpdateWrapper<SettleInItem> updateWrapper = Wrappers.lambdaUpdate(SettleInItem.class)
                .set(SettleInItem::getCode, vo.getCode()).set(SettleInItem::getName, vo.getName())
                .set(SettleInItem::getAvailable, vo.getAvailable()).set(SettleInItem::getDescription,
                        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
                .eq(SettleInItem::getId, vo.getId());

        settleInItemMapper.update(updateWrapper);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        ISettleInItemService thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(data.getId());
    }

    @CacheEvict(value = SettleInItemDto.CACHE_NAME, key = "#key")
    @Override
    public void cleanCacheByKey(String key) {

    }
}
