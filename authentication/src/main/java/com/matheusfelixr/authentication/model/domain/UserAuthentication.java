package com.matheusfelixr.authentication.model.domain;

import com.matheusfelixr.authentication.model.domain.CancellationImpl;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "USER_AUTHENTICATION" )
@SequenceGenerator(name = "SEQ_USER_AUTHENTICATION", sequenceName = "SEQ_USER_AUTHENTICATION", allocationSize = 1)
public class UserAuthentication {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_AUTHENTICATION")
    private Long id;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "CHANGE_PASSWORD", nullable = false)
    private Boolean changePassword;

    @Column(name = "IS_ADMIN", nullable = false)
    private Boolean isAdmin;

    public String[] getRoles() {
        return new String[]{"USER"};
    }

    @Embedded
    private CancellationImpl cancellation;

    public UserAuthentication() {
    }

    public UserAuthentication(Long id) {
        this.id = id;
    }

    public CancellationImpl getCancellation() {
        if(this.cancellation == null){
            cancellation = new CancellationImpl();
        }
        return cancellation;
    }
}
