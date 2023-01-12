package com.study.domain.post;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.paging.Pagination;
import com.study.paging.PaginationComment;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Override
	public boolean registerComment(CommentDto params) {
		int queryResult = 0;

		if (params.getIdx() == null) {
			queryResult = commentMapper.insertComment(params);
		} else {
			queryResult = commentMapper.updateComment(params);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public boolean deleteComment(int idx) {
		int queryResult = 0;

		CommentDto comment = commentMapper.selectCommentDetail(idx);

		if (comment != null && "N".equals(comment.getDeleteYn())) {
			queryResult = commentMapper.deleteComment(idx);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public List<CommentDto> getCommentList(CommentDto params) {

		System.out.println("CommentDto params(Service) ==================================> " + params);
		
		List<CommentDto> commentList = Collections.emptyList();

		int commentTotalCount = commentMapper.selectCommentTotalCount(params);



		/*  mapper.xml에 limitStart값 null 나오고 있어서 추가했음 (정상작동)*/
		if (commentTotalCount < 1) {
			params.setPagination(null);       
		 }  
		PaginationComment pagination = new PaginationComment(commentTotalCount, params);
        params.setPagination(pagination);






		if (commentTotalCount > 0) {
			commentList = commentMapper.selectCommentList(params);
		}

		return commentList;
	}

	// 댓글 페이징 그리기 처리 setPagination()
	@Override
	public CommentDto getcommentlListPage(CommentDto params) {

		int commentTotalCount = commentMapper.selectCommentTotalCount(params);

		CommentDto dto = new CommentDto();
		dto.setPage(params.getPage()); // page 값 추가

		if (commentTotalCount < 1) {
			dto.setPagination(null);       
		 }  

		PaginationComment pagination = new PaginationComment(commentTotalCount, params);
        dto.setPagination(pagination);

		return dto;
	}

	


}