package com.lframework.xingyun.basedata.enums;

import com.lframework.starter.web.core.components.oplog.OpLogType;
import org.springframework.stereotype.Component;

@Component
public class BaseDataOpLogType implements OpLogType {
    @Override
    public Integer getCode() {
        return 1000;
    }
}
