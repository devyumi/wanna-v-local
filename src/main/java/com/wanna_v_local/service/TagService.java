package com.wanna_v_local.service;

import com.wanna_v_local.domain.Tag;
import com.wanna_v_local.dto.request.TagSaveDTO;
import com.wanna_v_local.dto.request.TagRequestDTO;
import com.wanna_v_local.dto.response.TagResponseDTO;
import com.wanna_v_local.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {

    private final TagRepository tagRepository;

    /**
     * 태그 전체 조회 (필터링 적용)
     *
     * @param tagRequestDTO
     * @return
     */
    @Transactional(readOnly = true)
    public TagResponseDTO findTags(TagRequestDTO tagRequestDTO) {
        return TagResponseDTO.builder()
                .tagRequestDTO(tagRequestDTO)
                .tags(tagRepository.findAll(tagRequestDTO))
                .build();
    }

    /**
     * 태그 전체 조회 - 리뷰 작성 시 사용
     * @return
     */
    @Transactional(readOnly = true)
    public List<Tag> findTagsForReview() {
        return tagRepository.findAll();
    }

    /**
     * 태그 상세 조회
     *
     * @param tagId
     * @return
     */
    @Transactional(readOnly = true)
    public Tag findTag(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new IllegalArgumentException("잘못된 id 값입니다."));
    }

    /**
     * 태그 추가
     *
     * @param tagSaveDTO
     */
    @Transactional
    public void saveTag(TagSaveDTO tagSaveDTO) {
        tagRepository.save(Tag.builder()
                .category(tagSaveDTO.getCategory())
                .name(tagSaveDTO.getName())
                .build());
    }

    /**
     * 태그 업데이트
     * @param tagId
     * @param tagSaveDTO
     */
    @Transactional
    public void updateTag(Long tagId, TagSaveDTO tagSaveDTO) {
        Tag tag = findTag(tagId);
        tagRepository.save(Tag.builder()
                .id(tagId)
                .category(tagSaveDTO.getCategory())
                .name(tagSaveDTO.getName())
                .build());
    }

    /**
     * 태그 삭제
     *
     * @param tagId
     */
    @Transactional
    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }
}
