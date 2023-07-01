package com.lframework.xingyun.template.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.xingyun.template.gen.enums.GenCustomListDetailType;
import com.lframework.xingyun.template.gen.enums.GenQueryWidthType;
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
@TableName("gen_custom_list_detail")
public class GenCustomListDetail extends BaseEntity {

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
   * 关联ID 当type为主表时，此值为数据对象ID 当type为子表时，此值为数据对象明细ID 当type为自定义查询时，此值为GenDataObjQueryDetail的ID
   */
  private String relaId;

  /**
   * 数据实体ID
   */
  private String dataEntityId;

  /**
   * 宽度类型
   */
  private GenQueryWidthType widthType;

  /**
   * 宽度
   */
  private Integer width;

  /**
   * 是否页面排序
   */
  private Boolean sortable;

  /**
   * 排序编号
   */
  private Integer orderNo;

  /**
   * 类型
   */
  private GenCustomListDetailType type;

  /**
   * 格式化脚本
   */
  private String formatter;

}
