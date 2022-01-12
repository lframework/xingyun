package com.lframework.xingyun.chart.impl;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.*;
import com.lframework.starter.redis.components.RedisHandler;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.chart.dto.OrderChartSameMonthDto;
import com.lframework.xingyun.chart.dto.OrderChartSumDto;
import com.lframework.xingyun.chart.dto.OrderChartTodayDto;
import com.lframework.xingyun.chart.entity.OrderChart;
import com.lframework.xingyun.chart.enums.OrderChartBizType;
import com.lframework.xingyun.chart.mappers.OrderChartMapper;
import com.lframework.xingyun.chart.service.IOrderChartService;
import com.lframework.xingyun.chart.vo.CreateOrderChartVo;
import com.lframework.xingyun.chart.vo.GetOrderChartVo;
import com.lframework.xingyun.chart.vo.QueryOrderChartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderChartServiceImpl implements IOrderChartService {

    @Autowired
    private OrderChartMapper orderChartMapper;

    @Autowired
    private RedisHandler redisHandler;

    @Transactional
    @Override
    public String create(CreateOrderChartVo vo) {

        if (vo.getCreateTime() == null) {
            vo.setCreateTime(LocalDateTime.now());
        }

        OrderChart record = new OrderChart();
        record.setId(IdUtil.getId());
        record.setTotalAmount(vo.getTotalAmount());
        record.setCreateTime(vo.getCreateTime());
        record.setBizType(EnumUtil.getByCode(OrderChartBizType.class, vo.getBizType()));
        record.setCreateDate(DateUtil.formatDate(vo.getCreateTime().toLocalDate()));
        record.setCreateHour(DateUtil.format(vo.getCreateTime(), StringPool.DATE_TIME_HOUR_PATTER));

        orderChartMapper.insert(record);

        this.incrOrderChart(record.getBizType(), vo.getCreateTime().toLocalDate(), vo.getTotalAmount());

        return record.getId();
    }

    @Override
    public OrderChartSumDto getTodayChartSum(GetOrderChartVo vo) {

        LocalDate now = LocalDate.now();

        String key = this.getTodayKey(EnumUtil.getByCode(OrderChartBizType.class, vo.getBizType()), now);
        Number totalNum = (Number) redisHandler.get(StringUtil.format("{}_{}", key, "totalNum"));
        Number totalAmount = (Number) redisHandler.get(StringUtil.format("{}_{}", key, "totalAmount"));

        OrderChartSumDto result = new OrderChartSumDto();
        result.setTotalAmount(totalAmount == null ? BigDecimal.ZERO : NumberUtil.getNumber(totalAmount, 2));
        result.setTotalNum(totalNum == null ? 0L : NumberUtil.getNumber(totalNum, 0).longValue());

        return result;
    }

    @Override
    public OrderChartSumDto getSameMonthChartSum(GetOrderChartVo vo) {

        LocalDate startDate = LocalDate.now().withDayOfMonth(1);

        String key = this.getSameMonthKey(EnumUtil.getByCode(OrderChartBizType.class, vo.getBizType()), startDate);
        Number totalNum = (Number) redisHandler.get(StringUtil.format("{}_{}", key, "totalNum"));
        Number totalAmount = (Number) redisHandler.get(StringUtil.format("{}_{}", key, "totalAmount"));

        OrderChartSumDto result = new OrderChartSumDto();
        result.setTotalAmount(totalAmount == null ? BigDecimal.ZERO : NumberUtil.getNumber(totalAmount, 2));
        result.setTotalNum(totalNum == null ? 0L : NumberUtil.getNumber(totalNum, 0).longValue());

        return result;
    }

    @Override
    public List<OrderChartTodayDto> queryTodayChart(QueryOrderChartVo vo) {

        LocalDate now = LocalDate.now();

        List<OrderChartTodayDto> results = orderChartMapper
                .queryToday(EnumUtil.getByCode(OrderChartBizType.class, vo.getBizType()), DateUtil.toLocalDateTime(now),
                        DateUtil.toLocalDateTimeMax(now));

        int offset = 24;
        List<OrderChartTodayDto> newResults = new ArrayList<>(offset);

        for (int i = 0; i < offset; i++) {
            LocalTime localTime = LocalTime.of(i, 0, 0);
            String filterDateStr = DateUtil
                    .formatDateTime(LocalDateTime.of(now, localTime), StringPool.DATE_TIME_HOUR_PATTER);
            OrderChartTodayDto result = results.stream().filter(t -> t.getCreateHour().equals(filterDateStr))
                    .findFirst().orElse(null);
            if (result == null) {
                result = new OrderChartTodayDto();
                result.setCreateHour(filterDateStr);
                result.setTotalAmount(BigDecimal.ZERO);
                result.setTotalNum(0);
            }

            newResults.add(result);
        }

        return newResults;
    }

    @Override
    public List<OrderChartSameMonthDto> querySameMonthChart(QueryOrderChartVo vo) {

        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<OrderChartSameMonthDto> results = orderChartMapper
                .querySameMonth(EnumUtil.getByCode(OrderChartBizType.class, vo.getBizType()),
                        DateUtil.toLocalDateTime(startDate), DateUtil.toLocalDateTimeMax(endDate));

        LocalDate lastDate = startDate.plusMonths(1).minusDays(1);
        int offset = lastDate.getDayOfMonth() - startDate.getDayOfMonth() + 1;
        List<OrderChartSameMonthDto> newResults = new ArrayList<>(offset);

        for (int i = 0; i < offset; i++) {
            String filterDateStr = DateUtil.formatDate(startDate.plusDays(i));
            OrderChartSameMonthDto result = results.stream().filter(t -> t.getCreateDate().equals(filterDateStr))
                    .findFirst().orElse(null);
            if (result == null) {
                result = new OrderChartSameMonthDto();
                result.setCreateDate(filterDateStr);
                result.setTotalAmount(BigDecimal.ZERO);
                result.setTotalNum(0);
            }

            newResults.add(result);
        }

        return newResults;
    }

    private void incrOrderChart(OrderChartBizType bizType, LocalDate currentDate, BigDecimal totalAmount) {

        Assert.notNull(bizType);
        Assert.notNull(currentDate);
        Assert.notNull(totalAmount);

        String todayKey = this.getTodayKey(bizType, currentDate);

        redisHandler.incr(StringUtil.format("{}_{}", todayKey, "totalNum"), 1);
        redisHandler.expire(StringUtil.format("{}_{}", todayKey, "totalNum"), this.getTodayKeyExpire());

        redisHandler.incr(StringUtil.format("{}_{}", todayKey, "totalAmount"), totalAmount.doubleValue());
        redisHandler.expire(StringUtil.format("{}_{}", todayKey, "totalAmount"), this.getTodayKeyExpire());

        String sameMonthKey = this.getSameMonthKey(bizType, currentDate);

        redisHandler.incr(StringUtil.format("{}_{}", sameMonthKey, "totalNum"), 1);
        redisHandler.expire(StringUtil.format("{}_{}", sameMonthKey, "totalNum"), this.getSameMonthKeyExpire());

        redisHandler.incr(StringUtil.format("{}_{}", sameMonthKey, "totalAmount"), totalAmount.doubleValue());
        redisHandler.expire(StringUtil.format("{}_{}", sameMonthKey, "totalAmount"), this.getSameMonthKeyExpire());
    }

    private String getTodayKey(OrderChartBizType bizType, LocalDate currentDate) {

        Assert.notNull(bizType);
        Assert.notNull(currentDate);

        return StringUtil.format("{}_{}_today_{}", OrderChartSumDto.CACHE_NAME, bizType.getCode(),
                DateUtil.formatDate(currentDate));
    }

    private String getSameMonthKey(OrderChartBizType bizType, LocalDate currentDate) {

        Assert.notNull(bizType);
        Assert.notNull(currentDate);

        return StringUtil.format("{}_{}_sameMonth_{}", OrderChartSumDto.CACHE_NAME, bizType.getCode(),
                DateUtil.formatDate(currentDate.withDayOfMonth(1)));
    }

    private long getTodayKeyExpire() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime maxDateTime = DateUtil.toLocalDateTimeMax(now.toLocalDate());

        return DateUtil.getTime(maxDateTime) - DateUtil.getTime(now);
    }

    private long getSameMonthKeyExpire() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime maxDateTime = DateUtil
                .toLocalDateTimeMax(now.withDayOfMonth(1).plusMonths(1).minusDays(1).toLocalDate());

        return DateUtil.getTime(maxDateTime) - DateUtil.getTime(now);
    }
}
