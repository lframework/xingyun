package com.lframework.xingyun.produce.service.bom;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.produce.entity.Bom;
import com.lframework.xingyun.produce.vo.bom.CreateBomVo;
import com.lframework.xingyun.produce.vo.bom.QueryBomVo;
import com.lframework.xingyun.produce.vo.bom.UpdateBomVo;
import java.io.Serializable;
import java.util.List;

public interface BomService extends BaseMpService<Bom> {

  /**
   * 分页查询
   */
  PageResult<Bom> query(Integer pageIndex, Integer pageSize, QueryBomVo vo);

  /**
   * 查询列表
   */
  List<Bom> query(QueryBomVo vo);

  /**
   * 根据ID查询
   */
  Bom findById(String id);

  /**
   * 创建
   */
  String create(CreateBomVo vo);

  /**
   * 修改（仅草稿可修改）
   */
  void update(UpdateBomVo vo);

  /**
   * 审核启用
   */
  void approve(String id, String userId);

  /**
   * 停用
   */
  void disable(String id);

  /**
   * 删除（仅草稿可删除，物理删除）
   */
  void deleteById(String id);

  /**
   * 清理缓存
   */
  void cleanCacheByKey(Serializable key);
}
