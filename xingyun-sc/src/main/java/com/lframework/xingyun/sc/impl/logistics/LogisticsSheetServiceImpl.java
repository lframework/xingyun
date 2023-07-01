package com.lframework.xingyun.sc.impl.logistics;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.template.core.components.permission.DataPermissionHandler;
import com.lframework.xingyun.template.core.enums.DefaultOpLogType;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.starter.web.service.GenerateCodeService;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.core.annations.OrderTimeLineLog;
import com.lframework.xingyun.core.components.permission.DataPermissionPool;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.logistics.LogisticsSheetBizOrderDto;
import com.lframework.xingyun.sc.dto.logistics.LogisticsSheetFullDto;
import com.lframework.xingyun.sc.entity.LogisticsSheet;
import com.lframework.xingyun.sc.entity.LogisticsSheetDetail;
import com.lframework.xingyun.sc.enums.LogisticsSheetDetailBizType;
import com.lframework.xingyun.sc.enums.LogisticsSheetStatus;
import com.lframework.xingyun.sc.mappers.LogisticsSheetMapper;
import com.lframework.xingyun.sc.service.logistics.LogisticsSheetDetailService;
import com.lframework.xingyun.sc.service.logistics.LogisticsSheetService;
import com.lframework.xingyun.sc.vo.logistics.CreateLogisticsSheetVo;
import com.lframework.xingyun.sc.vo.logistics.DeliveryLogisticsSheetVo;
import com.lframework.xingyun.sc.vo.logistics.LogisticsSheetSelectorVo;
import com.lframework.xingyun.sc.vo.logistics.QueryLogisticsSheetBizOrderVo;
import com.lframework.xingyun.sc.vo.logistics.QueryLogisticsSheetVo;
import com.lframework.xingyun.sc.vo.logistics.UpdateLogisticsSheetVo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogisticsSheetServiceImpl extends
    BaseMpServiceImpl<LogisticsSheetMapper, LogisticsSheet> implements LogisticsSheetService {

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private LogisticsSheetDetailService logisticsSheetDetailService;

  @Override
  public PageResult<LogisticsSheet> query(Integer pageIndex, Integer pageSize,
      QueryLogisticsSheetVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<LogisticsSheet> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<LogisticsSheet> query(QueryLogisticsSheetVo vo) {
    return getBaseMapper().query(vo,
        DataPermissionHandler.getDataPermission(DataPermissionPool.ORDER, Arrays.asList("order"),
            Arrays.asList("o")));
  }

  @Override
  public PageResult<LogisticsSheet> selector(Integer pageIndex, Integer pageSize,
      LogisticsSheetSelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<LogisticsSheet> datas = getBaseMapper().selector(vo,
        DataPermissionHandler.getDataPermission(DataPermissionPool.ORDER, Arrays.asList("order"),
            Arrays.asList("o")));

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public LogisticsSheetFullDto getDetail(String id) {
    LogisticsSheetFullDto sheet = getBaseMapper().getDetail(id);
    if (sheet == null) {
      throw new InputErrorException("物流单不存在！");
    }

    return sheet;
  }

  @Override
  public PageResult<LogisticsSheetBizOrderDto> queryBizOrder(Integer pageIndex, Integer pageSize,
      QueryLogisticsSheetBizOrderVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<LogisticsSheetBizOrderDto> datas = getBaseMapper().queryBizOrder(vo,
        DataPermissionHandler.getDataPermission(DataPermissionPool.ORDER, Arrays.asList("order"),
            Arrays.asList("o")));

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "创建物流单，单号：{}", params = "#code")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateLogisticsSheetVo vo) {
    LogisticsSheet sheet = new LogisticsSheet();
    sheet.setId(IdUtil.getId());
    sheet.setCode(generateCodeService.generate(GenerateCodeTypePool.LOGISTICS_SHEET));

    this.create(sheet, vo);

    sheet.setStatus(LogisticsSheetStatus.CREATED);

    getBaseMapper().insert(sheet);

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);

    return sheet.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改物流单，单号：{}", params = "#code")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateLogisticsSheetVo vo) {
    LogisticsSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new InputErrorException("物流单不存在！");
    }

    if (sheet.getStatus() != LogisticsSheetStatus.CREATED) {
      throw new DefaultClientException("物流单已发货，无法修改！");
    }

    // 删除明细
    Wrapper<LogisticsSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
        LogisticsSheetDetail.class).eq(LogisticsSheetDetail::getSheetId, sheet.getId());
    logisticsSheetDetailService.remove(deleteDetailWrapper);

    this.create(sheet, vo);

    sheet.setStatus(LogisticsSheetStatus.CREATED);

    List<LogisticsSheetStatus> statusList = new ArrayList<>();
    statusList.add(LogisticsSheetStatus.CREATED);

    Wrapper<LogisticsSheet> updateOrderWrapper = Wrappers.lambdaUpdate(LogisticsSheet.class)
        .eq(LogisticsSheet::getId, sheet.getId()).in(LogisticsSheet::getStatus, statusList);
    if (getBaseMapper().updateAllColumn(sheet, updateOrderWrapper) != 1) {
      throw new DefaultClientException("物流单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "物流单发货，单号：{}", params = "#code")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void delivery(DeliveryLogisticsSheetVo vo) {

    LogisticsSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new InputErrorException("物流单不存在！");
    }

    if (sheet.getStatus() != LogisticsSheetStatus.CREATED) {
      throw new DefaultClientException("物流单已发货，无法重复发货！");
    }

    List<LogisticsSheetStatus> statusList = new ArrayList<>();
    statusList.add(LogisticsSheetStatus.CREATED);

    Wrapper<LogisticsSheet> updateOrderWrapper = Wrappers.lambdaUpdate(LogisticsSheet.class)
        .set(LogisticsSheet::getDeliveryBy, SecurityUtil.getCurrentUser().getId())
        .set(LogisticsSheet::getDeliveryTime, LocalDateTime.now())
        .set(StringUtil.isNotBlank(vo.getLogisticsNo()), LogisticsSheet::getLogisticsNo,
            vo.getLogisticsNo())
        .set(vo.getTotalAmount() != null, LogisticsSheet::getTotalAmount, vo.getTotalAmount())
        .set(LogisticsSheet::getStatus, LogisticsSheetStatus.DELIVERY)
        .eq(LogisticsSheet::getId, sheet.getId()).in(LogisticsSheet::getStatus, statusList);
    if (getBaseMapper().update(updateOrderWrapper) != 1) {
      throw new DefaultClientException("物流单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "删除物流单，单号：{}", params = "#code")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    LogisticsSheet order = getBaseMapper().selectById(id);
    if (order == null) {
      throw new InputErrorException("物流单不存在！");
    }

    if (order.getStatus() != LogisticsSheetStatus.CREATED) {
      throw new DefaultClientException("已发货的物流单不允许执行删除操作！");
    }

    // 删除明细
    Wrapper<LogisticsSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
        LogisticsSheetDetail.class).eq(LogisticsSheetDetail::getSheetId, order.getId());
    logisticsSheetDetailService.remove(deleteDetailWrapper);

    getBaseMapper().deleteById(id);

    OpLogUtil.setVariable("code", order.getCode());
  }

  @OrderTimeLineLog(orderId = "#ids", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          LogisticsSheetService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException(
              "第" + orderNo + "个物流单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  private void create(LogisticsSheet sheet, CreateLogisticsSheetVo vo) {
    sheet.setLogisticsNo(vo.getLogisticsNo());
    sheet.setLogisticsCompanyId(vo.getLogisticsCompanyId());
    sheet.setSenderName(vo.getSenderName());
    sheet.setSenderTelephone(vo.getSenderTelephone());
    sheet.setSenderProvinceId(vo.getSenderProvinceId());
    sheet.setSenderCityId(vo.getSenderCityId());
    sheet.setSenderDistrictId(vo.getSenderDistrictId());
    sheet.setSenderAddress(vo.getSenderAddress());
    sheet.setReceiverName(vo.getReceiverName());
    sheet.setReceiverTelephone(vo.getReceiverTelephone());
    sheet.setReceiverProvinceId(vo.getReceiverProvinceId());
    sheet.setReceiverCityId(vo.getReceiverCityId());
    sheet.setReceiverDistrictId(vo.getReceiverDistrictId());
    sheet.setReceiverAddress(vo.getReceiverAddress());
    sheet.setTotalWeight(vo.getTotalWeight());
    sheet.setTotalVolume(vo.getTotalVolume());
    sheet.setTotalAmount(vo.getTotalAmount());
    sheet.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    List<LogisticsSheetDetail> details = vo.getBizOrders().stream().map(t -> {
      LogisticsSheetDetail detail = new LogisticsSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(sheet.getId());
      detail.setBizId(t.getBizId());
      detail.setBizType(EnumUtil.getByCode(LogisticsSheetDetailBizType.class, t.getBizType()));

      return detail;
    }).collect(Collectors.toList());

    logisticsSheetDetailService.saveBatch(details);
  }
}
