package dev.devpool.repository;

import dev.devpool.domain.Latter;

import java.util.List;

public interface LatterRepositoryCustom {

    List<Latter> findAllBySenderId(Long senderId);

    List<Latter> findAllByReceiverId(Long receiverId);

    void deleteAllBySenderId(Long senderId);

}
