package com.matheusfelixr.authentication.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "HISTORY_RESET_PASSWORD" )
@SequenceGenerator(name = "SEQ_HISTORY_RESET_PASSWORD", sequenceName = "SEQ_HISTORY_RESET_PASSWORD")
public class HistoryResetPassword {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_HISTORY_RESET_PASSWORD")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_AUTHENTICATION", referencedColumnName = "ID")
    private UserAuthentication userAuthentication;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "IP")
    private String ip;

}
