package com.example.kunuzdemo.service.user;

import com.example.kunuzdemo.dtos.request.ChangeRoleDTO;
import com.example.kunuzdemo.dtos.request.UserUpdateProfileDTO;
import com.example.kunuzdemo.dtos.response.UserResponseDTO;
import com.example.kunuzdemo.entity.UserEntity;
import com.example.kunuzdemo.exceptions.DataNotFoundException;
import com.example.kunuzdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository ;
    @Override
    public UserEntity getUserByID(UUID id) {
        return userRepository.getUserEntityById(id);
    }

    @Override
    public UserResponseDTO getById(UUID id) {
        return null;
    }

    @Override
    public UserEntity getUserByEmail(String email) {

        Optional<UserEntity> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new DataNotFoundException("User not found with Email: " + email);
        }
        else {
            return user.get();
        }
    }

    @Override
    public List<UserResponseDTO> searchByEmail(String email, Integer page, Integer size) {
        return null;
    }

    @Override
    public List<UserResponseDTO> getAll(Integer page, Integer size) {
        return null;
    }

    @Override
    public List<UserResponseDTO> filterByRole(Integer page, Integer size, String role) {
        return null;
    }

    @Override
    public List<UserResponseDTO> getAllDeleted(Integer page, Integer size) {
        return null;
    }

    @Override
    public String blockByID(UUID userId) {
        return null;
    }

    @Override
    public String unblockByID(UUID userId) {
        return null;
    }

    @Override
    public UserResponseDTO changeRole(ChangeRoleDTO roleDTO) {
        return null;
    }

    @Override
    public UserResponseDTO updateProfile(UUID userId, UserUpdateProfileDTO dto) {
        return null;
    }

    @Override
    public String deleteByID(UUID userId) {
        return null;
    }

    @Override
    public void deleteSelectedUsers(List<UUID> userIds) {

    }
}
