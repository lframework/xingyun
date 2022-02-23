package com.lframework.xingyun.sc.service.stock.take;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetFullDto;
import com.lframework.xingyun.sc.vo.stock.take.pre.CreatePreTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.QueryPreTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.UpdatePreTakeStockSheetVo;

import java.util.List;

/**
 * 预先盘点单 Service
 * @author zmj
 */
public interface IPreTakeStockSheetService extends BaseService {

    /**
     * 查询列表
     * @return
     */
    PageResult<PreTakeStockSheetDto> query(Integer pageIndex, Integer pageSize, QueryPreTakeStockSheetVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<PreTakeStockSheetDto> query(QueryPreTakeStockSheetVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    PreTakeStockSheetDto getById(String id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    PreTakeStockSheetFullDto getDetail(String id);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreatePreTakeStockSheetVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdatePreTakeStockSheetVo vo);

}
