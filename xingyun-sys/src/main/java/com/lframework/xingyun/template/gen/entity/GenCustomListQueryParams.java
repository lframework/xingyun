package com.lframework.xingyun.template.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.xingyun.template.gen.enums.GenCustomListDetailType;
import com.lframework.xingyun.template.gen.enums.GenQueryType;
import com.lframework.starter.web.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-12-10
 */
@Data
@TableName("gen_custom_list_query_params")
public class GenCustomListQueryParams extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 自定义列表ID
   */
  private String customListId;

  /**
   * 关联ID 当type为主表时，此值为数据对象ID 当type为子表时，此值为数据对象明细ID
   */
  private String relaId;

  /**
   * 数据实体ID
   */
  private String dataEntityId;

  /**
   * 前端显示
   */
  private Boolean frontShow;

  /**
   * 查询类型
   */
  private GenQueryType queryType;

  /**
   * 表单宽度
   */
  private Integer formWidth;

  /**
   * 默认值
   */
  private String defaultValue;

  /**
   * 排序编号
   */
  private Integer orderNo;

  /**
   * 类型
   */
  private GenCustomListDetailType type;
}
