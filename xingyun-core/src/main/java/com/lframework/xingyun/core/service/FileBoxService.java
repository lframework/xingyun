package com.lframework.xingyun.core.service;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.core.entity.FileBox;
import com.lframework.xingyun.core.vo.sw.filebox.CreateFileBoxDirVo;
import com.lframework.xingyun.core.vo.sw.filebox.QueryFileBoxVo;
import com.lframework.xingyun.core.vo.sw.filebox.UpdateFileBoxVo;
import com.lframework.xingyun.core.vo.sw.filebox.UploadFileBoxVo;
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
   * 批量删除
   * @param ids
   */
  void batchDelete(List<String> ids);

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
