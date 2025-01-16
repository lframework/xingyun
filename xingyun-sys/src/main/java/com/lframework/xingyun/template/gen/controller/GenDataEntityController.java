package com.lframework.xingyun.template.gen.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.common.utils.ZipUtil;
import com.lframework.xingyun.template.gen.converters.GenStringConverter;
import com.lframework.xingyun.template.gen.converters.GenViewTypeConverter;
import com.lframework.xingyun.template.gen.dto.data.entity.DataEntityGenerateDto;
import com.lframework.xingyun.template.gen.dto.generate.GenerateDto;
import com.lframework.xingyun.template.gen.entity.GenDataEntity;
import com.lframework.xingyun.template.gen.entity.GenDataEntityDetail;
import com.lframework.xingyun.template.gen.generate.Generator;
import com.lframework.xingyun.template.gen.service.GenCustomListService;
import com.lframework.xingyun.template.gen.service.GenDataEntityService;
import com.lframework.xingyun.template.gen.service.GenDataObjService;
import com.lframework.xingyun.template.gen.service.SimpleTableColumnService;
import com.lframework.xingyun.template.gen.vo.data.entity.CreateDataEntityVo;
import com.lframework.xingyun.template.gen.vo.data.entity.QueryDataEntityVo;
import com.lframework.xingyun.template.gen.vo.data.entity.UpdateDataEntityGenerateVo;
import com.lframework.xingyun.template.gen.vo.data.entity.UpdateDataEntityVo;
import com.lframework.xingyun.template.gen.vo.simpledb.QuerySimpleTableColumnVo;
import com.lframework.xingyun.template.gen.bo.data.entity.DataEntityGenerateBo;
import com.lframework.xingyun.template.gen.bo.data.entity.GenDataEntityDetailBo;
import com.lframework.xingyun.template.gen.bo.data.entity.GetDataEntityBo;
import com.lframework.xingyun.template.gen.bo.data.entity.QueryDataEntityBo;
import com.lframework.xingyun.template.gen.entity.GenSimpleTableColumn;
import com.lframework.xingyun.template.gen.enums.GenConvertType;
import com.lframework.xingyun.template.gen.enums.GenViewType;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "数据实体")
@Slf4j
@Validated
@RestController
@RequestMapping("/gen/data/entity")
public class GenDataEntityController extends DefaultBaseController {

  @Autowired
  private GenDataEntityService genDataEntityService;

  @Autowired
  private SimpleTableColumnService simpleTableColumnService;

  @Autowired
  private GenViewTypeConverter genViewTypeConverter;

  /**
   * 上传文件的临时文件目录
   */
  @Value("${spring.servlet.multipart.location}")
  private String location;

