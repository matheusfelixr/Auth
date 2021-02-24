package com.matheusfelixr.authentication.repository;

import com.matheusfelixr.authentication.model.domain.HistoryResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryResetPasswordRepository extends JpaRepository<HistoryResetPassword, Long>  {

}
