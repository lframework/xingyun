package com.lframework.xingyun.core.impl;

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
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.xingyun.core.entity.FileBox;
import com.lframework.xingyun.core.mappers.FileBoxMapper;
import com.lframework.xingyun.core.service.IFileBoxService;
import com.lframework.xingyun.core.vo.sw.filebox.BatchSendFileBoxVo;
import com.lframework.xingyun.core.vo.sw.filebox.CreateFileBoxVo;
import com.lframework.xingyun.core.vo.sw.filebox.QueryFileBoxVo;
import com.lframework.xingyun.core.vo.sw.filebox.SendFileBoxVo;
import com.lframework.xingyun.core.vo.sw.filebox.UpdateFileBoxVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileBoxServiceImpl extends
    BaseMpServiceImpl<FileBoxMapper, FileBox> implements IFileBoxService {

  @Autowired
  private IUserService userService;

  @Override
  public PageResult<FileBox> query(Integer pageIndex, Integer pageSize, QueryFileBoxVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<FileBox> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<FileBox> query(QueryFileBoxVo vo) {

    return getBaseMapper().query(vo, SecurityUtil.getCurrentUser().getId());
  }

  @Override
  public FileBox findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增文件收纳箱，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public String create(CreateFileBoxVo vo) {

    FileBox data = new FileBox();
    data.setId(IdUtil.getId());
    data.setName(vo.getName());
    data.setUrl(vo.getUrl());
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改文件收纳箱，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public void update(UpdateFileBoxVo vo) {

    FileBox data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("文件收纳箱不存在！");
    }

    LambdaUpdateWrapper<FileBox> updateWrapper = Wrappers.lambdaUpdate(FileBox.class)
        .set(FileBox::getName, vo.getName()).set(FileBox::getAvailable, vo.getAvailable())
        .set(FileBox::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(FileBox::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "发送文件收纳箱，发送方{}, 接收方{}", params = {"#sender", "#receiver"})
  @Transactional
  @Override
  public void send(SendFileBoxVo vo) {

    FileBox record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("文件收纳箱不存在！");
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

  @OpLog(type = OpLogType.OTHER, name = "批量发送文件收纳箱，发送方{}, 接收方{}", params = {"#sender",
      "#receiver"})
  @Transactional
  @Override
  public void batchSend(BatchSendFileBoxVo vo) {

    UserDto receiver = userService.findById(vo.getUserId());
    if (receiver == null) {
      throw new DefaultClientException("接收方不存在！");
    }

    for (String id : vo.getIds()) {
      FileBox record = this.getById(id);
      if (record == null) {
        throw new DefaultClientException("文件收纳箱不存在！");
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
