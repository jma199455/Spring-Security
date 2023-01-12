package com.study.paging;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class PagingResponse<T> {

    private List<T> list = new ArrayList<>();
    private Pagination pagination;


    /*  service에서 생성자로 return 말고 set으로 넣어서 객체 리턴했을 경우에 사용 (기본생성자 있어야함)
    public PagingResponse(){
        
    }
    */

    public PagingResponse(List<T> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }



}