package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDetailDto;

import java.util.List;

public interface ISaleOutSheetDetailService extends BaseService {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleOutSheetDetailDto getById(String id);

    /**
     * 根据出库单ID查询
     * @param sheetId
     * @return
     */
    List<SaleOutSheetDetailDto> getBySheetId(String sheetId);

    /**
     * 增加退货数量
     * @param id
     * @param num
     */
    void addReturnNum(String id, Integer num);

    /**
     * 减少退货数量
     * @param id
     * @param num
     */
    void subReturnNum(String id, Integer num);
}
