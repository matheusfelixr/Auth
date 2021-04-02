package com.matheusfelixr.authentication.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
public class CancellationImpl implements Serializable {

	private static final long serialVersionUID = -858671587851814223L;

	@Column(name = "CANCELLATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cancellationDate;

	@Column(name = "CANCELLATION_OBS", length = 250)
	private String cancellationObservation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANCELLATION_USER", referencedColumnName = "ID")
	private UserAuthentication cancellationUser;

	public void markCanceled(String observation, UserAuthentication cancellationUser) {
		this.setCancellationDate(new Date());
		this.setCancellationObservation(observation);
		this.setCancellationUser(cancellationUser);
	}

	public boolean isCancelled() {
		if(cancellationDate == null){
			return true;
		}
		return false;
	}


}