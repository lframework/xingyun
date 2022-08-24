package com.lframework.xingyun.sc.biz.impl.stock.take;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.sc.biz.mappers.TakeStockPlanDetailMapper;
import com.lframework.xingyun.sc.biz.service.stock.IProductStockService;
import com.lframework.xingyun.sc.biz.service.stock.take.ITakeStockPlanDetailService;
import com.lframework.xingyun.sc.biz.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.facade.dto.stock.take.plan.GetTakeStockPlanDetailProductDto;
import com.lframework.xingyun.sc.facade.entity.ProductStock;
import com.lframework.xingyun.sc.facade.entity.TakeStockPlan;
import com.lframework.xingyun.sc.facade.entity.TakeStockPlanDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeStockPlanDetailServiceImpl extends
    BaseMpServiceImpl<TakeStockPlanDetailMapper, TakeStockPlanDetail>
    implements ITakeStockPlanDetailService {

  @Autowired
  private IProductStockService productStockService;

  @Autowired
  private ITakeStockPlanService takeStockPlanService;

  @Override
  public GetTakeStockPlanDetailProductDto getByPlanIdAndProductId(String planId, String productId) {

    return getBaseMapper().getByPlanIdAndProductId(planId, productId);
  }

  @Transactional
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

  @Transactional
  @Override
  public void updateOriTakeNum(String planId, String productId, Integer num) {

    getBaseMapper().updateOriTakeNum(planId, productId, num);
  }
}
