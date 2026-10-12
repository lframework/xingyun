package com.lframework.xingyun.produce.service.assemble;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.produce.bo.assemble.AssembleShortageBo;
import com.lframework.xingyun.produce.entity.AssembleSheet;
import com.lframework.xingyun.produce.vo.assemble.ApprovePassAssembleSheetVo;
import com.lframework.xingyun.produce.vo.assemble.ApproveRefuseAssembleSheetVo;
import com.lframework.xingyun.produce.vo.assemble.CreateAssembleSheetVo;
import com.lframework.xingyun.produce.vo.assemble.QueryAssembleSheetVo;
import com.lframework.xingyun.produce.vo.assemble.UpdateAssembleSheetVo;
import java.math.BigDecimal;
import java.util.List;

/**
 * 组装单 Service
 *
 * @author zmj
 */
public interface AssembleSheetService extends BaseMpService<AssembleSheet> {

  /**
   * 分页查询
   */
  PageResult<AssembleSheet> query(Integer pageIndex, Integer pageSize, QueryAssembleSheetVo vo);

  /**
   * 根据ID查询
   */
  AssembleSheet findById(String id);

  /**
   * 新增
   */
  String create(CreateAssembleSheetVo vo);

  /**
   * 修改（仅待审核状态）
   */
  void update(UpdateAssembleSheetVo vo);

  /**
   * 审核通过（子件出库 + 成品入库）
   */
  void approvePass(ApprovePassAssembleSheetVo vo, String userId);

  /**
   * 审核拒绝
   */
  void approveRefuse(ApproveRefuseAssembleSheetVo vo, String userId);

  /**
   * 直接审核通过（创建并审核）
   */
  String directApprovePass(CreateAssembleSheetVo vo, String userId);

  /**
   * 删除（仅待审核/审核拒绝状态）
   */
  void deleteById(String id);

  /**
   * 缺料计算：按 BOM 展开 × 组装数量，对比指定仓库现货
   */
  List<AssembleShortageBo> calcShortage(String bomId, BigDecimal totalNum, String scId);
}
