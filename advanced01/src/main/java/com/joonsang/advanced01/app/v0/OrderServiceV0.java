package com.joonsang.advanced01.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV0 {

    private final OrderRepoV0 orderRepository;

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
