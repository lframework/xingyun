package com.lframework.xingyun.settle.service;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.settle.dto.item.out.SettleOutItemDto;
import com.lframework.xingyun.settle.vo.item.out.CreateSettleOutItemVo;
import com.lframework.xingyun.settle.vo.item.out.QuerySettleOutItemVo;
import com.lframework.xingyun.settle.vo.item.out.SettleOutItemSelectorVo;
import com.lframework.xingyun.settle.vo.item.out.UpdateSettleOutItemVo;

import java.util.Collection;
import java.util.List;

public interface ISettleOutItemService extends BaseService {

    /**
     * 查询列表
     * @return
     */
    PageResult<SettleOutItemDto> query(Integer pageIndex, Integer pageSize, QuerySettleOutItemVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SettleOutItemDto> query(QuerySettleOutItemVo vo);

    /**
     * 选择器
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<SettleOutItemDto> selector(Integer pageIndex, Integer pageSize, SettleOutItemSelectorVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SettleOutItemDto getById(String id);

    /**
     * 根据ID停用
     * @param ids
     */
    void batchUnable(Collection<String> ids);

    /**
     * 根据ID启用
     * @param ids
     */
    void batchEnable(Collection<String> ids);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateSettleOutItemVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateSettleOutItemVo vo);
}
