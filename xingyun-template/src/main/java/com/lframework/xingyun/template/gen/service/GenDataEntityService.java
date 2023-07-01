package com.lframework.xingyun.template.gen.service;

import com.lframework.xingyun.template.gen.dto.data.entity.DataEntityGenerateDto;
import com.lframework.xingyun.template.gen.entity.GenDataEntity;
import com.lframework.xingyun.template.gen.vo.data.entity.CreateDataEntityVo;
import com.lframework.xingyun.template.gen.vo.data.entity.GenDataEntitySelectorVo;
import com.lframework.xingyun.template.gen.vo.data.entity.QueryDataEntityVo;
import com.lframework.xingyun.template.gen.vo.data.entity.UpdateDataEntityGenerateVo;
import com.lframework.xingyun.template.gen.vo.data.entity.UpdateDataEntityVo;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import java.util.List;

public interface GenDataEntityService extends BaseMpService<GenDataEntity> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenDataEntity> query(Integer pageIndex, Integer pageSize, QueryDataEntityVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenDataEntity> query(QueryDataEntityVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenDataEntity> selector(Integer pageIndex, Integer pageSize,
      GenDataEntitySelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenDataEntity findById(String id);

  /**
   * 创建
   *
   * @param data
   * @return
   */
  String create(CreateDataEntityVo data);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateDataEntityVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void delete(String id);

  /**
   * 根据ID批量删除
   *
   * @param ids
   */
  void batchDelete(List<String> ids);

  /**
   * 批量启用
   *
   * @param ids
   */
  void batchEnable(List<String> ids);

  /**
   * 批量停用
   *
   * @param ids
   */
  void batchUnable(List<String> ids);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DataEntityGenerateDto getGenerateById(String id);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(UpdateDataEntityGenerateVo vo);

  /**
   * 同步数据表
   *
   * @param id
   */
  void syncTable(String id);

}
