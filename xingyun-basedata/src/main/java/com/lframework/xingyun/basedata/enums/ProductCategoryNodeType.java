package com.lframework.xingyun.basedata.enums;

import com.lframework.starter.security.enums.system.NodeType;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public final class ProductCategoryNodeType implements NodeType, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Integer getCode() {

        return 2;
    }

    @Override
    public String getDesc() {

        return "商品类目";
    }
}
