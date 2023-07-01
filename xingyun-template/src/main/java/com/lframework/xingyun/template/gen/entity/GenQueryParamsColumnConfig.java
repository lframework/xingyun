package com.lframework.xingyun.template.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.xingyun.template.gen.enums.GenQueryType;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
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
@TableName("gen_query_params_column_config")
public class GenQueryParamsColumnConfig extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 查询类型
   */
  private GenQueryType queryType;

  /**
   * 排序编号
   */
  private Integer orderNo;


}
