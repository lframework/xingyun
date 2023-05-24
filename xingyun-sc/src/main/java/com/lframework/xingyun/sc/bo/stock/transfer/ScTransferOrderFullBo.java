package com.lframework.xingyun.sc.bo.stock.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferOrderFullDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.enums.ScTransferOrderStatus;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 仓库调拨单 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScTransferOrderFullBo extends BaseBo<ScTransferOrderFullDto> {

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
   * 转出仓库ID
   */
  @ApiModelProperty("转出仓库ID")
  private String sourceScId;

  /**
   * 转出仓库名称
   */
  @ApiModelProperty("转出仓库名称")
  private String sourceScName;

  /**
   * 转入仓库ID
   */
  @ApiModelProperty("转入仓库ID")
  private String targetScId;

  /**
   * 转入仓库名称
   */
  @ApiModelProperty("转入仓库名称")
  private String targetScName;

  /**
   * 调拨数量
   */
  @ApiModelProperty("调拨数量")
  private Integer totalNum;

  /**
   * 调拨成本金额
   */
  @ApiModelProperty("调拨成本金额")
  private BigDecimal totalAmount;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Integer status;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 修改人
   */
  @ApiModelProperty("修改人")
  private String updateBy;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  /**
   * 审核人
   */
  @ApiModelProperty("审核人")
  private String approveBy;

  /**
   * 审核时间
   */
  @ApiModelProperty("审核时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime approveTime;

  /**
   * 拒绝原因
   */
  @ApiModelProperty("拒绝原因")
  private String refuseReason;

  /**
   * 明细
   */
  @ApiModelProperty("明细")
  private List<DetailBo> details;

  public ScTransferOrderFullBo(ScTransferOrderFullDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<ScTransferOrderFullDto> convert(ScTransferOrderFullDto dto) {

    return super.convert(dto, ScTransferOrderFullBo::getStatus);
  }

  @Override
  protected void afterInit(ScTransferOrderFullDto dto) {

    this.status = dto.getStatus().getCode();

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sourceSc = storeCenterService.findById(dto.getSourceScId());
    this.sourceScName = sourceSc.getName();

    StoreCenter targetSc = storeCenterService.findById(dto.getTargetScId());
    this.targetScName = targetSc.getName();

    UserService userService = ApplicationUtil.getBean(UserService.class);
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    this.details = dto.getDetails().stream().map(t -> new DetailBo(t, this.sourceScId, this.status))
        .collect(Collectors.toList());
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class DetailBo extends BaseBo<ScTransferOrderFullDto.DetailDto> {

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
     * 调拨数量
     */
    @ApiModelProperty("调拨数量")
    private Integer transferNum;

    /**
     * 当前库存数量
     */
    @ApiModelProperty("当前库存数量")
    private Integer curStockNum;

    /**
     * 已收货数量
     */
    @ApiModelProperty("已收货数量")
    private Integer receiveNum;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    /**
     * 仓库ID
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String scId;

    /**
     * 状态
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Integer status;

    public DetailBo(ScTransferOrderFullDto.DetailDto dto, String scId, Integer status) {

      this.scId = scId;
      this.status = status;

      this.init(dto);
    }

    @Override
    public <A> BaseBo<ScTransferOrderFullDto.DetailDto> convert(
        ScTransferOrderFullDto.DetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(ScTransferOrderFullDto.DetailDto dto) {

      ProductService productService = ApplicationUtil.getBean(ProductService.class);

      Product product = productService.findById(dto.getProductId());

      ProductCategoryService productCategoryService = ApplicationUtil.getBean(
          ProductCategoryService.class);
      ProductCategory productCategory = productCategoryService.findById(product.getCategoryId());

      ProductBrandService productBrandService = ApplicationUtil.getBean(ProductBrandService.class);
      ProductBrand productBrand = productBrandService.findById(product.getBrandId());

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.brandName = productBrand.getName();
      this.categoryName = productCategory.getName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
      this.spec = product.getSpec();
      this.unit = product.getUnit();

      if (EnumUtil.getByCode(ScTransferOrderStatus.class, this.status)
          != ScTransferOrderStatus.APPROVE_PASS) {
        ProductStockService productStockService = ApplicationUtil.getBean(
            ProductStockService.class);
        ProductStock productStock = productStockService.getByProductIdAndScId(dto.getProductId(),
            this.scId);
        this.curStockNum = productStock == null ? 0 : productStock.getStockNum();
      }
    }
  }
}
