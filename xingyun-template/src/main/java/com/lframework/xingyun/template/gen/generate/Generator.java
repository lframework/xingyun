package com.lframework.xingyun.template.gen.generate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.components.validation.IsNumberPrecision;
import com.lframework.starter.web.components.validation.Pattern;
import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.constants.MyBatisStringPool;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.template.core.enums.DefaultOpLogType;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import com.lframework.xingyun.template.gen.builders.DataEntityBuilder;
import com.lframework.xingyun.template.gen.components.DataEntity;
import com.lframework.xingyun.template.gen.components.DataEntityColumn;
import com.lframework.xingyun.template.gen.directives.FormatDirective;
import com.lframework.xingyun.template.gen.dto.generate.GenerateDto;
import com.lframework.xingyun.template.gen.enums.GenConvertType;
import com.lframework.xingyun.template.gen.enums.GenDataType;
import com.lframework.xingyun.template.gen.enums.GenKeyType;
import com.lframework.xingyun.template.gen.enums.GenViewType;
import com.lframework.xingyun.template.gen.generate.templates.ControllerTemplate;
import com.lframework.xingyun.template.gen.generate.templates.CreateTemplate;
import com.lframework.xingyun.template.gen.generate.templates.DetailTemplate;
import com.lframework.xingyun.template.gen.generate.templates.EntityTemplate;
import com.lframework.xingyun.template.gen.generate.templates.MapperTemplate;
import com.lframework.xingyun.template.gen.generate.templates.QueryParamsTemplate;
import com.lframework.xingyun.template.gen.generate.templates.QueryTemplate;
import com.lframework.xingyun.template.gen.generate.templates.ServiceTemplate;
import com.lframework.xingyun.template.gen.generate.templates.SqlTemplate;
import com.lframework.xingyun.template.gen.generate.templates.UpdateTemplate;
import com.lframework.xingyun.template.inner.entity.SysDataDic;
import com.lframework.xingyun.template.inner.service.system.SysDataDicItemService;
import com.lframework.xingyun.template.inner.service.system.SysDataDicService;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

@Slf4j
public class Generator {

  private DataEntity dataEntity;

  private Generator() {

  }

  public static Generator getInstance(String entityId) {

    DataEntityBuilder builder = ApplicationUtil.getBean(DataEntityBuilder.class);
    Generator generator = new Generator();

    generator.setDataEntity(builder.build(entityId));

    return generator;
  }

  private void setDataEntity(DataEntity dataEntity) {

    this.dataEntity = dataEntity;
  }

  public List<GenerateDto> generateAll() {

    List<GenerateDto> results = new ArrayList<>();
    // Java代码
    GenerateDto entityJava = this.generateEntity();
    GenerateDto mapperJava = this.generateMapper();
    GenerateDto mapperXml = this.generateListMapperXml();
    GenerateDto queryVoJava = this.generateQueryVo();
    GenerateDto createVoJava = this.generateCreateVo();
    GenerateDto updateVoJava = this.generateUpdateVo();
    GenerateDto serviceJava = this.generateService();
    GenerateDto serviceImplJava = this.generateServiceImpl();
    GenerateDto queryBoJava = this.generateQueryBo();
    GenerateDto getBoJava = this.generateGetBo();
    GenerateDto controllerJava = this.generateController();

    results.add(entityJava);
    if (queryVoJava != null) {
      results.add(queryVoJava);
    }
    results.add(mapperJava);
    results.add(mapperXml);
    if (createVoJava != null) {
      results.add(createVoJava);
    }
    if (updateVoJava != null) {
      results.add(updateVoJava);
    }
    results.add(serviceJava);
    results.add(serviceImplJava);
    if (queryBoJava != null) {
      results.add(queryBoJava);
    }
    if (getBoJava != null) {
      results.add(getBoJava);
    }
    results.add(controllerJava);

    // Vue代码
    GenerateDto apiJs = this.generateApiJs();
    GenerateDto indexVue = this.generateIndexVue();
    GenerateDto addVue = this.generateAddVue();
    GenerateDto modifyVue = this.generateModifyVue();
    GenerateDto detailVue = this.generateDetailVue();

    results.add(apiJs);
    results.add(indexVue);
    if (addVue != null) {
      results.add(addVue);
    }
    if (modifyVue != null) {
      results.add(modifyVue);
    }
    if (detailVue != null) {
      results.add(detailVue);
    }

    // sql
    GenerateDto sql = this.generateSql();
    results.add(sql);

    return results;
  }

  /**
   * 生成Entity.java代码
   *
   * @return
   */
  public GenerateDto generateEntity() {

    EntityTemplate template = this.getEntityTemplate();

    String content = this.generate("entity.java.ftl", template);

    return this.buildGenerateResult(
        "java" + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator
            + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
            + "entity", template.getClassName() + ".java", content);
  }

  /**
   * 生成Mapper.java代码
   *
   * @return
   */
  public GenerateDto generateMapper() {

    MapperTemplate template = this.getMapperTemplate();

    String content = this.generate("mapper.java.ftl", template);

    return this.buildGenerateResult(
        "java" + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator
            + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
            + "mappers", template.getClassName() + "Mapper.java", content);
  }

  /**
   * 生成Mapper.xml代码
   *
   * @return
   */
  public GenerateDto generateListMapperXml() {

    MapperTemplate template = this.getMapperTemplate();

    String content = this.generate("mapper.list.xml.ftl", template);

    return this.buildGenerateResult(
        "java" + File.separator + "src" + File.separator + "main" + File.separator + "resources"
            + File.separator + "mappers" + File.separator + template.getModuleName(),
        template.getClassName() + "Mapper.xml", content);
  }

  /**
   * 生成QueryVo.java代码
   *
   * @return
   */
  public GenerateDto generateQueryVo() {

    QueryParamsTemplate template = this.getQueryParamsTemplate();
    if (template == null) {
      return null;
    }

    String content = this.generate("queryvo.java.ftl", template);

    return this.buildGenerateResult(
        "java" + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator
            + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
            + "vo"
            + File.separator + template.getModuleName() + File.separator + template.getBizName(),
        "Query" + template.getClassName() + "Vo.java", content);
  }

  /**
   * 生成Service.java代码
   *
   * @return
   */
  public GenerateDto generateService() {

    ServiceTemplate template = this.getServiceTemplate();

    String content = this.generate("service.java.ftl", template);

    return this.buildGenerateResult(
        "java" + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator
            + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
            + "service" + File.separator + template.getModuleName(),
        "I" + template.getClassName() + "Service.java", content);
  }

  /**
   * 生成ServiceImpl.java代码
   *
   * @return
   */
  public GenerateDto generateServiceImpl() {

    ServiceTemplate template = this.getServiceTemplate();

    String content = this.generate("serviceimpl.java.ftl", template);

    return this.buildGenerateResult(
        "java" + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator
            + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
            + "impl"
            + File.separator + template.getModuleName(),
        template.getClassName() + "ServiceImpl.java",
        content);
  }

