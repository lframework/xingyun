package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetFullDto;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetWithReturnDto;
import com.lframework.xingyun.sc.entity.ReceiveSheet;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.sc.vo.purchase.receive.QueryReceiveSheetVo;
import com.lframework.xingyun.sc.vo.purchase.receive.QueryReceiveSheetWithReturnVo;
import com.lframework.xingyun.sc.vo.purchase.receive.ReceiveSheetSelectorVo;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-09
 */
public interface ReceiveSheetMapper extends BaseMapper<ReceiveSheet> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ReceiveSheet> query(@Param("vo") QueryReceiveSheetVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<ReceiveSheet> selector(@Param("vo") ReceiveSheetSelectorVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ReceiveSheetFullDto getDetail(String id);

  /**
   * 根据ID查询（采购退货业务）
   *
   * @param id
   * @return
   */
  ReceiveSheetWithReturnDto getWithReturn(@Param("id") String id,
      @Param("requireReceive") Boolean requireReceive);

  /**
   * 查询列表（采购退货业务）
   *
   * @param vo
   * @return
   */
  List<ReceiveSheet> queryWithReturn(@Param("vo") QueryReceiveSheetWithReturnVo vo,
      @Param("multipleRelate") boolean multipleRelate,
      @Param("dataPermission") String dataPermission);

  /**
   * 查询已审核列表
   *
   * @param supplierId
   * @param startTime
   * @param endTime
   * @return
   */
  List<ReceiveSheet> getApprovedList(@Param("supplierId") String supplierId,
      @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime,
      @Param("settleStatus") SettleStatus settleStatus);
}
