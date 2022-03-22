package com.lframework.xingyun.sc.impl.retail;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.ClientException;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IGenerateCodeService;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.SecurityUtil;
import com.lframework.xingyun.basedata.dto.member.MemberDto;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.purchase.ProductPurchaseDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.service.product.IProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.core.events.order.impl.ApprovePassRetailReturnEvent;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.retail.config.RetailConfigDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDetailDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDetailLotDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDto;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnDto;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.dto.stock.ProductLotDto;
import com.lframework.xingyun.sc.entity.RetailReturn;
import com.lframework.xingyun.sc.entity.RetailReturnDetail;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import com.lframework.xingyun.sc.enums.RetailReturnStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.sc.mappers.RetailReturnDetailMapper;
import com.lframework.xingyun.sc.mappers.RetailReturnMapper;
import com.lframework.xingyun.sc.service.retail.IRetailConfigService;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetDetailLotService;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetDetailService;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetService;
import com.lframework.xingyun.sc.service.retail.IRetailReturnService;
import com.lframework.xingyun.sc.service.stock.IProductLotService;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import com.lframework.xingyun.sc.vo.retail.returned.ApprovePassRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.ApproveRefuseRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.BatchApprovePassRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.BatchApproveRefuseRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.CreateRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.QueryRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.RetailReturnProductVo;
import com.lframework.xingyun.sc.vo.retail.returned.UpdateRetailReturnVo;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetailReturnServiceImpl implements IRetailReturnService {

  @Autowired
  private RetailReturnMapper retailReturnMapper;

  @Autowired
  private RetailReturnDetailMapper retailReturnDetailMapper;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Autowired
  private IStoreCenterService storeCenterService;

  @Autowired
  private IMemberService memberService;

  @Autowired
  private IUserService userService;

  @Autowired
  private IProductService productService;

  @Autowired
  private IRetailOutSheetService retailOutSheetService;

  @Autowired
  private IRetailConfigService retailConfigService;

  @Autowired
  private IRetailOutSheetDetailService retailOutSheetDetailService;

  @Autowired
  private IRetailOutSheetDetailLotService retailOutSheetDetailLotService;

  @Autowired
  private IProductStockService productStockService;

  @Autowired
  private IProductLotService productLotService;

  @Autowired
  private IProductPurchaseService productPurchaseService;

  @Override
  public PageResult<RetailReturnDto> query(Integer pageIndex, Integer pageSize,
      QueryRetailReturnVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<RetailReturnDto> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<RetailReturnDto> query(QueryRetailReturnVo vo) {

    return retailReturnMapper.query(vo);
  }

  @Override
  public RetailReturnDto getById(String id) {

    return retailReturnMapper.getById(id);
  }

  @Override
  public RetailReturnFullDto getDetail(String id) {

    return retailReturnMapper.getDetail(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "创建零售退货单，单号：{}", params = "#code")
  @Transactional
  @Override
  public String create(CreateRetailReturnVo vo) {

    RetailReturn retailReturn = new RetailReturn();
    retailReturn.setId(IdUtil.getId());
    retailReturn.setCode(generateCodeService.generate(GenerateCodeTypePool.RETAIL_RETURN));

    RetailConfigDto retailConfig = retailConfigService.get();

    this.create(retailReturn, vo, retailConfig.getRetailReturnRequireOutStock());

    retailReturn.setStatus(RetailReturnStatus.CREATED);

    retailReturnMapper.insert(retailReturn);

    OpLogUtil.setVariable("code", retailReturn.getCode());
    OpLogUtil.setExtra(vo);

    return retailReturn.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改零售退货单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void update(UpdateRetailReturnVo vo) {

    RetailReturn retailReturn = retailReturnMapper.selectById(vo.getId());
    if (retailReturn == null) {
      throw new InputErrorException("零售退货单不存在！");
    }

    if (retailReturn.getStatus() != RetailReturnStatus.CREATED
        && retailReturn.getStatus() != RetailReturnStatus.APPROVE_REFUSE) {

      if (retailReturn.getStatus() == RetailReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("零售退货单已审核通过，无法修改！");
      }

      throw new DefaultClientException("零售退货单无法修改！");
    }

    boolean requireOut = !StringUtil.isBlank(retailReturn.getOutSheetId());

    if (requireOut) {
      //查询零售退货单明细
      Wrapper<RetailReturnDetail> queryDetailWrapper = Wrappers
          .lambdaQuery(RetailReturnDetail.class)
          .eq(RetailReturnDetail::getReturnId, retailReturn.getId());
      List<RetailReturnDetail> details = retailReturnDetailMapper.selectList(queryDetailWrapper);
      for (RetailReturnDetail detail : details) {
        if (!StringUtil.isBlank(detail.getOutSheetDetailId())) {
          //先恢复已退货数量
          retailOutSheetDetailLotService
              .subReturnNum(detail.getOutSheetDetailId(), detail.getReturnNum());
        }
      }
    }

    // 删除零售退货单明细
    Wrapper<RetailReturnDetail> deleteDetailWrapper = Wrappers.lambdaQuery(RetailReturnDetail.class)
        .eq(RetailReturnDetail::getReturnId, retailReturn.getId());
    retailReturnDetailMapper.delete(deleteDetailWrapper);

    this.create(retailReturn, vo, requireOut);

    retailReturn.setStatus(RetailReturnStatus.CREATED);

    List<RetailReturnStatus> statusList = new ArrayList<>();
    statusList.add(RetailReturnStatus.CREATED);
    statusList.add(RetailReturnStatus.APPROVE_REFUSE);

    Wrapper<RetailReturn> updateOrderWrapper = Wrappers.lambdaUpdate(RetailReturn.class)
        .set(RetailReturn::getApproveBy, null).set(RetailReturn::getApproveTime, null)
        .set(RetailReturn::getRefuseReason, StringPool.EMPTY_STR)
        .eq(RetailReturn::getId, retailReturn.getId())
        .in(RetailReturn::getStatus, statusList);
    if (retailReturnMapper.update(retailReturn, updateOrderWrapper) != 1) {
      throw new DefaultClientException("零售退货单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", retailReturn.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核通过零售退货单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void approvePass(ApprovePassRetailReturnVo vo) {

    RetailReturn retailReturn = retailReturnMapper.selectById(vo.getId());
    if (retailReturn == null) {
      throw new InputErrorException("零售退货单不存在！");
    }

    if (retailReturn.getStatus() != RetailReturnStatus.CREATED
        && retailReturn.getStatus() != RetailReturnStatus.APPROVE_REFUSE) {

      if (retailReturn.getStatus() == RetailReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("零售退货单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("零售退货单无法审核通过！");
    }

    RetailConfigDto retailConfig = retailConfigService.get();

    if (!retailConfig.getRetailReturnMultipleRelateOutStock()) {
      Wrapper<RetailReturn> checkWrapper = Wrappers.lambdaQuery(RetailReturn.class)
          .eq(RetailReturn::getOutSheetId, retailReturn.getOutSheetId())
          .ne(RetailReturn::getId, retailReturn.getId());
      if (retailReturnMapper.selectCount(checkWrapper) > 0) {
        RetailOutSheetDto retailOutSheet = retailOutSheetService
            .getById(retailReturn.getOutSheetId());
        throw new DefaultClientException(
            "零售出库单号：" + retailOutSheet.getCode() + "，已关联其他零售退货单，不允许关联多个零售退货单！");
      }
    }

    retailReturn.setStatus(RetailReturnStatus.APPROVE_PASS);

    List<RetailReturnStatus> statusList = new ArrayList<>();
    statusList.add(RetailReturnStatus.CREATED);
    statusList.add(RetailReturnStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<RetailReturn> updateOrderWrapper = Wrappers.lambdaUpdate(RetailReturn.class)
        .set(RetailReturn::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(RetailReturn::getApproveTime, LocalDateTime.now())
        .eq(RetailReturn::getId, retailReturn.getId())
        .in(RetailReturn::getStatus, statusList);
    if (!StringUtil.isBlank(vo.getDescription())) {
      updateOrderWrapper.set(RetailReturn::getDescription, vo.getDescription());
    }
    if (retailReturnMapper.update(retailReturn, updateOrderWrapper) != 1) {
      throw new DefaultClientException("零售退货单信息已过期，请刷新重试！");
    }

    Wrapper<RetailReturnDetail> queryDetailWrapper = Wrappers.lambdaQuery(RetailReturnDetail.class)
        .eq(RetailReturnDetail::getReturnId, retailReturn.getId())
        .orderByAsc(RetailReturnDetail::getOrderNo);
    List<RetailReturnDetail> details = retailReturnDetailMapper.selectList(queryDetailWrapper);
    for (RetailReturnDetail detail : details) {
      ProductPurchaseDto productPurchase = productPurchaseService.getById(detail.getProductId());
      AddProductStockVo addProductStockVo = new AddProductStockVo();
      addProductStockVo.setProductId(detail.getProductId());
      addProductStockVo.setScId(retailReturn.getScId());
      addProductStockVo.setSupplierId(detail.getSupplierId());
      addProductStockVo.setStockNum(detail.getReturnNum());
      addProductStockVo
          .setDefaultTaxAmount(NumberUtil.mul(productPurchase.getPrice(), detail.getReturnNum()));
      addProductStockVo.setTaxRate(detail.getTaxRate());
      addProductStockVo.setBizId(retailReturn.getId());
      addProductStockVo.setBizDetailId(detail.getId());
      addProductStockVo.setBizCode(retailReturn.getCode());
      addProductStockVo.setBizType(ProductStockBizType.RETAIL_RETURN.getCode());

      productStockService.addStock(addProductStockVo);
    }

    this.sendApprovePassEvent(retailReturn);

    OpLogUtil.setVariable("code", retailReturn.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional
  @Override
  public void batchApprovePass(BatchApprovePassRetailReturnVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassRetailReturnVo approvePassVo = new ApprovePassRetailReturnVo();
      approvePassVo.setId(id);

      try {
        IRetailReturnService thisService = getThis(this.getClass());
        thisService.approvePass(approvePassVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个零售退货单审核通过失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @Transactional
  @Override
  public void directApprovePass(CreateRetailReturnVo vo) {

    IRetailReturnService thisService = getThis(this.getClass());

    String returnId = thisService.create(vo);

    ApprovePassRetailReturnVo approvePassVo = new ApprovePassRetailReturnVo();
    approvePassVo.setId(returnId);
    approvePassVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassVo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核拒绝零售退货单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void approveRefuse(ApproveRefuseRetailReturnVo vo) {

    RetailReturn retailReturn = retailReturnMapper.selectById(vo.getId());
    if (retailReturn == null) {
      throw new InputErrorException("零售退货单不存在！");
    }

    if (retailReturn.getStatus() != RetailReturnStatus.CREATED) {

      if (retailReturn.getStatus() == RetailReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("零售退货单已审核通过，不允许继续执行审核！");
      }

      if (retailReturn.getStatus() == RetailReturnStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("零售退货单已审核拒绝，不允许继续执行审核！");
      }

      throw new DefaultClientException("零售退货单无法审核拒绝！");
    }

    retailReturn.setStatus(RetailReturnStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<RetailReturn> updateOrderWrapper = Wrappers.lambdaUpdate(RetailReturn.class)
        .set(RetailReturn::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(RetailReturn::getApproveTime, LocalDateTime.now())
        .set(RetailReturn::getRefuseReason, vo.getRefuseReason())
        .eq(RetailReturn::getId, retailReturn.getId())
        .eq(RetailReturn::getStatus, RetailReturnStatus.CREATED);
    if (retailReturnMapper.update(retailReturn, updateOrderWrapper) != 1) {
      throw new DefaultClientException("零售退货单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", retailReturn.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional
  @Override
  public void batchApproveRefuse(BatchApproveRefuseRetailReturnVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseRetailReturnVo approveRefuseVo = new ApproveRefuseRetailReturnVo();
      approveRefuseVo.setId(id);
      approveRefuseVo.setRefuseReason(vo.getRefuseReason());

      try {
        IRetailReturnService thisService = getThis(this.getClass());
        thisService.approveRefuse(approveRefuseVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个零售退货单审核拒绝失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "删除零售退货单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    RetailReturn retailReturn = retailReturnMapper.selectById(id);
    if (retailReturn == null) {
      throw new InputErrorException("零售退货单不存在！");
    }

    if (retailReturn.getStatus() != RetailReturnStatus.CREATED
        && retailReturn.getStatus() != RetailReturnStatus.APPROVE_REFUSE) {

      if (retailReturn.getStatus() == RetailReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("“审核通过”的零售退货单不允许执行删除操作！");
      }

      throw new DefaultClientException("零售退货单无法删除！");
    }

    if (!StringUtil.isBlank(retailReturn.getOutSheetId())) {
      //查询零售退货单明细
      Wrapper<RetailReturnDetail> queryDetailWrapper = Wrappers
          .lambdaQuery(RetailReturnDetail.class)
          .eq(RetailReturnDetail::getReturnId, retailReturn.getId());
      List<RetailReturnDetail> details = retailReturnDetailMapper.selectList(queryDetailWrapper);
      for (RetailReturnDetail detail : details) {
        if (!StringUtil.isBlank(detail.getOutSheetDetailId())) {
          //恢复已退货数量
          retailOutSheetDetailLotService
              .subReturnNum(detail.getOutSheetDetailId(), detail.getReturnNum());
        }
      }
    }

    // 删除退货单明细
    Wrapper<RetailReturnDetail> deleteDetailWrapper = Wrappers.lambdaQuery(RetailReturnDetail.class)
        .eq(RetailReturnDetail::getReturnId, retailReturn.getId());
    retailReturnDetailMapper.delete(deleteDetailWrapper);

    // 删除退货单
    retailReturnMapper.deleteById(id);

    OpLogUtil.setVariable("code", retailReturn.getCode());
  }

  @Transactional
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          IRetailReturnService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException("第" + orderNo + "个零售退货单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  private void create(RetailReturn retailReturn, CreateRetailReturnVo vo, boolean requireOut) {

    StoreCenterDto sc = storeCenterService.getById(vo.getScId());
    if (sc == null) {
      throw new InputErrorException("仓库不存在！");
    }

    retailReturn.setScId(vo.getScId());

    MemberDto member = memberService.getById(vo.getMemberId());
    if (member == null) {
      throw new InputErrorException("客户不存在！");
    }
    retailReturn.setMemberId(vo.getMemberId());

    if (!StringUtil.isBlank(vo.getSalerId())) {
      UserDto saler = userService.getById(vo.getSalerId());
      if (saler == null) {
        throw new InputErrorException("零售员不存在！");
      }

      retailReturn.setSalerId(vo.getSalerId());
    }

    RetailConfigDto retailConfig = retailConfigService.get();

    GetPaymentDateDto paymentDate = retailOutSheetService.getPaymentDate(member.getId());

    retailReturn.setPaymentDate(
        paymentDate.getAllowModify() ? vo.getPaymentDate() : paymentDate.getPaymentDate());

    if (requireOut) {

      RetailOutSheetDto retailOutSheet = retailOutSheetService.getById(vo.getOutSheetId());
      if (retailOutSheet == null) {
        throw new DefaultClientException("零售出库单不存在！");
      }

      retailReturn.setScId(retailOutSheet.getScId());
      retailReturn.setMemberId(retailOutSheet.getMemberId());
      retailReturn.setOutSheetId(retailOutSheet.getId());

      if (!retailConfig.getRetailReturnMultipleRelateOutStock()) {
        Wrapper<RetailReturn> checkWrapper = Wrappers.lambdaQuery(RetailReturn.class)
            .eq(RetailReturn::getOutSheetId, retailOutSheet.getId())
            .ne(RetailReturn::getId, retailReturn.getId());
        if (retailReturnMapper.selectCount(checkWrapper) > 0) {
          throw new DefaultClientException(
              "零售出库单号：" + retailOutSheet.getCode() + "，已关联其他零售退货单，不允许关联多个零售退货单！");
        }
      }
    }

    int returnNum = 0;
    int giftNum = 0;
    BigDecimal totalAmount = BigDecimal.ZERO;
    int orderNo = 1;
    for (RetailReturnProductVo productVo : vo.getProducts()) {
      if (requireOut) {
        if (!StringUtil.isBlank(productVo.getOutSheetDetailId())) {
          RetailOutSheetDetailLotDto detailLot = retailOutSheetDetailLotService
              .getById(productVo.getOutSheetDetailId());
          RetailOutSheetDetailDto detail = retailOutSheetDetailService
              .getById(detailLot.getDetailId());
          ProductLotDto lot = productLotService.getById(detailLot.getLotId());
          productVo.setOriPrice(detail.getOriPrice());
          productVo.setTaxPrice(detail.getTaxPrice());
          productVo.setDiscountRate(detail.getDiscountRate());
          productVo.setSupplierId(lot.getSupplierId());
        } else {
          productVo.setTaxPrice(BigDecimal.ZERO);
          productVo.setDiscountRate(BigDecimal.valueOf(100));
        }
      }

      boolean isGift = productVo.getTaxPrice().doubleValue() == 0D;

      if (requireOut) {
        if (StringUtil.isBlank(productVo.getOutSheetDetailId())) {
          if (!isGift) {
            throw new InputErrorException("第" + orderNo + "行商品必须为“赠品”！");
          }
        }
      }

      if (isGift) {
        giftNum += productVo.getReturnNum();
      } else {
        returnNum += productVo.getReturnNum();
      }

      totalAmount = NumberUtil
          .add(totalAmount, NumberUtil.mul(productVo.getTaxPrice(), productVo.getReturnNum()));

      RetailReturnDetail detail = new RetailReturnDetail();
      detail.setId(IdUtil.getId());
      detail.setReturnId(retailReturn.getId());

      ProductDto product = productService.getById(productVo.getProductId());
      if (product == null) {
        throw new InputErrorException("第" + orderNo + "行商品不存在！");
      }

      if (!NumberUtil.isNumberPrecision(productVo.getTaxPrice(), 2)) {
        throw new InputErrorException("第" + orderNo + "行商品价格最多允许2位小数！");
      }

      detail.setProductId(productVo.getProductId());
      detail.setSupplierId(productVo.getSupplierId());
      detail.setReturnNum(productVo.getReturnNum());
      detail.setOriPrice(productVo.getOriPrice());
      detail.setTaxPrice(productVo.getTaxPrice());
      detail.setDiscountRate(productVo.getDiscountRate());
      detail.setIsGift(isGift);
      detail.setTaxRate(product.getPoly().getTaxRate());
      detail.setDescription(
          StringUtil.isBlank(productVo.getDescription()) ? StringPool.EMPTY_STR
              : productVo.getDescription());
      detail.setOrderNo(orderNo);
      detail.setSettleStatus(this.getInitSettleStatus(member));
      if (requireOut && !StringUtil.isBlank(productVo.getOutSheetDetailId())) {
        detail.setOutSheetDetailId(productVo.getOutSheetDetailId());
        retailOutSheetDetailLotService
            .addReturnNum(productVo.getOutSheetDetailId(), detail.getReturnNum());
      }

      retailReturnDetailMapper.insert(detail);
      orderNo++;
    }
    retailReturn.setTotalNum(returnNum);
    retailReturn.setTotalGiftNum(giftNum);
    retailReturn.setTotalAmount(totalAmount);
    retailReturn
        .setDescription(
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    retailReturn.setSettleStatus(this.getInitSettleStatus(member));
  }

  /**
   * 根据客户获取初始结算状态
   *
   * @param member
   * @return
   */
  private SettleStatus getInitSettleStatus(MemberDto member) {

    return SettleStatus.UN_SETTLE;
  }

  private void sendApprovePassEvent(RetailReturn r) {

    ApprovePassRetailReturnEvent event = new ApprovePassRetailReturnEvent(this);
    event.setId(r.getId());
    event.setTotalAmount(r.getTotalAmount());
    event.setApproveTime(r.getApproveTime());

    ApplicationUtil.publishEvent(event);
  }
}
