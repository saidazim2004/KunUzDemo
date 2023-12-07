package com.example.kunuzdemo.service.user;

import com.example.kunuzdemo.dtos.request.ChangeRoleDTO;
import com.example.kunuzdemo.dtos.request.UserUpdateProfileDTO;
import com.example.kunuzdemo.dtos.response.UserResponseDTO;
import com.example.kunuzdemo.entity.Media;
import com.example.kunuzdemo.entity.UserEntity;
import com.example.kunuzdemo.enums.UserRole;
import com.example.kunuzdemo.enums.UserStatus;
import com.example.kunuzdemo.exceptions.DataNotFoundException;
import com.example.kunuzdemo.repository.MediaRepository;
import com.example.kunuzdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.kunuzdemo.enums.UserStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository ;
    private final ModelMapper modelMapper ;
    private final MediaRepository mediaRepository ;

    @Override
    public UserEntity getUserByID(UUID id) {
        return userRepository.getUserEntityById(id);
    }

    @Override
    public UserResponseDTO getById(UUID id) {
        return null;
    }


    @Override
    public List<UserResponseDTO> searchByEmail(String email, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "email");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<UserEntity> users = userRepository.searchByEmail(email, pageable).getContent();
        return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {}.getType());
    }


    @Override
    public List<UserResponseDTO> getAll(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<UserEntity> users = userRepository.findAllUsers(pageable).getContent();
        return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {
        }.getType());
    }

    @Override
    public List<UserResponseDTO> filterByRole(Integer page, Integer size, String role) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        Pageable pageable = PageRequest.of(page, size, sort);

        try {
            List<UserEntity> users = userRepository.filterByRole(UserRole.valueOf(role), pageable).getContent();
            return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {}.getType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Enum type not valid: " + role);
        }
    }

    @Override
    public List<UserResponseDTO> getAllDeleted(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<UserEntity> users = userRepository.findAllDeleted(pageable).getContent();
        return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {
        }.getType());
    }

    @Override
    public UserResponseDTO changeRole(ChangeRoleDTO roleDTO) {
        UserEntity user = findByID(roleDTO.getUserId());

        try {
            user.setRole(UserRole.valueOf(roleDTO.getRole()));
            return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Enum type not valid: " + roleDTO.getRole());
        }
    }

    @Override
    public UserResponseDTO updateProfile(UUID userID, UserUpdateProfileDTO dto) {
        UserEntity user = findByID(userID);

        if (dto.getMediaId() != null) {
            Media media = mediaRepository.findById(dto.getMediaId()).orElseThrow(
                    () -> new DataNotFoundException("Media not found with ID: " + dto.getMediaId()));
            user.setMedia(media);
        }

        modelMapper.map(dto, user);
        UserEntity updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new DataNotFoundException("User not found with Email: " + email)
        );
    }

    private UserEntity findByID(UUID id) {
        return userRepository.getUserByID(id).orElseThrow(
                () -> new DataNotFoundException("user not found with ID: " + id));
    }

    @Override
    public String blockByID(UUID userID) {
        UserEntity user = findByID(userID);
        if(user.getStatus().equals(UserStatus.BLOCKED)) {
            return "User already blocked";
        }
        user.setStatus(UserStatus.BLOCKED);
        userRepository.save(user);
        return "User blocked";
    }

    @Override
    public String unblockByID(UUID userID) {
        UserEntity user = findByID(userID);
        if(user.getStatus().equals(ACTIVE)) {
            return "User already unblocked";
        }
        user.setStatus(ACTIVE);
        userRepository.save(user);
        return "User unblocked";
    }


    @Override
    public String deleteByID(UUID userID) {
        UserEntity user = findByID(userID);
        user.setDeleted(true);
        userRepository.save(user);
        return "user deleted";
    }

    @Override
    public void deleteSelectedUsers(List<UUID> userIDs) {
        for (UUID userID : userIDs) {
            deleteByID(userID);
        }
    }
}
