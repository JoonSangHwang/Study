package com.joonsang.backendtech.config;

import com.joonsang.backendtech.domain.item.Item;
import com.joonsang.backendtech.domain.member.Member;
import com.joonsang.backendtech.repository.item.ItemRepository;
import com.joonsang.backendtech.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("1234");
        member.setName("테스터");

        memberRepository.save(member);
    }

}