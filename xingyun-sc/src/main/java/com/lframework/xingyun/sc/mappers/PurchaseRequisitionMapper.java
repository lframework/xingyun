package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.sc.entity.PurchaseRequisition;
import com.lframework.xingyun.sc.vo.purchase.requisition.QueryPurchaseRequisitionVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchaseRequisitionMapper extends BaseMapper<PurchaseRequisition> {

  List<PurchaseRequisition> query(@Param("vo") QueryPurchaseRequisitionVo vo);
}
