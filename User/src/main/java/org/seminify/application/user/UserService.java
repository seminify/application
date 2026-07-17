package org.seminify.application.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

  private final UserMapper userMapper;

  @Transactional(readOnly = true)
  public UserDTO[] userDTOs() {
    return userMapper.userDTOs();
  }
}
