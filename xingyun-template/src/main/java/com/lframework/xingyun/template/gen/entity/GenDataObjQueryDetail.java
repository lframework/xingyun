package com.lframework.xingyun.template.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.xingyun.template.gen.enums.GenDataType;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

/**
 * <p>
 * 数据对象自定义查询明细
 * </p>
 *
 * @author zmj
 * @since 2022-09-24
 */
@Data
@TableName("gen_data_obj_query_detail")
public class GenDataObjQueryDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "GenDataObjQueryDetail";

  /**
   * ID
   */
  private String id;

  /**
   * 数据对象ID
   */
  private String dataObjId;

  /**
   * 显示名称
   */
  private String customName;

  /**
   * 自定义SQL
   */
  private String customSql;

  /**
   * 别名
   */
  private String customAlias;

  /**
   * 数据类型
   */
  private GenDataType dataType;

  /**
   * 排序
   */
  private Integer orderNo;
}
