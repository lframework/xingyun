package com.lframework.xingyun.produce.impl.assemble;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.produce.entity.AssembleSheetDetail;
import com.lframework.xingyun.produce.mappers.AssembleSheetDetailMapper;
import com.lframework.xingyun.produce.service.assemble.AssembleSheetDetailService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 组装单明细 ServiceImpl
 *
 * @author zmj
 */
@Service
public class AssembleSheetDetailServiceImpl extends
    BaseMpServiceImpl<AssembleSheetDetailMapper, AssembleSheetDetail>
    implements AssembleSheetDetailService {

  @Override
  public List<AssembleSheetDetail> listBySheetId(String sheetId) {

    Wrapper<AssembleSheetDetail> wrapper = Wrappers.lambdaQuery(AssembleSheetDetail.class)
        .eq(AssembleSheetDetail::getSheetId, sheetId)
        .orderByAsc(AssembleSheetDetail::getOrderNo);

    return list(wrapper);
  }

  @Override
  public void deleteBySheetId(String sheetId) {

    Wrapper<AssembleSheetDetail> wrapper = Wrappers.lambdaQuery(AssembleSheetDetail.class)
        .eq(AssembleSheetDetail::getSheetId, sheetId);

    remove(wrapper);
  }
}
