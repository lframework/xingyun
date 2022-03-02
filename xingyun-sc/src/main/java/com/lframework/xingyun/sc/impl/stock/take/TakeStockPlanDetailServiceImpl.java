package com.lframework.xingyun.sc.impl.stock.take;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.xingyun.sc.dto.stock.ProductStockDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.GetTakeStockPlanDetailProductDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanDetailDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanDto;
import com.lframework.xingyun.sc.entity.TakeStockPlanDetail;
import com.lframework.xingyun.sc.mappers.TakeStockPlanDetailMapper;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanDetailService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TakeStockPlanDetailServiceImpl implements ITakeStockPlanDetailService {

    @Autowired
    private TakeStockPlanDetailMapper takeStockPlanDetailMapper;

    @Autowired
    private IProductStockService productStockService;

    @Autowired
    private ITakeStockPlanService takeStockPlanService;

    @Override
    public GetTakeStockPlanDetailProductDto getByPlanIdAndProductId(String planId, String productId) {

        return takeStockPlanDetailMapper.getByPlanIdAndProductId(planId, productId);
    }

    @Transactional
    @Override
    public void savePlanDetailBySimple(String planId, List<String> productIds) {

        TakeStockPlanDto takeStockPlan = takeStockPlanService.getById(planId);
        Wrapper<TakeStockPlanDetail> queryDetailWrapper = Wrappers.lambdaQuery(TakeStockPlanDetail.class).eq(TakeStockPlanDetail::getPlanId, planId).orderByAsc(TakeStockPlanDetail::getOrderNo);

        List<TakeStockPlanDetail> planDetails = takeStockPlanDetailMapper.selectList(queryDetailWrapper);

        int orderNo = planDetails.size() + 1;
        for (String productId : productIds) {
            if (planDetails.stream().anyMatch(t -> t.getProductId().equals(productId))) {
                continue;
            }

            TakeStockPlanDetail detail = new TakeStockPlanDetail();
            detail.setId(IdUtil.getId());
            detail.setPlanId(planId);
            detail.setProductId(productId);

            ProductStockDto productStock = productStockService.getByProductIdAndScId(productId, takeStockPlan.getScId());
            detail.setStockNum(productStock == null ? 0 : productStock.getStockNum());

            detail.setDescription(StringPool.EMPTY_STR);
            detail.setOrderNo(orderNo++);
            takeStockPlanDetailMapper.insert(detail);
        }
    }

    @Override
    public List<TakeStockPlanDetailDto> getDetailsByPlanId(String planId) {

        return takeStockPlanDetailMapper.getDetailsByPlanId(planId);
    }

    @Transactional
    @Override
    public void updateOriTakeNum(String planId, String productId, Integer num) {
        takeStockPlanDetailMapper.updateOriTakeNum(planId, productId, num);
    }
}
