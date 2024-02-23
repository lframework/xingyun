package com.lframework.xingyun.template.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.xingyun.template.gen.components.custom.list.CustomListConfig;
import com.lframework.xingyun.template.gen.entity.GenCustomList;
import com.lframework.xingyun.template.gen.entity.GenCustomListDetail;
import com.lframework.xingyun.template.gen.entity.GenCustomListHandleColumn;
import com.lframework.xingyun.template.gen.entity.GenCustomListQueryParams;
import com.lframework.xingyun.template.gen.entity.GenCustomListToolbar;
import com.lframework.xingyun.template.gen.enums.GenCustomListBtnType;
import com.lframework.xingyun.template.gen.enums.GenCustomListBtnViewType;
import com.lframework.xingyun.template.gen.enums.GenCustomListDetailType;
import com.lframework.xingyun.template.gen.enums.GenCustomListType;
import com.lframework.xingyun.template.gen.enums.GenQueryType;
import com.lframework.xingyun.template.gen.enums.GenQueryWidthType;
import com.lframework.xingyun.template.gen.events.CustomListDeleteEvent;
import com.lframework.xingyun.template.gen.mappers.GenCustomListMapper;
import com.lframework.xingyun.template.gen.service.GenCustomListDetailService;
import com.lframework.xingyun.template.gen.service.GenCustomListHandleColumnService;
import com.lframework.xingyun.template.gen.service.GenCustomListQueryParamsService;
import com.lframework.xingyun.template.gen.service.GenCustomListService;
import com.lframework.xingyun.template.gen.service.GenCustomListToolbarService;
import com.lframework.xingyun.template.gen.service.GenCustomSelectorService;
import com.lframework.xingyun.template.gen.vo.custom.list.CreateGenCustomListVo;
import com.lframework.xingyun.template.gen.vo.custom.list.GenCustomListDetailVo;
import com.lframework.xingyun.template.gen.vo.custom.list.GenCustomListHandleColumnVo;
import com.lframework.xingyun.template.gen.vo.custom.list.GenCustomListQueryParamsVo;
import com.lframework.xingyun.template.gen.vo.custom.list.GenCustomListSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.list.GenCustomListToolbarVo;
import com.lframework.xingyun.template.gen.vo.custom.list.QueryGenCustomListVo;
import com.lframework.xingyun.template.gen.vo.custom.list.UpdateGenCustomListVo;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.JsonUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCustomListServiceImpl extends
    BaseMpServiceImpl<GenCustomListMapper, GenCustomList> implements GenCustomListService {

  @Autowired
  private GenCustomListQueryParamsService genCustomListQueryParamsService;

  @Autowired
  private GenCustomListDetailService genCustomListDetailService;

  @Autowired
  private GenCustomListToolbarService genCustomListToolbarService;

  @Autowired
  private GenCustomListHandleColumnService genCustomListHandleColumnService;

  @Override
  public PageResult<GenCustomList> query(Integer pageIndex, Integer pageSize,
      QueryGenCustomListVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenCustomList> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<GenCustomList> query(QueryGenCustomListVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<GenCustomList> selector(Integer pageIndex, Integer pageSize,
      GenCustomListSelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenCustomList> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = GenCustomList.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public GenCustomList findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateGenCustomListVo data) {
    GenCustomList record = new GenCustomList();
    record.setId(IdUtil.getId());
    record.setName(data.getName());
    if (!StringUtil.isBlank(data.getCategoryId())) {
      record.setCategoryId(data.getCategoryId());
    }
    record.setListType(EnumUtil.getByCode(GenCustomListType.class, data.getListType()));
    record.setQueryPrefixSql(StringUtil.isBlank(data.getQueryPrefixSql()) ? StringPool.EMPTY_STR
        : data.getQueryPrefixSql());
    record.setQuerySuffixSql(StringUtil.isBlank(data.getQuerySuffixSql()) ? StringPool.EMPTY_STR
        : data.getQuerySuffixSql());
    record.setSuffixSql(
        StringUtil.isBlank(data.getSuffixSql()) ? StringPool.EMPTY_STR : data.getSuffixSql());

    record.setLabelWidth(data.getLabelWidth());
    record.setHasPage(data.getHasPage());
    record.setTreeData(data.getTreeData());
    // 如果是树形列表，那么分页必须禁用
    // 如果分页没有禁用，那么一定不是树形列表
    if (record.getTreeData()) {
      record.setHasPage(Boolean.FALSE);
    }

    if (record.getHasPage()) {
      record.setTreeData(Boolean.FALSE);
    }

    record.setIdColumn(data.getIdColumn());
    record.setIdColumnRelaId(data.getIdColumnRelaId());

    if (record.getTreeData()) {

      if (StringUtil.isBlank(data.getTreePidColumn())) {
        throw new DefaultClientException("父级ID字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreePidColumnRelaId())) {
        throw new DefaultClientException("父级ID字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeNodeColumn())) {
        throw new DefaultClientException("树形节点字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeNodeColumnRelaId())) {
        throw new DefaultClientException("树形节点字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeChildrenKey())) {
        throw new DefaultClientException("子节点Key值不能为空！");
      }
      record.setTreePidColumn(data.getTreePidColumn());
      record.setTreePidColumnRelaId(data.getTreePidColumnRelaId());
      record.setTreeNodeColumn(data.getTreeNodeColumn());
      record.setTreeNodeColumnRelaId(data.getTreeNodeColumnRelaId());
      record.setTreeChildrenKey(data.getTreeChildrenKey());
    }
    record.setAllowExport(data.getAllowExport());
    record.setDataObjId(data.getDataObjId());
    record.setAvailable(Boolean.TRUE);
    record.setDescription(
        StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR : data.getDescription());

    if (!CollectionUtil.isEmpty(data.getQueryParams())) {
      int orderNo = 1;
      for (GenCustomListQueryParamsVo queryParam : data.getQueryParams()) {
        GenCustomListQueryParams genCustomListQueryParams = new GenCustomListQueryParams();
        genCustomListQueryParams.setId(IdUtil.getId());
        genCustomListQueryParams.setCustomListId(record.getId());
        genCustomListQueryParams.setRelaId(queryParam.getRelaId());
        genCustomListQueryParams.setDataEntityId(queryParam.getId());
        genCustomListQueryParams.setQueryType(
            EnumUtil.getByCode(GenQueryType.class, queryParam.getQueryType()));
        genCustomListQueryParams.setFrontShow(queryParam.getFrontShow());
        genCustomListQueryParams.setFormWidth(queryParam.getFormWidth());
        genCustomListQueryParams.setDefaultValue(queryParam.getDefaultValue() == null ? null
            : queryParam.getDefaultValue() instanceof String ? queryParam.getDefaultValue()
                .toString() : JsonUtil.toJsonString(queryParam.getDefaultValue()));
        genCustomListQueryParams.setOrderNo(orderNo);
        genCustomListQueryParams.setType(
            EnumUtil.getByCode(GenCustomListDetailType.class, queryParam.getType()));

        genCustomListQueryParamsService.save(genCustomListQueryParams);

        orderNo++;
      }
    }

    int orderNo = 1;
    for (GenCustomListDetailVo detail : data.getDetails()) {
      GenCustomListDetail genCustomListDetail = new GenCustomListDetail();
      genCustomListDetail.setId(IdUtil.getId());
      genCustomListDetail.setCustomListId(record.getId());
      genCustomListDetail.setDataEntityId(detail.getId());
      genCustomListDetail.setRelaId(detail.getRelaId());
      genCustomListDetail.setWidthType(
          EnumUtil.getByCode(GenQueryWidthType.class, detail.getWidthType()));
      genCustomListDetail.setWidth(detail.getWidth());
      genCustomListDetail.setSortable(detail.getSortable());
      genCustomListDetail.setOrderNo(orderNo);
      genCustomListDetail.setType(
          EnumUtil.getByCode(GenCustomListDetailType.class, detail.getType()));
      if (genCustomListDetail.getType() == GenCustomListDetailType.CUSTOM) {
        genCustomListDetail.setDataEntityId(null);
      }
      if (!StringUtil.isBlank(detail.getFormatter())) {
        genCustomListDetail.setFormatter(detail.getFormatter());
      }

      genCustomListDetailService.save(genCustomListDetail);

      orderNo++;
    }

    if (!CollectionUtil.isEmpty(data.getToolbars())) {
      orderNo = 1;
      for (GenCustomListToolbarVo tb : data.getToolbars()) {
        GenCustomListToolbar toolbar = new GenCustomListToolbar();
        toolbar.setId(IdUtil.getId());
        toolbar.setCustomListId(record.getId());
        toolbar.setName(tb.getName());
        toolbar.setViewType(EnumUtil.getByCode(GenCustomListBtnViewType.class, tb.getViewType()));
        toolbar.setBtnType(EnumUtil.getByCode(GenCustomListBtnType.class, tb.getBtnType()));
        toolbar.setBtnConfig(tb.getBtnConfig());
        if (!StringUtil.isBlank(tb.getIcon())) {
          toolbar.setIcon(tb.getIcon());
        }
        toolbar.setOrderNo(orderNo);
        orderNo++;

        genCustomListToolbarService.save(toolbar);
      }
    }

    if (!CollectionUtil.isEmpty(data.getHandleColumns())) {
      orderNo = 1;
      for (GenCustomListHandleColumnVo hd : data.getHandleColumns()) {
        GenCustomListHandleColumn handleColumn = new GenCustomListHandleColumn();
        handleColumn.setId(IdUtil.getId());
        handleColumn.setCustomListId(record.getId());
        handleColumn.setName(hd.getName());
        handleColumn
            .setViewType(EnumUtil.getByCode(GenCustomListBtnViewType.class, hd.getViewType()));
        handleColumn.setBtnType(EnumUtil.getByCode(GenCustomListBtnType.class, hd.getBtnType()));
        handleColumn.setBtnConfig(hd.getBtnConfig());
        if (!StringUtil.isBlank(hd.getIcon())) {
          handleColumn.setIcon(hd.getIcon());
        }
        handleColumn.setOrderNo(orderNo);
        handleColumn.setWidth(hd.getWidth());
        orderNo++;

        genCustomListHandleColumnService.save(handleColumn);
      }
    }

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateGenCustomListVo data) {
    GenCustomList record = this.getById(data.getId());
    if (record == null) {
      throw new DefaultClientException("自定义列表不存在！");
    }

    record.setHasPage(data.getHasPage());
    record.setTreeData(data.getTreeData());

    // 如果是树形列表，那么分页必须禁用
    // 如果分页没有禁用，那么一定不是树形列表
    if (record.getTreeData()) {
      record.setHasPage(Boolean.FALSE);
    }

    if (record.getHasPage()) {
      record.setTreeData(Boolean.FALSE);
    }

    if (record.getTreeData()) {

      if (StringUtil.isBlank(data.getTreePidColumn())) {
        throw new DefaultClientException("父级ID字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreePidColumnRelaId())) {
        throw new DefaultClientException("父级ID字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeNodeColumn())) {
        throw new DefaultClientException("树形节点字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeNodeColumnRelaId())) {
        throw new DefaultClientException("树形节点字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeChildrenKey())) {
        throw new DefaultClientException("子节点Key值不能为空！");
      }
    }

    Wrapper<GenCustomList> updateWrapper = Wrappers.lambdaUpdate(GenCustomList.class)
        .eq(GenCustomList::getId, data.getId()).set(GenCustomList::getName, data.getName())
        .set(GenCustomList::getCategoryId,
            StringUtil.isBlank(data.getCategoryId()) ? null : data.getCategoryId())
        .set(GenCustomList::getListType,
            EnumUtil.getByCode(GenCustomListType.class, data.getListType()))
        .set(GenCustomList::getLabelWidth, data.getLabelWidth())
        .set(GenCustomList::getHasPage, record.getHasPage())
        .set(GenCustomList::getTreeData, record.getTreeData())
        .set(GenCustomList::getIdColumn, data.getIdColumn())
        .set(GenCustomList::getIdColumnRelaId, data.getIdColumnRelaId())
        .set(GenCustomList::getTreePidColumn, record.getTreeData() ? data.getTreePidColumn() : null)
        .set(GenCustomList::getTreePidColumnRelaId,
            record.getTreeData() ? data.getTreePidColumnRelaId() : null)
        .set(GenCustomList::getTreeNodeColumn,
            record.getTreeData() ? data.getTreeNodeColumn() : null)
        .set(GenCustomList::getTreeNodeColumnRelaId,
            record.getTreeData() ? data.getTreeNodeColumnRelaId() : null)
        .set(GenCustomList::getTreeChildrenKey,
            record.getTreeData() ? data.getTreeChildrenKey() : null)
        .set(GenCustomList::getQueryPrefixSql,
            StringUtil.isBlank(data.getQueryPrefixSql()) ? StringPool.EMPTY_STR
                : data.getQueryPrefixSql())
        .set(GenCustomList::getQuerySuffixSql,
            StringUtil.isBlank(data.getQuerySuffixSql()) ? StringPool.EMPTY_STR
                : data.getQuerySuffixSql())
        .set(GenCustomList::getSuffixSql,
            StringUtil.isBlank(data.getSuffixSql()) ? StringPool.EMPTY_STR : data.getSuffixSql())
        .set(GenCustomList::getDescription,
            StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR
                : data.getDescription()).set(GenCustomList::getAvailable, data.getAvailable())
        .set(GenCustomList::getAllowExport, data.getAllowExport());

    this.update(updateWrapper);

    genCustomListDetailService.deleteByCustomListId(data.getId());
    genCustomListQueryParamsService.deleteByCustomListId(data.getId());

    if (!CollectionUtil.isEmpty(data.getQueryParams())) {
      int orderNo = 1;
      for (GenCustomListQueryParamsVo queryParam : data.getQueryParams()) {
        GenCustomListQueryParams genCustomListQueryParams = new GenCustomListQueryParams();
        genCustomListQueryParams.setId(IdUtil.getId());
        genCustomListQueryParams.setCustomListId(record.getId());
        genCustomListQueryParams.setRelaId(queryParam.getRelaId());
        genCustomListQueryParams.setDataEntityId(queryParam.getId());
        genCustomListQueryParams.setQueryType(
            EnumUtil.getByCode(GenQueryType.class, queryParam.getQueryType()));
        genCustomListQueryParams.setFrontShow(queryParam.getFrontShow());
        genCustomListQueryParams.setFormWidth(queryParam.getFormWidth());
        genCustomListQueryParams.setDefaultValue(queryParam.getDefaultValue() == null ? null
            : queryParam.getDefaultValue() instanceof String ? queryParam.getDefaultValue()
                .toString() : JsonUtil.toJsonString(queryParam.getDefaultValue()));
        genCustomListQueryParams.setOrderNo(orderNo);
        genCustomListQueryParams.setType(
            EnumUtil.getByCode(GenCustomListDetailType.class, queryParam.getType()));

        genCustomListQueryParamsService.save(genCustomListQueryParams);

        orderNo++;
      }
    }

    int orderNo = 1;
    for (GenCustomListDetailVo detail : data.getDetails()) {
      GenCustomListDetail genCustomListDetail = new GenCustomListDetail();
      genCustomListDetail.setId(IdUtil.getId());
      genCustomListDetail.setCustomListId(record.getId());
      genCustomListDetail.setDataEntityId(detail.getId());
      genCustomListDetail.setRelaId(detail.getRelaId());
      genCustomListDetail.setWidthType(
          EnumUtil.getByCode(GenQueryWidthType.class, detail.getWidthType()));
      genCustomListDetail.setWidth(detail.getWidth());
      genCustomListDetail.setSortable(detail.getSortable());
      genCustomListDetail.setOrderNo(orderNo);
      genCustomListDetail.setType(
          EnumUtil.getByCode(GenCustomListDetailType.class, detail.getType()));
      if (genCustomListDetail.getType() == GenCustomListDetailType.CUSTOM) {
        genCustomListDetail.setDataEntityId(null);
      }
      genCustomListDetail.setFormatter(detail.getFormatter());

      genCustomListDetailService.save(genCustomListDetail);

      orderNo++;
    }

    genCustomListToolbarService.deleteByCustomListId(record.getId());

    if (!CollectionUtil.isEmpty(data.getToolbars())) {
      orderNo = 1;
      for (GenCustomListToolbarVo tb : data.getToolbars()) {
        GenCustomListToolbar toolbar = new GenCustomListToolbar();
        toolbar.setId(IdUtil.getId());
        toolbar.setCustomListId(record.getId());
        toolbar.setName(tb.getName());
        toolbar.setViewType(EnumUtil.getByCode(GenCustomListBtnViewType.class, tb.getViewType()));
        toolbar.setBtnType(EnumUtil.getByCode(GenCustomListBtnType.class, tb.getBtnType()));
        toolbar.setBtnConfig(tb.getBtnConfig());
        if (!StringUtil.isBlank(tb.getIcon())) {
          toolbar.setIcon(tb.getIcon());
        }
        toolbar.setOrderNo(orderNo);
        orderNo++;

        genCustomListToolbarService.save(toolbar);
      }
    }

    genCustomListHandleColumnService.deleteByCustomListId(record.getId());

    if (!CollectionUtil.isEmpty(data.getHandleColumns())) {
      orderNo = 1;
      for (GenCustomListHandleColumnVo hd : data.getHandleColumns()) {
        GenCustomListHandleColumn handleColumn = new GenCustomListHandleColumn();
        handleColumn.setId(IdUtil.getId());
        handleColumn.setCustomListId(record.getId());
        handleColumn.setName(hd.getName());
        handleColumn
            .setViewType(EnumUtil.getByCode(GenCustomListBtnViewType.class, hd.getViewType()));
        handleColumn.setBtnType(EnumUtil.getByCode(GenCustomListBtnType.class, hd.getBtnType()));
        handleColumn.setBtnConfig(hd.getBtnConfig());
        if (!StringUtil.isBlank(hd.getIcon())) {
          handleColumn.setIcon(hd.getIcon());
        }
        handleColumn.setWidth(hd.getWidth());
        handleColumn.setOrderNo(orderNo);
        orderNo++;

        genCustomListHandleColumnService.save(handleColumn);
      }
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void delete(String id) {
    GenCustomList data = this.getById(id);

    this.removeById(id);
    genCustomListDetailService.deleteByCustomListId(id);
    genCustomListQueryParamsService.deleteByCustomListId(id);

    if (data != null) {
      CustomListDeleteEvent event = new CustomListDeleteEvent(this);
      event.setId(id);
      event.setName(data.getName());
      ApplicationUtil.publishEvent(event);
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchDelete(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    for (String id : ids) {
      this.delete(id);
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchEnable(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<GenCustomList> wrapper = Wrappers.lambdaUpdate(GenCustomList.class)
        .set(GenCustomList::getAvailable, Boolean.TRUE).in(GenCustomList::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchUnable(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<GenCustomList> wrapper = Wrappers.lambdaUpdate(GenCustomList.class)
        .set(GenCustomList::getAvailable, Boolean.FALSE).in(GenCustomList::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @Override
  public List<String> getRelaGenDataObjIds(String objId) {
    return getBaseMapper().getRelaGenDataObjIds(objId);
  }

  @Override
  public List<String> getRelaGenDataEntityIds(String entityId) {
    return getBaseMapper().getRelaGenDataEntityIds(entityId);
  }

  @CacheEvict(value = {GenCustomList.CACHE_NAME, CustomListConfig.CACHE_NAME}, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
    ThreadUtil.execAsync(() -> {
      GenCustomSelectorService genCustomSelectorService = ApplicationUtil
          .getBean(GenCustomSelectorService.class);
      List<String> ids = genCustomSelectorService.getRelaGenCustomListIds(String.valueOf(key));
      if (CollectionUtil.isNotEmpty(ids)) {
        genCustomSelectorService.cleanCacheByKeys(ids);
      }
    });
  }
}
