package com.lframework.xingyun.common.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.common.facade.entity.FileBox;
import com.lframework.xingyun.common.facade.vo.sw.filebox.QueryFileBoxVo;
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
  List<FileBox> query(@Param("vo") QueryFileBoxVo vo, @Param("createBy") String createBy);
}
