package com.lframework.xingyun.sc.impl.stock.transfer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.components.permission.DataPermissionHandler;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.enums.system.SysDataPermissionDataPermissionType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.starter.web.service.GenerateCodeService;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.core.annations.OrderTimeLineLog;
import com.lframework.xingyun.core.dto.stock.ProductStockChangeDto;
import com.lframework.xingyun.core.enums.OrderTimeLineBizType;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferOrderFullDto;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferProductDto;
import com.lframework.xingyun.sc.entity.ScTransferOrder;
import com.lframework.xingyun.sc.entity.ScTransferOrderDetail;
import com.lframework.xingyun.sc.entity.ScTransferOrderDetailReceive;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import com.lframework.xingyun.sc.enums.ScTransferOrderStatus;
import com.lframework.xingyun.sc.mappers.ScTransferOrderMapper;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.service.stock.transfer.ScTransferOrderDetailReceiveService;
import com.lframework.xingyun.sc.service.stock.transfer.ScTransferOrderDetailService;
import com.lframework.xingyun.sc.service.stock.transfer.ScTransferOrderService;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;
import com.lframework.xingyun.sc.vo.stock.transfer.ApprovePassScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.ApproveRefuseScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.BatchApprovePassScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.BatchApproveRefuseScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.CreateScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.QueryScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.QueryScTransferProductVo;
import com.lframework.xingyun.sc.vo.stock.transfer.ReceiveScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.ReceiveScTransferOrderVo.ReceiveScTransferProductVo;
import com.lframework.xingyun.sc.vo.stock.transfer.ScTransferProductVo;
import com.lframework.xingyun.sc.vo.stock.transfer.UpdateScTransferOrderVo;
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
public class ScTransferOrderServiceImpl extends
    BaseMpServiceImpl<ScTransferOrderMapper, ScTransferOrder>
    implements ScTransferOrderService {

  @Autowired
  private ScTransferOrderDetailService scTransferOrderDetailService;

  @Autowired
  private ScTransferOrderDetailReceiveService scTransferOrderDetailReceiveService;

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private ProductStockService productStockService;

  @Autowired
  private ProductService productService;

  @Override
  public PageResult<ScTransferOrder> query(Integer pageIndex, Integer pageSize,
      QueryScTransferOrderVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ScTransferOrder> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ScTransferOrder> query(QueryScTransferOrderVo vo) {

    return getBaseMapper().query(vo,
        DataPermissionHandler.getDataPermission(SysDataPermissionDataPermissionType.ORDER,
            Arrays.asList("order"), Arrays.asList("tb")));
  }

  @Override
  public ScTransferOrderFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "新增仓库调拨单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(type = OrderTimeLineBizType.CREATE, orderId = "#_result", name = "创建调拨单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateScTransferOrderVo vo) {

    ScTransferOrder data = new ScTransferOrder();
    data.setId(IdUtil.getId());
    data.setCode(generateCodeService.generate(GenerateCodeTypePool.SC_TRANSFER_ORDER));

    this.create(data, vo);

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改仓库调拨单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(type = OrderTimeLineBizType.UPDATE, orderId = "#vo.id", name = "修改调拨单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateScTransferOrderVo vo) {

    ScTransferOrder data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("仓库调拨单不存在！");
    }

    if (data.getStatus() != ScTransferOrderStatus.CREATED
        && data.getStatus() != ScTransferOrderStatus.APPROVE_REFUSE) {

      if (data.getStatus() == ScTransferOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("仓库调拨单已审核通过，无法修改！");
      }

      throw new DefaultClientException("仓库调拨单无法修改！");
    }

    // 删除明细
    Wrapper<ScTransferOrderDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            ScTransferOrderDetail.class)
        .eq(ScTransferOrderDetail::getOrderId, data.getId());
    scTransferOrderDetailService.remove(deleteDetailWrapper);

    this.create(data, vo);

    data.setStatus(ScTransferOrderStatus.CREATED);

    List<ScTransferOrderStatus> statusList = new ArrayList<>();
    statusList.add(ScTransferOrderStatus.CREATED);
    statusList.add(ScTransferOrderStatus.APPROVE_REFUSE);

    Wrapper<ScTransferOrder> updateSheetWrapper = Wrappers.lambdaUpdate(
            ScTransferOrder.class)
        .set(ScTransferOrder::getApproveBy, null)
        .set(ScTransferOrder::getApproveTime, null)
        .set(ScTransferOrder::getRefuseReason, StringPool.EMPTY_STR)
        .eq(ScTransferOrder::getId, data.getId())
        .in(ScTransferOrder::getStatus, statusList);
    if (getBaseMapper().update(data, updateSheetWrapper) != 1) {
      throw new DefaultClientException("仓库调拨单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "删除仓库调拨单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(orderId = "#id", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    ScTransferOrder data = getBaseMapper().selectById(id);
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("仓库调拨单不存在！");
    }

    if (data.getStatus() == ScTransferOrderStatus.APPROVE_PASS
        || data.getStatus() == ScTransferOrderStatus.PART_RECEIVED
        || data.getStatus() == ScTransferOrderStatus.RECEIVED) {
      throw new DefaultClientException("“审核通过”的仓库调拨单不允许执行删除操作！");
    }

    Wrapper<ScTransferOrder> deleteWrapper = Wrappers.lambdaQuery(ScTransferOrder.class)
        .eq(ScTransferOrder::getId, id)
        .in(ScTransferOrder::getStatus, ScTransferOrderStatus.CREATED,
            ScTransferOrderStatus.APPROVE_REFUSE);
    if (getBaseMapper().delete(deleteWrapper) != 1) {
      throw new DefaultClientException("仓库调拨单信息已过期，请刷新重试！");
    }

    Wrapper<ScTransferOrderDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            ScTransferOrderDetail.class)
        .eq(ScTransferOrderDetail::getOrderId, id);
    scTransferOrderDetailService.remove(deleteDetailWrapper);

    Wrapper<ScTransferOrderDetailReceive> deleteDetailReceiveWrapper = Wrappers.lambdaQuery(
        ScTransferOrderDetailReceive.class).eq(ScTransferOrderDetailReceive::getOrderId, id);
    scTransferOrderDetailReceiveService.remove(deleteDetailReceiveWrapper);
  }

  @OrderTimeLineLog(orderId = "#ids", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          ScTransferOrderService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException(
              "第" + orderNo + "个仓库调拨单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "审核通过仓库调拨单，ID：{}", params = {"#vo.id"})
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.id", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approvePass(ApprovePassScTransferOrderVo vo) {

    ScTransferOrder data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("仓库调拨单不存在！");
    }

    if (data.getStatus() != ScTransferOrderStatus.CREATED
        && data.getStatus() != ScTransferOrderStatus.APPROVE_REFUSE) {

      if (data.getStatus() == ScTransferOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("仓库调拨单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("仓库调拨单无法审核通过！");
    }

    LocalDateTime now = LocalDateTime.now();
    Wrapper<ScTransferOrder> updateWrapper = Wrappers.lambdaUpdate(ScTransferOrder.class)
        .eq(ScTransferOrder::getId, data.getId())
        .in(ScTransferOrder::getStatus, ScTransferOrderStatus.CREATED,
            ScTransferOrderStatus.APPROVE_REFUSE)
        .set(ScTransferOrder::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(ScTransferOrder::getApproveTime, now)
        .set(ScTransferOrder::getStatus, ScTransferOrderStatus.APPROVE_PASS)
        .set(ScTransferOrder::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("仓库调拨单信息已过期，请刷新重试！");
    }

    Wrapper<ScTransferOrderDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            ScTransferOrderDetail.class)
        .eq(ScTransferOrderDetail::getOrderId, data.getId())
        .orderByAsc(ScTransferOrderDetail::getOrderNo);
    List<ScTransferOrderDetail> details = scTransferOrderDetailService.list(
        queryDetailWrapper);

    BigDecimal totalAmount = BigDecimal.ZERO;
    for (ScTransferOrderDetail detail : details) {
      Product product = productService.findById(detail.getProductId());
      SubProductStockVo subProductStockVo = new SubProductStockVo();
      subProductStockVo.setProductId(product.getId());
      subProductStockVo.setScId(data.getSourceScId());
      subProductStockVo.setStockNum(detail.getTransferNum());
      subProductStockVo.setCreateTime(now);
      subProductStockVo.setBizId(data.getId());
      subProductStockVo.setBizDetailId(detail.getId());
      subProductStockVo.setBizCode(data.getCode());
      subProductStockVo.setBizType(ProductStockBizType.SC_TRANSFER.getCode());

      ProductStockChangeDto changeDto = productStockService.subStock(subProductStockVo);

      detail.setTaxPrice(changeDto.getCurTaxPrice());

      Wrapper<ScTransferOrderDetail> updateDetailWrapper = Wrappers.lambdaUpdate(
              ScTransferOrderDetail.class).eq(ScTransferOrderDetail::getId, detail.getId())
          .set(ScTransferOrderDetail::getTaxPrice, detail.getTaxPrice());
      scTransferOrderDetailService.update(updateDetailWrapper);
      totalAmount = NumberUtil.add(totalAmount,
          NumberUtil.mul(detail.getTaxPrice(), detail.getTransferNum()));
    }

    updateWrapper = Wrappers.lambdaUpdate(ScTransferOrder.class)
        .eq(ScTransferOrder::getId, data.getId()).set(ScTransferOrder::getTotalAmount, totalAmount);
    this.update(updateWrapper);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.ids", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchApprovePass(BatchApprovePassScTransferOrderVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassScTransferOrderVo approvePassVo = new ApprovePassScTransferOrderVo();
      approvePassVo.setId(id);

      try {
        ScTransferOrderService thisService = getThis(this.getClass());
        thisService.approvePass(approvePassVo);
      } catch (ClientException e) {
        throw new DefaultClientException(
            "第" + orderNo + "个仓库调拨单审核通过失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#_result", name = "直接审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String directApprovePass(CreateScTransferOrderVo vo) {

    ScTransferOrderService thisService = getThis(this.getClass());

    String id = thisService.create(vo);

    ApprovePassScTransferOrderVo approvePassVo = new ApprovePassScTransferOrderVo();
    approvePassVo.setId(id);
    approvePassVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassVo);

    return id;
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "审核拒绝仓库调拨单，ID：{}", params = {"#id"})
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approveRefuse(ApproveRefuseScTransferOrderVo vo) {

    ScTransferOrder data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("仓库调拨单不存在！");
    }

    if (data.getStatus() != ScTransferOrderStatus.CREATED
        && data.getStatus() != ScTransferOrderStatus.APPROVE_REFUSE) {

      if (data.getStatus() == ScTransferOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("仓库调拨单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("仓库调拨单无法审核通过！");
    }

    Wrapper<ScTransferOrder> updateWrapper = Wrappers.lambdaUpdate(ScTransferOrder.class)
        .eq(ScTransferOrder::getId, data.getId())
        .in(ScTransferOrder::getStatus, ScTransferOrderStatus.CREATED,
            ScTransferOrderStatus.APPROVE_REFUSE)
        .set(ScTransferOrder::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(ScTransferOrder::getApproveTime, LocalDateTime.now())
        .set(ScTransferOrder::getRefuseReason, vo.getRefuseReason())
        .set(ScTransferOrder::getStatus, ScTransferOrderStatus.APPROVE_REFUSE);
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("仓库调拨单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.ids", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchApproveRefuse(BatchApproveRefuseScTransferOrderVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseScTransferOrderVo approveRefuseVo = new ApproveRefuseScTransferOrderVo();
      approveRefuseVo.setId(id);
      approveRefuseVo.setRefuseReason(vo.getRefuseReason());

      try {
        ScTransferOrderService thisService = getThis(this.getClass());
        thisService.approveRefuse(approveRefuseVo);
      } catch (ClientException e) {
        throw new DefaultClientException(
            "第" + orderNo + "个仓库调拨单审核拒绝失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.RECEIVE, orderId = "#vo.id", name = "仓库调拨单收货")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void receive(ReceiveScTransferOrderVo vo) {
    ScTransferOrder data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("仓库调拨单不存在！");
    }

    if (data.getStatus() != ScTransferOrderStatus.APPROVE_PASS
        && data.getStatus() != ScTransferOrderStatus.PART_RECEIVED) {

      throw new DefaultClientException("仓库调拨单信息已过期，请刷新重试！");
    }

    for (ReceiveScTransferProductVo productVo : vo.getProducts()) {
      if (scTransferOrderDetailService.receive(data.getId(), productVo.getProductId(),
          productVo.getReceiveNum()) != 1) {
        Product product = productService.findById(productVo.getProductId());
        throw new DefaultClientException(
            "商品（" + product.getCode() + "）" + product.getName() + "待收货数量不足，请检查！");
      }
    }

    Wrapper<ScTransferOrder> updateWrapper = Wrappers.lambdaUpdate(ScTransferOrder.class)
        .set(ScTransferOrder::getStatus,
            scTransferOrderDetailService.countUnReceive(data.getId()) > 0
                ? ScTransferOrderStatus.PART_RECEIVED : ScTransferOrderStatus.RECEIVED)
        .eq(ScTransferOrder::getId, data.getId())
        .in(ScTransferOrder::getStatus, ScTransferOrderStatus.APPROVE_PASS,
            ScTransferOrderStatus.PART_RECEIVED);
    if (!this.update(updateWrapper)) {
      throw new DefaultClientException("仓库调拨单信息已过期，请刷新重试！");
    }

    LocalDateTime now = LocalDateTime.now();
    for (ReceiveScTransferProductVo productVo : vo.getProducts()) {
      Wrapper<ScTransferOrderDetail> queryWrapper = Wrappers.lambdaQuery(
              ScTransferOrderDetail.class).eq(ScTransferOrderDetail::getOrderId, data.getId())
          .eq(ScTransferOrderDetail::getProductId, productVo.getProductId());
      ScTransferOrderDetail detail = scTransferOrderDetailService.getOne(queryWrapper);
      // 入库
      AddProductStockVo addProductStockVo = new AddProductStockVo();
      addProductStockVo.setProductId(detail.getProductId());
      addProductStockVo.setScId(data.getTargetScId());
      addProductStockVo.setStockNum(productVo.getReceiveNum());
      addProductStockVo.setTaxPrice(detail.getTaxPrice());
      addProductStockVo.setCreateTime(now);
      addProductStockVo.setBizId(data.getId());
      addProductStockVo.setBizDetailId(detail.getId());
      addProductStockVo.setBizCode(data.getCode());
      addProductStockVo.setBizType(ProductStockBizType.SC_TRANSFER.getCode());

      productStockService.addStock(addProductStockVo);

      ScTransferOrderDetailReceive detailReceive = new ScTransferOrderDetailReceive();
      detailReceive.setId(IdUtil.getId());
      detailReceive.setOrderId(data.getId());
      detailReceive.setDetailId(detail.getId());
      detailReceive.setReceiveNum(productVo.getReceiveNum());
      scTransferOrderDetailReceiveService.save(detailReceive);
    }
  }


  @Override
  public PageResult<ScTransferProductDto> queryScTransferByCondition(Integer pageIndex,
      Integer pageSize, String scId, String condition) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<ScTransferProductDto> datas = getBaseMapper().queryScTransferByCondition(scId,
        condition,
        DataPermissionHandler.getDataPermission(
            SysDataPermissionDataPermissionType.PRODUCT,
            Arrays.asList("product", "brand", "category"),
            Arrays.asList("g", "b", "c")));
    PageResult<ScTransferProductDto> pageResult = PageResultUtil.convert(
        new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public PageResult<ScTransferProductDto> queryScTransferList(Integer pageIndex,
      Integer pageSize, QueryScTransferProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<ScTransferProductDto> datas = getBaseMapper().queryScTransferList(vo,
        DataPermissionHandler.getDataPermission(
            SysDataPermissionDataPermissionType.PRODUCT,
            Arrays.asList("product", "brand", "category"),
            Arrays.asList("g", "b", "c")));
    PageResult<ScTransferProductDto> pageResult = PageResultUtil.convert(
        new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public void cleanCacheByKey(Serializable key) {

  }

  private void create(ScTransferOrder data, CreateScTransferOrderVo vo) {

    data.setSourceScId(vo.getSourceScId());
    data.setTargetScId(vo.getTargetScId());
    if (StringUtil.equals(vo.getSourceScId(), vo.getTargetScId())) {
      throw new DefaultClientException("转出仓库和转入仓库不允许相同！");
    }
    data.setStatus(ScTransferOrderStatus.CREATED);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    int totalNum = 0;
    int orderNo = 1;
    for (ScTransferProductVo product : vo.getProducts()) {
      ScTransferOrderDetail detail = new ScTransferOrderDetail();
      detail.setId(IdUtil.getId());
      detail.setOrderId(data.getId());
      detail.setProductId(product.getProductId());
      detail.setTransferNum(product.getTransferNum());
      detail.setDescription(
          StringUtil.isBlank(product.getDescription()) ? StringPool.EMPTY_STR
              : product.getDescription());
      detail.setOrderNo(orderNo++);

      totalNum += detail.getTransferNum();

      scTransferOrderDetailService.save(detail);
    }
    data.setTotalNum(totalNum);
  }
}
