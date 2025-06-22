package com.lframework.xingyun.settle.enums;

import com.lframework.starter.web.core.components.oplog.OpLogType;
import org.springframework.stereotype.Component;

@Component
public class SettleOpLogType implements OpLogType {
    @Override
    public Integer getCode() {
        return 3000;
    }
}
