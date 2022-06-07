package com.unncbandsclub.utopia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Component
public class UtopiaSystemConfiguration {

  @Value("${utopia.open-register}")
  private Boolean isRegisterOpen;

  @Value("${utopia.open-token-verify}")
  private Boolean isTokenVerifyOpen;

  @Value("${utopia.open-cdkey}")
  private Boolean isOpenCdkey;

  @Value("${utopia.nicknames}")
  private String nickNames;

  @Value("${utopia.default_avatars}")
  private String defaultAvatarIds;

  public Boolean getRegisterOpen() {
    return isRegisterOpen;
  }

  public Boolean getTokenVerifyOpen() {
    return isTokenVerifyOpen;
  }

  public Boolean getOpenCdkey() {
    return isOpenCdkey;
  }

  public String[] getNickNames() {
    return nickNames.split(",");
  }

  public List<Integer> getDefaultAvatarIds() {
    return Arrays.stream(defaultAvatarIds.split(",")).map(Integer::parseInt).collect(Collectors.toList());
  }
}
