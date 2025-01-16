package com.lframework.xingyun.core.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.core.entity.OpLogs;
import com.lframework.xingyun.core.vo.QueryOpLogsVo;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 操作日志 Mapper
 * </p>
 *
 * @author zmj
 */
public interface OpLogsMapper extends BaseMapper<OpLogs> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<OpLogs> query(@Param("vo") QueryOpLogsVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  OpLogs findById(String id);

  /**
   * 根据截止时间删除日志
   *
   * @param endTime
   */
  void clearLogs(@Param("endTime") LocalDateTime endTime);
}
