package com.lframework.xingyun.sc.impl.stock.adjust;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.ClientException;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.service.IGenerateCodeService;
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.purchase.ProductPurchaseDto;
import com.lframework.xingyun.basedata.service.product.IProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.stock.ProductStockDto;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustSheetDto;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustSheetFullDto;
import com.lframework.xingyun.sc.entity.StockCostAdjustSheet;
import com.lframework.xingyun.sc.entity.StockCostAdjustSheetDetail;
import com.lframework.xingyun.sc.enums.StockCostAdjustSheetStatus;
import com.lframework.xingyun.sc.mappers.StockCostAdjustSheetDetailMapper;
import com.lframework.xingyun.sc.mappers.StockCostAdjustSheetMapper;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import com.lframework.xingyun.sc.service.stock.adjust.IStockCostAdjustSheetService;
import com.lframework.xingyun.sc.vo.stock.StockCostAdjustVo;
import com.lframework.xingyun.sc.vo.stock.adjust.ApprovePassStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.ApproveRefuseStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.BatchApprovePassStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.BatchApproveRefuseStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.CreateStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.QueryStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.StockCostAdjustProductVo;
import com.lframework.xingyun.sc.vo.stock.adjust.UpdateStockCostAdjustSheetVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockCostAdjustSheetServiceImpl implements IStockCostAdjustSheetService {

  @Autowired
  private StockCostAdjustSheetMapper stockCostAdjustSheetMapper;

  @Autowired
  private StockCostAdjustSheetDetailMapper stockCostAdjustSheetDetailMapper;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Autowired
  private IProductStockService productStockService;

  @Autowired
  private IProductPurchaseService productPurchaseService;

  @Autowired
  private IProductService productService;

  @Override
  public PageResult<StockCostAdjustSheetDto> query(Integer pageIndex, Integer pageSize,
      QueryStockCostAdjustSheetVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<StockCostAdjustSheetDto> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<StockCostAdjustSheetDto> query(QueryStockCostAdjustSheetVo vo) {

    return stockCostAdjustSheetMapper.query(vo);
  }

  @Override
  public StockCostAdjustSheetDto getById(String id) {

    return stockCostAdjustSheetMapper.getById(id);
  }

  @Override
  public StockCostAdjustSheetFullDto getDetail(String id) {

    return stockCostAdjustSheetMapper.getDetail(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增库存成本调整单，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public String create(CreateStockCostAdjustSheetVo vo) {

    StockCostAdjustSheet data = new StockCostAdjustSheet();
    data.setId(IdUtil.getId());
    data.setCode(generateCodeService.generate(GenerateCodeTypePool.STOCK_COST_ADJUST_SHEET));

    this.create(data, vo);

    stockCostAdjustSheetMapper.insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改库存成本调整单，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public void update(UpdateStockCostAdjustSheetVo vo) {

    StockCostAdjustSheet data = stockCostAdjustSheetMapper.selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("库存成本调整单不存在！");
    }

    if (data.getStatus() != StockCostAdjustSheetStatus.CREATED
        && data.getStatus() != StockCostAdjustSheetStatus.APPROVE_REFUSE) {

      if (data.getStatus() == StockCostAdjustSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("库存成本调整单已审核通过，无法修改！");
      }

      throw new DefaultClientException("库存成本调整单无法修改！");
    }

    // 删除出库单明细
    Wrapper<StockCostAdjustSheetDetail> deleteDetailWrapper = Wrappers
        .lambdaQuery(StockCostAdjustSheetDetail.class)
        .eq(StockCostAdjustSheetDetail::getSheetId, data.getId());
    stockCostAdjustSheetDetailMapper.delete(deleteDetailWrapper);

    this.create(data, vo);

    data.setStatus(StockCostAdjustSheetStatus.CREATED);

    List<StockCostAdjustSheetStatus> statusList = new ArrayList<>();
    statusList.add(StockCostAdjustSheetStatus.CREATED);
    statusList.add(StockCostAdjustSheetStatus.APPROVE_REFUSE);

    Wrapper<StockCostAdjustSheet> updateSheetWrapper = Wrappers
        .lambdaUpdate(StockCostAdjustSheet.class)
        .set(StockCostAdjustSheet::getApproveBy, null)
        .set(StockCostAdjustSheet::getApproveTime, null)
        .set(StockCostAdjustSheet::getRefuseReason, StringPool.EMPTY_STR)
        .eq(StockCostAdjustSheet::getId, data.getId())
        .in(StockCostAdjustSheet::getStatus, statusList);
    if (stockCostAdjustSheetMapper.update(data, updateSheetWrapper) != 1) {
      throw new DefaultClientException("库存成本调整单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "删除库存成本调整单，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public void deleteById(String id) {
    StockCostAdjustSheet data = stockCostAdjustSheetMapper.selectById(id);
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("库存成本调整单不存在！");
    }

    if (data.getStatus() == StockCostAdjustSheetStatus.APPROVE_PASS) {
      throw new DefaultClientException("“审核通过”的库存成本调整单不允许执行删除操作！");
    }

    Wrapper<StockCostAdjustSheet> deleteWrapper = Wrappers.lambdaQuery(StockCostAdjustSheet.class)
        .eq(StockCostAdjustSheet::getId, id)
        .in(StockCostAdjustSheet::getStatus, StockCostAdjustSheetStatus.CREATED,
            StockCostAdjustSheetStatus.APPROVE_REFUSE);
    if (stockCostAdjustSheetMapper.delete(deleteWrapper) != 1) {
      throw new DefaultClientException("库存成本调整单信息已过期，请刷新重试！");
    }

    Wrapper<StockCostAdjustSheetDetail> deleteDetailWrapper = Wrappers
        .lambdaQuery(StockCostAdjustSheetDetail.class)
        .eq(StockCostAdjustSheetDetail::getSheetId, id);
    stockCostAdjustSheetDetailMapper.delete(deleteDetailWrapper);
  }

  @Transactional
  @Override
  public void deleteByIds(List<String> ids) {
    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          IStockCostAdjustSheetService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException("第" + orderNo + "个库存成本调整单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "审核通过库存成本调整单，ID：{}", params = {"#vo.id"})
  @Transactional
  @Override
  public void approvePass(ApprovePassStockCostAdjustSheetVo vo) {
    StockCostAdjustSheet data = stockCostAdjustSheetMapper.selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("库存成本调整单不存在！");
    }

    if (data.getStatus() != StockCostAdjustSheetStatus.CREATED
        && data.getStatus() != StockCostAdjustSheetStatus.APPROVE_REFUSE) {

      if (data.getStatus() == StockCostAdjustSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("库存成本调整单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("库存成本调整单无法审核通过！");
    }

    LocalDateTime now = LocalDateTime.now();
    Wrapper<StockCostAdjustSheet> updateWrapper = Wrappers.lambdaUpdate(StockCostAdjustSheet.class)
        .eq(StockCostAdjustSheet::getId, data.getId())
        .in(StockCostAdjustSheet::getStatus, StockCostAdjustSheetStatus.CREATED,
            StockCostAdjustSheetStatus.APPROVE_REFUSE)
        .set(StockCostAdjustSheet::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(StockCostAdjustSheet::getApproveTime, now)
        .set(StockCostAdjustSheet::getStatus, StockCostAdjustSheetStatus.APPROVE_PASS)
        .set(StockCostAdjustSheet::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    if (stockCostAdjustSheetMapper.update(updateWrapper) != 1) {
      throw new DefaultClientException("库存成本调整单信息已过期，请刷新重试！");
    }

    Wrapper<StockCostAdjustSheetDetail> queryDetailWrapper = Wrappers
        .lambdaQuery(StockCostAdjustSheetDetail.class)
        .eq(StockCostAdjustSheetDetail::getSheetId, data.getId())
        .orderByAsc(StockCostAdjustSheetDetail::getOrderNo);
    List<StockCostAdjustSheetDetail> details = stockCostAdjustSheetDetailMapper
        .selectList(queryDetailWrapper);

    for (StockCostAdjustSheetDetail detail : details) {
      ProductDto product = productService.getById(detail.getProductId());
      StockCostAdjustVo adjustVo = new StockCostAdjustVo();
      adjustVo.setProductId(detail.getProductId());
      adjustVo.setScId(data.getScId());
      adjustVo.setTaxPrice(detail.getPrice());
      adjustVo.setTaxRate(product.getPoly().getTaxRate());
      adjustVo.setCreateTime(now);
      adjustVo.setBizId(data.getId());
      adjustVo.setBizDetailId(detail.getId());
      adjustVo.setBizCode(data.getCode());

      productStockService.stockCostAdjust(adjustVo);
    }
  }

  @Transactional
  @Override
  public void batchApprovePass(BatchApprovePassStockCostAdjustSheetVo vo) {
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassStockCostAdjustSheetVo approvePassVo = new ApprovePassStockCostAdjustSheetVo();
      approvePassVo.setId(id);

      try {
        IStockCostAdjustSheetService thisService = getThis(this.getClass());
        thisService.approvePass(approvePassVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个库存成本调整单审核通过失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @Override
  public void directApprovePass(CreateStockCostAdjustSheetVo vo) {
    IStockCostAdjustSheetService thisService = getThis(this.getClass());

    String id = thisService.create(vo);

    ApprovePassStockCostAdjustSheetVo approvePassVo = new ApprovePassStockCostAdjustSheetVo();
    approvePassVo.setId(id);
    approvePassVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassVo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核拒绝库存成本调整单，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public void approveRefuse(ApproveRefuseStockCostAdjustSheetVo vo) {
    StockCostAdjustSheet data = stockCostAdjustSheetMapper.selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("库存成本调整单不存在！");
    }

    if (data.getStatus() != StockCostAdjustSheetStatus.CREATED
        && data.getStatus() != StockCostAdjustSheetStatus.APPROVE_REFUSE) {

      if (data.getStatus() == StockCostAdjustSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("库存成本调整单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("库存成本调整单无法审核通过！");
    }

    Wrapper<StockCostAdjustSheet> updateWrapper = Wrappers.lambdaUpdate(StockCostAdjustSheet.class)
        .eq(StockCostAdjustSheet::getId, data.getId())
        .in(StockCostAdjustSheet::getStatus, StockCostAdjustSheetStatus.CREATED,
            StockCostAdjustSheetStatus.APPROVE_REFUSE)
        .set(StockCostAdjustSheet::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(StockCostAdjustSheet::getApproveTime, LocalDateTime.now())
        .set(StockCostAdjustSheet::getRefuseReason, vo.getRefuseReason())
        .set(StockCostAdjustSheet::getStatus, StockCostAdjustSheetStatus.APPROVE_REFUSE);
    if (stockCostAdjustSheetMapper.update(updateWrapper) != 1) {
      throw new DefaultClientException("库存成本调整单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public void batchApproveRefuse(BatchApproveRefuseStockCostAdjustSheetVo vo) {
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseStockCostAdjustSheetVo approveRefuseVo = new ApproveRefuseStockCostAdjustSheetVo();
      approveRefuseVo.setId(id);
      approveRefuseVo.setRefuseReason(vo.getRefuseReason());

      try {
        IStockCostAdjustSheetService thisService = getThis(this.getClass());
        thisService.approveRefuse(approveRefuseVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个库存成本调整单审核拒绝失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @Override
  public void cleanCacheByKey(String key) {

  }

  private void create(StockCostAdjustSheet data, CreateStockCostAdjustSheetVo vo) {
    data.setScId(vo.getScId());
    data.setStatus(StockCostAdjustSheetStatus.CREATED);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    int productNum = 0;
    BigDecimal diffAmount = BigDecimal.ZERO;
    int orderNo = 1;
    for (StockCostAdjustProductVo product : vo.getProducts()) {
      StockCostAdjustSheetDetail detail = new StockCostAdjustSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(data.getId());
      detail.setProductId(product.getProductId());
      ProductStockDto productStock = productStockService
          .getByProductIdAndScId(product.getProductId(), data.getScId());
      ProductPurchaseDto productPurchase = productPurchaseService.getById(product.getProductId());
      detail.setStockNum(productStock == null ? 0 : productStock.getStockNum());
      detail.setPurchasePrice(productPurchase.getPrice());
      detail.setOriPrice(productStock == null ? BigDecimal.ZERO
          : NumberUtil.getNumber(productStock.getTaxPrice(), 2));
      detail.setPrice(product.getPrice());
      detail.setDiffAmount(NumberUtil.getNumber(NumberUtil
          .mul(NumberUtil.sub(detail.getPrice(), detail.getOriPrice()), detail.getStockNum()), 2));
      detail.setDescription(StringUtil.isBlank(product.getDescription()) ? StringPool.EMPTY_STR
          : product.getDescription());
      detail.setOrderNo(orderNo++);
      productNum++;

      diffAmount = NumberUtil.add(diffAmount, detail.getDiffAmount());

      stockCostAdjustSheetDetailMapper.insert(detail);
    }

    data.setProductNum(productNum);
    data.setDiffAmount(diffAmount);
  }
}
