package com.example.kunuzdemo.service.media;

import com.example.kunuzdemo.entity.Media;
import com.example.kunuzdemo.exceptions.DataNotFoundException;
import com.example.kunuzdemo.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository ;
    @Override
    public Media getMediaById(Long mediaID) {
        return mediaRepository.findById(mediaID).orElseThrow(
                () -> new DataNotFoundException("Media not found with ID: " + mediaID)
        );
    }
}
