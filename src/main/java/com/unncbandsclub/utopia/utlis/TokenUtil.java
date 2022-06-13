package com.unncbandsclub.utopia.utlis;

import com.unncbandsclub.utopia.pojo.LoginResult;
import com.unncbandsclub.utopia.vo.TokenVo;
import com.unncbandsclub.utopia.service.UserService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;


/**
 * @author Ruizhe Huang
 * @date 2022/05/17
 */
@Slf4j
@Component
public class TokenUtil {
    public static final String DEFAULT_TOKEN = "UNNC_BANDSCLUB_UTOPIA_DEFAULT_LOGIN_TOKEN";
    public static final int EXPIRED_SECONDS = 60 * 3; //三分钟TOKEN超时

    /**
     * 获取token中的参数
     *
     * @param token
     * @return
     */

    @Resource
    private UserService userService;

    private static TokenUtil tokenUtil;

    @PostConstruct
    public void init() {
        tokenUtil = this;
        tokenUtil.userService = this.userService;
    }

    public static Claims parseToken(String token) {
        if ("".equals(token)) {
            return null;
        }

        try {
            log.info("Parse token:" + token);
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(DEFAULT_TOKEN))
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.info("Token Timeout");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported Token");
        } catch (MalformedJwtException e) {
            log.info("Malformed Token");
        } catch (SignatureException e) {
            log.info("Signature Error");
        } catch (IllegalArgumentException e) {
            log.info("Illegal Argument Token");
        }
        return null;
    }

    /**
     * 生成token
     *
     * @param
     * @return
     */
    public static String createToken(TokenVo tokenVo) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(DEFAULT_TOKEN);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


        JwtBuilder builder = Jwts.builder()

                .claim("tokenData", tokenVo)
                .setExpiration(DateTime.now().plusSeconds(EXPIRED_SECONDS).toDate())
                .signWith(signatureAlgorithm, signingKey);

        //生成JWT
        return builder.compact();
    }

    public static TokenVo parseToken(Claims claim) {
        if (claim == null) return null;

        LinkedHashMap tokenData = claim.get("tokenData", LinkedHashMap.class);
        log.info(tokenData.toString());
        TokenVo vo = new TokenVo();
        vo.setUsername((String) tokenData.get("username"));
        vo.setPassword((String) tokenData.get("password"));
        vo.setLoginTime((Long) tokenData.get("loginTime"));
        List<Object> l
                = ((ArrayList<Object>) tokenData.get("roleList"));

        List<Integer> roleIds = new ArrayList<>();
        for (Object o : l) {
            roleIds.add((Integer) o);
        }
        vo.setRoleList(roleIds);
        log.info("parsed vo: " + vo);
        return vo;
    }

    public static TokenVo parseTokenToVo(String token) {
        if (token == null) return null;
        Claims claims = parseToken(token);
        return parseToken(claims);
    }

    public static LoginResult verifyTokenLogin(TokenVo vo) {
        return tokenUtil.userService.login(vo.getUsername(), vo.getPassword());
    }


}
