package com.lframework.xingyun.common.biz.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.xingyun.common.biz.mappers.OnlineExcelMapper;
import com.lframework.xingyun.common.biz.service.IOnlineExcelService;
import com.lframework.xingyun.common.facade.entity.OnlineExcel;
import com.lframework.xingyun.common.facade.vo.sw.excel.BatchSendOnlineExcelVo;
import com.lframework.xingyun.common.facade.vo.sw.excel.CreateOnlineExcelVo;
import com.lframework.xingyun.common.facade.vo.sw.excel.QueryOnlineExcelVo;
import com.lframework.xingyun.common.facade.vo.sw.excel.SendOnlineExcelVo;
import com.lframework.xingyun.common.facade.vo.sw.excel.UpdateOnlineExcelContentVo;
import com.lframework.xingyun.common.facade.vo.sw.excel.UpdateOnlineExcelVo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OnlineExcelServiceImpl extends
    BaseMpServiceImpl<OnlineExcelMapper, OnlineExcel> implements IOnlineExcelService {

  @Autowired
  private IUserService userService;

  @Override
  public PageResult<OnlineExcel> query(Integer pageIndex, Integer pageSize, QueryOnlineExcelVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<OnlineExcel> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<OnlineExcel> query(QueryOnlineExcelVo vo) {

    return getBaseMapper().query(vo, SecurityUtil.getCurrentUser().getId());
  }

  @Override
  public OnlineExcel findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增在线Excel，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public String create(CreateOnlineExcelVo vo) {

    OnlineExcel data = new OnlineExcel();
    data.setId(IdUtil.getId());
    data.setName(vo.getName());
    data.setContent(vo.getContent());
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改在线Excel，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public void update(UpdateOnlineExcelVo vo) {

    OnlineExcel data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("在线Excel不存在！");
    }

    LambdaUpdateWrapper<OnlineExcel> updateWrapper = Wrappers.lambdaUpdate(OnlineExcel.class)
        .set(OnlineExcel::getName, vo.getName()).set(OnlineExcel::getAvailable, vo.getAvailable())
        .set(OnlineExcel::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(OnlineExcel::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "修改在线Excel内容，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public void updateContent(UpdateOnlineExcelContentVo vo) {
    OnlineExcel data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("在线Excel不存在！");
    }

    Map content = JsonUtil.parseObject(data.getContent(), Map.class);
    List sheets = JsonUtil.parseList(vo.getContent(), Map.class);
    content.put("sheets", sheets);

    LambdaUpdateWrapper<OnlineExcel> updateWrapper = Wrappers.lambdaUpdate(OnlineExcel.class)
        .set(OnlineExcel::getContent, JsonUtil.toJsonString(content))
        .eq(OnlineExcel::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "发送Excel文件，发送方{}, 接收方{}", params = {"#sender", "#receiver"})
  @Transactional
  @Override
  public void send(SendOnlineExcelVo vo) {

    OnlineExcel record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("Excel文件不存在！");
    }

    UserDto receiver = userService.findById(vo.getUserId());
    if (receiver == null) {
      throw new DefaultClientException("接收方不存在！");
    }

    if (vo.getSelfSave()) {
      record.setId(IdUtil.getId());
      record.setAvailable(Boolean.TRUE);
      record.setCreateBy(receiver.getId());
      this.save(record);
    } else {
      record.setCreateBy(vo.getUserId());
      record.setAvailable(Boolean.TRUE);
      this.updateById(record);
    }

    OpLogUtil.setVariable("sender", SecurityUtil.getCurrentUser().getId());
    OpLogUtil.setVariable("receiver", vo.getUserId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "批量发送Excel文件，发送方{}, 接收方{}", params = {"#sender",
      "#receiver"})
  @Transactional
  @Override
  public void batchSend(BatchSendOnlineExcelVo vo) {

    UserDto receiver = userService.findById(vo.getUserId());
    if (receiver == null) {
      throw new DefaultClientException("接收方不存在！");
    }

    for (String id : vo.getIds()) {
      OnlineExcel record = this.getById(id);
      if (record == null) {
        throw new DefaultClientException("Excel文件不存在！");
      }

      if (vo.getSelfSave()) {
        record.setId(IdUtil.getId());
        record.setCreateBy(receiver.getId());
        record.setAvailable(Boolean.TRUE);
        this.save(record);
      } else {
        record.setCreateBy(vo.getUserId());
        record.setAvailable(Boolean.TRUE);
        this.updateById(record);
      }
    }

    OpLogUtil.setVariable("sender", SecurityUtil.getCurrentUser().getId());
    OpLogUtil.setVariable("receiver", vo.getUserId());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
