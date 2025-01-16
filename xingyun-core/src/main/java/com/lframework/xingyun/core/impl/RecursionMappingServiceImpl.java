package com.lframework.xingyun.core.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.core.entity.RecursionMapping;
import com.lframework.xingyun.core.enums.NodeType;
import com.lframework.xingyun.core.service.RecursionMappingService;
import com.lframework.xingyun.core.mappers.RecursionMappingMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecursionMappingServiceImpl extends
    BaseMpServiceImpl<RecursionMappingMapper, RecursionMapping>
    implements RecursionMappingService {

  @Override
  public List<String> getNodeParentIds(@NonNull String nodeId, @NonNull NodeType nodeType) {

    if (StringUtil.isEmpty(nodeId)) {
      return CollectionUtil.emptyList();
    }

    Wrapper<RecursionMapping> queryWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
        .eq(RecursionMapping::getNodeId, nodeId)
        .eq(RecursionMapping::getNodeType, nodeType.getCode());

    RecursionMapping recursionMappings = getBaseMapper().selectOne(queryWrapper);
    if (recursionMappings == null || StringUtil.isEmpty(recursionMappings.getPath())) {
      return CollectionUtil.emptyList();
    }

    return StringUtil.split(recursionMappings.getPath(), StringPool.STR_SPLIT);
  }

  @Override
  public List<String> getNodeChildIds(String nodeId, NodeType nodeType) {

    return getBaseMapper().getNodeChildIds(nodeId, nodeType.getCode());
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void saveNode(String nodeId, NodeType nodeType) {

    this.saveNode(nodeId, nodeType, null);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteNode(String nodeId, NodeType nodeType) {

    Wrapper<RecursionMapping> deleteWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
        .eq(RecursionMapping::getNodeId, nodeId)
        .eq(RecursionMapping::getNodeType, nodeType.getCode());
    getBaseMapper().delete(deleteWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteNodeAndChildren(String nodeId, NodeType nodeType) {
    List<String> childNodeIds = this.getNodeChildIds(nodeId, nodeType);
    List<String> allNodeIds = new ArrayList<>();
    allNodeIds.add(nodeId);
    allNodeIds.addAll(childNodeIds);
    Wrapper<RecursionMapping> deleteWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
        .in(RecursionMapping::getNodeId, allNodeIds)
        .eq(RecursionMapping::getNodeType, nodeType.getCode());
    getBaseMapper().delete(deleteWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void saveNode(@NonNull String nodeId, @NonNull NodeType nodeType, List<String> parentIds) {

    Wrapper<RecursionMapping> deleteWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
        .eq(RecursionMapping::getNodeId, nodeId)
        .eq(RecursionMapping::getNodeType, nodeType.getCode());
    getBaseMapper().delete(deleteWrapper);

    RecursionMapping data = new RecursionMapping();
    data.setId(IdUtil.getId());
    data.setNodeId(nodeId);
    data.setNodeType(nodeType.getCode());
    data.setLevel(1);
    data.setPath(StringPool.EMPTY_STR);
    if (!CollectionUtil.isEmpty(parentIds)) {
      data.setPath(StringUtil.join(StringPool.STR_SPLIT, parentIds));
      data.setLevel(parentIds.size() + 1);
    }

    getBaseMapper().insert(data);
  }
}
