package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.sale.SaleOrderDetailDto;

import java.util.List;

public interface ISaleOrderDetailService extends BaseService {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleOrderDetailDto getById(String id);

    /**
     * 根据订单ID查询
     * @param orderId
     * @return
     */
    List<SaleOrderDetailDto> getByOrderId(String orderId);

    /**
     * 增加出库数量
     * @param id
     * @param num
     */
    void addOutNum(String id, Integer num);

    /**
     * 减少出库数量
     * @param id
     * @param num
     */
    void subOutNum(String id, Integer num);
}
