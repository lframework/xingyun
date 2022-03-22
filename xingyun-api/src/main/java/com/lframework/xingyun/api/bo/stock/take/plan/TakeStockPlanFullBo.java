package com.lframework.xingyun.api.bo.stock.take.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanFullDto;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TakeStockPlanFullBo extends BaseBo<TakeStockPlanFullDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 业务单据号
   */
  private String code;

  /**
   * 仓库名称
   */
  private String scName;

  /**
   * 盘点类别
   */
  private Integer takeType;

  /**
   * 备注
   */
  private String description;

  /**
   * 盘点状态
   */
  private Integer takeStatus;

  /**
   * 创建人
   */
  private String createBy;

  /**
   * 创建时间
   */
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改人
   */
  private String updateBy;

  /**
   * 修改时间
   */
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  /**
   * 明细
   */
  private List<DetailBo> details;

  public TakeStockPlanFullBo(TakeStockPlanFullDto dto) {
    super(dto);
  }

  @Override
  public <A> BaseBo<TakeStockPlanFullDto> convert(TakeStockPlanFullDto dto) {
    return super.convert(dto, TakeStockPlanFullBo::getTakeType, TakeStockPlanFullBo::getTakeStatus,
        TakeStockPlanFullBo::getDetails);
  }

  @Override
  protected void afterInit(TakeStockPlanFullDto dto) {
    this.takeType = dto.getTakeType().getCode();
    this.takeStatus = dto.getTakeStatus().getCode();

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scName = sc.getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    this.createBy = userService.getById(dto.getCreateBy()).getName();
    this.updateBy = userService.getById(dto.getUpdateBy()).getName();

    this.details = CollectionUtil.isEmpty(dto.getDetails()) ? Collections.EMPTY_LIST
        : dto.getDetails().stream().map(t -> new DetailBo(t, this.id)).collect(Collectors.toList());
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class DetailBo extends BaseBo<TakeStockPlanFullDto.DetailDto> {

    /**
     * 明细ID
     */
    private String id;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品编号
     */
    private String productCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * SKU
     */
    private String skuCode;

    /**
     * 外部编号
     */
    private String externalCode;

    /**
     * 规格
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 库存数量
     */
    private Integer stockNum;

    /**
     * 原盘点数量（通过盘点单统计）
     */
    private Integer oriTakeNum;

    /**
     * 修改后的盘点数量
     */
    private Integer takeNum;

    /**
     * 出项数量
     */
    private Integer totalOutNum;

    /**
     * 进项数量
     */
    private Integer totalInNum;

    /**
     * 备注
     */
    private String description;

    /**
     * 差异数量
     */
    private Integer diffNum;

    /**
     * 盘点任务ID
     */
    @JsonIgnore
    private String planId;

    public DetailBo(TakeStockPlanFullDto.DetailDto dto, String planId) {
      this.planId = planId;

      if (dto != null) {
        this.convert(dto);

        this.afterInit(dto);
      }
    }

    @Override
    public <A> BaseBo<TakeStockPlanFullDto.DetailDto> convert(TakeStockPlanFullDto.DetailDto dto) {
      return super.convert(dto);
    }

    @Override
    protected void afterInit(TakeStockPlanFullDto.DetailDto dto) {
      IProductService productService = ApplicationUtil.getBean(IProductService.class);
      ProductDto product = productService.getById(dto.getProductId());

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.brandName = product.getPoly().getBrandName();
      this.categoryName = product.getPoly().getCategoryName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
      this.spec = product.getSpec();
      this.unit = product.getUnit();

      if (this.oriTakeNum != null || this.takeNum != null) {

        if (this.takeNum != null) {
          this.diffNum = this.takeNum - this.stockNum;
        } else {
          this.diffNum = this.oriTakeNum - this.stockNum;
        }
      }
    }
  }
}