  /**
   * 生成CreateVo.java代码
   *
   * @return
   */
  public GenerateDto generateCreateVo() {

    CreateTemplate template = this.getCreateTemplate();

    if (template == null) {
      return null;
    }

    String content = this.generate("createvo.java.ftl", template);

    return this.buildGenerateResult(
        "java" + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator
            + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
            + "vo"
            + File.separator + template.getModuleName() + File.separator + template.getBizName(),
        "Create" + template.getClassName() + "Vo.java", content);
  }

  /**
   * 生成UpdateVo.java代码
   *
   * @return
   */
  public GenerateDto generateUpdateVo() {

    UpdateTemplate template = this.getUpdateTemplate();

    if (template == null) {
      return null;
    }

    String content = this.generate("updatevo.java.ftl", template);

    return this.buildGenerateResult(
        "java" + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator
            + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
            + "vo"
            + File.separator + template.getModuleName() + File.separator + template.getBizName(),
        "Update" + template.getClassName() + "Vo.java", content);
  }

  /**
   * 生成QueryBo.java代码
   *
   * @return
   */
  public GenerateDto generateQueryBo() {

    QueryTemplate template = this.getQueryTemplate();

    if (template == null) {
      return null;
    }

    String content = this.generate("querybo.java.ftl", template);

    return this.buildGenerateResult(
        "java" + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator
            + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
            + "bo"
            + File.separator + template.getModuleName() + File.separator + template.getBizName(),
        "Query" + template.getClassName() + "Bo.java", content);
  }

  /**
   * 生成GetBo.java代码
   *
   * @return
   */
  public GenerateDto generateGetBo() {

    DetailTemplate template = this.getDetailTemplate();

    if (template == null) {
      return null;
    }

    String content = this.generate("getbo.java.ftl", template);

    return this.buildGenerateResult(
        "java" + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator
            + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
            + "bo"
            + File.separator + template.getModuleName() + File.separator + template.getBizName(),
        "Get" + template.getClassName() + "Bo.java", content);
  }

  /**
   * 生成Controller.java代码
   *
   * @return
   */
  public GenerateDto generateController() {

    ControllerTemplate template = this.getControllerTemplate();

    String content = this.generate("controller.java.ftl", template);

    return this.buildGenerateResult(
        "java" + File.separator + "src" + File.separator + "main" + File.separator + "java"
            + File.separator
            + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
            + "controller" + File.separator + template.getModuleName(),
        template.getClassName() + "Controller.java", content);
  }

  /**
   * 生成api.js代码
   *
   * @return
   */
  public GenerateDto generateApiJs() {

    ControllerTemplate template = this.getControllerTemplate();

    String content = this.generate("api.js.ftl", template);

    return this.buildGenerateResult(
        "vue" + File.separator + "src" + File.separator + "api" + File.separator + "modules"
            + File.separator
            + template.getModuleName(), template.getBizName() + ".js", content);
  }

  /**
   * 生成index.vue代码
   *
   * @return
   */
  public GenerateDto generateIndexVue() {

    ControllerTemplate template = this.getControllerTemplate();
    String content = this.generate("index.vue.ftl", template);

    return this.buildGenerateResult(
        "vue" + File.separator + "src" + File.separator + "views" + File.separator
            + template.getModuleName()
            + File.separator + template.getBizName(), "index.vue", content);
  }

  /**
   * add.vue代码
   *
   * @return
   */
  public GenerateDto generateAddVue() {

    CreateTemplate template = this.getCreateTemplate();

    if (template == null) {
      return null;
    }

    String content = this.generate("add.vue.ftl", template);

    return this.buildGenerateResult(
        "vue" + File.separator + "src" + File.separator + "views" + File.separator
            + template.getModuleName()
            + File.separator + template.getBizName(), "add.vue", content);
  }

  /**
   * modify.vue代码
   *
   * @return
   */
  public GenerateDto generateModifyVue() {

    UpdateTemplate template = this.getUpdateTemplate();

    if (template == null) {
      return null;
    }

    String content = this.generate("modify.vue.ftl", template);

    return this.buildGenerateResult(
        "vue" + File.separator + "src" + File.separator + "views" + File.separator
            + template.getModuleName()
            + File.separator + template.getBizName(), "modify.vue", content);
  }

  /**
   * detail.vue代码
   *
   * @return
   */
  public GenerateDto generateDetailVue() {

    DetailTemplate template = this.getDetailTemplate();

    if (template == null) {
      return null;
    }

    String content = this.generate("detail.vue.ftl", template);

    return this.buildGenerateResult(
        "vue" + File.separator + "src" + File.separator + "views" + File.separator
            + template.getModuleName()
            + File.separator + template.getBizName(), "detail.vue", content);
  }

  /**
   * detail.vue代码
   *
   * @return
   */
  public GenerateDto generateSql() {

    SqlTemplate template = this.getSqlTemplate();

    String content = this.generate("sql.ftl", template);

    return this.buildGenerateResult(StringPool.EMPTY_STR, "sql.sql", content);
  }

