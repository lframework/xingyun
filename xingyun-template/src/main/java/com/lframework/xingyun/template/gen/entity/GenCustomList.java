package com.lframework.xingyun.template.gen.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.xingyun.template.gen.enums.GenCustomListType;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 自定义列表
 * </p>
 *
 * @author zmj
 * @since 2022-09-24
 */
@Data
@TableName("gen_custom_list")
public class GenCustomList extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "GenCustomList";

  /**
   * ID
   */
  private String id;

  /**
   * 名称
   */
  private String name;

  /**
   * 分类ID
   */
  private String categoryId;

  /**
   * 数据对象ID
   */
  private String dataObjId;

  /**
   * 列表类型
   */
  private GenCustomListType listType;

  /**
   * 表单Label宽度
   */
  private Integer labelWidth;

  /**
   * 是否分页
   */
  private Boolean hasPage;

  /**
   * 是否树形列表
   */
  private Boolean treeData;

  /**
   * ID字段
   */
  private String idColumn;

  /**
   * ID字段关联ID
   */
  private String idColumnRelaId;

  /**
   * 父级ID字段
   */
  private String treePidColumn;

  /**
   * 父级ID字段关联ID
   */
  private String treePidColumnRelaId;

  /**
   * 树形节点字段
   */
  private String treeNodeColumn;

  /**
   * 树形节点字段关联ID
   */
  private String treeNodeColumnRelaId;

  /**
   * 子节点Key值
   */
  private String treeChildrenKey;

  /**
   * 是否允许导出
   */
  private Boolean allowExport;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  /**
   * 查询前置SQL
   */
  private String queryPrefixSql;

  /**
   * 查询后置SQL
   */
  private String querySuffixSql;

  /**
   * 后置SQL
   */
  private String suffixSql;

  /**
   * 创建人ID 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createById;

  /**
   * 创建人 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 创建时间 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 修改人 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateBy;

  /**
   * 修改人ID 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateById;

  /**
   * 修改时间 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
