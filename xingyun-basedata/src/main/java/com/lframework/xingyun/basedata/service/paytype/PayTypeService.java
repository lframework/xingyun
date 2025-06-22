package com.lframework.xingyun.basedata.service.paytype;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.PayType;
import com.lframework.xingyun.basedata.vo.paytype.CreatePayTypeVo;
import com.lframework.xingyun.basedata.vo.paytype.PayTypeSelectorVo;
import com.lframework.xingyun.basedata.vo.paytype.QueryPayTypeVo;
import com.lframework.xingyun.basedata.vo.paytype.UpdatePayTypeVo;
import java.util.List;

public interface PayTypeService extends BaseMpService<PayType> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<PayType> query(Integer pageIndex, Integer pageSize, QueryPayTypeVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<PayType> query(QueryPayTypeVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<PayType> selector(Integer pageIndex, Integer pageSize,
      PayTypeSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PayType findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreatePayTypeVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdatePayTypeVo vo);
}