  /**
   * 获取freeMarker Template
   *
   * @param templateName
   * @return
   */
  private Template getTemplate(String templateName) {

    Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    cfg.setClassForTemplateLoading(Generator.class, "/templates");
    cfg.setDefaultEncoding("UTF-8");
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    cfg.setSharedVariable(FormatDirective.DIRECTIVE_NAME, new FormatDirective());
    try {
      return cfg.getTemplate(templateName);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }

  /**
   * Entity.java模板数据
   *
   * @return
   */
  private EntityTemplate getEntityTemplate() {

    EntityTemplate entityTemplate = new EntityTemplate();
    entityTemplate.setPackageName(dataEntity.getGenerateInfo().getPackageName());
    entityTemplate.setTableName(dataEntity.getTable().getTableName());
    entityTemplate.setClassName(dataEntity.getGenerateInfo().getClassName());
    entityTemplate.setModuleName(dataEntity.getGenerateInfo().getModuleName());
    entityTemplate.setBizName(dataEntity.getGenerateInfo().getBizName());
    entityTemplate.setClassDescription(dataEntity.getGenerateInfo().getClassDescription());
    entityTemplate.setAuthor(dataEntity.getGenerateInfo().getAuthor());

    Set<String> importPackages = new HashSet<>();
    importPackages.add(BaseEntity.class.getName());
    importPackages.add(BaseDto.class.getName());
    List<EntityTemplate.Column> columns = new ArrayList<>();
    for (DataEntityColumn column : dataEntity.getColumns()) {
      EntityTemplate.Column columnObj = new EntityTemplate.Column();
      columnObj.setIsKey(column.getIsKey());
      if (columnObj.getIsKey()) {
        // 如果是主键，判断是否是自增主键
        columnObj.setAutoIncrKey(dataEntity.getGenerateInfo().getKeyType() == GenKeyType.AUTO);
        if (columnObj.getAutoIncrKey()) {
          importPackages.add(TableId.class.getName());
          importPackages.add(IdType.class.getName());
        }
      }
      if (column.getFixEnum()) {
        // 如果是枚举类型
        columnObj.setDataType(
            column.getEnumBack().substring(column.getEnumBack().lastIndexOf(".") + 1));
        columnObj.setFrontType(column.getEnumFront());
        importPackages.add(column.getEnumBack());
      } else {
        columnObj.setDataType(column.getDataType().getDesc());
      }
      // 以下类型需要单独引包
      if (column.getDataType() == GenDataType.LOCAL_DATE) {
        importPackages.add(LocalDate.class.getName());
      } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
        importPackages.add(LocalDateTime.class.getName());
      } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
        importPackages.add(LocalTime.class.getName());
      } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
        importPackages.add(BigDecimal.class.getName());
      }
      columnObj.setName(column.getColumnName());
      // MybatisPlus默认命名规则是下划线转驼峰，所以如果不是这个规则的话，需要单独指定TableField和TableId
      columnObj.setColumnName(column.getTableColumn().getDbColumnName());
      columnObj.setDefaultConvertType(
          dataEntity.getTable().getConvertType() == GenConvertType.UNDERLINE_TO_CAMEL);
      if (!columnObj.getDefaultConvertType()) {
        importPackages.add(TableId.class.getName());
        importPackages.add(TableField.class.getName());
      }
      columnObj.setDescription(column.getName());
      if (!columnObj.getIsKey()) {
        // 如果不是主键，需要判断是否需要字段填充
        if (MyBatisStringPool.COLUMN_CREATE_BY.equals(columnObj.getName())
            || MyBatisStringPool.COLUMN_CREATE_BY_ID.equals(columnObj.getName())
            || MyBatisStringPool.COLUMN_CREATE_TIME.equals(columnObj.getName())) {
          columnObj.setFill(Boolean.TRUE);
          columnObj.setFillStrategy(FieldFill.INSERT.name());
          importPackages.add(TableField.class.getName());
          importPackages.add(FieldFill.class.getName());
        } else if (MyBatisStringPool.COLUMN_UPDATE_BY.equals(columnObj.getName())
            || MyBatisStringPool.COLUMN_UPDATE_BY_ID.equals(columnObj.getName())
            || MyBatisStringPool.COLUMN_UPDATE_TIME.equals(columnObj.getName())) {
          columnObj.setFill(Boolean.TRUE);
          columnObj.setFillStrategy(FieldFill.INSERT_UPDATE.name());
          importPackages.add(TableField.class.getName());
          importPackages.add(FieldFill.class.getName());
        }
      }

