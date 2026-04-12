package com.lframework.xingyun.comp.controller;

import com.lframework.starter.common.exceptions.impl.AccessDeniedException;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.web.core.components.redis.RedisHandler;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.utils.ResponseUtil;
import com.lframework.starter.web.core.utils.UploadUtil;
import com.lframework.starter.web.inner.entity.SecurityUploadRecord;
import com.lframework.starter.web.inner.service.SecurityUploadRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Operation;
import java.io.File;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 安全下载
 *
 * @author zmj
 */
@Tag(name = "安全下载")
@Slf4j
@Validated
@RestController
@RequestMapping("/download/security")
public class SecurityDownloadController extends DefaultBaseController {

  @Autowired
  private SecurityUploadRecordService securityUploadRecordService;

  @Autowired
  private RedisHandler redisHandler;

  @Operation(summary = "下载文件（获取签名URL）")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @GetMapping("/url")
  public InvokeResult<String> getSecurityDownloadUrl(@NotBlank(message = "ID不能为空！") String id) {
    SecurityUploadRecord record = securityUploadRecordService.getById(id);
    if (record == null) {
      throw new AccessDeniedException();
    }

    if (!record.getCreateById().equals(getCurrentUser().getId())) {
      throw new AccessDeniedException();
    }

    return InvokeResultBuilder.success(
        UploadUtil.generatePresignedUrl(record.getUploadType().getCode(), record.getFilePath()));
  }

  @Operation(summary = "下载文件")
  @Parameter(name = "sign", description = "签名", in = ParameterIn.QUERY, required = true)
  @GetMapping
  public void download(@NotBlank(message = "签名不能为空！") String sign) {
    Object val = redisHandler.hget("security-upload", sign);
    if (val == null) {
      throw new AccessDeniedException();
    }

    File file = new File(val.toString());
    if (!FileUtil.exist(file) || !FileUtil.isFile(file)) {
      log.info("下载的文件不存在，sign {} val {}", sign, val);
      throw new AccessDeniedException();
    }

    ResponseUtil.download(file);
  }
}
