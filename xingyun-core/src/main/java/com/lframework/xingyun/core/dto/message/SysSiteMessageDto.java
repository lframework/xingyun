package com.lframework.xingyun.core.dto.message;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class SysSiteMessageDto implements BaseDto, Serializable {

  /**
   * 接收邮件列表
   */
  private List<String> userIdList;

  /**
   * 标题
   */
  private String title;

  /**
   * 内容
   */
  private String content;

  /**
   * 业务键
   */
  private String bizKey;

  /**
   * 创建人ID 如果是空表示由系统自动发起
   */
  private String createUserId;
}
