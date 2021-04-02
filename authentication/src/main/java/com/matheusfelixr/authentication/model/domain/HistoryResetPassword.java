package com.matheusfelixr.authentication.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "HISTORY_RESET_PASSWORD" )
@SequenceGenerator(name = "SEQ_HISTORY_RESET_PASSWORD", sequenceName = "SEQ_HISTORY_RESET_PASSWORD", allocationSize = 1)
public class HistoryResetPassword {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_HISTORY_RESET_PASSWORD")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_AUTHENTICATION", referencedColumnName = "ID", nullable = false)
    private UserAuthentication userAuthentication;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE", nullable = false)
    private Date date;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NTP_DATE", nullable = true)
    private Date ntpDate;

    @Column(name = "IP", nullable = true)
    private String ip;

}
