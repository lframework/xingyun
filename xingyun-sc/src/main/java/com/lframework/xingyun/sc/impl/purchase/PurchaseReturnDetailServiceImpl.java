package com.lframework.xingyun.sc.impl.purchase;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.PurchaseReturnDetail;
import com.lframework.xingyun.sc.mappers.PurchaseReturnDetailMapper;
import com.lframework.xingyun.sc.service.purchase.PurchaseReturnDetailService;
import org.springframework.stereotype.Service;

@Service
public class PurchaseReturnDetailServiceImpl extends
    BaseMpServiceImpl<PurchaseReturnDetailMapper, PurchaseReturnDetail>
    implements PurchaseReturnDetailService {

}
