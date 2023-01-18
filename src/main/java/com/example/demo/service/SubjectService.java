package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.dto.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.OAEPParameterSpec;

import java.util.Collections;
import java.util.*;

import static com.example.demo.dto.MajorStatus.컴퓨터공학과;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final UserRepository userRepository;
    private final UserSubjectRepository userSubjectRepository;
    private final StandardRepository standardRepository;
    private final SubjectRepository subjectRepository;
    private final TypeRepository typeRepository;
    private final ChangeRepository changeRepository;

    @Transactional
    public String saveComputer(List<ShowDto> array, Long userId) {

        User user = userRepository.findById(userId).get();
        String userNum = user.getUserNum().substring(2,4);
        List<UserSubject> userSubjectList = new ArrayList<>();

        for (ShowDto s: array) {

            if (s.getStudentScore().equals("F") || s.getStudentScore().equals("RE")) continue; // F인 과목은 수강처리 하지 않음

            Optional<Subject> getSubject = subjectRepository.findBySubjectNum(s.getSubjectNum());
            if (getSubject.isPresent()) { // 과목 db안에 과목이 존재하는 경우
                Subject subject = getSubject.get();
                SubjectStatus subjectStatus = subject.getSubjectStatus();
                Integer subjectScore = subject.getSubjectScore();

                // 전과생이면 전과생 로직 먼저
                if(subjectStatus == SubjectStatus.대체과목){ // 대체과목이 있는 경우 학번에 맞는 것을 가져온다
                    Change change = changeRepository.findBySubjectNum(s.getSubjectNum()).get();
                    Subject changeSubject = change.getSubject();

                    if(changeSubject.getSubjectStatus() == SubjectStatus.측정불가 &&
                            typeRepository.findBySubjectAndTypeYear(changeSubject, userNum).get().getTypeCode() != SubjectStatus.일반교양){
                        subject = changeSubject;
                        subjectScore = s.getSubjectScore().charAt(0) - '0';
                        subjectStatus = subject.getSubjectStatus();
                    }else if(changeSubject.getSubjectStatus() != SubjectStatus.일반교양 && changeSubject.getSubjectStatus() != SubjectStatus.측정불가){
                        // 바꿔서 일반교양이면 처리하지 않는다
                        subject = changeSubject;
                        subjectScore = s.getSubjectScore().charAt(0) - '0';
                        subjectStatus = subject.getSubjectStatus();
                    }
                }

                if(subjectStatus == SubjectStatus.측정불가 && subject.getSubjectMajor() == MajorStatus.컴퓨터공학과){ // 측정불가인 경우 학번에 맞는 것을 가져온다
                    subjectStatus = typeRepository.findBySubjectAndTypeYear(subject, userNum).get().getTypeCode();
                }

                if(subject.getSubjectMajor() != MajorStatus.컴퓨터공학과){ // 측정불가든 뭐든 컴공 과목이 아닌걸 들었으면 일반교양으로 다시 바꾸어버린다
                    subjectStatus = SubjectStatus.일반교양;
                }

                userSubjectList.add(UserSubject.builder()
                        .user(user)
                        .subjectNum(subject.getSubjectNum())
                        .majorStatus(subject.getSubjectMajor())
                        .subjectStatus(subjectStatus)
                        .subjectScore(subjectScore)
                        .build());

            } else {
                // 과목 db안에 과목이 존재하지 않는 경우 -> 교양선택
                userSubjectList.add(UserSubject.builder()
                        .user(user)
                        .subjectNum(s.getSubjectNum())
                        .subjectScore(Integer.parseInt(s.getSubjectScore().substring(0, s.getSubjectScore().length()-2)))
                        .subjectStatus(SubjectStatus.일반교양)
                        .build());
            }
            userSubjectRepository.saveAll(userSubjectList);
        }

        return "success!!";
    }

    @Transactional
    public String saveDual(List<ShowDto> array, Long userId) {

        User user = userRepository.findById(userId).get();
        String userNum = user.getUserNum().substring(2,4);
        List<UserSubject> userSubjectList = new ArrayList<>();

        for (ShowDto s: array) {
            if (s.getStudentScore().equals("F") || s.getStudentScore().equals("RE")) continue; // F인 과목은 수강처리 하지 않음

            Optional<Subject> getSubject = subjectRepository.findBySubjectNum(s.getSubjectNum());
            if (getSubject.isPresent()) { // 과목 db안에 과목이 존재하는 경우

                Subject subject = getSubject.get();
                SubjectStatus subjectStatus = subject.getSubjectStatus();
                Integer subjectScore = subject.getSubjectScore();

                // 전과생이면 전과생 로직 먼저
                if(subjectStatus == SubjectStatus.대체과목){ // 대체과목이 있는 경우 학번에 맞는 것을 가져온다
                    Change change = changeRepository.findBySubjectNum(s.getSubjectNum()).get();
                    Subject changeSubject = change.getSubject();

                    if(changeSubject.getSubjectStatus() == SubjectStatus.측정불가 &&
                            typeRepository.findBySubjectAndTypeYear(changeSubject, userNum).get().getTypeCode() != SubjectStatus.일반교양){
                        subject = changeSubject;
                        subjectScore = s.getSubjectScore().charAt(0) - '0';
                        subjectStatus = subject.getSubjectStatus();
                    }else if(changeSubject.getSubjectStatus() != SubjectStatus.일반교양 && changeSubject.getSubjectStatus() != SubjectStatus.측정불가){
                        // 바꿔서 일반교양이면 처리하지 않는다
                        subject = changeSubject;
                        subjectScore = s.getSubjectScore().charAt(0) - '0';
                        subjectStatus = subject.getSubjectStatus();
                    }
                }

                if(subjectStatus == SubjectStatus.측정불가 && subject.getSubjectMajor() == 컴퓨터공학과){ // 측정불가인 경우 학번에 맞는 것을 가져온다
                    subjectStatus = typeRepository.findBySubjectAndTypeYear(subject, userNum).get().getTypeCode();
                }else if(subjectStatus == SubjectStatus.측정불가 && subject.getSubjectMajor() == user.getUserSmajor()){
                    userNum = user.getAcceptYear().toString();
                    subjectStatus = typeRepository.findBySubjectAndTypeYearAndUserStatus(subject, userNum, user.getUserStatus()).get().getTypeCode();
                }

                if(subject.getSubjectMajor() != MajorStatus.컴퓨터공학과 && subject.getSubjectMajor() != user.getUserSmajor()){ // 측정불가든 뭐든 컴공이랑 복부전 과목이 아닌걸 들었으면 일반교양
                    subjectStatus = SubjectStatus.일반교양;
                }

                userSubjectList.add(UserSubject.builder()
                        .user(user)
                        .subjectNum(subject.getSubjectNum())
                        .majorStatus(subject.getSubjectMajor())
                        .subjectStatus(subjectStatus)
                        .subjectScore(subjectScore)
                        .build());

            } else {
                // 과목 db안에 과목이 존재하지 않는 경우 -> 교양선택
                userSubjectList.add(UserSubject.builder()
                        .user(user)
                        .subjectNum(s.getSubjectNum())
                        .subjectScore(Integer.parseInt(s.getSubjectScore().substring(0, s.getSubjectScore().length()-2)))
                        .subjectStatus(SubjectStatus.일반교양)
                        .build());
            }
            userSubjectRepository.saveAll(userSubjectList);
        }

        return "success!!";
    }

    public ScoreDto calculate() {

        int[] arr = new int[15];
        Arrays.fill(arr, 0);
        int sum_ipp = 0;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();

        System.out.println(user);
        String userNum = user.getUserNum().substring(2,4);
        MajorStatus userSMajorStatus = user.getUserSmajor();

        List<UserSubject> userSubjectList = user.getUserSubjectList();

        for (UserSubject u : userSubjectList) {

            System.out.println(u.getSubjectNum());
            // 현장실습 과목 총 학점 계산
            if(u.getSubjectStatus() == SubjectStatus.현장실습){
                sum_ipp += u.getSubjectScore();
                continue;
            }

            if(u.getMajorStatus() == 컴퓨터공학과 && u.getSubjectStatus() == SubjectStatus.전공필수){
                arr[0] += u.getSubjectScore();
                arr[1]++; // 전공필수 들은 갯수
                arr[2] += u.getSubjectScore(); // 전공전체 학점
            }else if(u.getMajorStatus() == 컴퓨터공학과 && u.getSubjectStatus() == SubjectStatus.전공선택){
                arr[0] += u.getSubjectScore(); // 전체
                arr[2] += u.getSubjectScore(); // 전공전체
            }else if(u.getMajorStatus() == 컴퓨터공학과 && u.getSubjectStatus() == SubjectStatus.교양필수){
                arr[0] += u.getSubjectScore();
                arr[3]++; // 교양필수 전체 갯수
            }else if(userSMajorStatus != 컴퓨터공학과 && u.getMajorStatus() == user.getUserSmajor() && u.getSubjectStatus() == SubjectStatus.전공필수){
                // 다중전공 전공필수
                arr[0] += u.getSubjectScore();
                arr[4]++;
                arr[5] += u.getSubjectScore();
            }else if(userSMajorStatus != 컴퓨터공학과 && u.getMajorStatus() == user.getUserSmajor() && u.getSubjectStatus() == SubjectStatus.전공선택){
                // 다중전공 전공선택
                arr[0] += u.getSubjectScore();
                arr[5] += u.getSubjectScore();
            }else if(userSMajorStatus != 컴퓨터공학과 && u.getMajorStatus() == user.getUserSmajor() && u.getSubjectStatus() == SubjectStatus.교양필수){
                // 다중전공 교양필수
                arr[0] += u.getSubjectScore();
                arr[6]++;
            }
            else {
                arr[0] += u.getSubjectScore();
                if(u.getSubjectStatus().ordinal() != 3 && u.getSubjectStatus().ordinal() < 12) arr[u.getSubjectStatus().ordinal()+3] += u.getSubjectScore();
            }
        }


        //현장실습 관련 계산
        arr[0] += sum_ipp; // 전체 추가
        if(sum_ipp > 12){
            arr[2] += 12;
        }else{
            arr[2] += sum_ipp;
        }

        Standard s1 = standardRepository.findByMajorStatusAndStudentStatusAndStudentYear(컴퓨터공학과, UserStatus.SINGLE, userNum).get();
        Standard s2 = standardRepository.findByMajorStatusAndStudentStatusAndStudentYear(user.getUserSmajor(), user.getUserStatus(), userNum).get();

        if(Integer.valueOf(userNum) <= 20){ // 개편 이전
            ScoreDto score;
            Boolean result = false;
            if(user.getUserSmajor() == 컴퓨터공학과){
                if((arr[0] >= 130 && arr[2] >= s1.getMajScore() && arr[1] >= s1.getEssScore() && arr[3] >= s1.getEduScore())
                && ((arr[7] + arr[8] + arr[9] + arr[10] + arr[11] + arr[12] + arr[13]) >= 9) && (!user.getUserEnglish().equals("불만족")) && (!user.getUserTest().equals("미응시"))){
                    System.out.println(user.getUserTest());
                    result = true;
                }


                score = ScoreDto.builder()
                        .totalScore(String.valueOf(arr[0]) + "/130") // 전체학점
                        .firstMajorScore(String.valueOf(arr[2]) + "/" + String.valueOf(s1.getMajScore())) // 본전공전체학점
                        .firstMajorRequireScore(String.valueOf(arr[1]) + "/" + String.valueOf(s1.getEssScore())) // 본전공전공필수학점
                        .firstCoreRequireScore(String.valueOf(arr[3]) + "/" + String.valueOf(s1.getEduScore())) // 본전공교양필수학점
                        .coreScore7((arr[7] + arr[8] + arr[9] + arr[10] + arr[11] + arr[12] + arr[13]) + "/9") // 핵교 수강 학점
                        .userStatus(user.getUserStatus())
                        .majorStatus(user.getUserSmajor())
                        .userEnglish(user.getUserEnglish())
                        .userTest(user.getUserTest())
                        .result(result)
                        .build();

            }else{

                if(arr[0] >= 130 && arr[2] >= s1.getMajScore() && arr[1] >= s1.getEssScore() && arr[3] >= s1.getEduScore()
                        && arr[4] >= s2.getEssScore() && arr[5] >= s2.getMajScore() && arr[6] >= s2.getEduScore()
                        && (arr[7] + arr[8] + arr[9] + arr[10] + arr[11] + arr[12] + arr[13]) >= 9 && (!user.getUserEnglish().equals("불만족")) && (!user.getUserTest().equals("미응시"))){
                    result = true;
                }
                Integer requireScore = 48;
                if(user.getUserStatus() == UserStatus.DUAL) requireScore = 39;
                score = ScoreDto.builder()
                        .totalScore(String.valueOf(arr[0]) + "/130") // 전체학점
                        .firstMajorScore(String.valueOf(arr[2]) + "/" + String.valueOf(requireScore)) // 본전공전체학점
                        .firstMajorRequireScore(String.valueOf(arr[1]) + "/" + String.valueOf(s1.getEssScore())) // 본전공전공필수학점
                        .firstCoreRequireScore(String.valueOf(arr[3]) + "/" + String.valueOf(s1.getEduScore())) // 본전공교양필수학점
                        .secondMajorRequireScore(String.valueOf(arr[4]) + "/" + String.valueOf(s2.getEssScore())) // 다중전공 전공필수이수학점
                        .secondMajorScore(String.valueOf(arr[5]) + "/" + String.valueOf(s2.getMajScore())) // 다중전공 전공전체이수학점
                        .secondCoreRequireScore(String.valueOf(arr[6]) + "/" + String.valueOf(s2.getEduScore())) // 다중전공 교양필수 학점
                        .coreScore7(String.valueOf(arr[7] + arr[8] + arr[9] + arr[10] + arr[11] + arr[12] + arr[13]) + "/9")
                        .majorStatus(user.getUserSmajor())
                        .userStatus(user.getUserStatus())
                        .userEnglish(user.getUserEnglish())
                        .userTest(user.getUserTest())
                        .result(result)
                        .build();

            }
            return score;


        }else{
            ScoreDto score;
            Boolean result = false;
            if(user.getUserSmajor() == 컴퓨터공학과){
                if(arr[0] >= 130 && arr[2] >= s1.getMajScore() && arr[1] >= s1.getEssScore() && arr[3] >= s1.getEduScore()
                        && arr[7] >= 3 && arr[8] >= 3 && arr[10] >= 3 && arr[14] >= 3 && (!user.getUserEnglish().equals("불만족")) && (!user.getUserTest().equals("미응시"))){
                    result = true;
                }

                score = ScoreDto.builder()
                        .totalScore(String.valueOf(arr[0]) + "/130") // 전체학점
                        .firstMajorScore(String.valueOf(arr[2]) + "/" + String.valueOf(s1.getMajScore())) // 본전공전체학점
                        .firstMajorRequireScore(String.valueOf(arr[1]) + "/" + String.valueOf(s1.getEssScore())) // 본전공전공필수학점
                        .firstCoreRequireScore(String.valueOf(arr[3]) + "/" + String.valueOf(s1.getEduScore())) // 본전공교양필수학점
                        .coreScore1(String.valueOf(arr[7]) + "/3")
                        .coreScore2(String.valueOf(arr[8]) + "/3")
                        .coreScore4(String.valueOf(arr[10]) + "/3")
                        .creativeScore(String.valueOf(arr[14]) + "/3")
                        .userStatus(user.getUserStatus())
                        .majorStatus(user.getUserSmajor())
                        .userEnglish(user.getUserEnglish())
                        .userTest(user.getUserTest())
                        .result(result)
                        .build();

            }else{
                if(arr[0] >= 130 && arr[2] >= s1.getMajScore() && arr[1] >= s1.getEssScore() && arr[3] >= s1.getEduScore()
                        && arr[4] >= s2.getEssScore() && arr[5] >= s2.getMajScore() && arr[6] >= s2.getEduScore()
                        && arr[7] >= 3 && arr[8] >= 3 && arr[10] >= 3 && arr[14] >= 3 && user.getUserEnglish() != "불만족" && user.getUserTest() != "미응시"){
                    result = true;
                }
                Integer requireScore = 48;
                if(user.getUserStatus() == UserStatus.DUAL) requireScore = 39;
                score = ScoreDto.builder()
                        .totalScore(String.valueOf(arr[0]) + "/130") // 전체학점
                        .firstMajorScore(String.valueOf(arr[2]) + "/" + String.valueOf(requireScore)) // 본전공전체학점
                        .firstMajorRequireScore(String.valueOf(arr[1]) + "/" + String.valueOf(s1.getEssScore())) // 본전공전공필수학점
                        .firstCoreRequireScore(String.valueOf(arr[3]) + "/" + String.valueOf(s1.getEduScore())) // 본전공교양필수학점
                        .secondMajorScore(String.valueOf(arr[4]) + "/" + String.valueOf(s2.getMajScore())) // 다중전공 전공필수이수학점
                        .secondMajorRequireScore(String.valueOf(arr[5]) + "/" + String.valueOf(s2.getEssScore())) // 다중전공 전공전체이수학점
                        .secondCoreRequireScore(String.valueOf(arr[6]) + "/" + String.valueOf(s2.getEduScore())) // 다중전공 교양필수 학점
                        .coreScore1(String.valueOf(arr[7]) + "/3")
                        .coreScore2(String.valueOf(arr[8]) + "/3")
                        .coreScore4(String.valueOf(arr[10]) + "/3")
                        .creativeScore(String.valueOf(arr[14]) + "/3")
                        .userStatus(user.getUserStatus())
                        .majorStatus(user.getUserSmajor())
                        .userEnglish(user.getUserEnglish())
                        .userTest(user.getUserTest())
                        .result(result)
                        .build();
            }
            return score;
        }
    }

}


