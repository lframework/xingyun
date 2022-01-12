package com.lframework.xingyun.sc.impl.retail;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.NumberUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDetailDto;
import com.lframework.xingyun.sc.entity.RetailOutSheetDetail;
import com.lframework.xingyun.sc.mappers.RetailOutSheetDetailMapper;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RetailOutSheetDetailServiceImpl implements IRetailOutSheetDetailService {

    @Autowired
    private RetailOutSheetDetailMapper retailOutSheetDetailMapper;

    @Autowired
    private IProductService productService;

    @Override
    public RetailOutSheetDetailDto getById(String id) {

        return retailOutSheetDetailMapper.getById(id);
    }

    @Override
    public List<RetailOutSheetDetailDto> getBySheetId(String sheetId) {

        return retailOutSheetDetailMapper.getBySheetId(sheetId);
    }

    @Transactional
    @Override
    public void addReturnNum(String id, Integer num) {

        Assert.notBlank(id);
        Assert.greaterThanZero(num);

        RetailOutSheetDetail detail = retailOutSheetDetailMapper.selectById(id);

        Integer remainNum = NumberUtil.sub(detail.getOrderNum(), detail.getReturnNum()).intValue();
        if (NumberUtil.lt(remainNum, num)) {
            ProductDto product = productService.getById(detail.getProductId());

            throw new DefaultClientException(
                    "（" + product.getCode() + "）" + product.getName() + "剩余退货数量为" + remainNum + "个，本次退货数量不允许大于"
                            + remainNum + "个！");
        }

        if (retailOutSheetDetailMapper.addReturnNum(detail.getId(), num) != 1) {
            ProductDto product = productService.getById(detail.getProductId());

            throw new DefaultClientException("（" + product.getCode() + "）" + product.getName() + "剩余退货数量不足，不允许继续退货！");
        }
    }

    @Transactional
    @Override
    public void subReturnNum(String id, Integer num) {

        Assert.notBlank(id);
        Assert.greaterThanZero(num);

        RetailOutSheetDetail orderDetail = retailOutSheetDetailMapper.selectById(id);

        if (NumberUtil.lt(orderDetail.getReturnNum(), num)) {
            ProductDto product = productService.getById(orderDetail.getProductId());

            throw new DefaultClientException(
                    "（" + product.getCode() + "）" + product.getName() + "已退货数量为" + orderDetail.getReturnNum()
                            + "个，本次取消退货数量不允许大于" + orderDetail.getReturnNum() + "个！");
        }

        if (retailOutSheetDetailMapper.subReturnNum(orderDetail.getId(), num) != 1) {
            ProductDto product = productService.getById(orderDetail.getProductId());

            throw new DefaultClientException("（" + product.getCode() + "）" + product.getName() + "已退货数量不足，不允许取消退货！");
        }
    }
}
