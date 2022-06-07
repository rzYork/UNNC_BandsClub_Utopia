package com.unncbandsclub.utopia;

import com.unncbandsclub.utopia.utlis.RegularUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UtilTest {
  @Test
  public void testSuffix(){
    System.out.println(RegularUtil.extractFileSuffix("http://s86111/2342342/342/12312/sss.222/s1.2323.2323111.jpg"));
  }
}
