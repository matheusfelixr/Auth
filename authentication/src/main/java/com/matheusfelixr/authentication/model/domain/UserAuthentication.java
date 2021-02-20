package com.matheusfelixr.authentication.model.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "USER_AUTHENTICATION" )
@SequenceGenerator(name = "SEQ_USER_AUTHENTICATION", sequenceName = "SEQ_USER_AUTHENTICATION")
public class UserAuthentication {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_USER_AUTHENTICATION")
    private Long id;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    public String[] getRoles() {
        return new String[]{"USER"};
    }
}
