package com.lframework.xingyun.produce.impl.bom;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.produce.entity.Bom;
import com.lframework.xingyun.produce.entity.BomDetail;
import com.lframework.xingyun.produce.enums.BomStatus;
import com.lframework.xingyun.produce.enums.ProduceOpLogType;
import com.lframework.xingyun.produce.mappers.BomMapper;
import com.lframework.xingyun.produce.service.bom.BomDetailService;
import com.lframework.xingyun.produce.service.bom.BomService;
import com.lframework.xingyun.produce.vo.bom.BomDetailVo;
import com.lframework.xingyun.produce.vo.bom.CreateBomVo;
import com.lframework.xingyun.produce.vo.bom.QueryBomVo;
import com.lframework.xingyun.produce.vo.bom.UpdateBomVo;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BomServiceImpl extends BaseMpServiceImpl<BomMapper, Bom> implements BomService {

  @Autowired
  private BomDetailService bomDetailService;

  @Autowired
  private ProductSkuService productSkuService;

  @Override
  public PageResult<Bom> query(Integer pageIndex, Integer pageSize, QueryBomVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Bom> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<Bom> query(QueryBomVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = Bom.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public Bom findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = ProduceOpLogType.class, name = "新增BOM，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateBomVo vo) {

    vo.validate();

    // 校验编号唯一
    Wrapper<Bom> checkCodeWrapper = Wrappers.lambdaQuery(Bom.class)
        .eq(Bom::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("BOM编号重复，请重新输入！");
    }

    // 校验成品SKU存在
    ProductSku mainSku = productSkuService.findById(vo.getMainSkuId());
    if (ObjectUtil.isNull(mainSku)) {
      throw new DefaultClientException("成品商品不存在！");
    }

    Bom data = new Bom();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setMainProductId(vo.getMainProductId());
    data.setMainSkuId(vo.getMainSkuId());
    data.setOutputNum(vo.getOutputNum());
    data.setScrapRate(vo.getScrapRate() == null ? java.math.BigDecimal.ZERO : vo.getScrapRate());
    data.setStatus(BomStatus.DRAFT);
    data.setIsDefault(vo.getIsDefault());
    data.setVersion(vo.getVersion());
    data.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    // 如果设为默认，取消同SKU其他默认
    if (Boolean.TRUE.equals(vo.getIsDefault())) {
      clearDefaultByMainSkuId(vo.getMainSkuId(), data.getId());
    }

    // 保存明细
    saveDetails(data.getId(), vo.getDetails());

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = ProduceOpLogType.class, name = "修改BOM，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateBomVo vo) {

    vo.validate();

    Bom data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("BOM不存在！");
    }
    if (data.getStatus() != BomStatus.DRAFT) {
      throw new DefaultClientException("仅草稿状态的BOM允许修改！");
    }

    Wrapper<Bom> checkCodeWrapper = Wrappers.lambdaQuery(Bom.class)
        .eq(Bom::getCode, vo.getCode()).ne(Bom::getId, vo.getId());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("BOM编号重复，请重新输入！");
    }

    ProductSku mainSku = productSkuService.findById(vo.getMainSkuId());
    if (ObjectUtil.isNull(mainSku)) {
      throw new DefaultClientException("成品商品不存在！");
    }

    LambdaUpdateWrapper<Bom> updateWrapper = Wrappers.lambdaUpdate(Bom.class)
        .set(Bom::getCode, vo.getCode())
        .set(Bom::getName, vo.getName())
        .set(Bom::getMainProductId, vo.getMainProductId())
        .set(Bom::getMainSkuId, vo.getMainSkuId())
        .set(Bom::getOutputNum, vo.getOutputNum())
        .set(Bom::getScrapRate, vo.getScrapRate() == null ? java.math.BigDecimal.ZERO : vo.getScrapRate())
        .set(Bom::getIsDefault, vo.getIsDefault())
        .set(Bom::getVersion, vo.getVersion())
        .set(Bom::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(Bom::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    if (Boolean.TRUE.equals(vo.getIsDefault())) {
      clearDefaultByMainSkuId(vo.getMainSkuId(), vo.getId());
    }

    // 重建明细（先删后插）
    bomDetailService.deleteByBomId(vo.getId());
    saveDetails(vo.getId(), vo.getDetails());

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = ProduceOpLogType.class, name = "审核启用BOM，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approve(String id, String userId) {

    Bom data = getBaseMapper().selectById(id);
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("BOM不存在！");
    }
    if (data.getStatus() != BomStatus.DRAFT) {
      throw new DefaultClientException("仅草稿状态的BOM允许审核！");
    }

    LambdaUpdateWrapper<Bom> updateWrapper = Wrappers.lambdaUpdate(Bom.class)
        .set(Bom::getStatus, BomStatus.ENABLED)
        .set(Bom::getApproveBy, userId)
        .set(Bom::getApproveTime, LocalDateTime.now())
        .eq(Bom::getId, id);

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", id);
  }

  @OpLog(type = ProduceOpLogType.class, name = "停用BOM，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void disable(String id) {

    Bom data = getBaseMapper().selectById(id);
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("BOM不存在！");
    }
    if (data.getStatus() != BomStatus.ENABLED) {
      throw new DefaultClientException("仅启用状态的BOM允许停用！");
    }

    LambdaUpdateWrapper<Bom> updateWrapper = Wrappers.lambdaUpdate(Bom.class)
        .set(Bom::getStatus, BomStatus.DISABLED)
        .eq(Bom::getId, id);

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", id);
  }

  @OpLog(type = ProduceOpLogType.class, name = "删除BOM，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Bom data = getBaseMapper().selectById(id);
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("BOM不存在！");
    }
    if (data.getStatus() != BomStatus.DRAFT) {
      throw new DefaultClientException("仅草稿状态的BOM允许删除！");
    }

    bomDetailService.deleteByBomId(id);
    getBaseMapper().deleteById(id);

    OpLogUtil.setVariable("id", id);
  }

  @CacheEvict(value = Bom.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }

  /**
   * 取消同成品SKU其他BOM的默认标记
   */
  private void clearDefaultByMainSkuId(String mainSkuId, String excludeId) {

    LambdaUpdateWrapper<Bom> wrapper = Wrappers.lambdaUpdate(Bom.class)
        .set(Bom::getIsDefault, Boolean.FALSE)
        .eq(Bom::getMainSkuId, mainSkuId)
        .ne(Bom::getId, excludeId);
    getBaseMapper().update(wrapper);
  }

  /**
   * 保存BOM明细
   */
  private void saveDetails(String bomId, List<BomDetailVo> details) {

    for (BomDetailVo detailVo : details) {
      BomDetail detail = new BomDetail();
      detail.setId(IdUtil.getId());
      detail.setBomId(bomId);
      detail.setProductId(detailVo.getProductId());
      detail.setSkuId(detailVo.getSkuId());
      detail.setUnitNum(detailVo.getUnitNum());
      detail.setScrapRate(detailVo.getScrapRate() == null ? java.math.BigDecimal.ZERO
          : detailVo.getScrapRate());
      detail.setIsKeyMaterial(Boolean.TRUE.equals(detailVo.getIsKeyMaterial()));
      detail.setSortOrder(detailVo.getSortOrder() == null ? 0 : detailVo.getSortOrder());
      detail.setDescription(StringUtil.isBlank(detailVo.getDescription())
          ? StringPool.EMPTY_STR : detailVo.getDescription());
      bomDetailService.save(detail);
    }
  }
}
