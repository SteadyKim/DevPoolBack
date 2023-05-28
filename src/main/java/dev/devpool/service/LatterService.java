package dev.devpool.service;

import dev.devpool.domain.Latter;
import dev.devpool.domain.Member;
import dev.devpool.dto.LatterDto;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.exception.CustomEntityNotFoundException;
import dev.devpool.repository.LatterRepository;
import dev.devpool.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LatterService {
    private final LatterRepository latterRepository;

    private final MemberRepository memberRepository;


    // 저장
    @Transactional
    public CommonResponseDto<Object> join(LatterDto.Save latterDto) {
        Long senderId = latterDto.getSenderId();
        Member sender = memberRepository.findOneById(senderId);
        Long receiverId = latterDto.getReceiverId();
        Member receiver = memberRepository.findOneById(receiverId);

        Latter latter = Latter.builder()
                .sender(sender)
                .receiver(receiver)
                .content(latterDto.getContent())
                .build();

        latterRepository.save(latter);


        return CommonResponseDto.builder()
                .message("쪽지 저장에 성공하였습니다.")
                .build();
    }

    // 조회
    public List<List<LatterDto.Response>> findAllByMemberId(Long senderId) {
        List<Latter> latterSenderList = latterRepository.findAllBySenderId(senderId); // 내가 보낸 모든 쪽지
        List<Latter> latterReceiverList = latterRepository.findAllByReceiverId(senderId); // 내가 받은 모든 쪽지

        List<List<LatterDto.Response>> latterDtoList = new ArrayList<>();

        latterSenderList.sort(Comparator.comparing(Latter::getCreateDate));
        // 2 3 4 5 순서라고 생각 해보자.
        Set<Long> receiverIdSet = latterSenderList.stream()
                .map(latter -> latter.getReceiver().getId())
                .collect(Collectors.toSet());

        for (Long receiverId : receiverIdSet) {

            List<LatterDto.Response> latterSenderDtoList = latterSenderList.stream()  // 내가 보낸 쪽지 중 receiver가 특정한 경우
                    .filter(latter -> latter.getReceiver() != null && Objects.equals(latter.getReceiver().getId(), receiverId))
                    .map(latter -> {
                        String receiverNickName = Optional.ofNullable(latter.getReceiver())
                                .map(Member::getNickName)
                                .orElse("탈퇴한 회원");

                        return   LatterDto.Response.builder()
                                .senderNickName(latter.getSender().getNickName())
                                .receiverNickName(receiverNickName)
                                .content(latter.getContent())
                                .createTime(latter.getCreateDate())
                                .build();
                    })
                    .collect(Collectors.toList());

            // sender receiverId인 쪽지 중 receiver의 아이디가 senderId인 쪽지의 Dto   receiver >> sender 내가 받은 쪽지 중 sender가 특정한 경우
            List<LatterDto.Response> latterReceiverDtoList = latterReceiverList.stream()
                    .filter(latter -> latter.getSender() != null && Objects.equals(latter.getSender().getId(), receiverId))
                    .map(latter -> {
                        /**
                         * 여기가 안찍힘
                         */
                        String senderNickName = Optional.ofNullable(latter.getSender())
                                .map(Member::getNickName)
                                .orElse("탈퇴한 회원");

                        return LatterDto.Response.builder()
                                .senderNickName(senderNickName)
                                .receiverNickName(latter.getReceiver().getNickName())
                                .content(latter.getContent())
                                .createTime(latter.getCreateDate())
                                .build();
                    })
                    .collect(Collectors.toList());

            List<LatterDto.Response> sumDtoList = new ArrayList<>();
            sumDtoList.addAll(latterSenderDtoList);
            sumDtoList.addAll(latterReceiverDtoList);
            sumDtoList.sort(Comparator.comparing(LatterDto.Response::getCreateTime));

            latterDtoList.add(sumDtoList);
        }

        latterDtoList.sort(Comparator.comparing(l -> l.get(0).getCreateTime()));

        return latterDtoList;
    }

    public Latter findById(Long latterId) {
        Latter latter = latterRepository.findById(latterId)
                .orElseThrow(() -> new CustomEntityNotFoundException(Latter.class.getName(), latterId));

        return latter;
    }

    // 삭제
    @Transactional
    public void deleteById(Long latterId) {
        latterRepository.deleteById(latterId);
    }

    @Transactional
    public void deleteBySenderId(Long memberId) {
        latterRepository.deleteAllBySenderId(memberId);
    }
    @Transactional
    public void deleteAll() {
        latterRepository.deleteAll();
    }


}
