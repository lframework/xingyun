package com.lframework.xingyun.sc.impl.purchase;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.PurchaseRequisitionDetail;
import com.lframework.xingyun.sc.mappers.PurchaseRequisitionDetailMapper;
import com.lframework.xingyun.sc.service.purchase.PurchaseRequisitionDetailService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseRequisitionDetailServiceImpl
    extends BaseMpServiceImpl<PurchaseRequisitionDetailMapper, PurchaseRequisitionDetail>
    implements PurchaseRequisitionDetailService {

  @Override
  public List<PurchaseRequisitionDetail> listByRequisitionId(String requisitionId) {
    return getBaseMapper().listByRequisitionId(requisitionId);
  }

  @Transactional
  @Override
  public void deleteByRequisitionId(String requisitionId) {
    LambdaQueryWrapper<PurchaseRequisitionDetail> wrapper =
        Wrappers.<PurchaseRequisitionDetail>lambdaQuery()
            .eq(PurchaseRequisitionDetail::getRequisitionId, requisitionId);
    remove(wrapper);
  }
}
