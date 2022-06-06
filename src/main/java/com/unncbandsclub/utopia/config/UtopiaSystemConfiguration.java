package com.unncbandsclub.utopia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
  private List<String> nickNames;

  public Boolean getRegisterOpen() {
    return isRegisterOpen;
  }

  public Boolean getTokenVerifyOpen() {
    return isTokenVerifyOpen;
  }

  public Boolean getOpenCdkey() {
    return isOpenCdkey;
  }

  public List<String> getNickNames() {
    return new ArrayList<>(nickNames);
  }
}
