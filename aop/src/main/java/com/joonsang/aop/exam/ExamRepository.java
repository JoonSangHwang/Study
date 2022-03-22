package com.joonsang.aop.exam;

import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

    private static int seq = 0;

    /**
     * 5번에 1번 실패하는 요청
     */
    @Trace
    @Retry(value = 4)
    public String save(String itemId) {
        seq++;
        if (seq % 5 == 0) {
            System.out.println("예외 발생");
            throw new IllegalStateException("예외 발생");
        }
        return "ok";
    }
}
