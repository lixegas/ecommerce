package com.lixega.ecommerce.auth.service;

import com.lixega.ecommerce.auth.model.entity.Access;
import com.lixega.ecommerce.auth.model.entity.Session;
import com.lixega.ecommerce.auth.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.hibernate.SessionBuilder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    private final Long sessionTtlMillis = 500000L;

    protected Session createSession(Access access){
        return sessionRepository.save(buildSession(access));
    }

    private Session buildSession(Access access){
        String sessionUuid = UUID.randomUUID().toString();
        Session.SessionBuilder sessionBuilder = Session.builder()
                .sessionUuid(sessionUuid)
                .expiryDate(Instant.now().plusMillis(sessionTtlMillis))
                .access(access);

        return sessionBuilder.build();
    }
}
