package com.lframework.xingyun.core.service;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.core.entity.SecurityUploadRecord;

/**
 * 安全上传记录 Service
 */
public interface SecurityUploadRecordService extends BaseMpService<SecurityUploadRecord> {

  /**
   * 新增
   *
   * @param uploadType
   * @param filePath
   * @return
   */
  String create(String uploadType, String filePath);
}
