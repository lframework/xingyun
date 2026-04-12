package com.lframework.xingyun.sc.vo.stock.take.plan;

import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTakeStockPlanVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入仓库ID！")
  private String scId;


  /**
   * 盘点类别
   */
  @Schema(description = "盘点类别", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择盘点类别！")
  @TypeMismatch(message = "盘点类别格式有误！")
  @IsEnum(message = "盘点类别格式有误！", enumClass = TakeStockPlanType.class)
  private Integer takeType;

  /**
   * 业务ID
   */
  @Schema(description = "业务ID")
  private List<String> bizIds;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public void validate() {

    TakeStockPlanType takeType = EnumUtil.getByCode(TakeStockPlanType.class, this.takeType);
    if (takeType == TakeStockPlanType.CATEGORY) {
      if (CollectionUtil.isEmpty(this.bizIds)) {
        throw new InputErrorException("请选择商品分类！");
      }
    } else if (takeType == TakeStockPlanType.BRAND) {
      if (CollectionUtil.isEmpty(this.bizIds)) {
        throw new InputErrorException("请选择商品品牌！");
      }
    }
  }
}
