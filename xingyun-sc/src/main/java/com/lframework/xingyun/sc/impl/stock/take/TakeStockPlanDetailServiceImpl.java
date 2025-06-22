package com.lframework.xingyun.sc.impl.stock.take;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.xingyun.sc.dto.stock.take.plan.GetTakeStockPlanDetailProductDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.entity.TakeStockPlanDetail;
import com.lframework.xingyun.sc.mappers.TakeStockPlanDetailMapper;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanDetailService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeStockPlanDetailServiceImpl extends
    BaseMpServiceImpl<TakeStockPlanDetailMapper, TakeStockPlanDetail>
    implements TakeStockPlanDetailService {

  @Autowired
  private ProductStockService productStockService;

  @Autowired
  private TakeStockPlanService takeStockPlanService;

  @Override
  public GetTakeStockPlanDetailProductDto getByPlanIdAndProductId(String planId, String productId) {

    return getBaseMapper().getByPlanIdAndProductId(planId, productId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void savePlanDetailBySimple(String planId, List<String> productIds) {

    TakeStockPlan takeStockPlan = takeStockPlanService.getById(planId);
    Wrapper<TakeStockPlanDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            TakeStockPlanDetail.class)
        .eq(TakeStockPlanDetail::getPlanId, planId).orderByAsc(TakeStockPlanDetail::getOrderNo);

    List<TakeStockPlanDetail> planDetails = getBaseMapper().selectList(queryDetailWrapper);

    int orderNo = planDetails.size() + 1;
    for (String productId : productIds) {
      if (planDetails.stream().anyMatch(t -> t.getProductId().equals(productId))) {
        continue;
      }

      TakeStockPlanDetail detail = new TakeStockPlanDetail();
      detail.setId(IdUtil.getId());
      detail.setPlanId(planId);
      detail.setProductId(productId);

      ProductStock productStock = productStockService.getByProductIdAndScId(productId,
          takeStockPlan.getScId());
      detail.setStockNum(productStock == null ? 0 : productStock.getStockNum());

      detail.setDescription(StringPool.EMPTY_STR);
      detail.setOrderNo(orderNo++);
      getBaseMapper().insert(detail);
    }
  }

  @Override
  public List<TakeStockPlanDetail> getDetailsByPlanId(String planId) {

    return getBaseMapper().getDetailsByPlanId(planId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateOriTakeNum(String planId, String productId, Integer num) {

    getBaseMapper().updateOriTakeNum(planId, productId, num);
  }
}
