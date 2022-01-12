package com.lframework.xingyun.sc.impl.sale;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.NumberUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDetailDto;
import com.lframework.xingyun.sc.entity.SaleOutSheetDetail;
import com.lframework.xingyun.sc.mappers.SaleOutSheetDetailMapper;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleOutSheetDetailServiceImpl implements ISaleOutSheetDetailService {

    @Autowired
    private SaleOutSheetDetailMapper saleOutSheetDetailMapper;

    @Autowired
    private IProductService productService;

    @Override
    public SaleOutSheetDetailDto getById(String id) {

        return saleOutSheetDetailMapper.getById(id);
    }

    @Override
    public List<SaleOutSheetDetailDto> getBySheetId(String sheetId) {

        return saleOutSheetDetailMapper.getBySheetId(sheetId);
    }

    @Transactional
    @Override
    public void addReturnNum(String id, Integer num) {

        Assert.notBlank(id);
        Assert.greaterThanZero(num);

        SaleOutSheetDetail detail = saleOutSheetDetailMapper.selectById(id);

        Integer remainNum = NumberUtil.sub(detail.getOrderNum(), detail.getReturnNum()).intValue();
        if (NumberUtil.lt(remainNum, num)) {
            ProductDto product = productService.getById(detail.getProductId());

            throw new DefaultClientException(
                    "（" + product.getCode() + "）" + product.getName() + "剩余退货数量为" + remainNum + "个，本次退货数量不允许大于"
                            + remainNum + "个！");
        }

        if (saleOutSheetDetailMapper.addReturnNum(detail.getId(), num) != 1) {
            ProductDto product = productService.getById(detail.getProductId());

            throw new DefaultClientException("（" + product.getCode() + "）" + product.getName() + "剩余退货数量不足，不允许继续退货！");
        }
    }

    @Transactional
    @Override
    public void subReturnNum(String id, Integer num) {

        Assert.notBlank(id);
        Assert.greaterThanZero(num);

        SaleOutSheetDetail orderDetail = saleOutSheetDetailMapper.selectById(id);

        if (NumberUtil.lt(orderDetail.getReturnNum(), num)) {
            ProductDto product = productService.getById(orderDetail.getProductId());

            throw new DefaultClientException(
                    "（" + product.getCode() + "）" + product.getName() + "已退货数量为" + orderDetail.getReturnNum()
                            + "个，本次取消退货数量不允许大于" + orderDetail.getReturnNum() + "个！");
        }

        if (saleOutSheetDetailMapper.subReturnNum(orderDetail.getId(), num) != 1) {
            ProductDto product = productService.getById(orderDetail.getProductId());

            throw new DefaultClientException("（" + product.getCode() + "）" + product.getName() + "已退货数量不足，不允许取消退货！");
        }
    }
}
