package com.unncbandsclub.utopia.service;

import com.unncbandsclub.utopia.entity.Avatar;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AvatarService {

  List<Avatar> findAll();

  List<Avatar> findAvatarById(List<Integer> ids);

  Avatar findAvatarById(Integer id);

  String uploadAvatar(MultipartFile file);


}
