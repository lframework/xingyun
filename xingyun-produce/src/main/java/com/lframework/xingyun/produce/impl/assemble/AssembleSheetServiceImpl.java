package com.lframework.xingyun.produce.impl.assemble;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.service.GenerateCodeService;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.xingyun.basedata.entity.ProductSkuPurchase;
import com.lframework.xingyun.basedata.service.product.ProductSkuPurchaseService;
import com.lframework.xingyun.produce.bo.assemble.AssembleShortageBo;
import com.lframework.xingyun.produce.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.produce.dto.bom.BomDetailDto;
import com.lframework.xingyun.produce.entity.AssembleSheet;
import com.lframework.xingyun.produce.entity.AssembleSheetDetail;
import com.lframework.xingyun.produce.entity.Bom;
import com.lframework.xingyun.produce.enums.AssembleSheetStatus;
import com.lframework.xingyun.produce.enums.BomStatus;
import com.lframework.xingyun.produce.enums.ProduceOpLogType;
import com.lframework.xingyun.produce.mappers.AssembleSheetMapper;
import com.lframework.xingyun.produce.service.assemble.AssembleSheetDetailService;
import com.lframework.xingyun.produce.service.assemble.AssembleSheetService;
import com.lframework.xingyun.produce.service.bom.BomDetailService;
import com.lframework.xingyun.produce.service.bom.BomService;
import com.lframework.xingyun.produce.vo.assemble.ApprovePassAssembleSheetVo;
import com.lframework.xingyun.produce.vo.assemble.ApproveRefuseAssembleSheetVo;
import com.lframework.xingyun.produce.vo.assemble.AssembleProductVo;
import com.lframework.xingyun.produce.vo.assemble.CreateAssembleSheetVo;
import com.lframework.xingyun.produce.vo.assemble.QueryAssembleSheetVo;
import com.lframework.xingyun.produce.vo.assemble.UpdateAssembleSheetVo;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 组装单 ServiceImpl
 *
 * @author zmj
 */
