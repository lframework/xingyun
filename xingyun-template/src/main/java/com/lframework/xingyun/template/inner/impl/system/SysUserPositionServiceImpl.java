package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.template.core.enums.DefaultOpLogType;
import com.lframework.xingyun.template.inner.vo.system.position.SysUserPositionSettingVo;
import com.lframework.xingyun.template.inner.mappers.system.SysUserPositionMapper;
import com.lframework.xingyun.template.inner.entity.SysUserPosition;
import com.lframework.xingyun.template.inner.service.system.SysUserPositionService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserPositionServiceImpl
    extends BaseMpServiceImpl<SysUserPositionMapper, SysUserPosition>
    implements SysUserPositionService {

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "用户设置岗位，用户ID：{}，岗位ID：{}", params = {"#vo.userId",
      "#vo.positionIds"}, loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SysUserPositionSettingVo vo) {

    this.doSetting(vo);
  }

  @Override
  public List<SysUserPosition> getByUserId(String userId) {

    return doGetByUserId(userId);
  }

  protected void doSetting(SysUserPositionSettingVo vo) {

    Wrapper<SysUserPosition> deleteWrapper = Wrappers.lambdaQuery(
            SysUserPosition.class)
        .eq(SysUserPosition::getUserId, vo.getUserId());
    getBaseMapper().delete(deleteWrapper);

    if (!CollectionUtil.isEmpty(vo.getPositionIds())) {
      for (String positionId : vo.getPositionIds()) {
        SysUserPosition record = new SysUserPosition();
        record.setId(IdUtil.getId());
        record.setUserId(vo.getUserId());
        record.setPositionId(positionId);

        getBaseMapper().insert(record);
      }
    }
  }

  protected List<SysUserPosition> doGetByUserId(String userId) {

    return getBaseMapper().getByUserId(userId);
  }
}
