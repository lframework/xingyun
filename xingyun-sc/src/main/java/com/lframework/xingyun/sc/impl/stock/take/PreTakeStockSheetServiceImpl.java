package com.lframework.xingyun.sc.impl.stock.take;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.service.IGenerateCodeService;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetFullDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetSelectorDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.QueryPreTakeStockSheetProductDto;
import com.lframework.xingyun.sc.entity.PreTakeStockSheet;
import com.lframework.xingyun.sc.entity.PreTakeStockSheetDetail;
import com.lframework.xingyun.sc.enums.PreTakeStockSheetStatus;
import com.lframework.xingyun.sc.mappers.PreTakeStockSheetDetailMapper;
import com.lframework.xingyun.sc.mappers.PreTakeStockSheetMapper;
import com.lframework.xingyun.sc.service.stock.take.IPreTakeStockSheetService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockSheetService;
import com.lframework.xingyun.sc.vo.stock.take.pre.CreatePreTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.PreTakeStockProductVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.PreTakeStockSheetSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.QueryPreTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.UpdatePreTakeStockSheetVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PreTakeStockSheetServiceImpl implements IPreTakeStockSheetService {

  @Autowired
  private PreTakeStockSheetMapper preTakeStockSheetMapper;

  @Autowired
  private PreTakeStockSheetDetailMapper preTakeStockSheetDetailMapper;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Autowired
  private ITakeStockSheetService takeStockSheetService;

  @Override
  public PageResult<PreTakeStockSheetDto> query(Integer pageIndex, Integer pageSize,
      QueryPreTakeStockSheetVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<PreTakeStockSheetDto> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<PreTakeStockSheetDto> query(QueryPreTakeStockSheetVo vo) {

    return preTakeStockSheetMapper.query(vo);
  }

  @Override
  public PageResult<PreTakeStockSheetSelectorDto> selector(Integer pageIndex, Integer pageSize,
      PreTakeStockSheetSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<PreTakeStockSheetSelectorDto> datas = preTakeStockSheetMapper.selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public PreTakeStockSheetDto getById(String id) {

    return preTakeStockSheetMapper.getById(id);
  }

  @Override
  public PreTakeStockSheetFullDto getDetail(String id) {

    PreTakeStockSheetFullDto data = preTakeStockSheetMapper.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("预先盘点单不存在！");
    }

    return data;
  }

  @Override
  public List<QueryPreTakeStockSheetProductDto> getProducts(String id, String planId) {

    return preTakeStockSheetMapper.getProducts(id, planId);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增预先盘点单，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public String create(CreatePreTakeStockSheetVo vo) {

    PreTakeStockSheet data = new PreTakeStockSheet();
    data.setId(IdUtil.getId());
    data.setCode(generateCodeService.generate(GenerateCodeTypePool.PRE_TAKE_STOCK_SHEET));
    data.setScId(vo.getScId());
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    data.setTakeStatus(EnumUtil.getByCode(PreTakeStockSheetStatus.class, vo.getTakeStatus()));

    preTakeStockSheetMapper.insert(data);

    int orderNo = 1;
    for (PreTakeStockProductVo product : vo.getProducts()) {
      PreTakeStockSheetDetail detail = new PreTakeStockSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(data.getId());
      detail.setProductId(product.getProductId());
      detail.setFirstNum(product.getFirstNum());
      detail.setSecondNum(product.getSecondNum());
      detail.setRandNum(product.getRandNum());
      detail.setOrderNo(orderNo++);

      preTakeStockSheetDetailMapper.insert(detail);
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改预先盘点单，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public void update(UpdatePreTakeStockSheetVo vo) {

    PreTakeStockSheet data = preTakeStockSheetMapper.selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("预先盘点单不存在！");
    }

    LambdaUpdateWrapper<PreTakeStockSheet> updateWrapper = Wrappers
        .lambdaUpdate(PreTakeStockSheet.class)
        .set(PreTakeStockSheet::getScId, vo.getScId())
        .set(PreTakeStockSheet::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(PreTakeStockSheet::getTakeStatus,
            EnumUtil.getByCode(PreTakeStockSheetStatus.class, vo.getTakeStatus()))
        .eq(PreTakeStockSheet::getId, vo.getId());

    preTakeStockSheetMapper.update(updateWrapper);

    Wrapper<PreTakeStockSheetDetail> deleteDetailWrapper = Wrappers
        .lambdaQuery(PreTakeStockSheetDetail.class)
        .eq(PreTakeStockSheetDetail::getSheetId, data.getId());
    preTakeStockSheetDetailMapper.delete(deleteDetailWrapper);

    int orderNo = 1;
    for (PreTakeStockProductVo product : vo.getProducts()) {
      PreTakeStockSheetDetail detail = new PreTakeStockSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(data.getId());
      detail.setProductId(product.getProductId());
      detail.setFirstNum(product.getFirstNum());
      detail.setSecondNum(product.getSecondNum());
      detail.setRandNum(product.getRandNum());
      detail.setOrderNo(orderNo++);

      preTakeStockSheetDetailMapper.insert(detail);
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "删除预先盘点单，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public void deleteById(String id) {

    if (takeStockSheetService.hasRelatePreTakeStockSheet(id)) {
      throw new DefaultClientException("已有库存盘点单关联此预先盘点单，不允许执行删除操作！");
    }

    preTakeStockSheetMapper.deleteById(id);

    Wrapper<PreTakeStockSheetDetail> deleteDetailWrapper = Wrappers
        .lambdaQuery(PreTakeStockSheetDetail.class).eq(PreTakeStockSheetDetail::getSheetId, id);
    preTakeStockSheetDetailMapper.delete(deleteDetailWrapper);
  }

  @Transactional
  @Override
  public void batchDelete(List<String> ids) {
    for (String id : ids) {
      getThis(this.getClass()).deleteById(id);
    }
  }

  @Override
  public void cleanCacheByKey(String key) {

  }
}
