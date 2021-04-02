package com.matheusfelixr.authentication.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "HISTORY_AUTHENTICATION" )
@SequenceGenerator(name = "SEQ_HISTORY_AUTHENTICATION", sequenceName = "SEQ_HISTORY_AUTHENTICATION", allocationSize = 1)
public class HistoryAuthentication {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_HISTORY_AUTHENTICATION")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_AUTHENTICATION", referencedColumnName = "ID")
    private UserAuthentication userAuthentication;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "IP")
    private String ip;

    @Column(name = "OBSERVATION")
    private String observation;

}
