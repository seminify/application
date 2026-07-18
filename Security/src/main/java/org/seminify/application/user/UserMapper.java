package org.seminify.application.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
  @Select("SELECT * FROM users;")
  UserDTO[] userDTOs();

  @Insert(
    "INSERT INTO users (`username`, `password`) VALUES(#{username}, #{password});"
  )
  void insert(UserDTO userDTO);
}
