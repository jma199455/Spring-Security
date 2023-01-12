package com.study.domain.post;

import java.util.List;

public interface CommentService {

    
    public boolean registerComment(CommentDto params);

	public boolean deleteComment(int idx);

	public List<CommentDto> getCommentList(CommentDto params);

	public CommentDto getcommentlListPage(CommentDto params);
}
