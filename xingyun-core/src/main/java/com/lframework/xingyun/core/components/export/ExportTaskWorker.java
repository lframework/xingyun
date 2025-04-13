package com.lframework.xingyun.core.components.export;

import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.resp.PageResult;

public interface ExportTaskWorker<P, D, M extends ExcelModel> {

  /**
   * 解析传入参数
   *
   * @param json
   * @return
   */
  P parseParams(String json);

  /**
   * 查询数据
   *
   * @param pageIndex
   * @param pageSize
   * @param params
   * @return
   */
  PageResult<D> getDataList(int pageIndex, int pageSize, P params);

  /**
   * 导出数据
   *
   * @param data
   */
  M exportData(D data);

  /**
   * 处理异常
   *
   * @param e
   */
  default void handleError(Exception e) {
  }

  /**
   * 获取导出模型类
   *
   * @return
   */
  Class<M> getModelClass();
}
