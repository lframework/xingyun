package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.LogisticsCompany;
import com.lframework.xingyun.basedata.vo.logistics.company.QueryLogisticsCompanySelectorVo;
import com.lframework.xingyun.basedata.vo.logistics.company.QueryLogisticsCompanyVo;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 物流公司 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2023-06-02
 */
public interface LogisticsCompanyMapper extends BaseMapper<LogisticsCompany> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", autoParse = true),
      @Sort(value = "name", autoParse = true),
      @Sort(value = "createTime", autoParse = true),
      @Sort(value = "updateTime", autoParse = true),
  })
  List<LogisticsCompany> query(@Param("vo") QueryLogisticsCompanyVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<LogisticsCompany> selector(@Param("vo") QueryLogisticsCompanySelectorVo vo);
}
