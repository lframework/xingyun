package com.lframework.xingyun.sc.impl.purchase;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.IdUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.service.GenerateCodeService;
import com.lframework.xingyun.produce.entity.ProductionOrder;
import com.lframework.xingyun.produce.entity.ProductionOrderDetail;
import com.lframework.xingyun.produce.service.order.ProductionOrderDetailService;
import com.lframework.xingyun.produce.service.order.ProductionOrderService;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.entity.PurchaseRequisition;
import com.lframework.xingyun.sc.entity.PurchaseRequisitionDetail;
import com.lframework.xingyun.sc.enums.PurchaseOpLogType;
import com.lframework.xingyun.sc.enums.PurchaseRequisitionStatus;
import com.lframework.xingyun.sc.mappers.PurchaseRequisitionMapper;
import com.lframework.xingyun.sc.service.purchase.PurchaseRequisitionDetailService;
import com.lframework.xingyun.sc.service.purchase.PurchaseRequisitionService;
import com.lframework.xingyun.sc.vo.purchase.requisition.AdjustRequisitionDetailVo;
import com.lframework.xingyun.sc.vo.purchase.requisition.QueryPurchaseRequisitionVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseRequisitionServiceImpl
    extends BaseMpServiceImpl<PurchaseRequisitionMapper, PurchaseRequisition>
    implements PurchaseRequisitionService {

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private ProductionOrderService productionOrderService;

  @Autowired
  private ProductionOrderDetailService productionOrderDetailService;

  @Autowired
  private PurchaseRequisitionDetailService detailService;

  @Override
  public PageResult<PurchaseRequisition> query(Integer pageIndex, Integer pageSize,
      QueryPurchaseRequisitionVo vo) {
    PageHelperUtil.startPage(pageIndex, pageSize);
    List<PurchaseRequisition> datas = getBaseMapper().query(vo);
    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = PurchaseOpLogType.class, name = "从生产工单生成采购建议单，工单ID：{}", params = "#productionOrderId")
  @Transactional
  @Override
  public String generateFromProductionOrder(String productionOrderId) {
    ProductionOrder order = productionOrderService.getById(productionOrderId);
    if (order == null) {
      throw new DefaultClientException("生产工单不存在！");
    }

    // 检查同一工单是否已存在未取消的建议单
    LambdaQueryWrapper<PurchaseRequisition> existCheck = Wrappers.<PurchaseRequisition>lambdaQuery()
        .eq(PurchaseRequisition::getProductionOrderId, productionOrderId)
        .ne(PurchaseRequisition::getStatus, PurchaseRequisitionStatus.CANCELLED);
    if (count(existCheck) > 0) {
      throw new DefaultClientException("该生产工单已存在采购建议单，不能重复生成！");
    }

    PurchaseRequisition req = new PurchaseRequisition();
    req.setId(IdUtil.getId());
    req.setCode(generateCodeService.generate(GenerateCodeTypePool.PURCHASE_REQUISITION));
    req.setProductionOrderId(productionOrderId);
    req.setScId(order.getScId());
    req.setStatus(PurchaseRequisitionStatus.PENDING);
    save(req);

    List<ProductionOrderDetail> details =
        productionOrderDetailService.listByOrderId(productionOrderId);
    int seq = 1;
    for (ProductionOrderDetail d : details) {
      PurchaseRequisitionDetail rd = new PurchaseRequisitionDetail();
      rd.setId(IdUtil.getId());
      rd.setRequisitionId(req.getId());
      rd.setProductId(d.getProductId());
      rd.setSkuId(d.getSkuId());
      rd.setRequireNum(d.getRequireNum());
      rd.setSuggestNum(d.getRequireNum());
      rd.setOrderNo(seq++);
      detailService.save(rd);
    }

    return req.getId();
  }

  @OpLog(type = PurchaseOpLogType.class, name = "确认采购建议单，编号：{}", params = "#code")
  @Transactional
  @Override
  public void confirm(String id) {
    PurchaseRequisition req = getById(id);
    if (req == null) {
      throw new DefaultClientException("采购建议单不存在！");
    }
    if (req.getStatus() != PurchaseRequisitionStatus.PENDING) {
      throw new DefaultClientException("只有待确认的建议单才能确认！");
    }
    LambdaUpdateWrapper<PurchaseRequisition> wrapper = Wrappers.<PurchaseRequisition>lambdaUpdate()
        .set(PurchaseRequisition::getStatus, PurchaseRequisitionStatus.CONFIRMED)
        .eq(PurchaseRequisition::getId, id);
    update(wrapper);
  }

  @OpLog(type = PurchaseOpLogType.class, name = "调整采购建议单明细，明细ID：{}", params = "#vo.id")
  @Transactional
  @Override
  public void adjustDetail(AdjustRequisitionDetailVo vo) {
    PurchaseRequisitionDetail detail = detailService.getById(vo.getId());
    if (detail == null) {
      throw new DefaultClientException("明细不存在！");
    }
    PurchaseRequisition req = getById(detail.getRequisitionId());
    if (req.getStatus() == PurchaseRequisitionStatus.CONVERTED
        || req.getStatus() == PurchaseRequisitionStatus.CANCELLED) {
      throw new DefaultClientException("已转单或已取消的建议单不允许修改！");
    }
    if (vo.getSuggestNum().compareTo(java.math.BigDecimal.ZERO) <= 0) {
      throw new DefaultClientException("建议采购数量必须大于0！");
    }
    LambdaUpdateWrapper<PurchaseRequisitionDetail> wrapper =
        Wrappers.<PurchaseRequisitionDetail>lambdaUpdate()
            .set(PurchaseRequisitionDetail::getSuggestNum, vo.getSuggestNum())
            .eq(PurchaseRequisitionDetail::getId, vo.getId());
    detailService.update(wrapper);
  }

  @OpLog(type = PurchaseOpLogType.class, name = "采购建议单转采购订单，编号：{}", params = "#code")
  @Transactional
  @Override
  public String convertToPurchaseOrder(String id) {
    PurchaseRequisition req = getById(id);
    if (req == null) {
      throw new DefaultClientException("采购建议单不存在！");
    }
    if (req.getStatus() == PurchaseRequisitionStatus.CONVERTED) {
      throw new DefaultClientException("建议单已转采购订单！");
    }
    if (req.getStatus() == PurchaseRequisitionStatus.CANCELLED) {
      throw new DefaultClientException("已取消的建议单不能转单！");
    }
    LambdaUpdateWrapper<PurchaseRequisition> wrapper = Wrappers.<PurchaseRequisition>lambdaUpdate()
        .set(PurchaseRequisition::getStatus, PurchaseRequisitionStatus.CONVERTED)
        .eq(PurchaseRequisition::getId, id);
    update(wrapper);
    // 返回建议单ID，前端以此查询明细并预填采购订单
    return id;
  }

  @OpLog(type = PurchaseOpLogType.class, name = "取消采购建议单，编号：{}", params = "#code")
  @Transactional
  @Override
  public void cancel(String id) {
    PurchaseRequisition req = getById(id);
    if (req == null) {
      throw new DefaultClientException("采购建议单不存在！");
    }
    if (req.getStatus() == PurchaseRequisitionStatus.CONVERTED) {
      throw new DefaultClientException("已转采购订单的建议单不能取消！");
    }
    if (req.getStatus() == PurchaseRequisitionStatus.CANCELLED) {
      throw new DefaultClientException("建议单已取消！");
    }
    LambdaUpdateWrapper<PurchaseRequisition> wrapper = Wrappers.<PurchaseRequisition>lambdaUpdate()
        .set(PurchaseRequisition::getStatus, PurchaseRequisitionStatus.CANCELLED)
        .eq(PurchaseRequisition::getId, id);
    update(wrapper);
  }
}
