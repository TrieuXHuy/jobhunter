package vn.huy.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.huy.jobhunter.domain.Job;
import java.util.List;
import vn.huy.jobhunter.domain.Skill;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    Boolean existsById(long id);

    List<Job> findBySkillsIn(List<Skill> skills);
}
