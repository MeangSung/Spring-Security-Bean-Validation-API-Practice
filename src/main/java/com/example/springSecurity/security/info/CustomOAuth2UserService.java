package com.example.springSecurity.security.info;

import com.example.springSecurity.user.repo.UserRepository;
import com.example.springSecurity.user.repo.entity.User;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 유저 정보 가져오기
        Map<String, Object> oauth2UserAttributes = super.loadUser(userRequest).getAttributes();
        System.out.println("attributes: " + oauth2UserAttributes);


        //registrationId 가져오기
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //UsernameAttributeName 가져오기
        String usernameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        //유저 정보 dto 생성
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oauth2UserAttributes);

        System.out.println("nickname : "+ oAuth2UserInfo.getName());
        System.out.println("email : "+ oAuth2UserInfo.getEmail());


        //회원 가입 및 로그인
        User user = getOrSave(oAuth2UserInfo);

        return new PrincipalDetails(user, oauth2UserAttributes, usernameAttributeName);
    }

    private User getOrSave(OAuth2UserInfo oAuth2UserInfo) {
        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail())
                .orElseGet(oAuth2UserInfo::toEntity);
        return userRepository.save(user);
    }

}
