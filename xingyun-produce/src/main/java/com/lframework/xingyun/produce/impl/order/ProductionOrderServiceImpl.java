package com.lframework.xingyun.produce.impl.order;

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
import com.lframework.xingyun.produce.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.produce.dto.bom.BomDetailDto;
import com.lframework.xingyun.produce.entity.Bom;
import com.lframework.xingyun.produce.entity.ProductionOrder;
import com.lframework.xingyun.produce.entity.ProductionOrderDetail;
import com.lframework.xingyun.produce.enums.BomStatus;
import com.lframework.xingyun.produce.enums.ProduceOpLogType;
import com.lframework.xingyun.produce.enums.ProductionOrderStatus;
import com.lframework.xingyun.produce.mappers.ProductionOrderMapper;
import com.lframework.xingyun.produce.service.bom.BomDetailService;
import com.lframework.xingyun.produce.service.bom.BomService;
import com.lframework.xingyun.produce.service.order.ProductionOrderDetailService;
import com.lframework.xingyun.produce.service.order.ProductionOrderService;
import com.lframework.xingyun.produce.vo.order.CompleteProductionOrderVo;
import com.lframework.xingyun.produce.vo.order.CreateProductionOrderVo;
import com.lframework.xingyun.produce.vo.order.QueryProductionOrderVo;
import com.lframework.xingyun.produce.vo.order.UpdateProductionOrderVo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductionOrderServiceImpl
    extends BaseMpServiceImpl<ProductionOrderMapper, ProductionOrder>
    implements ProductionOrderService {

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private BomService bomService;

  @Autowired
  private BomDetailService bomDetailService;

  @Autowired
  private ProductionOrderDetailService detailService;

  @Override
  public PageResult<ProductionOrder> query(Integer pageIndex, Integer pageSize,
      QueryProductionOrderVo vo) {
    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductionOrder> datas = getBaseMapper().query(vo);
    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = ProduceOpLogType.class, name = "新增生产工单，编号：{}", params = "#code")
  @Transactional
  @Override
  public String create(CreateProductionOrderVo vo) {
    Bom bom = bomService.getById(vo.getBomId());
    if (bom == null) {
      throw new DefaultClientException("BOM不存在！");
    }
    if (bom.getStatus() != BomStatus.APPROVE_PASS) {
      throw new DefaultClientException("BOM未审核通过，不能创建工单！");
    }

    ProductionOrder order = new ProductionOrder();
    order.setId(IdUtil.getId());
    order.setCode(generateCodeService.generate(GenerateCodeTypePool.PRODUCTION_ORDER));
    order.setBomId(bom.getId());
    order.setProductId(bom.getMainProductId());
    order.setSkuId(bom.getMainSkuId());
    order.setScId(vo.getScId());
    order.setPlanNum(vo.getPlanNum());
    order.setPlanStartDate(LocalDate.parse(vo.getPlanStartDate()));
    order.setPlanEndDate(LocalDate.parse(vo.getPlanEndDate()));
    order.setSaleOrderId(vo.getSaleOrderId());
    order.setOwnerId(vo.getOwnerId());
    order.setStatus(ProductionOrderStatus.PENDING);
    order.setDescription(vo.getDescription());
    getBaseMapper().insert(order);

    // 展开 BOM 生成工单明细
    List<BomDetailDto> bomDetails = bomDetailService.listByBomId(bom.getId());
    int seq = 1;
    for (BomDetailDto detail : bomDetails) {
      ProductionOrderDetail d = new ProductionOrderDetail();
      d.setId(IdUtil.getId());
      d.setOrderId(order.getId());
      d.setProductId(detail.getProductId());
      d.setSkuId(detail.getSkuId());
      d.setBomNum(detail.getUnitNum());
      // requireNum = unitNum × planNum，向上取整保留2位小数
      BigDecimal require = detail.getUnitNum().multiply(vo.getPlanNum())
          .setScale(2, java.math.RoundingMode.CEILING);
      d.setRequireNum(require);
      d.setOrderNo(seq++);
      detailService.save(d);
    }

    return order.getId();
  }

  @OpLog(type = ProduceOpLogType.class, name = "修改生产工单，编号：{}", params = "#code")
  @Transactional
  @Override
  public void update(UpdateProductionOrderVo vo) {
    ProductionOrder order = getById(vo.getId());
    if (order == null) {
      throw new DefaultClientException("生产工单不存在！");
    }
    if (order.getStatus() == ProductionOrderStatus.COMPLETED
        || order.getStatus() == ProductionOrderStatus.CANCELLED) {
      throw new DefaultClientException("已完工或已取消的工单不允许修改！");
    }
    LambdaUpdateWrapper<ProductionOrder> wrapper = Wrappers.<ProductionOrder>lambdaUpdate()
        .set(vo.getPlanStartDate() != null, ProductionOrder::getPlanStartDate,
            vo.getPlanStartDate() != null ? LocalDate.parse(vo.getPlanStartDate()) : null)
        .set(vo.getPlanEndDate() != null, ProductionOrder::getPlanEndDate,
            vo.getPlanEndDate() != null ? LocalDate.parse(vo.getPlanEndDate()) : null)
        .set(ProductionOrder::getOwnerId, vo.getOwnerId())
        .set(ProductionOrder::getDescription, vo.getDescription())
        // 有排产日期即标记为已排产
        .set(vo.getPlanStartDate() != null && order.getStatus() == ProductionOrderStatus.PENDING,
            ProductionOrder::getStatus, ProductionOrderStatus.SCHEDULED)
        .eq(ProductionOrder::getId, vo.getId());
    update(wrapper);
  }

  @OpLog(type = ProduceOpLogType.class, name = "开始生产，工单编号：{}", params = "#code")
  @Transactional
  @Override
  public void start(String id) {
    ProductionOrder order = getById(id);
    if (order == null) {
      throw new DefaultClientException("生产工单不存在！");
    }
    if (order.getStatus() != ProductionOrderStatus.PENDING
        && order.getStatus() != ProductionOrderStatus.SCHEDULED) {
      throw new DefaultClientException("只有待排产或已排产的工单才能开始生产！");
    }
    LambdaUpdateWrapper<ProductionOrder> wrapper = Wrappers.<ProductionOrder>lambdaUpdate()
        .set(ProductionOrder::getStatus, ProductionOrderStatus.IN_PROGRESS)
        .eq(ProductionOrder::getId, id);
    update(wrapper);
  }

  @OpLog(type = ProduceOpLogType.class, name = "完工，工单编号：{}", params = "#code")
  @Transactional
  @Override
  public void complete(CompleteProductionOrderVo vo) {
    ProductionOrder order = getById(vo.getId());
    if (order == null) {
      throw new DefaultClientException("生产工单不存在！");
    }
    if (order.getStatus() != ProductionOrderStatus.IN_PROGRESS) {
      throw new DefaultClientException("只有生产中的工单才能完工！");
    }
    if (vo.getActualNum().compareTo(BigDecimal.ZERO) <= 0) {
      throw new DefaultClientException("实际生产数量必须大于0！");
    }
    LocalDate endDate = vo.getActualEndDate() != null
        ? LocalDate.parse(vo.getActualEndDate()) : LocalDate.now();
    LambdaUpdateWrapper<ProductionOrder> wrapper = Wrappers.<ProductionOrder>lambdaUpdate()
        .set(ProductionOrder::getStatus, ProductionOrderStatus.COMPLETED)
        .set(ProductionOrder::getActualNum, vo.getActualNum())
        .set(ProductionOrder::getActualEndDate, endDate)
        .set(vo.getDescription() != null, ProductionOrder::getDescription, vo.getDescription())
        .eq(ProductionOrder::getId, vo.getId());
    update(wrapper);
  }

  @OpLog(type = ProduceOpLogType.class, name = "取消生产工单，编号：{}", params = "#code")
  @Transactional
  @Override
  public void cancel(String id) {
    ProductionOrder order = getById(id);
    if (order == null) {
      throw new DefaultClientException("生产工单不存在！");
    }
    if (order.getStatus() == ProductionOrderStatus.COMPLETED) {
      throw new DefaultClientException("已完工的工单不允许取消！");
    }
    if (order.getStatus() == ProductionOrderStatus.CANCELLED) {
      throw new DefaultClientException("工单已取消！");
    }
    LambdaUpdateWrapper<ProductionOrder> wrapper = Wrappers.<ProductionOrder>lambdaUpdate()
        .set(ProductionOrder::getStatus, ProductionOrderStatus.CANCELLED)
        .eq(ProductionOrder::getId, id);
    update(wrapper);
  }

  @OpLog(type = ProduceOpLogType.class, name = "删除生产工单，编号：{}", params = "#code")
  @Transactional
  @Override
  public void deleteById(String id) {
    ProductionOrder order = getById(id);
    if (order == null) {
      throw new DefaultClientException("生产工单不存在！");
    }
    if (order.getStatus() != ProductionOrderStatus.PENDING
        && order.getStatus() != ProductionOrderStatus.CANCELLED) {
      throw new DefaultClientException("只有待排产或已取消的工单才能删除！");
    }
    detailService.deleteByOrderId(id);
    removeById(id);
  }
}
