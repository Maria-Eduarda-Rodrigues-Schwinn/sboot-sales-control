package com.sales_control.pi.service;

import com.sales_control.pi.dto.UserDTO;
import com.sales_control.pi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repo;
  private final PasswordEncoder encoder;

  public UserDTO authenticate(String userName, String password) {
    return repo.findByUserName(userName)
        .filter(u -> encoder.matches(password, u.getPasswordHash()))
        .map(
            u ->
                UserDTO.builder()
                    .id(u.getId())
                    .username(u.getUserName())
                    .name(u.getName())
                    .passwordHash(u.getPasswordHash())
                    .userType(u.getUserType())
                    .build())
        .orElse(null);
  }
}
