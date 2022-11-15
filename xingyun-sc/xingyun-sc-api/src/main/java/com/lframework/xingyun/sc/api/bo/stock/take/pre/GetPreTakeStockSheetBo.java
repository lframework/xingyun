package com.lframework.xingyun.sc.api.bo.stock.take.pre;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.sc.facade.dto.stock.take.pre.PreTakeStockSheetFullDto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 预先盘点单 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetPreTakeStockSheetBo extends BaseBo<PreTakeStockSheetFullDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 业务单据号
   */
  @ApiModelProperty("业务单据号")
  private String code;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 仓库名称
   */
  @ApiModelProperty("仓库名称")
  private String scName;

  /**
   * 盘点状态
   */
  @ApiModelProperty("盘点状态")
  private Integer takeStatus;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 操作人
   */
  @ApiModelProperty("操作人")
  private String updateBy;

  /**
   * 操作时间
   */
  @ApiModelProperty("操作时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  /**
   * 明细
   */
  @ApiModelProperty("明细")
  private List<SheetDetailBo> details;

  public GetPreTakeStockSheetBo() {

  }

  public GetPreTakeStockSheetBo(PreTakeStockSheetFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<PreTakeStockSheetFullDto> convert(PreTakeStockSheetFullDto dto) {

    return super.convert(dto, GetPreTakeStockSheetBo::getTakeStatus);
  }

  @Override
  protected void afterInit(PreTakeStockSheetFullDto dto) {

    StoreCenterFeignClient storeCenterService = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId()).getData();

    this.scId = sc.getId();
    this.scName = sc.getName();

    this.takeStatus = dto.getTakeStatus().getCode();

    this.details = dto.getDetails().stream().map(SheetDetailBo::new).collect(Collectors.toList());
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class SheetDetailBo extends BaseBo<PreTakeStockSheetFullDto.SheetDetailDto> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    private String productId;

    /**
     * 编号
     */
    @ApiModelProperty("编号")
    private String productCode;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String productName;

    /**
     * 类目名称
     */
    @ApiModelProperty("类目名称")
    private String categoryName;

    /**
     * 品牌名称
     */
    @ApiModelProperty("品牌名称")
    private String brandName;

    /**
     * SKU
     */
    @ApiModelProperty("SKU")
    private String skuCode;

    /**
     * 外部编号
     */
    @ApiModelProperty("外部编号")
    private String externalCode;

    /**
     * 规格
     */
    @ApiModelProperty("规格")
    private String spec;

    /**
     * 单位
     */
    @ApiModelProperty("单位")
    private String unit;

    /**
     * 初盘数量
     */
    @ApiModelProperty("初盘数量")
    private Integer firstNum;

    /**
     * 复盘数量
     */
    @ApiModelProperty("复盘数量")
    private Integer secondNum;

    /**
     * 抽盘数量
     */
    @ApiModelProperty("抽盘数量")
    private Integer randNum;

    public SheetDetailBo(PreTakeStockSheetFullDto.SheetDetailDto dto) {

      super(dto);
    }

    @Override
    protected void afterInit(PreTakeStockSheetFullDto.SheetDetailDto dto) {

      ProductFeignClient productService = ApplicationUtil.getBean(ProductFeignClient.class);

      ProductDto product = productService.findById(dto.getProductId()).getData();

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.brandName = product.getPoly().getBrandName();
      this.categoryName = product.getPoly().getCategoryName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
      this.spec = product.getSpec();
      this.unit = product.getUnit();
    }
  }
}