      columns.add(columnObj);
    }
    entityTemplate.setColumns(columns);

    entityTemplate.setImportPackages(importPackages);

    return entityTemplate;
  }

  /**
   * Mapper.java模板数据
   *
   * @return
   */
  private MapperTemplate getMapperTemplate() {

    MapperTemplate mapperTemplate = new MapperTemplate();
    mapperTemplate.setPackageName(dataEntity.getGenerateInfo().getPackageName());
    mapperTemplate.setClassName(dataEntity.getGenerateInfo().getClassName());
    mapperTemplate.setModuleName(dataEntity.getGenerateInfo().getModuleName());
    mapperTemplate.setBizName(dataEntity.getGenerateInfo().getBizName());
    mapperTemplate.setClassDescription(dataEntity.getGenerateInfo().getClassDescription());
    mapperTemplate.setAuthor(dataEntity.getGenerateInfo().getAuthor());
    Set<String> importPackages = new HashSet<>();
    importPackages.add(BaseMapper.class.getName());
    List<MapperTemplate.Key> keys = new ArrayList<>();
    for (DataEntityColumn column : dataEntity.getColumns()) {
      if (column.getIsKey()) {
        MapperTemplate.Key key = new MapperTemplate.Key();
        // 主键不允许是枚举，所以直接取desc
        key.setDataType(column.getDataType().getDesc());
        key.setName(column.getColumnName());
        key.setColumnName(column.getTableColumn().getDbColumnName());
        // 以下类型需要单独引包
        if (column.getDataType() == GenDataType.LOCAL_DATE) {
          importPackages.add(LocalDate.class.getName());
        } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
          importPackages.add(LocalDateTime.class.getName());
        } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
          importPackages.add(LocalTime.class.getName());
        } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
          importPackages.add(BigDecimal.class.getName());
        }
        keys.add(key);
      }
    }

    mapperTemplate.setKeys(keys);
    List<MapperTemplate.OrderColumn> orderColumns = new ArrayList<>();
    for (DataEntityColumn column : dataEntity.getColumns()) {
      if (!column.getIsOrder()) {
        continue;
      }
      MapperTemplate.OrderColumn orderColumn = new MapperTemplate.OrderColumn();
      orderColumn.setColumnName(column.getTableColumn().getDbColumnName());
      orderColumn.setOrderType(column.getOrderType().getCode());
      orderColumns.add(orderColumn);
    }
    mapperTemplate.setOrderColumns(orderColumns);
    mapperTemplate.setEntity(this.getEntityTemplate());
    mapperTemplate.setImportPackages(importPackages);
    mapperTemplate.setQueryParams(this.getQueryParamsTemplate());
    if (mapperTemplate.getQueryParams() != null) {
      mapperTemplate.getImportPackages()
          .addAll(mapperTemplate.getQueryParams().getImportPackages());
    }

    return mapperTemplate;
  }

  private ServiceTemplate getServiceTemplate() {

    ServiceTemplate serviceTemplate = new ServiceTemplate();
    serviceTemplate.setPackageName(dataEntity.getGenerateInfo().getPackageName());
    serviceTemplate.setClassName(dataEntity.getGenerateInfo().getClassName());
    serviceTemplate.setClassNameProperty(
        dataEntity.getGenerateInfo().getClassName().substring(0, 1).toLowerCase()
            + dataEntity.getGenerateInfo()
            .getClassName().substring(1));
    serviceTemplate.setModuleName(dataEntity.getGenerateInfo().getModuleName());
    serviceTemplate.setBizName(dataEntity.getGenerateInfo().getBizName());
    serviceTemplate.setClassDescription(dataEntity.getGenerateInfo().getClassDescription());
    serviceTemplate.setAuthor(dataEntity.getGenerateInfo().getAuthor());
    serviceTemplate.setIsCache(dataEntity.getGenerateInfo().getIsCache());
    serviceTemplate.setHasDelete(dataEntity.getGenerateInfo().getHasDelete());
    Set<String> importPackages = new HashSet<>();
    importPackages.add(PageResult.class.getName());
    importPackages.add(BaseMpService.class.getName());
    importPackages.add(StringUtil.class.getName());
    importPackages.add(BaseMpServiceImpl.class.getName());
    importPackages.add(DefaultClientException.class.getName());
    importPackages.add(Assert.class.getName());
    importPackages.add(ObjectUtil.class.getName());
    importPackages.add(OpLog.class.getName());
    importPackages.add(DefaultOpLogType.class.getName());
    importPackages.add(OpLogUtil.class.getName());
    importPackages.add(PageHelperUtil.class.getName());
    importPackages.add(PageResultUtil.class.getName());
    importPackages.add(EnumUtil.class.getName());
    if (serviceTemplate.getHasDelete()) {
      importPackages.add(Transactional.class.getName());
    }
    List<ServiceTemplate.Key> keys = new ArrayList<>();
    for (DataEntityColumn column : dataEntity.getColumns()) {
      if (column.getIsKey()) {
        ServiceTemplate.Key key = new ServiceTemplate.Key();
        // 主键不允许是枚举，所以直接取desc
        key.setDataType(column.getDataType().getDesc());
        key.setName(column.getColumnName());
        key.setNameProperty(
            column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName()
                .substring(1));
        key.setColumnName(column.getTableColumn().getDbColumnName());
        // 以下类型需要单独引包
        if (column.getDataType() == GenDataType.LOCAL_DATE) {
          importPackages.add(LocalDate.class.getName());
        } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
          importPackages.add(LocalDateTime.class.getName());
        } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
          importPackages.add(LocalTime.class.getName());
        } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
          importPackages.add(BigDecimal.class.getName());
        }
        keys.add(key);
      }
    }

    serviceTemplate.setKeys(keys);
    serviceTemplate.setImportPackages(importPackages);
    serviceTemplate.setQueryParams(this.getQueryParamsTemplate());
    serviceTemplate.setCreate(this.getCreateTemplate());
    serviceTemplate.setUpdate(this.getUpdateTemplate());
    if (serviceTemplate.getIsCache()) {
      importPackages.add(Serializable.class.getName());
    }
    if (serviceTemplate.getQueryParams() != null) {
      serviceTemplate.getImportPackages()
          .addAll(serviceTemplate.getQueryParams().getImportPackages());
    }
    if (serviceTemplate.getCreate() != null) {
      serviceTemplate.getImportPackages().addAll(serviceTemplate.getCreate().getImportPackages());
    }
    if (serviceTemplate.getUpdate() != null) {
      serviceTemplate.getImportPackages().addAll(serviceTemplate.getUpdate().getImportPackages());
    }

    return serviceTemplate;
  }

  private QueryParamsTemplate getQueryParamsTemplate() {

    List<DataEntityColumn> targetColumns = dataEntity.getColumns().stream()
        .filter(t -> t.getQueryParamsConfig() != null)
        .sorted(Comparator.comparing(t -> t.getQueryParamsConfig().getOrderNo()))
        .collect(Collectors.toList());
    if (CollectionUtil.isEmpty(targetColumns)) {
      return null;
    }
    QueryParamsTemplate queryParamsTemplate = new QueryParamsTemplate();
    queryParamsTemplate.setPackageName(dataEntity.getGenerateInfo().getPackageName());
    queryParamsTemplate.setClassName(dataEntity.getGenerateInfo().getClassName());
    queryParamsTemplate.setModuleName(dataEntity.getGenerateInfo().getModuleName());
    queryParamsTemplate.setBizName(dataEntity.getGenerateInfo().getBizName());
    queryParamsTemplate.setClassDescription(dataEntity.getGenerateInfo().getClassDescription());
    queryParamsTemplate.setAuthor(dataEntity.getGenerateInfo().getAuthor());
    Set<String> importPackages = new HashSet<>();
    importPackages.add(BaseVo.class.getName());
    importPackages.add(PageVo.class.getName());
    importPackages.add(TypeMismatch.class.getName());
    importPackages.add(ApiModelProperty.class.getName());
    List<QueryParamsTemplate.Column> columns = new ArrayList<>();
    for (DataEntityColumn column : targetColumns) {
      QueryParamsTemplate.Column columnObj = new QueryParamsTemplate.Column();
      if (column.getFixEnum()) {
        // 如果是枚举类型
        columnObj.setDataType(
            column.getEnumBack().substring(column.getEnumBack().lastIndexOf(".") + 1));
        columnObj.setFrontType(column.getEnumFront());
        columnObj.setViewType(column.getViewType().getCode());
        columnObj.setEnumCodeType(column.getDataType().getDesc());
        importPackages.add(column.getEnumBack());
        importPackages.add(IsEnum.class.getName());
      } else {
        columnObj.setDataType(column.getDataType().getDesc());
        columnObj.setViewType(column.getViewType().getCode());
      }
      // 以下类型需要单独引包
      if (column.getDataType() == GenDataType.LOCAL_DATE) {
        importPackages.add(LocalDate.class.getName());
      } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
        importPackages.add(LocalDateTime.class.getName());
      } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
        importPackages.add(LocalTime.class.getName());
      } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
        importPackages.add(BigDecimal.class.getName());
      }
      columnObj.setName(column.getColumnName());
      columnObj.setColumnName(column.getTableColumn().getDbColumnName());
      columnObj.setQueryType(column.getQueryParamsConfig().getQueryType().getCode());
      columnObj.setNameProperty(
          column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName()
              .substring(1));
      columnObj.setDescription(column.getName());
      columnObj.setHasAvailableTag(
          column.getViewType() == GenViewType.SELECT && column.getDataType() == GenDataType.BOOLEAN
              && "available".equals(column.getColumnName()));
      columnObj.setFixEnum(column.getFixEnum());
      if (!StringUtil.isBlank(column.getRegularExpression())) {
        columnObj.setRegularExpression(column.getRegularExpression());
        importPackages.add(Pattern.class.getName());
      }
      if (column.getViewType() == GenViewType.DATA_DIC) {
        SysDataDicService sysDataDicService = ApplicationUtil.getBean(SysDataDicService.class);
        SysDataDic dic = sysDataDicService.findById(column.getDataDicId());
        columnObj.setDataDicCode(dic.getCode());
      } else if (column.getViewType() == GenViewType.CUSTOM_SELECTOR) {
        columnObj.setCustomSelectorId(column.getCustomSelectorId());
      }

      columns.add(columnObj);
    }
    queryParamsTemplate.setColumns(columns);
    queryParamsTemplate.setImportPackages(importPackages);

    return queryParamsTemplate;
  }

  private CreateTemplate getCreateTemplate() {

    List<DataEntityColumn> targetColumns = dataEntity.getColumns().stream()
        .filter(t -> t.getCreateConfig() != null)
        .sorted(Comparator.comparing(t -> t.getCreateConfig().getOrderNo()))
        .collect(Collectors.toList());
    if (CollectionUtil.isEmpty(targetColumns)) {
      return null;
    }
    Set<String> importPackages = new HashSet<>();
    importPackages.add(ApiModelProperty.class.getName());
    CreateTemplate createTemplate = new CreateTemplate();
    createTemplate.setAppointId(dataEntity.getGenerateInfo().getKeyType() != GenKeyType.AUTO);
    if (dataEntity.getGenerateInfo().getKeyType() == GenKeyType.UUID) {
      // 如果是UUID，则引入IdUtil包
      importPackages.add(IdUtil.class.getName());
      createTemplate.setIdCode(IdUtil.class.getSimpleName() + ".getUUID()");
    } else if (dataEntity.getGenerateInfo().getKeyType() == GenKeyType.SNOW_FLAKE) {
      // 如果是雪花算法，则引入IdWorker包
      importPackages.add(IdUtil.class.getName());
      createTemplate.setIdCode(IdUtil.class.getSimpleName() + ".getId()");
    }
    createTemplate.setPackageName(dataEntity.getGenerateInfo().getPackageName());
    createTemplate.setClassName(dataEntity.getGenerateInfo().getClassName());
    createTemplate.setModuleName(dataEntity.getGenerateInfo().getModuleName());
    createTemplate.setBizName(dataEntity.getGenerateInfo().getBizName());
    createTemplate.setClassDescription(dataEntity.getGenerateInfo().getClassDescription());
    createTemplate.setAuthor(dataEntity.getGenerateInfo().getAuthor());
    importPackages.add(TypeMismatch.class.getName());
    importPackages.add(BaseVo.class.getName());


    List<CreateTemplate.Column> columns = new ArrayList<>();
    for (DataEntityColumn column : targetColumns) {
      CreateTemplate.Column columnObj = new CreateTemplate.Column();
      columnObj.setIsKey(column.getIsKey());
      columnObj.setRequired(column.getCreateConfig().getRequired());
      if (column.getFixEnum()) {
        // 如果是枚举类型
        columnObj.setDataType(
            column.getEnumBack().substring(column.getEnumBack().lastIndexOf(".") + 1));
        columnObj.setFrontType(column.getEnumFront());
        columnObj.setViewType(column.getViewType().getCode());
        importPackages.add(column.getEnumBack());
        importPackages.add(EnumUtil.class.getName());
      } else {
        columnObj.setDataType(column.getDataType().getDesc());
        columnObj.setViewType(column.getViewType().getCode());
      }
      if (column.getViewType() == GenViewType.DATE_RANGE) {
        if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
          columnObj.setViewType(GenViewType.DATETIME.getCode());
        } else {
          columnObj.setViewType(GenViewType.DATE.getCode());
        }
      }
      // 以下类型需要单独引包
      if (column.getDataType() == GenDataType.LOCAL_DATE) {
        importPackages.add(LocalDate.class.getName());
      } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
        importPackages.add(LocalDateTime.class.getName());
      } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
        importPackages.add(LocalTime.class.getName());
      } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
        importPackages.add(BigDecimal.class.getName());
      }
      columnObj.setFixEnum(column.getFixEnum());
      if (columnObj.getFixEnum()) {
        columnObj.setEnumCodeType(column.getDataType().getDesc());
      }
      if (columnObj.getRequired()) {
        // 如果必填，那么需要引Validation注解包
        if (column.getDataType() == GenDataType.STRING) {
          // 如果是String，则引@NotBlank注解
          columnObj.setValidateAnno(NotBlank.class.getSimpleName());
          importPackages.add(NotBlank.class.getName());
        } else {
          // 否则引@NotNull注解
          columnObj.setValidateAnno(NotNull.class.getSimpleName());
          importPackages.add(NotNull.class.getName());
        }

        if (column.getViewType() == GenViewType.SELECT
            || column.getViewType() == GenViewType.DATA_DIC) {
          columnObj.setValidateMsg("请选择");
        } else {
          columnObj.setValidateMsg("请输入");
        }

        if (columnObj.getFixEnum()) {
          // 如果是内置枚举，那么引IsEnum注解包
          importPackages.add(IsEnum.class.getName());
        }
      }
      columnObj.setName(column.getColumnName());
      columnObj.setColumnName(column.getTableColumn().getDbColumnName());
      columnObj.setNameProperty(
          column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName()
              .substring(1));
      columnObj.setDescription(column.getName());
      columnObj.setHasAvailableTag(
          column.getViewType() == GenViewType.SELECT && column.getDataType() == GenDataType.BOOLEAN
              && "available".equals(column.getColumnName()));
      if (!StringUtil.isBlank(column.getRegularExpression())) {
        columnObj.setRegularExpression(column.getRegularExpression());
        importPackages.add(Pattern.class.getName());
      }

      columnObj.setIsNumberType(GenDataType.isNumberType(column.getDataType()));
      columnObj.setIsDecimalType(GenDataType.isDecimalType(column.getDataType()));
      if (column.getViewType() == GenViewType.DATA_DIC) {
        SysDataDicService sysDataDicService = ApplicationUtil.getBean(SysDataDicService.class);
        SysDataDic dic = sysDataDicService.findById(column.getDataDicId());
        columnObj.setDataDicCode(dic.getCode());
      } else if (column.getViewType() == GenViewType.CUSTOM_SELECTOR) {
        columnObj.setCustomSelectorId(column.getCustomSelectorId());
      }
      columnObj.setLen(column.getLen());
      columnObj.setDecimals(column.getDecimals());
      if (columnObj.getIsNumberType()) {
        if (columnObj.getIsDecimalType()) {
          importPackages.add(IsNumberPrecision.class.getName());
        }
      } else if (column.getDataType() == GenDataType.STRING) {
        if (column.getViewType() == GenViewType.INPUT
            || column.getViewType() == GenViewType.TEXTATREA) {
          importPackages.add(Length.class.getName());
        }
      }
      columns.add(columnObj);
    }

    createTemplate.setColumns(columns);
    createTemplate.setImportPackages(importPackages);
    List<DataEntityColumn> keyColumns = dataEntity.getColumns().stream()
        .filter(DataEntityColumn::getIsKey)
        .collect(Collectors.toList());
    List<CreateTemplate.Key> keys = keyColumns.stream().map(t -> {
      CreateTemplate.Key key = new CreateTemplate.Key();
      // 主键不会是枚举
      key.setDataType(t.getDataType().getDesc());
      key.setName(t.getColumnName());
      key.setNameProperty(
          t.getColumnName().substring(0, 1).toUpperCase() + t.getColumnName().substring(1));
      key.setColumnName(t.getTableColumn().getDbColumnName());
      key.setDescription(t.getName());

      return key;
    }).collect(Collectors.toList());

    createTemplate.setKeys(keys);

    return createTemplate;
  }

  private UpdateTemplate getUpdateTemplate() {

    List<DataEntityColumn> targetColumns = dataEntity.getColumns().stream()
        .filter(t -> t.getUpdateConfig() != null)
        .sorted(Comparator.comparing(t -> t.getUpdateConfig().getOrderNo()))
        .collect(Collectors.toList());
    if (CollectionUtil.isEmpty(targetColumns)) {
      return null;
    }
    Set<String> importPackages = new HashSet<>();
    UpdateTemplate updateTemplate = new UpdateTemplate();
    updateTemplate.setPackageName(dataEntity.getGenerateInfo().getPackageName());
    updateTemplate.setClassName(dataEntity.getGenerateInfo().getClassName());
    updateTemplate.setModuleName(dataEntity.getGenerateInfo().getModuleName());
    updateTemplate.setBizName(dataEntity.getGenerateInfo().getBizName());
    updateTemplate.setClassDescription(dataEntity.getGenerateInfo().getClassDescription());
    updateTemplate.setAuthor(dataEntity.getGenerateInfo().getAuthor());
    importPackages.add(TypeMismatch.class.getName());
    importPackages.add(ApiModelProperty.class.getName());
    importPackages.add(BaseVo.class.getName());

    List<UpdateTemplate.Column> columns = new ArrayList<>();
    for (DataEntityColumn column : targetColumns) {
      UpdateTemplate.Column columnObj = new UpdateTemplate.Column();
      columnObj.setIsKey(column.getIsKey());
      columnObj.setRequired(column.getUpdateConfig().getRequired());
      if (column.getFixEnum()) {
        // 如果是枚举类型
        columnObj.setDataType(
            column.getEnumBack().substring(column.getEnumBack().lastIndexOf(".") + 1));
        columnObj.setFrontType(column.getEnumFront());
        columnObj.setViewType(column.getViewType().getCode());
        importPackages.add(column.getEnumBack());
        importPackages.add(EnumUtil.class.getName());
      } else {
        columnObj.setDataType(column.getDataType().getDesc());
        columnObj.setViewType(column.getViewType().getCode());
      }
      if (column.getViewType() == GenViewType.DATE_RANGE) {
        if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
          columnObj.setViewType(GenViewType.DATETIME.getCode());
        } else {
          columnObj.setViewType(GenViewType.DATE.getCode());
        }
      }
      // 以下类型需要单独引包
      if (column.getDataType() == GenDataType.LOCAL_DATE) {
        importPackages.add(LocalDate.class.getName());
      } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
        importPackages.add(LocalDateTime.class.getName());
      } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
        importPackages.add(LocalTime.class.getName());
      } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
        importPackages.add(BigDecimal.class.getName());
      }
      columnObj.setFixEnum(column.getFixEnum());
      if (columnObj.getFixEnum()) {
        columnObj.setEnumCodeType(column.getDataType().getDesc());
      }
      if (columnObj.getRequired()) {
        // 如果必填，那么需要引Validation注解包
        if (column.getDataType() == GenDataType.STRING) {
          // 如果是String，则引@NotBlank注解
          columnObj.setValidateAnno(NotBlank.class.getSimpleName());
          importPackages.add(NotBlank.class.getName());
        } else {
          // 否则引@NotNull注解
          columnObj.setValidateAnno(NotNull.class.getSimpleName());
          importPackages.add(NotNull.class.getName());
        }

        if (column.getViewType() == GenViewType.SELECT
            || column.getViewType() == GenViewType.DATA_DIC) {
          columnObj.setValidateMsg("请选择");
        } else {
          columnObj.setValidateMsg("请输入");
        }

        if (columnObj.getFixEnum()) {
          // 如果是内置枚举，那么引IsEnum注解包
          importPackages.add(IsEnum.class.getName());
        }
      }
      columnObj.setName(column.getColumnName());
      columnObj.setColumnName(column.getTableColumn().getDbColumnName());
      columnObj.setNameProperty(
          column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName()
              .substring(1));
      columnObj.setDescription(column.getName());
      columnObj.setHasAvailableTag(
          column.getViewType() == GenViewType.SELECT && column.getDataType() == GenDataType.BOOLEAN
              && "available".equals(column.getColumnName()));
      if (!StringUtil.isBlank(column.getRegularExpression())) {
        columnObj.setRegularExpression(column.getRegularExpression());
        importPackages.add(Pattern.class.getName());
      }
      columnObj.setIsNumberType(GenDataType.isNumberType(column.getDataType()));
      columnObj.setIsDecimalType(GenDataType.isDecimalType(column.getDataType()));
      columnObj.setLen(column.getLen());
      columnObj.setDecimals(column.getDecimals());
      if (columnObj.getIsNumberType()) {
        if (columnObj.getIsDecimalType()) {
          importPackages.add(IsNumberPrecision.class.getName());
        }
      } else if (column.getDataType() == GenDataType.STRING) {
        if (column.getViewType() == GenViewType.INPUT
            || column.getViewType() == GenViewType.TEXTATREA) {
          importPackages.add(Length.class.getName());
        }
      }

      if (column.getViewType() == GenViewType.DATA_DIC) {
        SysDataDicService sysDataDicService = ApplicationUtil.getBean(SysDataDicService.class);
        SysDataDic dic = sysDataDicService.findById(column.getDataDicId());
        columnObj.setDataDicCode(dic.getCode());
      } else if (column.getViewType() == GenViewType.CUSTOM_SELECTOR) {
        columnObj.setCustomSelectorId(column.getCustomSelectorId());
      }

      columns.add(columnObj);
    }

    updateTemplate.setColumns(columns);
    updateTemplate.setImportPackages(importPackages);
    List<DataEntityColumn> keyColumns = dataEntity.getColumns().stream()
        .filter(DataEntityColumn::getIsKey)
        .collect(Collectors.toList());
    List<UpdateTemplate.Key> keys = keyColumns.stream().map(t -> {
      UpdateTemplate.Key key = new UpdateTemplate.Key();
      // 主键不会是枚举
      key.setDataType(t.getDataType().getDesc());
      key.setName(t.getColumnName());
      key.setNameProperty(
          t.getColumnName().substring(0, 1).toUpperCase() + t.getColumnName().substring(1));
      key.setColumnName(t.getTableColumn().getDbColumnName());
      key.setDescription(t.getName());
      if (t.getDataType() == GenDataType.STRING) {
        // 如果是String，则引@NotBlank注解
        importPackages.add(NotBlank.class.getName());
      } else {
        // 否则引@NotNull注解
        importPackages.add(NotNull.class.getName());
      }
      return key;
    }).collect(Collectors.toList());
    updateTemplate.setKeys(keys);

    return updateTemplate;
  }

  private QueryTemplate getQueryTemplate() {

    List<DataEntityColumn> targetColumns = dataEntity.getColumns().stream()
        .filter(t -> t.getQueryConfig() != null)
        .sorted(Comparator.comparing(t -> t.getQueryConfig().getOrderNo()))
        .collect(Collectors.toList());
    if (CollectionUtil.isEmpty(targetColumns)) {
      return null;
    }
    QueryTemplate queryTemplate = new QueryTemplate();
    queryTemplate.setPackageName(dataEntity.getGenerateInfo().getPackageName());
    queryTemplate.setClassName(dataEntity.getGenerateInfo().getClassName());
    queryTemplate.setModuleName(dataEntity.getGenerateInfo().getModuleName());
    queryTemplate.setBizName(dataEntity.getGenerateInfo().getBizName());
    queryTemplate.setClassDescription(dataEntity.getGenerateInfo().getClassDescription());
    queryTemplate.setAuthor(dataEntity.getGenerateInfo().getAuthor());

    Set<String> importPackages = new HashSet<>();
    importPackages.add(TypeMismatch.class.getName());
    importPackages.add(ApiModelProperty.class.getName());
    importPackages.add(StringPool.class.getName());
    importPackages.add(BaseBo.class.getName());
    List<QueryTemplate.Column> columns = new ArrayList<>();
    for (DataEntityColumn column : targetColumns) {
      QueryTemplate.Column columnObj = new QueryTemplate.Column();
      if (column.getFixEnum()) {
        // 如果是枚举类型
        columnObj.setDataType(
            column.getEnumBack().substring(column.getEnumBack().lastIndexOf(".") + 1));
        columnObj.setFrontType(column.getEnumFront());
        columnObj.setViewType(column.getViewType().getCode());
        importPackages.add(column.getEnumBack());
        importPackages.add(EnumUtil.class.getName());
      } else {
        columnObj.setDataType(column.getDataType().getDesc());
        columnObj.setIsNumberType(GenDataType.isNumberType(column.getDataType()));
        columnObj.setViewType(column.getViewType().getCode());
        columnObj.setHasAvailableTag(
            column.getViewType() == GenViewType.SELECT
                && column.getDataType() == GenDataType.BOOLEAN
                && "available".equals(column.getColumnName()));
      }
      // 以下类型需要单独引包
      if (column.getDataType() == GenDataType.LOCAL_DATE) {
        importPackages.add(LocalDate.class.getName());
      } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
        importPackages.add(LocalDateTime.class.getName());
      } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
        importPackages.add(LocalTime.class.getName());
      } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
        importPackages.add(BigDecimal.class.getName());
      }
      columnObj.setFixEnum(column.getFixEnum());
      if (columnObj.getFixEnum()) {
        columnObj.setEnumCodeType(column.getDataType().getDesc());
      }
      columnObj.setName(column.getColumnName());
      columnObj.setNameProperty(
          column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName()
              .substring(1));
      columnObj.setWidthType(column.getQueryConfig().getWidthType().getCode());
      columnObj.setWidth(column.getQueryConfig().getWidth());
      columnObj.setSortable(column.getQueryConfig().getSortable());
      columnObj.setDescription(column.getName());
      if (column.getViewType() == GenViewType.DATA_DIC) {
        SysDataDicService sysDataDicService = ApplicationUtil.getBean(SysDataDicService.class);
        SysDataDic dic = sysDataDicService.findById(column.getDataDicId());
        columnObj.setDataDicCode(dic.getCode());
        importPackages.add(SysDataDicItemService.class.getName());
        importPackages.add(StringUtil.class.getName());
        importPackages.add(ApplicationUtil.class.getName());
        importPackages.add(StringPool.class.getName());
      }

      columns.add(columnObj);
    }

    queryTemplate.setColumns(columns);
    queryTemplate.setHasFixEnum(columns.stream().anyMatch(QueryTemplate.Column::getFixEnum));

    List<DataEntityColumn> keyColumns = dataEntity.getColumns().stream()
        .filter(DataEntityColumn::getIsKey)
        .collect(Collectors.toList());
    List<QueryTemplate.Key> keys = keyColumns.stream().map(t -> {
      QueryTemplate.Key key = new QueryTemplate.Key();
      // 主键不会是枚举
      key.setDataType(t.getDataType().getDesc());
      key.setName(t.getColumnName());
      key.setNameProperty(
          t.getColumnName().substring(0, 1).toUpperCase() + t.getColumnName().substring(1));
      key.setDescription(t.getName());

      return key;
    }).collect(Collectors.toList());

    queryTemplate.setKeys(keys);

    queryTemplate.setImportPackages(importPackages);

    return queryTemplate;
  }

  private DetailTemplate getDetailTemplate() {

    List<DataEntityColumn> targetColumns = dataEntity.getColumns().stream()
        .filter(t -> t.getDetailConfig() != null)
        .sorted(Comparator.comparing(t -> t.getDetailConfig().getOrderNo()))
        .collect(Collectors.toList());
    if (CollectionUtil.isEmpty(targetColumns)) {
      return null;
    }
    DetailTemplate detailTemplate = new DetailTemplate();
    detailTemplate.setPackageName(dataEntity.getGenerateInfo().getPackageName());
    detailTemplate.setClassName(dataEntity.getGenerateInfo().getClassName());
    detailTemplate.setModuleName(dataEntity.getGenerateInfo().getModuleName());
    detailTemplate.setBizName(dataEntity.getGenerateInfo().getBizName());
    detailTemplate.setClassDescription(dataEntity.getGenerateInfo().getClassDescription());
    detailTemplate.setAuthor(dataEntity.getGenerateInfo().getAuthor());

    Set<String> importPackages = new HashSet<>();
    importPackages.add(TypeMismatch.class.getName());
    importPackages.add(ApiModelProperty.class.getName());
    importPackages.add(StringPool.class.getName());
    importPackages.add(BaseBo.class.getName());
    List<DetailTemplate.Column> columns = new ArrayList<>();
    for (DataEntityColumn column : targetColumns) {
      DetailTemplate.Column columnObj = new DetailTemplate.Column();
      if (column.getFixEnum()) {
        // 如果是枚举类型
        columnObj.setDataType(
            column.getEnumBack().substring(column.getEnumBack().lastIndexOf(".") + 1));
        columnObj.setFrontType(column.getEnumFront());
        importPackages.add(column.getEnumBack());
        importPackages.add(EnumUtil.class.getName());
      } else {
        columnObj.setDataType(column.getDataType().getDesc());
        columnObj.setHasAvailableTag(
            column.getViewType() == GenViewType.SELECT
                && column.getDataType() == GenDataType.BOOLEAN
                && "available".equals(column.getColumnName()));
        if (columnObj.getHasAvailableTag()) {
          detailTemplate.setHasAvailableTag(Boolean.TRUE);
        }
      }
      // 以下类型需要单独引包
      if (column.getDataType() == GenDataType.LOCAL_DATE) {
        importPackages.add(LocalDate.class.getName());
      } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
        importPackages.add(LocalDateTime.class.getName());
      } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
        importPackages.add(LocalTime.class.getName());
      } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
        importPackages.add(BigDecimal.class.getName());
      }
      columnObj.setFixEnum(column.getFixEnum());
      if (columnObj.getFixEnum()) {
        columnObj.setEnumCodeType(column.getDataType().getDesc());
      }
      columnObj.setName(column.getColumnName());
      columnObj.setNameProperty(
          column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName()
              .substring(1));
      columnObj.setDescription(column.getName());
      columnObj.setSpan(column.getDetailConfig().getSpan());

      if (column.getViewType() == GenViewType.DATA_DIC) {
        SysDataDicService sysDataDicService = ApplicationUtil.getBean(SysDataDicService.class);
        SysDataDic dic = sysDataDicService.findById(column.getDataDicId());
        columnObj.setDataDicCode(dic.getCode());
        importPackages.add(SysDataDicItemService.class.getName());
        importPackages.add(StringUtil.class.getName());
        importPackages.add(ApplicationUtil.class.getName());
        importPackages.add(StringPool.class.getName());
      }

      columns.add(columnObj);
    }

    detailTemplate.setColumns(columns);
    detailTemplate.setHasFixEnum(columns.stream().anyMatch(DetailTemplate.Column::getFixEnum));
    detailTemplate.setDetailSpan(dataEntity.getGenerateInfo().getDetailSpan());
    List<DataEntityColumn> keyColumns = dataEntity.getColumns().stream()
        .filter(DataEntityColumn::getIsKey)
        .collect(Collectors.toList());
    List<DetailTemplate.Key> keys = keyColumns.stream().map(t -> {
      DetailTemplate.Key key = new DetailTemplate.Key();
      // 主键不会是枚举
      key.setDataType(t.getDataType().getDesc());
      key.setName(t.getColumnName());
      key.setNameProperty(
          t.getColumnName().substring(0, 1).toUpperCase() + t.getColumnName().substring(1));
      key.setDescription(t.getName());

      return key;
    }).collect(Collectors.toList());

    detailTemplate.setKeys(keys);

    detailTemplate.setImportPackages(importPackages);

    return detailTemplate;
  }

  private ControllerTemplate getControllerTemplate() {

    Set<String> importPackages = new HashSet<>();
    ControllerTemplate controllerTemplate = new ControllerTemplate();
    controllerTemplate.setPackageName(dataEntity.getGenerateInfo().getPackageName());
    controllerTemplate.setClassName(dataEntity.getGenerateInfo().getClassName());
    controllerTemplate.setClassNameProperty(
        dataEntity.getGenerateInfo().getClassName().substring(0, 1).toLowerCase()
            + dataEntity.getGenerateInfo()
            .getClassName().substring(1));
    controllerTemplate.setModuleName(dataEntity.getGenerateInfo().getModuleName());
    controllerTemplate.setBizName(dataEntity.getGenerateInfo().getBizName());
    controllerTemplate.setClassDescription(dataEntity.getGenerateInfo().getClassDescription());
    controllerTemplate.setAuthor(dataEntity.getGenerateInfo().getAuthor());
    controllerTemplate.setIsCache(dataEntity.getGenerateInfo().getIsCache());
    controllerTemplate.setHasDelete(dataEntity.getGenerateInfo().getHasDelete());
    if (controllerTemplate.getHasDelete()) {
      importPackages.add(DeleteMapping.class.getName());
    }
    importPackages.add(DefaultClientException.class.getName());
    importPackages.add(CollectionUtil.class.getName());
    importPackages.add(PageResult.class.getName());
    importPackages.add(PageResultUtil.class.getName());
    importPackages.add(InvokeResult.class.getName());
    importPackages.add(InvokeResultBuilder.class.getName());
    importPackages.add(Api.class.getName());
    importPackages.add(ApiOperation.class.getName());
    importPackages.add(ApiImplicitParam.class.getName());
    importPackages.add(ApiImplicitParams.class.getName());

    List<DataEntityColumn> keyColumns = dataEntity.getColumns().stream()
        .filter(DataEntityColumn::getIsKey)
        .collect(Collectors.toList());
    List<ControllerTemplate.Key> keys = keyColumns.stream().map(t -> {
      ControllerTemplate.Key key = new ControllerTemplate.Key();
      // 主键不会是枚举
      key.setDataType(t.getDataType().getDesc());
      key.setName(t.getColumnName());
      key.setNameProperty(t.getColumnName().substring(0, 1).toUpperCase() + t.getColumnName()
          .substring(1));

      if (t.getDataType() == GenDataType.STRING) {
        importPackages.add(NotBlank.class.getName());
      } else {
        importPackages.add(NotNull.class.getName());
      }

      return key;
    }).collect(Collectors.toList());

    controllerTemplate.setKeys(keys);
    controllerTemplate.setImportPackages(importPackages);
    controllerTemplate.setCreate(this.getCreateTemplate());
    controllerTemplate.setUpdate(this.getUpdateTemplate());
    controllerTemplate.setQuery(this.getQueryTemplate());
    controllerTemplate.setQueryParams(this.getQueryParamsTemplate());
    controllerTemplate.setDetail(this.getDetailTemplate());
    if (controllerTemplate.getCreate() != null) {
      importPackages.addAll(controllerTemplate.getCreate().getImportPackages());
    }
    if (controllerTemplate.getUpdate() != null) {
      importPackages.addAll(controllerTemplate.getUpdate().getImportPackages());
    }
    if (controllerTemplate.getQuery() != null) {
      importPackages.addAll(controllerTemplate.getQuery().getImportPackages());
      if (!controllerTemplate.getHasAvailableTag()) {
        controllerTemplate.setHasAvailableTag(controllerTemplate.getQuery().getColumns().stream()
            .anyMatch(QueryTemplate.Column::getHasAvailableTag));
      }
    }
    if (controllerTemplate.getQueryParams() != null) {
      importPackages.addAll(controllerTemplate.getQueryParams().getImportPackages());
      if (!controllerTemplate.getHasAvailableTag()) {
        controllerTemplate.setHasAvailableTag(
            controllerTemplate.getQueryParams().getColumns().stream()
                .anyMatch(QueryParamsTemplate.Column::getHasAvailableTag));
      }
    }
    if (controllerTemplate.getDetail() != null) {
      importPackages.addAll(controllerTemplate.getDetail().getImportPackages());
    }

    return controllerTemplate;
  }

  private SqlTemplate getSqlTemplate() {

    SqlTemplate sqlTemplate = new SqlTemplate();
    sqlTemplate.setModuleName(dataEntity.getGenerateInfo().getModuleName());
    sqlTemplate.setBizName(dataEntity.getGenerateInfo().getBizName());
    sqlTemplate.setClassName(dataEntity.getGenerateInfo().getClassName());
    sqlTemplate.setClassDescription(dataEntity.getGenerateInfo().getClassDescription());
    sqlTemplate.setParentMenuId(dataEntity.getGenerateInfo().getParentMenuId());
    sqlTemplate.setMenuId(dataEntity.getGenerateInfo().getId());
    sqlTemplate.setMenuCode(dataEntity.getGenerateInfo().getMenuCode());
    sqlTemplate.setMenuName(dataEntity.getGenerateInfo().getMenuName());
    sqlTemplate.setCreate(this.getCreateTemplate());
    sqlTemplate.setUpdate(this.getUpdateTemplate());

    return sqlTemplate;
  }

  /**
   * 生成代码
   *
   * @param templateName 模板名称
   * @param data         数据
   * @return
   */
  private String generate(String templateName, Object data) {

    Template template = this.getTemplate(templateName);
    Map root = JsonUtil.convert(data, Map.class);
    StringWriter stringWriter = new StringWriter();
    BufferedWriter writer = new BufferedWriter(stringWriter);

    loadStaticClasses(root);

    try {
      template.process(root, writer);
    } catch (TemplateException | IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }

    return stringWriter.toString();
  }

  private GenerateDto buildGenerateResult(String path, String fileName, String content) {

    if (StringUtil.isBlank(content)) {
      return null;
    }
    GenerateDto result = new GenerateDto();
    result.setPath(path);
    result.setFileName(fileName);
    result.setContent(content);

    return result;
  }

  private void loadStaticClasses(Map root) {

    BeansWrapper wrapper = BeansWrapper.getDefaultInstance();

    TemplateHashModel staticModels = wrapper.getStaticModels();

    TemplateHashModel fileStatics = null;
    try {
      fileStatics = (TemplateHashModel) staticModels.get(IdUtil.class.getName());
    } catch (TemplateModelException e) {
      log.error("加载静态类失败", e);
      throw new DefaultSysException("加载静态类失败");
    }

    root.put(IdUtil.class.getSimpleName(), fileStatics);
  }
}
