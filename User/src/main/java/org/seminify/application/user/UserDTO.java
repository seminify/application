package org.seminify.application.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDTO {

  private Long id;
  private String username;
  private String password;
}
