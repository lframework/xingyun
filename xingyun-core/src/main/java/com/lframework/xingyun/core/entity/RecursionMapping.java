package com.lframework.xingyun.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

/**
 * <p>
 * 递归映射
 * </p>
 *
 * @author zmj
 * @since 2021-06-27
 */
@Data
@TableName("recursion_mapping")
public class RecursionMapping extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  private String id;

  /**
   * 节点ID
   */
  private String nodeId;

  /**
   * 节点类型
   */
  private Integer nodeType;

  /**
   * 从顶点到当前结点的路径，用,分割
   */
  private String path;

  /**
   * 节点层级
   */
  private Integer level;
}
