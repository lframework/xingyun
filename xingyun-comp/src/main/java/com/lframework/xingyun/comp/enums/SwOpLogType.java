package com.lframework.xingyun.comp.enums;

import com.lframework.starter.web.core.components.oplog.OpLogType;
import org.springframework.stereotype.Component;

@Component
public class SwOpLogType implements OpLogType {
    @Override
    public Integer getCode() {
        return 4000;
    }
}
