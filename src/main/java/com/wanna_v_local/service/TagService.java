package com.wanna_v_local.service;

import com.wanna_v_local.domain.Tag;
import com.wanna_v_local.dto.TagSaveDTO;
import com.wanna_v_local.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    /**
     * 태그 추가
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