  @ApiOperation("查询数据对象列表")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryDataEntityBo>> query(@Valid QueryDataEntityVo vo) {

    PageResult<GenDataEntity> pageResult = genDataEntityService.query(getPageIndex(vo),
        getPageSize(vo), vo);
    List<GenDataEntity> datas = pageResult.getDatas();
    List<QueryDataEntityBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(QueryDataEntityBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("查询列信息")
  @GetMapping("/query/columns")
  public InvokeResult<List<GenDataEntityDetailBo>> getColumns(@Valid QuerySimpleTableColumnVo vo) {
    List<GenSimpleTableColumn> datas = simpleTableColumnService.query(vo);
    if (CollectionUtil.isEmpty(datas)) {
      throw new DefaultClientException("数据表不存在，请检查！");
    }
    List<GenDataEntityDetail> details = datas.stream().map(t -> {
      GenDataEntityDetail detail = new GenDataEntityDetail();
      detail.setId(t.getDbColumnName());
      detail.setName(t.getColumnComment());
      detail.setColumnName(GenStringConverter.convertToCamelCase(GenConvertType.UNDERLINE_TO_CAMEL,
          t.getDbColumnName()));
      detail.setIsKey(t.getIsKey());
      detail.setDataType(t.getDataType());
      detail.setColumnOrder(t.getOrdinalPosition());
      List<GenViewType> viewTypes = genViewTypeConverter.convert(t.getDataType());
      if (CollectionUtil.isEmpty(viewTypes)) {
        throw new DefaultClientException("字段：" + t.getDbColumnName() + "类型暂不支持！");
      }
      detail.setViewType(viewTypes.get(0));
      detail.setFixEnum(Boolean.FALSE);
      detail.setIsOrder(Boolean.FALSE);
      detail.setLen(t.getLen());
      detail.setDecimals(t.getDecimals());

      return detail;
    }).collect(Collectors.toList());

    List<GenDataEntityDetailBo> results = details.stream().map(GenDataEntityDetailBo::new).collect(
        Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetDataEntityBo> get(@NotBlank(message = "ID不能为空！") String id) {

    GenDataEntity data = genDataEntityService.findById(id);

    return InvokeResultBuilder.success(new GetDataEntityBo(data));
  }

  @ApiOperation("新增")
  @PostMapping
  public InvokeResult<Void> create(@RequestBody @Valid CreateDataEntityVo vo) {

    genDataEntityService.create(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("修改")
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateDataEntityVo vo) {

    genDataEntityService.update(vo);

    this.evictRelaCache(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    genDataEntityService.delete(id);

    this.evictRelaCache(id);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量删除")
  @DeleteMapping("/batch")
  public InvokeResult<Void> batchDelete(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    genDataEntityService.batchDelete(ids);

    for (String id : ids) {
      this.evictRelaCache(id);
    }

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量启用")
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    genDataEntityService.batchEnable(ids);

    for (String id : ids) {
      this.evictRelaCache(id);
    }

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量停用")
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    genDataEntityService.batchUnable(ids);

    for (String id : ids) {
      this.evictRelaCache(id);
    }

    return InvokeResultBuilder.success();
  }

  @ApiOperation("获取生成代码配置")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/generate")
  public InvokeResult<DataEntityGenerateBo> getGenerate(@NotNull(message = "ID不能为空！") String id) {

    DataEntityGenerateDto data = genDataEntityService.getGenerateById(id);

    DataEntityGenerateBo result = new DataEntityGenerateBo(data);

    return InvokeResultBuilder.success(result);
  }

  @ApiOperation("修改生成代码配置")
  @PatchMapping("/generate")
  public InvokeResult<Void> updateGenerate(@Valid @RequestBody UpdateDataEntityGenerateVo vo) {

    genDataEntityService.updateGenerate(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("预览")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/preview")
  public InvokeResult<Map<String, String>> preView(@NotNull(message = "ID不能为空！") String id) {

    Generator generator = Generator.getInstance(id);
    List<GenerateDto> datas = generator.generateAll();
    Map<String, String> result = new LinkedHashMap<>();
    for (GenerateDto data : datas) {
      result.put(data.getFileName(), data.getContent());
    }

    return InvokeResultBuilder.success(result);
  }

  @ApiOperation("下载")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/download")
  public void download(@NotNull(message = "ID不能为空！") String id) {

    String fileLocation = location.endsWith(File.separator) ? location : location + File.separator;
    String filePath = fileLocation + IdUtil.getUUID() + File.separator;

    Generator generator = Generator.getInstance(id);

    List<GenerateDto> datas = generator.generateAll();
    for (GenerateDto data : datas) {

      File file = FileUtil.file(filePath + data.getPath() + File.separator + data.getFileName());
      FileUtil.writeString(data.getContent(), file, StandardCharsets.UTF_8);
    }

    File zipFile = ZipUtil.zip(filePath, fileLocation + IdUtil.getUUID() + ".zip", false);

    ResponseUtil.download(zipFile);
  }

  @ApiOperation("同步数据表")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PutMapping("/sync/table")
  public InvokeResult<Void> syncTable(@NotNull(message = "ID不能为空！") String id) {
    genDataEntityService.syncTable(id);

    this.evictRelaCache(id);

    return InvokeResultBuilder.success();
  }

  // 失效关联数据的缓存
  private void evictRelaCache(String entityId) {
    ThreadUtil.execAsync(() -> {
      GenDataObjService genDataObjService = ApplicationUtil.getBean(GenDataObjService.class);
      List<String> ids = genDataObjService.getRelaGenDataEntityIds(entityId);
      if (CollectionUtil.isNotEmpty(ids)) {
        genDataObjService.cleanCacheByKeys(ids);
      }

      GenCustomListService genCustomListService = ApplicationUtil.getBean(GenCustomListService.class);
      ids = genCustomListService.getRelaGenDataEntityIds(entityId);
      if (CollectionUtil.isNotEmpty(ids)) {
        genCustomListService.cleanCacheByKeys(ids);
      }
    });
  }
}
