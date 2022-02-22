package com.lframework.xingyun.sc.service.stock.take;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.stock.take.config.TakeStockConfigDto;
import com.lframework.xingyun.sc.vo.stock.take.config.UpdateTakeStockConfigVo;

/**
 * 盘点参数 Service
 * @author zmj
 */
public interface ITakeStockConfigService extends BaseService {

    /**
     * 根据ID查询
     * @return
     */
    TakeStockConfigDto get();


    /**
     * 修改
     * @param vo
     */
    void update(UpdateTakeStockConfigVo vo);

}
