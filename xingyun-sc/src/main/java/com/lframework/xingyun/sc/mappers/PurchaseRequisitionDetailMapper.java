package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.sc.entity.PurchaseRequisitionDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchaseRequisitionDetailMapper extends BaseMapper<PurchaseRequisitionDetail> {

  List<PurchaseRequisitionDetail> listByRequisitionId(@Param("requisitionId") String requisitionId);
}
