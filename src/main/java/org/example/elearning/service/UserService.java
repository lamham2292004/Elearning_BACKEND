package org.example.elearning.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.elearning.dto.request.UserCreationRequest;
import org.example.elearning.dto.request.UserUpdateRequest;
import org.example.elearning.dto.response.UserResponse;
import org.example.elearning.entity.User;
import org.example.elearning.enums.Role;
import org.example.elearning.exception.AppException;
import org.example.elearning.exception.ErrorCode;
import org.example.elearning.mapper.UserMapper;
import org.example.elearning.repository.UserRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    PasswordEncoder passwordEncoder ;

    UserRepository userRepository;

     UserMapper userMapper;
//tạo user
    public UserResponse createUser(UserCreationRequest request) {


        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXITED);
        }
        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

// ai đăng ký có email là .edu thì sẽ có role là teacher
        HashSet<String> roles = new HashSet<>();
        if (request.getEmail().endsWith("@edu.com")) {
            roles.add(Role.TEACHER.name());
        }
        else {
            roles.add(Role.USERS.name());
        }
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    public UserResponse GetMyinfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();


        User user = userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXITED));


        return userMapper.toUserResponse(user);
    }

//xem thông tin của user bằng ID
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){
        log.info("In Method getUsers");
        return userRepository.findAll().stream()
            .map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse getUser(String id) {
        log.info("In Method getUser by ID");
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXITED)));
    }
//Sửa thông tin user bằng ID

    public UserResponse updateUser(String userId,  UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXITED));;

        userMapper.updateUser(user ,request);
        if (request.getPassword() != null && !request.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }


//xóa user bằng ID
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
