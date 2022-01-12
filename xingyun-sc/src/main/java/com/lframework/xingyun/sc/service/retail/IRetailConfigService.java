package com.lframework.xingyun.sc.service.retail;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.retail.config.RetailConfigDto;
import com.lframework.xingyun.sc.vo.retail.config.UpdateRetailConfigVo;

public interface IRetailConfigService extends BaseService {

    /**
     * 查询
     * @return
     */
    RetailConfigDto get();

    /**
     * 修改
     * @param vo
     */
    void update(UpdateRetailConfigVo vo);
}
