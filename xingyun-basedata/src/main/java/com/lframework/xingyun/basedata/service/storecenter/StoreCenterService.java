package com.lframework.xingyun.basedata.service.storecenter;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.vo.storecenter.CreateStoreCenterVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterSelectorVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterVo;
import com.lframework.xingyun.basedata.vo.storecenter.UpdateStoreCenterVo;

public interface StoreCenterService extends BaseMpService<StoreCenter> {

    /**
     * 查询列表
     *
     * @return
     */
    PageResult<StoreCenter> query(Integer pageIndex, Integer pageSize, QueryStoreCenterVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    StoreCenter findById(String id);

    /**
     * 根据ID停用
     *
     * @param id
     */
    void unable(String id);

    /**
     * 根据ID启用
     *
     * @param id
     */
    void enable(String id);

    /**
     * 创建
     *
     * @param vo
     * @return
     */
    String create(CreateStoreCenterVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void update(UpdateStoreCenterVo vo);

    /**
     * 选择器
     *
     * @return
     */
    PageResult<StoreCenter> selector(Integer pageIndex, Integer pageSize, QueryStoreCenterSelectorVo vo);
}
