package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.logistics.LogisticsSheetBizOrderDto;
import com.lframework.xingyun.sc.dto.logistics.LogisticsSheetFullDto;
import com.lframework.xingyun.sc.entity.LogisticsSheet;
import com.lframework.xingyun.sc.vo.logistics.LogisticsSheetSelectorVo;
import com.lframework.xingyun.sc.vo.logistics.QueryLogisticsSheetBizOrderVo;
import com.lframework.xingyun.sc.vo.logistics.QueryLogisticsSheetVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 物流单 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface LogisticsSheetMapper extends BaseMapper<LogisticsSheet> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<LogisticsSheet> query(@Param("vo") QueryLogisticsSheetVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  LogisticsSheetFullDto getDetail(String id);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<LogisticsSheet> selector(@Param("vo") LogisticsSheetSelectorVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 查询业务单据列表
   *
   * @param vo
   * @param dataPermission
   * @return
   */
  List<LogisticsSheetBizOrderDto> queryBizOrder(@Param("vo") QueryLogisticsSheetBizOrderVo vo,
      @Param("dataPermission") String dataPermission);
}
