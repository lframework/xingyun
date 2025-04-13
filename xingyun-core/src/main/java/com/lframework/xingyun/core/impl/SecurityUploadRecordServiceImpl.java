package com.lframework.xingyun.core.impl;

import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.core.entity.SecurityUploadRecord;
import com.lframework.xingyun.core.enums.UploadType;
import com.lframework.xingyun.core.mappers.SecurityUploadRecordMapper;
import com.lframework.xingyun.core.service.SecurityUploadRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityUploadRecordServiceImpl extends
    BaseMpServiceImpl<SecurityUploadRecordMapper, SecurityUploadRecord> implements
    SecurityUploadRecordService {

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(String uploadType, String filePath) {
    SecurityUploadRecord uploadRecord = new SecurityUploadRecord();
    uploadRecord.setId(IdUtil.getId());
    uploadRecord.setUploadType(EnumUtil.getByCode(UploadType.class, uploadType));
    uploadRecord.setFilePath(filePath);

    this.save(uploadRecord);

    return uploadRecord.getId();
  }
}
