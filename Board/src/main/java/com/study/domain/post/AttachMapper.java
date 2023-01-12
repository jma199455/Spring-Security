package com.study.domain.post;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachMapper {

	public int insertAttach(List<AttachDto> attachList);
	// 파일 다운로드에서 사용
	public AttachDto selectAttachDetail(int id);

	public int deleteAttach(int boardIdx);

	public List<AttachDto> selectAttachList(int id);

	public int selectAttachTotalCount(int boardIdx);

    public int undeleteAttach(List<Integer> list);

	// public int undeleteAttach(int[] array); 배열로 받아볼 때 사용
}