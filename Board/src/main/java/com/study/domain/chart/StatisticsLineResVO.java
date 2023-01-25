package com.study.domain.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor 	// 어노테이션 확인
@AllArgsConstructor // 어노테이션 확인
public class StatisticsLineResVO {
    
    private String[] labels;
	private Integer[] men;
	private Integer[] women;
	private Integer[] total;


	/*  
	public StatisticsLineResVO(){

	}
	*/

}
