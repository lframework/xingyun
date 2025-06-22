package com.lframework.xingyun.comp.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.comp.entity.OnlineExcel;
import com.lframework.xingyun.comp.vo.sw.excel.QueryOnlineExcelVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 在线Excel Mapper 接口
 * </p>
 */
public interface OnlineExcelMapper extends BaseMapper<OnlineExcel> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<OnlineExcel> query(@Param("vo") QueryOnlineExcelVo vo, @Param("createById") String createById);
}
