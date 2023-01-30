package com.study.domain.chart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ChartService {

    @Autowired
    private ChartMapper chartMapper;

    public StatisticsLineResVO getLine(ChartRequestVO request, String type) throws Exception {

        /*  
        //Security 사용자 정보 가져오기 지금 사용 X
        String username = LoginUtils.getDetails().getUsername(); // 계정 고유한 값
        System.out.println(username);
        Collection<? extends GrantedAuthority> authority = LoginUtils.getDetails().getAuthorities(); // 권한
        System.out.println(authority);
        //Security 사용자 정보 가져오기 End
        */


        
        //StatisticsLineResVO result = new StatisticsLineResVO(); //setter 로 값넣고 리턴할 때 사용

        // 전일 ~ 1주일 전 까지 날짜
        List<String> labels = new ArrayList<>();
        
        // try 안에 선언하면 return 못함
		Integer[] men = new Integer[labels.size()]; // 일단 0으로 선언 초기화는 아직
		Integer[] women = new Integer[labels.size()];
		Integer[] total = new Integer[labels.size()];

        try {

            // 날짜 비교를 위해
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(sdf.parse(request.getStartDt()));
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(sdf.parse(request.getEndDt()));
            
            // 전일 보다 큰 경우 while문 탈출
            while(cal1.compareTo(cal2) != 1) {  // 1크다, 0같다, -1작다
				labels.add(sdf.format(cal1.getTime()));
				cal1.add(Calendar.DATE, 1);     // cal1 (startDt) 를 1주일 전부터 하나씩 증가시켜 endDt와 비교해야 하기 때문에!
			}
			log.debug("label : {}", labels);

            // list size만큼 배열 사이즈 선언
            men = new Integer[labels.size()];
			women = new Integer[labels.size()];
			total = new Integer[labels.size()];

            
            List<StatisticsGenderBarVO> line = new ArrayList<>();  // 쿼리 결과 담아올 리스트

            log.debug("cccccccccccccccccccccccccccc ====> " + request.getStartDt());
            log.debug("cccccccccccccccccccccccccccc2 ====> " + request.getEndDt());

            if (type.equals("L1")) {
                line = chartMapper.getListLine(request);    // 방문횟수
            } else {
                line = chartMapper.getListLine2(request);   // 방문자수
            }
             
            for(int i = 0; i < labels.size(); i++) { 
				men[i] = women[i] = total[i] = 0; // 초기화 꼭 해야함 (data값이 없는 경우 0으로 표시해야 하기 때문에!!!) 
                                                  // 초기화 하지 않으면 데이터가 있는 날짜만 표시됨
				for(StatisticsGenderBarVO vo : line) {
					if(labels.get(i).equals(vo.getLabel())) {   // vo.getLabel() : 데이터가 있는 label만 골라서 해당 값들 넣기 위한 조건
						men[i] = vo.getMen();
						women[i] = vo.getWomen();
						total[i] = vo.getTotal();
						break;
					}
				}
			}

            // return 생성자 사용하지 않고 setter 로 return 하기
            /*  
            result.setLabels(labels.stream().toArray(String[]::new));
            result.setMen(men);
            result.setWomen(women);
            result.setTotal(total);
            */

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // toArray 사용 (배열 선언과 동시에 할당) , List를 String Array로 바꿈
        /* 1. 
        String[] arr = labels.toArray(String[]::new);  String[]::new 사용  java11~ 이상	
        */
        /* 2. 
        String[] arr = labels.toArray(new String[0]);  new String[0] 사용 toArray인자로 넘어가는 배열 객체 사이즈보다 list size가 더 커서 list size로 배열 반환
        */

        /* 3.   
        String[] arr = new String[labels.size()];      String[] 사이즈 선언 후 사이즈만큼 배열 반환
        arr = labels.toArray(arr);
        */

        // 데이터 확인
        String[] temp = labels.toArray(String[]::new);  // List를 String Array로 바꿈
        System.out.println(temp);

        // 같은 의미 생성자로 return  
        return new StatisticsLineResVO(labels.toArray(String[]::new), men, women, total);
        //return new StatisticsLineResVO(labels.stream().toArray(String[]::new), men, women, total);	// Stream API 사용 labels.toArray(String[]::new)랑 의미는 같음 

        //return result; // setter사용시 리턴



    }




} // end class
