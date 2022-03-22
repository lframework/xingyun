package com.lframework.xingyun.sc.vo.stock.take.config;

import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTakeStockConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 库存盘点单关联盘点任务后，是否显示盘点任务中的商品数据
   */
  @TypeMismatch(message = "库存盘点单关联盘点任务后，是否显示盘点任务中的商品数据格式有误！")
  @NotNull(message = "请选择库存盘点单关联盘点任务后，是否显示盘点任务中的商品数据！")
  private Boolean showProduct;

  /**
   * 库存盘点单是否显示盘点任务创建时商品的系统库存数量
   */
  @TypeMismatch(message = "库存盘点单是否显示盘点任务创建时商品的系统库存数量格式有误！")
  @NotNull(message = "请选择库存盘点单是否显示盘点任务创建时商品的系统库存数量！")
  private Boolean showStock;

  /**
   * 盘点差异生成时是否自动调整盘点任务中商品的系统库存数量
   */
  @TypeMismatch(message = "盘点差异生成时是否自动调整盘点任务中商品的系统库存数量格式有误！")
  @NotNull(message = "请选择盘点差异生成时是否自动调整盘点任务中商品的系统库存数量！")
  private Boolean autoChangeStock;

  /**
   * 盘点差异单中的盘点数量是否允许手动修改
   */
  @TypeMismatch(message = "盘点差异单中的盘点数量是否允许手动修改格式有误！")
  @NotNull(message = "请选择盘点差异单中的盘点数量是否允许手动修改！")
  private Boolean allowChangeNum;

  /**
   * 盘点任务创建后多少小时内未完成，则自动作废
   */
  @TypeMismatch(message = "盘点任务自动作废时间格式有误！")
  @NotNull(message = "请输入盘点任务自动作废时间！")
  @Min(value = 1, message = "盘点任务自动作废时间必须大于0！")
  private Integer cancelHours;

}
