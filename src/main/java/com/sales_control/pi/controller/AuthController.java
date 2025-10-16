package com.sales_control.pi.controller;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.sales_control.pi.dto.LoginRequestDTO;
import com.sales_control.pi.dto.LoginResponseDTO;
import com.sales_control.pi.service.UserService;
import com.sales_control.pi.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;
  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
    var user = userService.authenticate(dto.username(), dto.password());
    if (isNull(user)) return ResponseEntity.status(UNAUTHORIZED).build();

    var token = jwtUtil.generateToken(user.username(), user.userType().name());
    return ResponseEntity.ok(LoginResponseDTO.builder().token(token).role(user.userType().getTranslation()).build());
  }
}
