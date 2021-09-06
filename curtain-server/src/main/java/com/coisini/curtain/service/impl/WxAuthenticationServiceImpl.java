package com.coisini.curtain.service.impl;

import com.coisini.curtain.exception.http.ParameterException;
import com.coisini.curtain.entity.User;
import com.coisini.curtain.repository.UserRepository;
import com.coisini.curtain.service.WxAuthenticationService;
import com.coisini.curtain.util.JwtToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @Description 微信认证实现类
 * @author coisini
 * @date Aug 18, 2021
 * @Version 1.0
 */
@Service
public class WxAuthenticationServiceImpl implements WxAuthenticationService {

    @Value("${wechat.code2session}")
    private String code2SessionUrl;

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.appsecret}")
    private String appsecret;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String code2session(String code) {
        String url = MessageFormat.format(this.code2SessionUrl, this.appid, this.appsecret, code);
        RestTemplate rest = new RestTemplate();
        String sessionText = rest.getForObject(url, String.class);

        Map<String, Object> session = new HashMap<>();

        try {
            session = mapper.readValue(sessionText, Map.class);
            System.out.println(mapper.writeValueAsString(session));
            // {"session_key":"oImL2VXoGLMvae/5bsgJkA==","openid":"oBfjq4p7h0kuQEV4_mYRtNj1FX8s"}

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return this.registerUser(session);
    }

    private String registerUser(Map<String, Object> session) {
        Object openidObj = session.get("openid");

        if (Objects.isNull(openidObj)) {
            throw new ParameterException(20004);
        }

        String openid = openidObj.toString();

        Optional<User> userOptional = userRepository.findByOpenid(openidObj.toString());

        if (userOptional.isPresent()) {
            // TODO:返回JWT令牌
            return JwtToken.makeToken(userOptional.get().getId());
        }

        User user = User.builder()
                        .openid(openid)
                        .build();

        userRepository.saveAndFlush(user);

        // TODO:返回JWT令牌
        Long uid = user.getId();
        return JwtToken.makeToken(uid);
    }
}
