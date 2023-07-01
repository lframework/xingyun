package com.lframework.xingyun.template.gen.bo.data.entity;

import com.lframework.starter.common.functions.SFunction;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.gen.bo.gen.GenCreateColumnConfigBo;
import com.lframework.xingyun.template.gen.bo.gen.GenDetailColumnConfigBo;
import com.lframework.xingyun.template.gen.bo.gen.GenGenerateInfoBo;
import com.lframework.xingyun.template.gen.bo.gen.GenQueryColumnConfigBo;
import com.lframework.xingyun.template.gen.bo.gen.GenQueryParamsColumnConfigBo;
import com.lframework.xingyun.template.gen.bo.gen.GenUpdateColumnConfigBo;
import com.lframework.xingyun.template.gen.dto.data.entity.DataEntityGenerateDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class DataEntityGenerateBo extends BaseBo<DataEntityGenerateDto> {

  /**
   * 字段信息
   */
  @ApiModelProperty("字段信息")
  private List<GenDataEntityDetailGenerateBo> columns;

  /**
   * 基本设置
   */
  @ApiModelProperty("基本设置")
  private GenGenerateInfoBo generateInfo;

  /**
   * 新增配置
   */
  @ApiModelProperty("新增配置")
  private List<GenCreateColumnConfigBo> createConfigs;

  /**
   * 修改配置
   */
  @ApiModelProperty("修改配置")
  private List<GenUpdateColumnConfigBo> updateConfigs;

  /**
   * 查询配置
   */
  @ApiModelProperty("查询配置")
  private List<GenQueryColumnConfigBo> queryConfigs;

  /**
   * 查询参数配置
   */
  @ApiModelProperty("查询参数配置")
  private List<GenQueryParamsColumnConfigBo> queryParamsConfigs;

  /**
   * 详情配置
   */
  @ApiModelProperty("详情配置")
  private List<GenDetailColumnConfigBo> detailConfigs;

  public DataEntityGenerateBo(DataEntityGenerateDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<DataEntityGenerateDto> convert(DataEntityGenerateDto dto) {

    return this;
  }

  @Override
  public <A> BaseBo<DataEntityGenerateDto> convert(DataEntityGenerateDto dto,
      SFunction<A, ?>... columns) {

    return this;
  }

  @Override
  protected void afterInit(DataEntityGenerateDto dto) {

    this.columns = dto.getColumns().stream().map(GenDataEntityDetailGenerateBo::new)
        .collect(Collectors.toList());
    this.generateInfo =
        dto.getGenerateInfo() == null ? null : new GenGenerateInfoBo(dto.getGenerateInfo());
    if (!CollectionUtil.isEmpty(dto.getCreateConfigs())) {
      this.createConfigs = dto.getCreateConfigs().stream().map(GenCreateColumnConfigBo::new)
          .collect(Collectors.toList());
    }

    if (!CollectionUtil.isEmpty(dto.getUpdateConfigs())) {
      this.updateConfigs = dto.getUpdateConfigs().stream().map(GenUpdateColumnConfigBo::new)
          .collect(Collectors.toList());
    }

    if (!CollectionUtil.isEmpty(dto.getQueryConfigs())) {
      this.queryConfigs = dto.getQueryConfigs().stream().map(GenQueryColumnConfigBo::new)
          .collect(Collectors.toList());
    }

    if (!CollectionUtil.isEmpty(dto.getQueryParamsConfigs())) {
      this.queryParamsConfigs = dto.getQueryParamsConfigs().stream()
          .map(GenQueryParamsColumnConfigBo::new)
          .collect(Collectors.toList());
    }

    if (!CollectionUtil.isEmpty(dto.getDetailConfigs())) {
      this.detailConfigs = dto.getDetailConfigs().stream().map(GenDetailColumnConfigBo::new)
          .collect(Collectors.toList());
    }
  }
}
