package com.study.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.study.domain.post.AttachDto;
import com.study.exception.AttachFileException;

@Component
public class FileUtils {

	/** 오늘 날짜 */
	private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

	/** 업로드 경로 */
	private final String uploadPath = Paths.get("C:", "dev_security", "upload", today).toString();

	/**
	 * 서버에 생성할 파일명을 처리할 랜덤 문자열 반환
	 * @return 랜덤 문자열
	 */
	private final String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 서버에 첨부 파일을 생성하고, 업로드 파일 목록 반환
	 * @param files    - 파일 Array
	 * @param boardIdx - 게시글 번호
	 * @return 업로드 파일 목록
	 */
	public List<AttachDto> uploadFiles(MultipartFile[] files, int boardIdx) {

        System.out.println("filesfilesfilesfilesfiles ==============> " + files);
		System.out.println(uploadPath);

		/* 업로드 파일 정보를 담을 비어있는 리스트 */
		List<AttachDto> attachList = new ArrayList<>();

		/* uploadPath에 해당하는 디렉터리가 존재하지 않으면, 부모 디렉터리를 포함한 모든 디렉터리를 생성 */
		File dir = new File(uploadPath);
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		/* 파일 개수만큼 forEach 실행 */
		for (MultipartFile file : files) {

			System.out.println("filefilefilefilefile=============================> " + file.getSize()); // 파일 갯수만큼 size 확인

			System.out.println(files[0].getSize()); // 동일한지 확인
			System.out.println(file.getSize()); 	// 동일한지 확인
			
			// files 데이터 중에서 사이즈 1보다 작으면 등록 x 다음 for문 진행
            if (file.getSize() < 1) { 
                    continue; 
            }

			try {
				/* 파일 확장자 */
				String temp = file.getOriginalFilename().toString();
				System.out.println(temp);
				final String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 확장자 가져오기
				/* 서버에 저장할 파일명 (랜덤 문자열 + 확장자) */
				final String saveName = getRandomString() + "." + extension;

				/* 업로드 경로에 saveName과 동일한 이름을 가진 파일 생성 */
				File target = new File(uploadPath, saveName);
 				file.transferTo(target); // MultipartFile 객체의 실제 업로드 (파일을 복사해서 전송)

				/* 파일 정보 저장 */
				AttachDto attach = new AttachDto();
				attach.setBoardIdx(boardIdx);
				attach.setOriginalName(file.getOriginalFilename());	// multipartFile 파일 이름 일겅오기
				attach.setSaveName(saveName);
				attach.setSize(file.getSize());

				/* 파일 정보 추가 */
				attachList.add(attach);

			} catch (IOException e) {
				throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");

			} catch (Exception e) {
				throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");
			}
		} // end of for

		return attachList;
	}

}