package com.seunggyu.book.springboot.service.posts;

import com.seunggyu.book.springboot.domain.posts.Posts;
import com.seunggyu.book.springboot.domain.posts.PostsRepository;
import com.seunggyu.book.springboot.web.dto.PostsResponseDto;
import com.seunggyu.book.springboot.web.dto.PostsSaveRequestDto;
import com.seunggyu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    /*
    @Transactional 어노테이션 => 트랜잭션 처리 지원
    트랜잭션의 개념
    모든 작업들이 성공적으로 완료되어야 작업 묶음의 결과를 적용하고, 어떤 작업에서 오류가 발생했을 때는
    이전에 있던 모든 작업들이 성공적이었더라도 없었던 일처럼 완전히 되돌리는 것

    데이터베이스를 다룰 때 트랜잭션을 적용하면 데이터 추가, 갱신, 삭제 등으로 이루어진 작업을 처리하던 중
    오류가 발생했을 때 모든 작업들을 원상태로 되돌릴 수 있다.

    일련의 작업들을 묶어서 하나의 단위로 처리하고 싶을 때 사용
    */
    @Transactional // 등록
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional // 수정
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()-> new
                        IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) { // 조회
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()-> new
                        IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        return new PostsResponseDto(entity);

    }
}
