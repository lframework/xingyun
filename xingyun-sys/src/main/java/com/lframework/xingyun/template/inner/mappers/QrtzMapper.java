package com.lframework.xingyun.template.inner.mappers;

import com.lframework.xingyun.template.inner.dto.qrtz.QrtzDto;
import com.lframework.xingyun.template.inner.vo.qrtz.QueryQrtzVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zmj
 * @since 2022/8/20
 */
public interface QrtzMapper {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<QrtzDto> query(@Param("vo") QueryQrtzVo vo);

  /**
   * 根据ID查询
   *
   * @param name
   * @param group
   * @return
   */
  QrtzDto findById(@Param("name") String name, @Param("group") String group);
}
