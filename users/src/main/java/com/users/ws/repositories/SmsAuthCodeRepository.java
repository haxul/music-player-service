package com.users.ws.repositories;

import com.users.ws.entities.SmsAuthCode;
import com.users.ws.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsAuthCodeRepository extends JpaRepository<SmsAuthCode, Long> {
    SmsAuthCode findByUser(UserEntity user);
}
