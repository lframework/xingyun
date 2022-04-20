package com.lframework.xingyun.settle.service;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.vo.item.in.CreateSettleInItemVo;
import com.lframework.xingyun.settle.vo.item.in.QuerySettleInItemVo;
import com.lframework.xingyun.settle.vo.item.in.SettleInItemSelectorVo;
import com.lframework.xingyun.settle.vo.item.in.UpdateSettleInItemVo;

import java.util.Collection;
import java.util.List;

public interface ISettleInItemService extends BaseMpService<SettleInItem> {

    /**
     * 查询列表
     *
     * @return
     */
    PageResult<SettleInItem> query(Integer pageIndex, Integer pageSize, QuerySettleInItemVo vo);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<SettleInItem> query(QuerySettleInItemVo vo);

    /**
     * 选择器
     *
     * @return
     */
    PageResult<SettleInItem> selector(Integer pageIndex, Integer pageSize, SettleInItemSelectorVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SettleInItem findById(String id);

    /**
     * 根据ID停用
     *
     * @param ids
     */
    void batchUnable(Collection<String> ids);

    /**
     * 根据ID启用
     *
     * @param ids
     */
    void batchEnable(Collection<String> ids);

    /**
     * 创建
     *
     * @param vo
     * @return
     */
    String create(CreateSettleInItemVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void update(UpdateSettleInItemVo vo);
}
