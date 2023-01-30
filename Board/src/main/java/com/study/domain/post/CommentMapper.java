package com.study.domain.post;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CommentMapper {

    public int insertComment(CommentDto params);

	public CommentDto selectCommentDetail(int idx);

	public int updateComment(CommentDto params);

	public int deleteComment(int idx);

	public List<CommentDto> selectCommentList(CommentDto params);	// 댓글 리스트 출력

	public int selectCommentTotalCount(CommentDto params); // 댓글 리스트 갯수 출력
    
}
