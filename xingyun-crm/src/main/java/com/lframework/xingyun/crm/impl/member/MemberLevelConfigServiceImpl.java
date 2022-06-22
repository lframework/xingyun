package com.lframework.xingyun.crm.impl.member;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.crm.entity.MemberLevelConfig;
import com.lframework.xingyun.crm.enums.DownGradeCycle;
import com.lframework.xingyun.crm.mappers.MemberLevelConfigMapper;
import com.lframework.xingyun.crm.service.member.IMemberLevelConfigService;
import com.lframework.xingyun.crm.vo.member.level.config.UpdateMemberLevelConfigVo;
import java.io.Serializable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberLevelConfigServiceImpl extends
    BaseMpServiceImpl<MemberLevelConfigMapper, MemberLevelConfig> implements
    IMemberLevelConfigService {

  @Cacheable(value = MemberLevelConfig.CACHE_NAME, key = "'config'", unless = "#result == null")
  @Override
  public MemberLevelConfig get() {
    return this.getOne(Wrappers.lambdaQuery(MemberLevelConfig.class));
  }

  @Transactional
  @Override
  public void update(UpdateMemberLevelConfigVo vo) {
    if (!vo.getIsDownGrade()) {
      vo.setDownGradeCycle(DownGradeCycle.DAY.getCode());
      vo.setDownGradeExp(0);
    } else {
      if (vo.getDownGradeCycle() == null) {
        throw new DefaultClientException("降级周期不能为空！");
      }

      if (vo.getDownGradeExp() == null) {
        throw new DefaultClientException("每次降级的经验值不能为空！");
      }
    }

    Wrapper<MemberLevelConfig> updateWrapper = Wrappers.lambdaUpdate(MemberLevelConfig.class)
        .set(MemberLevelConfig::getExp, vo.getExp())
        .set(MemberLevelConfig::getDownGradeCycle,
            EnumUtil.getByCode(DownGradeCycle.class, vo.getDownGradeCycle()))
        .set(MemberLevelConfig::getIsDownGrade, vo.getIsDownGrade())
        .set(MemberLevelConfig::getDownGradeExp, vo.getDownGradeExp());

    this.update(updateWrapper);
  }

  @CacheEvict(value = MemberLevelConfig.CACHE_NAME, key = "'config'")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
