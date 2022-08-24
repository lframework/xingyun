package com.lframework.xingyun.common.biz.service;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.common.facade.entity.FileBox;
import com.lframework.xingyun.common.facade.vo.sw.filebox.BatchSendFileBoxVo;
import com.lframework.xingyun.common.facade.vo.sw.filebox.CreateFileBoxVo;
import com.lframework.xingyun.common.facade.vo.sw.filebox.QueryFileBoxVo;
import com.lframework.xingyun.common.facade.vo.sw.filebox.SendFileBoxVo;
import com.lframework.xingyun.common.facade.vo.sw.filebox.UpdateFileBoxVo;
import java.util.List;

/**
 * 文件收纳箱 Service
 */
public interface IFileBoxService extends BaseMpService<FileBox> {

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
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateFileBoxVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateFileBoxVo vo);

  /**
   * 发送
   *
   * @param vo
   */
  void send(SendFileBoxVo vo);

  /**
   * 批量发送
   *
   * @param vo
   */
  void batchSend(BatchSendFileBoxVo vo);

}
