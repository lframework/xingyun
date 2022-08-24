package com.lframework.xingyun.sc.biz.impl.purchase;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.biz.mappers.PurchaseReturnDetailMapper;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseReturnDetailService;
import com.lframework.xingyun.sc.facade.entity.PurchaseReturnDetail;
import org.springframework.stereotype.Service;

@Service
public class PurchaseReturnDetailServiceImpl extends
    BaseMpServiceImpl<PurchaseReturnDetailMapper, PurchaseReturnDetail>
    implements IPurchaseReturnDetailService {

}
