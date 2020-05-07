package com.users.ws.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "sms_auth_codes")
@NoArgsConstructor
public class SmsAuthCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "sent")
    @CreationTimestamp
    private Timestamp date;

    @Column(name = "code")
    private String code;

    @Column(name = "is_relevant")
    private Boolean isRelevant = true;

    @OneToOne
    private UserEntity user;

    public SmsAuthCode(String code, UserEntity user) {
        this.code = code;
        this.user = user;
    }
}
