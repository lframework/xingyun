package com.lframework.xingyun.comp.service;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.comp.entity.FileBox;
import com.lframework.xingyun.comp.vo.sw.filebox.CreateFileBoxDirVo;
import com.lframework.xingyun.comp.vo.sw.filebox.QueryFileBoxVo;
import com.lframework.xingyun.comp.vo.sw.filebox.UpdateFileBoxVo;
import com.lframework.xingyun.comp.vo.sw.filebox.UploadFileBoxVo;
import java.util.List;

/**
 * 文件收纳箱 Service
 */
public interface FileBoxService extends BaseMpService<FileBox> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<FileBox> query(Integer pageIndex, Integer pageSize, QueryFileBoxVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<FileBox> query(QueryFileBoxVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  FileBox findById(String id);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateFileBoxVo vo);


  /**
   * 根据ID删除
   * @param id
   */
  void deleteById(String id);

  /**
   * 创建文件夹
   * @param vo
   */
  void createDir(CreateFileBoxDirVo vo);

  /**
   * 上传文件
   * @param vo
   */
  void upload(UploadFileBoxVo vo);

}
