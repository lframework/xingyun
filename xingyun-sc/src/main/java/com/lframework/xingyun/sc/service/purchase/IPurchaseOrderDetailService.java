package com.lframework.xingyun.sc.service.purchase;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderDetailDto;

import java.util.List;

public interface IPurchaseOrderDetailService extends BaseService {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    PurchaseOrderDetailDto getById(String id);

    /**
     * 根据订单ID查询
     * @param orderId
     * @return
     */
    List<PurchaseOrderDetailDto> getByOrderId(String orderId);

    /**
     * 增加收货数量
     * @param id
     * @param num
     */
    void addReceiveNum(String id, Integer num);

    /**
     * 减少收货数量
     * @param id
     * @param num
     */
    void subReceiveNum(String id, Integer num);
}
