package com.wanna_v_local.service;

import com.wanna_v_local.domain.Tag;
import com.wanna_v_local.dto.TagSaveDTO;
import com.wanna_v_local.dto.request.TagRequestDTO;
import com.wanna_v_local.dto.response.TagResponseDTO;
import com.wanna_v_local.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    /**
     * 태그 전체 조회 (개수 포함)
     * @param tagRequestDTO
     * @return
     */
    @Transactional(readOnly = true)
    public TagResponseDTO findTags(TagRequestDTO tagRequestDTO) {
        return TagResponseDTO.builder()
                .tagRequestDTO(tagRequestDTO)
                .tags(tagRepository.findAll(tagRequestDTO))
                .count(tagRepository.count(tagRequestDTO))
                .build();
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


}
