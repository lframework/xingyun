package com.lframework.xingyun.comp.service;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.comp.entity.OnlineExcel;
import com.lframework.xingyun.comp.vo.sw.excel.BatchSendOnlineExcelVo;
import com.lframework.xingyun.comp.vo.sw.excel.CreateOnlineExcelVo;
import com.lframework.xingyun.comp.vo.sw.excel.QueryOnlineExcelVo;
import com.lframework.xingyun.comp.vo.sw.excel.SendOnlineExcelVo;
import com.lframework.xingyun.comp.vo.sw.excel.UpdateOnlineExcelContentVo;
import com.lframework.xingyun.comp.vo.sw.excel.UpdateOnlineExcelVo;
import java.util.List;

/**
 * 在线Excel Service
 */
public interface OnlineExcelService extends BaseMpService<OnlineExcel> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<OnlineExcel> query(Integer pageIndex, Integer pageSize, QueryOnlineExcelVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<OnlineExcel> query(QueryOnlineExcelVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  OnlineExcel findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateOnlineExcelVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateOnlineExcelVo vo);

  /**
   * 修改内容
   *
   * @param vo
   */
  void updateContent(UpdateOnlineExcelContentVo vo);

  /**
   * 发送
   *
   * @param vo
   */
  void send(SendOnlineExcelVo vo);

  /**
   * 批量发送
   *
   * @param vo
   */
  void batchSend(BatchSendOnlineExcelVo vo);

}
