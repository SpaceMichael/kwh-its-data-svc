package hk.org.ha.kcc.its.repository;

import hk.org.ha.kcc.its.model.MenuService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuService, Integer> {

}
