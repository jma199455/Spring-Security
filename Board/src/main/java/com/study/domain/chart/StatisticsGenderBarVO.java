package com.study.domain.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StatisticsGenderBarVO {

    private String label;
    private int men;
    private int women;
    private int total;

}
