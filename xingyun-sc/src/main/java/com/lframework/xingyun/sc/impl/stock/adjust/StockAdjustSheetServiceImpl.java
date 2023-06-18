package com.lframework.xingyun.sc.impl.stock.adjust;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.components.permission.DataPermissionHandler;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.components.permission.SysDataPermissionDataPermissionType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.starter.web.service.GenerateCodeService;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductPurchase;
import com.lframework.xingyun.basedata.service.product.ProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.core.annations.OrderTimeLineLog;
import com.lframework.xingyun.core.components.permission.DataPermissionPool;
import com.lframework.xingyun.core.enums.OrderTimeLineBizType;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustProductDto;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustSheetFullDto;
import com.lframework.xingyun.sc.entity.StockAdjustSheet;
import com.lframework.xingyun.sc.entity.StockAdjustSheetDetail;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import com.lframework.xingyun.sc.enums.StockAdjustSheetBizType;
import com.lframework.xingyun.sc.enums.StockAdjustSheetStatus;
import com.lframework.xingyun.sc.mappers.StockAdjustSheetMapper;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustSheetDetailService;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustSheetService;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.ApprovePassStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.ApproveRefuseStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.BatchApprovePassStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.BatchApproveRefuseStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.CreateStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.QueryStockAdjustProductVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.QueryStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.StockAdjustProductVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.UpdateStockAdjustSheetVo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockAdjustSheetServiceImpl extends
    BaseMpServiceImpl<StockAdjustSheetMapper, StockAdjustSheet>
    implements StockAdjustSheetService {

  @Autowired
  private StockAdjustSheetDetailService stockCostAdjustSheetDetailService;

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private ProductStockService productStockService;

  @Autowired
  private ProductPurchaseService productPurchaseService;

  @Autowired
  private ProductService productService;

  @Override
  public PageResult<StockAdjustSheet> query(Integer pageIndex, Integer pageSize,
      QueryStockAdjustSheetVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<StockAdjustSheet> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<StockAdjustSheet> query(QueryStockAdjustSheetVo vo) {

    return getBaseMapper().query(vo,
        DataPermissionHandler.getDataPermission(DataPermissionPool.ORDER,
            Arrays.asList("order"), Arrays.asList("tb")));
  }

  @Override
  public StockAdjustSheetFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "新增库存调整单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(type = OrderTimeLineBizType.CREATE, orderId = "#_result", name = "创建调整单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateStockAdjustSheetVo vo) {

    StockAdjustSheet data = new StockAdjustSheet();
    data.setId(IdUtil.getId());
    data.setCode(generateCodeService.generate(GenerateCodeTypePool.STOCK_ADJUST_SHEET));

    this.create(data, vo);

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改库存调整单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(type = OrderTimeLineBizType.UPDATE, orderId = "#vo.id", name = "修改调整单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateStockAdjustSheetVo vo) {

    StockAdjustSheet data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("库存调整单不存在！");
    }

    if (data.getStatus() != StockAdjustSheetStatus.CREATED
        && data.getStatus() != StockAdjustSheetStatus.APPROVE_REFUSE) {

      if (data.getStatus() == StockAdjustSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("库存调整单已审核通过，无法修改！");
      }

      throw new DefaultClientException("库存调整单无法修改！");
    }

    // 删除出库单明细
    Wrapper<StockAdjustSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            StockAdjustSheetDetail.class)
        .eq(StockAdjustSheetDetail::getSheetId, data.getId());
    stockCostAdjustSheetDetailService.remove(deleteDetailWrapper);

    this.create(data, vo);

    data.setStatus(StockAdjustSheetStatus.CREATED);

    List<StockAdjustSheetStatus> statusList = new ArrayList<>();
    statusList.add(StockAdjustSheetStatus.CREATED);
    statusList.add(StockAdjustSheetStatus.APPROVE_REFUSE);

    Wrapper<StockAdjustSheet> updateSheetWrapper = Wrappers.lambdaUpdate(
            StockAdjustSheet.class)
        .set(StockAdjustSheet::getApproveBy, null)
        .set(StockAdjustSheet::getApproveTime, null)
        .set(StockAdjustSheet::getRefuseReason, StringPool.EMPTY_STR)
        .eq(StockAdjustSheet::getId, data.getId())
        .in(StockAdjustSheet::getStatus, statusList);
    if (getBaseMapper().updateAllColumn(data, updateSheetWrapper) != 1) {
      throw new DefaultClientException("库存调整单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "删除库存调整单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(orderId = "#id", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    StockAdjustSheet data = getBaseMapper().selectById(id);
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("库存调整单不存在！");
    }

    if (data.getStatus() == StockAdjustSheetStatus.APPROVE_PASS) {
      throw new DefaultClientException("“审核通过”的库存调整单不允许执行删除操作！");
    }

    Wrapper<StockAdjustSheet> deleteWrapper = Wrappers.lambdaQuery(StockAdjustSheet.class)
        .eq(StockAdjustSheet::getId, id)
        .in(StockAdjustSheet::getStatus, StockAdjustSheetStatus.CREATED,
            StockAdjustSheetStatus.APPROVE_REFUSE);
    if (getBaseMapper().delete(deleteWrapper) != 1) {
      throw new DefaultClientException("库存调整单信息已过期，请刷新重试！");
    }

    Wrapper<StockAdjustSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            StockAdjustSheetDetail.class)
        .eq(StockAdjustSheetDetail::getSheetId, id);
    stockCostAdjustSheetDetailService.remove(deleteDetailWrapper);
  }

  @OrderTimeLineLog(orderId = "#ids", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          StockAdjustSheetService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException(
              "第" + orderNo + "个库存调整单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "审核通过库存调整单，ID：{}", params = {"#vo.id"})
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.id", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approvePass(ApprovePassStockAdjustSheetVo vo) {

    StockAdjustSheet data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("库存调整单不存在！");
    }

    if (data.getStatus() != StockAdjustSheetStatus.CREATED
        && data.getStatus() != StockAdjustSheetStatus.APPROVE_REFUSE) {

      if (data.getStatus() == StockAdjustSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("库存调整单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("库存调整单无法审核通过！");
    }

    LocalDateTime now = LocalDateTime.now();
    Wrapper<StockAdjustSheet> updateWrapper = Wrappers.lambdaUpdate(StockAdjustSheet.class)
        .eq(StockAdjustSheet::getId, data.getId())
        .in(StockAdjustSheet::getStatus, StockAdjustSheetStatus.CREATED,
            StockAdjustSheetStatus.APPROVE_REFUSE)
        .set(StockAdjustSheet::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(StockAdjustSheet::getApproveTime, now)
        .set(StockAdjustSheet::getStatus, StockAdjustSheetStatus.APPROVE_PASS)
        .set(StockAdjustSheet::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("库存调整单信息已过期，请刷新重试！");
    }

    Wrapper<StockAdjustSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            StockAdjustSheetDetail.class)
        .eq(StockAdjustSheetDetail::getSheetId, data.getId())
        .orderByAsc(StockAdjustSheetDetail::getOrderNo);
    List<StockAdjustSheetDetail> details = stockCostAdjustSheetDetailService.list(
        queryDetailWrapper);

    for (StockAdjustSheetDetail detail : details) {
      Product product = productService.findById(detail.getProductId());
      ProductPurchase productPurchase = productPurchaseService.getById(product.getId());
      if (data.getBizType() == StockAdjustSheetBizType.IN) {
        // 入库
        AddProductStockVo addProductStockVo = new AddProductStockVo();
        addProductStockVo.setProductId(product.getId());
        addProductStockVo.setScId(data.getScId());
        addProductStockVo.setStockNum(detail.getStockNum());
        addProductStockVo.setDefaultTaxPrice(productPurchase.getPrice());
        addProductStockVo.setCreateTime(now);
        addProductStockVo.setBizId(data.getId());
        addProductStockVo.setBizDetailId(detail.getId());
        addProductStockVo.setBizCode(data.getCode());
        addProductStockVo.setBizType(ProductStockBizType.STOCK_ADJUST.getCode());

        productStockService.addStock(addProductStockVo);
      } else {
        SubProductStockVo subProductStockVo = new SubProductStockVo();
        subProductStockVo.setProductId(product.getId());
        subProductStockVo.setScId(data.getScId());
        subProductStockVo.setStockNum(detail.getStockNum());
        subProductStockVo.setCreateTime(now);
        subProductStockVo.setBizId(data.getId());
        subProductStockVo.setBizDetailId(detail.getId());
        subProductStockVo.setBizCode(data.getCode());
        subProductStockVo.setBizType(ProductStockBizType.STOCK_ADJUST.getCode());

        productStockService.subStock(subProductStockVo);
      }
    }
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.ids", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchApprovePass(BatchApprovePassStockAdjustSheetVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassStockAdjustSheetVo approvePassVo = new ApprovePassStockAdjustSheetVo();
      approvePassVo.setId(id);

      try {
        StockAdjustSheetService thisService = getThis(this.getClass());
        thisService.approvePass(approvePassVo);
      } catch (ClientException e) {
        throw new DefaultClientException(
            "第" + orderNo + "个库存调整单审核通过失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#_result", name = "直接审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String directApprovePass(CreateStockAdjustSheetVo vo) {

    StockAdjustSheetService thisService = getThis(this.getClass());

    String id = thisService.create(vo);

    ApprovePassStockAdjustSheetVo approvePassVo = new ApprovePassStockAdjustSheetVo();
    approvePassVo.setId(id);
    approvePassVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassVo);

    return id;
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "审核拒绝库存调整单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approveRefuse(ApproveRefuseStockAdjustSheetVo vo) {

    StockAdjustSheet data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("库存调整单不存在！");
    }

    if (data.getStatus() != StockAdjustSheetStatus.CREATED
        && data.getStatus() != StockAdjustSheetStatus.APPROVE_REFUSE) {

      if (data.getStatus() == StockAdjustSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("库存调整单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("库存调整单无法审核通过！");
    }

    Wrapper<StockAdjustSheet> updateWrapper = Wrappers.lambdaUpdate(StockAdjustSheet.class)
        .eq(StockAdjustSheet::getId, data.getId())
        .in(StockAdjustSheet::getStatus, StockAdjustSheetStatus.CREATED,
            StockAdjustSheetStatus.APPROVE_REFUSE)
        .set(StockAdjustSheet::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(StockAdjustSheet::getApproveTime, LocalDateTime.now())
        .set(StockAdjustSheet::getRefuseReason, vo.getRefuseReason())
        .set(StockAdjustSheet::getStatus, StockAdjustSheetStatus.APPROVE_REFUSE);
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("库存调整单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.ids", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchApproveRefuse(BatchApproveRefuseStockAdjustSheetVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseStockAdjustSheetVo approveRefuseVo = new ApproveRefuseStockAdjustSheetVo();
      approveRefuseVo.setId(id);
      approveRefuseVo.setRefuseReason(vo.getRefuseReason());

      try {
        StockAdjustSheetService thisService = getThis(this.getClass());
        thisService.approveRefuse(approveRefuseVo);
      } catch (ClientException e) {
        throw new DefaultClientException(
            "第" + orderNo + "个库存调整单审核拒绝失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }


  @Override
  public PageResult<StockAdjustProductDto> queryStockAdjustByCondition(Integer pageIndex,
      Integer pageSize, String scId, String condition) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<StockAdjustProductDto> datas = getBaseMapper().queryStockAdjustByCondition(scId,
        condition,
        DataPermissionHandler.getDataPermission(
            DataPermissionPool.PRODUCT,
            Arrays.asList("product", "brand", "category"),
            Arrays.asList("g", "b", "c")));
    PageResult<StockAdjustProductDto> pageResult = PageResultUtil.convert(
        new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public PageResult<StockAdjustProductDto> queryStockAdjustList(Integer pageIndex,
      Integer pageSize, QueryStockAdjustProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<StockAdjustProductDto> datas = getBaseMapper().queryStockAdjustList(vo,
        DataPermissionHandler.getDataPermission(
            DataPermissionPool.PRODUCT,
            Arrays.asList("product", "brand", "category"),
            Arrays.asList("g", "b", "c")));
    PageResult<StockAdjustProductDto> pageResult = PageResultUtil.convert(
        new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public void cleanCacheByKey(Serializable key) {

  }

  private void create(StockAdjustSheet data, CreateStockAdjustSheetVo vo) {

    data.setScId(vo.getScId());
    data.setStatus(StockAdjustSheetStatus.CREATED);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    data.setBizType(EnumUtil.getByCode(StockAdjustSheetBizType.class, vo.getBizType()));
    data.setReasonId(vo.getReasonId());

    int productNum = 0;
    BigDecimal diffAmount = BigDecimal.ZERO;
    int orderNo = 1;
    for (StockAdjustProductVo product : vo.getProducts()) {
      StockAdjustSheetDetail detail = new StockAdjustSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(data.getId());
      detail.setProductId(product.getProductId());
      detail.setStockNum(product.getStockNum());
      detail.setDescription(
          StringUtil.isBlank(product.getDescription()) ? StringPool.EMPTY_STR
              : product.getDescription());
      detail.setOrderNo(orderNo++);
      productNum++;

      stockCostAdjustSheetDetailService.save(detail);
    }
  }
}
