package hk.org.ha.kcc.its.repository;

import hk.org.ha.kcc.its.model.UserAccess;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserAcessRespository extends JpaRepository<UserAccess, String> {
}
