package com.example.practiceShop.domain.member;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // 메소드들의 기본 세팅은 readOnly
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional  // 따로 Transcational 어노테이션을 부여하면 이게 더 우선권을 가짐
    public UUID join(Member member) {
        // 가입 가능 여부 검증
        validateDuplicateMember(member);

        // 회원 가입 진행
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 검증
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(UUID memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findByEmail(String email) {
        List<Member> members = memberRepository.findByEmail(email);
        if (members.size() >= 2) {
            throw new IllegalStateException("이메일이 같은 회원이 둘 이상 존재합니다.");
        }
        return members;
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void update(UUID id, String name) {
        Member member = memberRepository.findOne(id);
        member.updateName(name);
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void remove(UUID id) {
        Member member = memberRepository.findOne(id);
        memberRepository.remove(member);
    }
}
