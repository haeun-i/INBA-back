package com.example.demo.service;

import com.example.demo.auth.TokenProvider;
import com.example.demo.domain.Shows;
import com.example.demo.domain.Subject;
import com.example.demo.domain.User;
import com.example.demo.domain.UserSubject;
import com.example.demo.dto.*;
import com.example.demo.repository.ShowsRepository;
import com.example.demo.repository.SubjectRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserSubjectRepository userSubjectRepository;
    private final ShowsRepository showsRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider jwtTokenProvider;

    @Transactional
    public SignResultDto signup(UserSignDto userSignDto) {
        User user = User.builder()
                .userEmail(userSignDto.getUserEmail())
                .userLogpw(passwordEncoder.encode(userSignDto.getUserLogpw()))
                .userRealName(userSignDto.getUserName())
                .userStatus(userSignDto.getUserStatus())
                .userSmajor(userSignDto.getUserSmajor())
                .acceptYear(userSignDto.getAcceptYear())
                .userEnglish(userSignDto.getUserEnglish())
                .userTest(userSignDto.getUserTest())
                .isChanging(userSignDto.getIsChanging())
                .pastMajor(userSignDto.getPastMajor())
                .userNum(userSignDto.getUserNum())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        userRepository.save(user);

        SignResultDto result = SignResultDto.builder()
                .userId(user.getUserId())
                .userStatus(user.getUserStatus())
                .build();
        return result; // user??? ???????????? ??????
    }

    public String login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUserEmail(userLoginDto.getUserEmail()).get();
        if (!passwordEncoder.matches(userLoginDto.getUserLogpw(), user.getUserLogpw())) {
            throw new IllegalArgumentException("????????? ?????????????????????.");
        }
        return jwtTokenProvider.createToken(user.getUserEmail(), user.getRoles());
    }

    @Transactional
    public boolean deleteUser(Long userId) {
        User user = userRepository.findById(userId).get();
        userRepository.delete(user);
        return true;

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("???????????? ?????? ??? ????????????."));
    }


    public UserSignDto showMypage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();

        UserSignDto userSignDto = UserSignDto.builder()
                .userName(user.getUserRealName())
                .userEmail(user.getUserEmail())
                .userStatus(user.getUserStatus())
                .userSmajor(user.getUserSmajor())
                .isChanging(user.getIsChanging())
                .acceptYear(user.getAcceptYear())
                .pastMajor(user.getPastMajor())
                .userEnglish(user.getUserEnglish())
                .userTest(user.getUserTest())
                .build();

        return userSignDto;

    }

    @Transactional
    public void modify(MyPageDto myPageDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();

        user.updateUserStatus(myPageDto.getUserStatus());
        user.updateUserSmajor(myPageDto.getUserSmajor());
        user.updateAcceptYear(myPageDto.getAcceptYear());
        user.updateUserEnglish(myPageDto.getUserEnglish());
        user.updateUserTest(myPageDto.getUserTest());
    }

    @Transactional
    public void deleteUserSubject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();

        List<UserSubject> subjectList = userSubjectRepository.findAllByUser(user);
        userSubjectRepository.deleteAllInBatch(subjectList);
    }

    public UserStatus userStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();
        return user.getUserStatus();
    }

    public User user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();
        return user;
    }

    public List<String> userGraduate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();

        List<String> resultList = new ArrayList<>();

        String result = "?????? 130?????? ??? ";
        if(user.getUserStatus() == UserStatus.SINGLE) result += "?????? 65??????, ";
        else if(user.getUserStatus() == UserStatus.DUAL) result += "?????? 39??????, ";
        else if(user.getUserStatus() == UserStatus.MINOR) result += "?????? 48??????, ";

        if(Integer.parseInt(user.getUserNum().substring(2,4)) <= 20){
            result += "???????????? 17??????, ???????????? 3?????? ??????";
       }else{
            result += "???????????? 10??????, ???????????? 1??????, 2??????, 4??????, ?????? ??? 3?????? ??????";
        }
        resultList.add(result);

        if(user.getUserSmajor() != MajorStatus.??????????????????){
            Optional<Shows> show = showsRepository.findByMajorStatusAndUserStatusAndTypeYear(user.getUserSmajor(), user.getUserStatus(), user.getAcceptYear().toString());
            resultList.add(show.get().getGraduateString());
        }

        return resultList;

    }


}
