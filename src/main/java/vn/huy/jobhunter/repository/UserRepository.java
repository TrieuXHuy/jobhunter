package vn.huy.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.huy.jobhunter.domain.Company;
import vn.huy.jobhunter.domain.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsById(long id);

    User findUserByRefreshTokenAndEmail(String token, String email);

    List<User> findByCompany(Company company);
}