@Service
public class AssembleSheetServiceImpl extends BaseMpServiceImpl<AssembleSheetMapper, AssembleSheet>
    implements AssembleSheetService {

  @Autowired
  private AssembleSheetDetailService assembleSheetDetailService;

  @Autowired
  private BomService bomService;

  @Autowired
  private BomDetailService bomDetailService;

  @Autowired
  private ProductStockService productStockService;

  @Autowired
  private ProductSkuPurchaseService productSkuPurchaseService;

  @Autowired
  private GenerateCodeService generateCodeService;

  @Override
  public PageResult<AssembleSheet> query(Integer pageIndex, Integer pageSize,
      QueryAssembleSheetVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<AssembleSheet> datas = getBaseMapper().query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = AssembleSheet.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public AssembleSheet findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = ProduceOpLogType.class, name = "新增组装单，ID：{}, 单号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateAssembleSheetVo vo) {

    vo.validate();

    Bom bom = checkBom(vo.getBomId());

    AssembleSheet data = new AssembleSheet();
    data.setId(IdUtil.getId());
    data.setCode(generateCodeService.generate(GenerateCodeTypePool.ASSEMBLE_SHEET));
    data.setScId(vo.getScId());
    data.setBomId(vo.getBomId());
    data.setProductId(bom.getMainProductId());
    data.setSkuId(bom.getMainSkuId());
    data.setTotalNum(vo.getTotalNum());
    data.setStatus(AssembleSheetStatus.CREATED);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    saveDetails(data.getId(), vo.getProducts());

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", data.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = ProduceOpLogType.class, name = "修改组装单，ID：{}", params = {"#vo.id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateAssembleSheetVo vo) {

    vo.validate();

    AssembleSheet data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("组装单不存在！");
    }
    if (data.getStatus() != AssembleSheetStatus.CREATED
        && data.getStatus() != AssembleSheetStatus.APPROVE_REFUSE) {
      throw new DefaultClientException("组装单已审核通过，不允许修改！");
    }

    checkBom(vo.getBomId());
    Bom bom = bomService.findById(vo.getBomId());

    LambdaUpdateWrapper<AssembleSheet> updateWrapper = Wrappers.lambdaUpdate(AssembleSheet.class)
        .set(AssembleSheet::getScId, vo.getScId())
        .set(AssembleSheet::getBomId, vo.getBomId())
        .set(AssembleSheet::getProductId, bom.getMainProductId())
        .set(AssembleSheet::getSkuId, bom.getMainSkuId())
        .set(AssembleSheet::getTotalNum, vo.getTotalNum())
        .set(AssembleSheet::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(AssembleSheet::getStatus, AssembleSheetStatus.CREATED)
        .eq(AssembleSheet::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    // 重建明细（先删后插）
    assembleSheetDetailService.deleteBySheetId(vo.getId());
    saveDetails(vo.getId(), vo.getProducts());

    OpLogUtil.setVariable("vo.id", vo.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = ProduceOpLogType.class, name = "审核通过组装单，ID：{}", params = {"#vo.id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approvePass(ApprovePassAssembleSheetVo vo, String userId) {

    AssembleSheet data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("组装单不存在！");
    }

    if (data.getStatus() != AssembleSheetStatus.CREATED
        && data.getStatus() != AssembleSheetStatus.APPROVE_REFUSE) {
      if (data.getStatus() == AssembleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("组装单已审核通过，不允许继续执行审核！");
      }
      throw new DefaultClientException("组装单无法审核通过！");
    }

    LocalDateTime now = LocalDateTime.now();
    Wrapper<AssembleSheet> updateWrapper = Wrappers.lambdaUpdate(AssembleSheet.class)
        .eq(AssembleSheet::getId, data.getId())
        .in(AssembleSheet::getStatus, AssembleSheetStatus.CREATED,
            AssembleSheetStatus.APPROVE_REFUSE)
        .set(AssembleSheet::getApproveBy, userId)
        .set(AssembleSheet::getApproveTime, now)
        .set(AssembleSheet::getStatus, AssembleSheetStatus.APPROVE_PASS)
        .set(AssembleSheet::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR
                : vo.getDescription());
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("组装单信息已过期，请刷新重试！");
    }

    List<AssembleSheetDetail> details = assembleSheetDetailService.listBySheetId(data.getId());
    if (CollectionUtil.isEmpty(details)) {
      throw new DefaultClientException("组装单明细为空，不允许审核！");
    }

    // 1. 子件出库
    for (AssembleSheetDetail detail : details) {
      SubProductStockVo subProductStockVo = new SubProductStockVo();
      subProductStockVo.setProductId(detail.getProductId());
      subProductStockVo.setSkuId(detail.getSkuId());
      subProductStockVo.setScId(data.getScId());
      subProductStockVo.setStockNum(detail.getStockNum());
      subProductStockVo.setCreateTime(now);
      subProductStockVo.setBizId(data.getId());
      subProductStockVo.setBizDetailId(detail.getId());
      subProductStockVo.setBizCode(data.getCode());
      subProductStockVo.setBizType(ProductStockBizType.ASSEMBLE_OUT.getCode());

      productStockService.subStock(subProductStockVo);
    }

    // 2. 成品入库（按采购价估值）
    ProductSkuPurchase productPurchase = productSkuPurchaseService.getById(data.getSkuId());
    AddProductStockVo addProductStockVo = new AddProductStockVo();
    addProductStockVo.setProductId(data.getProductId());
    addProductStockVo.setSkuId(data.getSkuId());
    addProductStockVo.setScId(data.getScId());
    addProductStockVo.setStockNum(data.getTotalNum());
    if (productPurchase != null && productPurchase.getPrice() != null) {
      addProductStockVo.setDefaultTaxAmount(
          NumberUtil.getNumber(NumberUtil.mul(productPurchase.getPrice(), data.getTotalNum()), 2));
    }
    addProductStockVo.setCreateTime(now);
    addProductStockVo.setBizId(data.getId());
    addProductStockVo.setBizDetailId(data.getId());
    addProductStockVo.setBizCode(data.getCode());
    addProductStockVo.setBizType(ProductStockBizType.ASSEMBLE_IN.getCode());

    productStockService.addStock(addProductStockVo);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String directApprovePass(CreateAssembleSheetVo vo, String userId) {

    AssembleSheetService thisService = getThis(this.getClass());

    String id = thisService.create(vo);

    ApprovePassAssembleSheetVo approvePassVo = new ApprovePassAssembleSheetVo();
    approvePassVo.setId(id);
    approvePassVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassVo, userId);

    return id;
  }

  @OpLog(type = ProduceOpLogType.class, name = "审核拒绝组装单，ID：{}", params = {"#vo.id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approveRefuse(ApproveRefuseAssembleSheetVo vo, String userId) {

    AssembleSheet data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("组装单不存在！");
    }

    if (data.getStatus() != AssembleSheetStatus.CREATED
        && data.getStatus() != AssembleSheetStatus.APPROVE_REFUSE) {
      if (data.getStatus() == AssembleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("组装单已审核通过，不允许拒绝！");
      }
      throw new DefaultClientException("组装单无法审核拒绝！");
    }

    Wrapper<AssembleSheet> updateWrapper = Wrappers.lambdaUpdate(AssembleSheet.class)
        .eq(AssembleSheet::getId, data.getId())
        .in(AssembleSheet::getStatus, AssembleSheetStatus.CREATED,
            AssembleSheetStatus.APPROVE_REFUSE)
        .set(AssembleSheet::getApproveBy, userId)
        .set(AssembleSheet::getApproveTime, LocalDateTime.now())
        .set(AssembleSheet::getStatus, AssembleSheetStatus.APPROVE_REFUSE)
        .set(AssembleSheet::getRefuseReason, vo.getRefuseReason());

    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("组装单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("vo.id", vo.getId());
  }

  @OpLog(type = ProduceOpLogType.class, name = "删除组装单，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    AssembleSheet data = getBaseMapper().selectById(id);
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("组装单不存在！");
    }
    if (data.getStatus() == AssembleSheetStatus.APPROVE_PASS) {
      throw new DefaultClientException("组装单已审核通过，不允许删除！");
    }

    // 物理删除主单 + 明细
    getBaseMapper().deleteById(id);
    assembleSheetDetailService.deleteBySheetId(id);

    OpLogUtil.setVariable("id", id);
  }

  @Override
  public List<AssembleShortageBo> calcShortage(String bomId, BigDecimal totalNum, String scId) {

    if (totalNum == null || totalNum.compareTo(BigDecimal.ZERO) <= 0) {
      throw new DefaultClientException("组装数量必须大于0！");
    }

    Bom bom = checkBom(bomId);
    List<BomDetailDto> details = bomDetailService.listByBomId(bom.getId());
    if (CollectionUtil.isEmpty(details)) {
      throw new DefaultClientException("BOM未配置子件，无法计算缺料！");
    }

    List<AssembleShortageBo> results = new ArrayList<>();
    for (BomDetailDto detail : details) {
      BigDecimal required = NumberUtil.mul(detail.getUnitNum(), totalNum);

      ProductStock stock = productStockService.getBySkuIdAndScId(detail.getSkuId(), scId);
      BigDecimal curStock = stock == null || stock.getStockNum() == null
          ? BigDecimal.ZERO : stock.getStockNum();

      BigDecimal shortage = required.subtract(curStock);
      if (shortage.compareTo(BigDecimal.ZERO) < 0) {
        shortage = BigDecimal.ZERO;
      }

      AssembleShortageBo bo = new AssembleShortageBo();
      bo.setProductId(detail.getProductId());
      bo.setSkuId(detail.getSkuId());
      bo.setSkuCode(detail.getSkuCode());
      bo.setProductCode(detail.getProductCode());
      bo.setProductName(detail.getProductName());
      bo.setProductSpec(detail.getProductSpec());
      bo.setProductUnit(detail.getProductUnit());
      bo.setUnitNum(detail.getUnitNum());
      bo.setRequiredNum(required);
      bo.setStockNum(curStock);
      bo.setShortageNum(shortage);
      results.add(bo);
    }

    return results;
  }

  @CacheEvict(value = AssembleSheet.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }

  /**
   * 校验BOM存在且已启用
   */
  private Bom checkBom(String bomId) {

    Bom bom = bomService.findById(bomId);
    if (ObjectUtil.isNull(bom)) {
      throw new DefaultClientException("BOM不存在！");
    }
    if (bom.getStatus() != BomStatus.ENABLED) {
      throw new DefaultClientException("BOM未启用，不允许引用！");
    }
    return bom;
  }

  /**
   * 保存明细
   */
  private void saveDetails(String sheetId, List<AssembleProductVo> products) {

    int orderNo = 1;
    for (AssembleProductVo product : products) {
      AssembleSheetDetail detail = new AssembleSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(sheetId);
      detail.setProductId(product.getProductId());
      detail.setSkuId(product.getSkuId());
      detail.setNum(product.getNum());
      detail.setStockNum(product.getStockNum());
      detail.setDescription(
          StringUtil.isBlank(product.getDescription()) ? StringPool.EMPTY_STR
              : product.getDescription());
      detail.setOrderNo(orderNo++);

      assembleSheetDetailService.save(detail);
    }
  }
}
