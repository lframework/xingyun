package com.lframework.xingyun.core.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.core.entity.FileBox;
import com.lframework.xingyun.core.vo.sw.filebox.QueryFileBoxVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 收件收纳箱 Mapper 接口
 * </p>
 */
public interface FileBoxMapper extends BaseMapper<FileBox> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<FileBox> query(@Param("vo") QueryFileBoxVo vo, @Param("createById") String createById);
}
