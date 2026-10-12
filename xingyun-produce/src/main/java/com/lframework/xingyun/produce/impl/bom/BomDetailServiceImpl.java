package com.lframework.xingyun.produce.impl.bom;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.produce.dto.bom.BomDetailDto;
import com.lframework.xingyun.produce.entity.BomDetail;
import com.lframework.xingyun.produce.mappers.BomDetailMapper;
import com.lframework.xingyun.produce.service.bom.BomDetailService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BomDetailServiceImpl extends BaseMpServiceImpl<BomDetailMapper, BomDetail>
    implements BomDetailService {

  @Override
  public List<BomDetailDto> listByBomId(String bomId) {

    return getBaseMapper().listByBomId(bomId);
  }

  @Override
  public void deleteByBomId(String bomId) {

    getBaseMapper().delete(Wrappers.lambdaQuery(BomDetail.class).eq(BomDetail::getBomId, bomId));
  }
}
