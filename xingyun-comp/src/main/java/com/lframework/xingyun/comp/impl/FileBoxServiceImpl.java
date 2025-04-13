package com.lframework.xingyun.comp.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.components.upload.client.dto.UploadDto;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.SysConfService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.utils.UploadUtil;
import com.lframework.xingyun.comp.entity.FileBox;
import com.lframework.xingyun.comp.enums.FileBoxFileType;
import com.lframework.xingyun.comp.enums.FileBoxNodeType;
import com.lframework.xingyun.comp.enums.SwOpLogType;
import com.lframework.xingyun.comp.mappers.FileBoxMapper;
import com.lframework.xingyun.comp.service.FileBoxService;
import com.lframework.xingyun.comp.vo.sw.filebox.CreateFileBoxDirVo;
import com.lframework.xingyun.comp.vo.sw.filebox.QueryFileBoxVo;
import com.lframework.xingyun.comp.vo.sw.filebox.UpdateFileBoxVo;
import com.lframework.xingyun.comp.vo.sw.filebox.UploadFileBoxVo;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.core.service.RecursionMappingService;
import com.lframework.xingyun.core.service.SecurityUploadRecordService;
import com.lframework.xingyun.core.utils.OpLogUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileBoxServiceImpl extends
    BaseMpServiceImpl<FileBoxMapper, FileBox> implements FileBoxService {

  @Autowired
  private RecursionMappingService recursionMappingService;

  @Autowired
  private SecurityUploadRecordService securityUploadRecordService;

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

  @OpLog(type = SwOpLogType.SW, name = "修改文件，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateFileBoxVo vo) {

    FileBox data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("文件或文件夹不存在！");
    }

    LambdaUpdateWrapper<FileBox> updateWrapper = Wrappers.lambdaUpdate(FileBox.class)
        .set(data.getFileType() != FileBoxFileType.DIR, FileBox::getName, vo.getName())
        .set(FileBox::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR
                : vo.getDescription())
        .eq(FileBox::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = SwOpLogType.SW, name = "删除文件，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    List<String> delIds = new ArrayList<>();
    delIds.add(id);

    List<String> childIds = recursionMappingService.getNodeChildIds(id,
        ApplicationUtil.getBean(FileBoxNodeType.class));
    delIds.addAll(childIds);

    recursionMappingService.deleteNodeAndChildren(id,
        ApplicationUtil.getBean(FileBoxNodeType.class));

    Wrapper<FileBox> deleteWrapper = Wrappers.lambdaQuery(FileBox.class)
        .in(FileBox::getId, delIds)
        .eq(FileBox::getCreateById, SecurityUtil.getCurrentUser().getId());
    this.remove(deleteWrapper);
  }

  @OpLog(type = SwOpLogType.SW, name = "创建文件夹，父级目录：{}，文件夹名称：{}", params = {
      "#vo.parentPath", "#vo.name"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void createDir(CreateFileBoxDirVo vo) {

    Wrapper<FileBox> checkWrapper = Wrappers.lambdaQuery(FileBox.class)
        .eq(FileBox::getName, vo.getName()).eq(FileBox::getFilePath, vo.getParentPath())
        .eq(FileBox::getCreateById, SecurityUtil.getCurrentUser().getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("文件夹名称重复，请重新输入！");
    }
    FileBox dir = new FileBox();
    dir.setId(IdUtil.getId());
    dir.setName(vo.getName());
    dir.setFileType(FileBoxFileType.DIR);
    dir.setFilePath(vo.getParentPath());
    dir.setDescription(StringPool.EMPTY_STR);

    this.save(dir);

    if ("/".equals(vo.getParentPath())) {
      recursionMappingService.saveNode(dir.getId(),
          ApplicationUtil.getBean(FileBoxNodeType.class));
    } else {
      String path = vo.getParentPath().substring(0,
          vo.getParentPath().lastIndexOf("/") == 0 ? 1 : vo.getParentPath().lastIndexOf("/"));
      String name = vo.getParentPath().substring(vo.getParentPath().lastIndexOf("/") + 1);

      Wrapper<FileBox> queryWrapper = Wrappers.lambdaQuery(FileBox.class)
          .eq(FileBox::getName, name).eq(FileBox::getFilePath, path)
          .eq(FileBox::getCreateById, SecurityUtil.getCurrentUser().getId());
      FileBox fileBox = this.getOne(queryWrapper);
      if (fileBox == null) {
        throw new DefaultClientException("父级目录不存在！");
      }

      List<String> parentIds = recursionMappingService.getNodeParentIds(fileBox.getId(),
          ApplicationUtil.getBean(FileBoxNodeType.class));
      parentIds.add(fileBox.getId());

      recursionMappingService.saveNode(dir.getId(),
          ApplicationUtil.getBean(FileBoxNodeType.class), parentIds);
    }
  }

  @OpLog(type = SwOpLogType.SW, name = "上传文件，父级目录：{}，文件名称：{}", params = {
      "#vo.path", "#vo.name"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void upload(UploadFileBoxVo vo) {
    MultipartFile file = vo.getFile();
    UploadDto uploadDto = UploadUtil.upload(file,
        CollectionUtil.toList("filebox", SecurityUtil.getCurrentUser().getId()), true);

    String recordId = securityUploadRecordService.create(uploadDto.getUploadType(),
        uploadDto.getObjectName());

    FileBox record = new FileBox();
    record.setId(IdUtil.getId());
    record.setName(file.getOriginalFilename());
    record.setRecordId(recordId);
    record.setContentType(file.getContentType());
    record.setFileType(FileBoxFileType.FILE);
    record.setFileSize(FileUtil.readableFileSize(file.getSize()));
    record.setFilePath(vo.getPath());
    record.setFileSuffix(FileUtil.getSuffix(file.getOriginalFilename()));
    record.setDescription(StringPool.EMPTY_STR);

    this.save(record);

    if ("/".equals(vo.getPath())) {
      recursionMappingService.saveNode(record.getId(),
          ApplicationUtil.getBean(FileBoxNodeType.class));
    } else {
      String path = vo.getPath()
          .substring(0, vo.getPath().lastIndexOf("/") == 0 ? 1 : vo.getPath().lastIndexOf("/"));
      String name = vo.getPath().substring(vo.getPath().lastIndexOf("/") + 1);

      Wrapper<FileBox> queryWrapper = Wrappers.lambdaQuery(FileBox.class)
          .eq(FileBox::getName, name).eq(FileBox::getFilePath, path)
          .eq(FileBox::getCreateById, SecurityUtil.getCurrentUser().getId());
      FileBox fileBox = this.getOne(queryWrapper);
      if (fileBox == null) {
        throw new DefaultClientException("父级目录不存在！");
      }

      List<String> parentIds = recursionMappingService.getNodeParentIds(fileBox.getId(),
          ApplicationUtil.getBean(FileBoxNodeType.class));
      parentIds.add(fileBox.getId());

      recursionMappingService.saveNode(record.getId(),
          ApplicationUtil.getBean(FileBoxNodeType.class), parentIds);
    }
  }

  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
