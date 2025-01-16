package com.lframework.xingyun.basedata.impl.supplier;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.xingyun.core.utils.OpLogUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.enums.ManageType;
import com.lframework.xingyun.basedata.enums.SettleType;
import com.lframework.xingyun.basedata.mappers.SupplierMapper;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.basedata.vo.supplier.CreateSupplierVo;
import com.lframework.xingyun.basedata.vo.supplier.QuerySupplierSelectorVo;
import com.lframework.xingyun.basedata.vo.supplier.QuerySupplierVo;
import com.lframework.xingyun.basedata.vo.supplier.UpdateSupplierVo;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.DicCityService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplierServiceImpl extends BaseMpServiceImpl<SupplierMapper, Supplier> implements
    SupplierService {

    @Autowired
    private DicCityService dicCityService;

    @Override
    public PageResult<Supplier> query(Integer pageIndex, Integer pageSize, QuerySupplierVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<Supplier> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<Supplier> query(QuerySupplierVo vo) {

        return getBaseMapper().query(vo);
    }

    @Cacheable(value = Supplier.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
    @Override
    public Supplier findById(String id) {

        return getBaseMapper().selectById(id);
    }

    @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "停用供应商，ID：{}", params = "#ids", loopFormat = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchUnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        Wrapper<Supplier> updateWrapper = Wrappers.lambdaUpdate(Supplier.class)
                .set(Supplier::getAvailable, Boolean.FALSE).in(Supplier::getId, ids);
        getBaseMapper().update(updateWrapper);
    }

    @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "启用供应商，ID：{}", params = "#ids", loopFormat = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchEnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        Wrapper<Supplier> updateWrapper = Wrappers.lambdaUpdate(Supplier.class)
                .set(Supplier::getAvailable, Boolean.TRUE).in(Supplier::getId, ids);
        getBaseMapper().update(updateWrapper);
    }

    @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "新增供应商，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(CreateSupplierVo vo) {

        Wrapper<Supplier> checkWrapper = Wrappers.lambdaQuery(Supplier.class).eq(Supplier::getCode, vo.getCode());
        if (getBaseMapper().selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        Supplier data = new Supplier();
        data.setId(IdUtil.getId());
        data.setCode(vo.getCode());
        data.setName(vo.getName());
        if (!StringUtil.isBlank(vo.getMnemonicCode())) {
            data.setMnemonicCode(vo.getMnemonicCode());
        }
        if (!StringUtil.isBlank(vo.getContact())) {
            data.setContact(vo.getContact());
        }
        if (!StringUtil.isBlank(vo.getTelephone())) {
            data.setTelephone(vo.getTelephone());
        }
        if (!StringUtil.isBlank(vo.getEmail())) {
            data.setEmail(vo.getEmail());
        }
        if (!StringUtil.isBlank(vo.getZipCode())) {
            data.setZipCode(vo.getZipCode());
        }
        if (!StringUtil.isBlank(vo.getFax())) {
            data.setFax(vo.getFax());
        }
        if (!StringUtil.isBlank(vo.getCityId())) {
            DicCityDto city = dicCityService.findById(vo.getCityId());
            if (!ObjectUtil.isNull(city)) {
                data.setCityId(vo.getCityId());
            }
        }
        if (!StringUtil.isBlank(vo.getAddress())) {
            data.setAddress(vo.getAddress());
        }
        if (vo.getDeliveryCycle() != null) {
            data.setDeliveryCycle(vo.getDeliveryCycle());
        }
        data.setManageType(EnumUtil.getByCode(ManageType.class, vo.getManageType()));

        data.setSettleType(EnumUtil.getByCode(SettleType.class, vo.getSettleType()));

        if (!StringUtil.isBlank(vo.getCreditCode())) {
            data.setCreditCode(vo.getCreditCode());
        }
        if (!StringUtil.isBlank(vo.getTaxIdentifyNo())) {
            data.setTaxIdentifyNo(vo.getTaxIdentifyNo());
        }
        if (!StringUtil.isBlank(vo.getBankName())) {
            data.setBankName(vo.getBankName());
        }
        if (!StringUtil.isBlank(vo.getAccountName())) {
            data.setAccountName(vo.getAccountName());
        }
        if (!StringUtil.isBlank(vo.getAccountNo())) {
            data.setAccountNo(vo.getAccountNo());
        }
        data.setAvailable(Boolean.TRUE);
        data.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

        getBaseMapper().insert(data);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        return data.getId();
    }

    @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "修改供应商，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UpdateSupplierVo vo) {

        Supplier data = getBaseMapper().selectById(vo.getId());
        if (ObjectUtil.isNull(data)) {
            throw new DefaultClientException("供应商不存在！");
        }

        Wrapper<Supplier> checkWrapper = Wrappers.lambdaQuery(Supplier.class).eq(Supplier::getCode, vo.getCode())
                .ne(Supplier::getId, vo.getId());
        if (getBaseMapper().selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        LambdaUpdateWrapper<Supplier> updateWrapper = Wrappers.lambdaUpdate(Supplier.class)
                .set(Supplier::getCode, vo.getCode()).set(Supplier::getName, vo.getName())
                .set(Supplier::getMnemonicCode, !StringUtil.isBlank(vo.getMnemonicCode()) ? vo.getMnemonicCode() : null)
                .set(Supplier::getContact, !StringUtil.isBlank(vo.getContact()) ? vo.getContact() : null)
                .set(Supplier::getTelephone, !StringUtil.isBlank(vo.getTelephone()) ? vo.getTelephone() : null)
                .set(Supplier::getEmail, !StringUtil.isBlank(vo.getEmail()) ? vo.getEmail() : null)
                .set(Supplier::getZipCode, !StringUtil.isBlank(vo.getZipCode()) ? vo.getZipCode() : null)
                .set(Supplier::getFax, !StringUtil.isBlank(vo.getFax()) ? vo.getFax() : null)
                .set(Supplier::getAddress, !StringUtil.isBlank(vo.getAddress()) ? vo.getAddress() : null)
                .set(Supplier::getDeliveryCycle, vo.getDeliveryCycle())
                .set(Supplier::getCreditCode, !StringUtil.isBlank(vo.getCreditCode()) ? vo.getCreditCode() : null)
                .set(Supplier::getTaxIdentifyNo,
                        !StringUtil.isBlank(vo.getTaxIdentifyNo()) ? vo.getTaxIdentifyNo() : null)
                .set(Supplier::getBankName, !StringUtil.isBlank(vo.getBankName()) ? vo.getBankName() : null)
                .set(Supplier::getAccountName, !StringUtil.isBlank(vo.getAccountName()) ? vo.getAccountName() : null)
                .set(Supplier::getAccountNo, !StringUtil.isBlank(vo.getAccountNo()) ? vo.getAccountNo() : null)
                .set(Supplier::getAvailable, vo.getAvailable()).set(Supplier::getDescription,
                        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
                .eq(Supplier::getId, vo.getId());

        if (!StringUtil.isBlank(vo.getCityId())) {
            DicCityDto city = dicCityService.findById(vo.getCityId());
            if (!ObjectUtil.isNull(city)) {
                updateWrapper.set(Supplier::getCityId, vo.getCityId());
            }
        } else {
            updateWrapper.set(Supplier::getCityId, null);
        }

        getBaseMapper().update(updateWrapper);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);
    }

    @Override
    public PageResult<Supplier> selector(Integer pageIndex, Integer pageSize, QuerySupplierSelectorVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<Supplier> datas = getBaseMapper().selector(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @CacheEvict(value = Supplier.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
    @Override
    public void cleanCacheByKey(Serializable key) {

    }
}
