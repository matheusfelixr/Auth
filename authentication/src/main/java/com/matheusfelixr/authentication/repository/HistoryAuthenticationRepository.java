package com.matheusfelixr.authentication.repository;

import com.matheusfelixr.authentication.model.domain.HistoryAuthentication;
import com.matheusfelixr.authentication.model.domain.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryAuthenticationRepository extends JpaRepository<HistoryAuthentication, Long>  {

}
