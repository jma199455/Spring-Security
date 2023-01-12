package com.study.paging;

import javax.xml.stream.events.Comment;

import com.study.common.dto.SearchDto;
import com.study.domain.post.CommentDto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Pagination {

    private int totalRecordCount;   // 전체 데이터 수
    private int totalPageCount;     // 전체 페이지 수
    private int startPage;          // 첫 페이지 번호
    private int endPage;            // 끝 페이지 번호
    private int limitStart;         // LIMIT 시작 위치
    private boolean existPrevPage;  // 이전 페이지 존재 여부
    private boolean existNextPage;  // 다음 페이지 존재 여부


    public Pagination(int totalRecordCount, SearchDto params) {
        if (totalRecordCount > 0) {
            this.totalRecordCount = totalRecordCount;
            this.calculation(params);
        }
    }

    private void calculation(SearchDto params) {
                                     
        // System.out.println(params);  // SearchDto(page=1, recordSize=10, pageSize=10, keyword=null, searchType=null, pagination=null)

        // 전체 페이지 수 계산
        totalPageCount = ((totalRecordCount - 1) / params.getRecordSize()) + 1;
                                
        // 현재 페이지 번호가 전체 페이지 수보다 큰 경우, 현재 페이지 번호에 전체 페이지 수 저장 (파라미터에 전체페이지 수 이상이 들어갈 경우)
        if (params.getPage() > totalPageCount) {
            params.setPage(totalPageCount);
        }

        // 첫 페이지 번호 계산
        startPage = ((params.getPage() - 1) / params.getPageSize()) * params.getPageSize() + 1; // 1 , 11 , 21, 31 ....

        // 끝 페이지 번호 계산
        endPage = startPage + params.getPageSize() - 1; // 10, 20, 30
                     
        // 끝 페이지가 전체 페이지 수보다 큰 경우, 끝 페이지 전체 페이지 수 저장
        if (endPage > totalPageCount) {
            endPage = totalPageCount;
        }

        // LIMIT 시작 위치 계산
        limitStart = (params.getPage() - 1) * params.getRecordSize(); // 11 21 31 41 
    
        // 이전 페이지 존재 여부 확인

        // existPrevPage = startPage != 1; // 기존 코드 : page1일 때는 이전 페이지가 존재하지 않음
        existPrevPage = startPage > 0;

        // 다음 페이지 존재 여부 확인
        //existNextPage = (endPage * params.getRecordSize()) < totalRecordCount; // 기존 코드 : endPage가 11 미만일경우 다음 페이지가 존재하지 않음 11부터 다음 페이지 true
        existNextPage = endPage > 0;
                            
    }



}