package com.study.domain.chart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChartMapper {
    
    // 방문횟수
    public List<StatisticsGenderBarVO> getListLine(@Param("param") ChartRequestVO request) throws Exception;
    // 방문자수
    public List<StatisticsGenderBarVO> getListLine2(@Param("param") ChartRequestVO request) throws Exception;








}
