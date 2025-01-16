package com.lframework.xingyun.template.gen.mappers;

import com.lframework.xingyun.template.gen.components.data.obj.DataObjectQueryObj;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface GenMapper {

    List<Map<String, Object>> findList(@Param("obj") DataObjectQueryObj obj);
}
