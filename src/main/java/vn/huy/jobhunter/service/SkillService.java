package vn.huy.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.huy.jobhunter.domain.Skill;
import vn.huy.jobhunter.domain.response.ResultPaginationDTO;
import vn.huy.jobhunter.repository.SkillRepository;

@Service
public class SkillService {

    private SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill handleCreateSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    public Skill handleUpdateSkill(Skill requestSkill) {
        Optional<Skill> skillOptional = this.skillRepository.findById(requestSkill.getId());
        if (skillOptional.isPresent()) {
            Skill currentSkill = skillOptional.get();
            currentSkill.setName(requestSkill.getName());

            return this.skillRepository.save(currentSkill);
        }

        return null;
    }

    public Boolean existsBySkill(String name) {
        return this.skillRepository.existsByName(name);
    }

    public Boolean existsById(long id) {
        return this.skillRepository.existsById(id);
    }

    public ResultPaginationDTO fetchAllSkill(Specification<Skill> spec, Pageable pageable) {

        Page<Skill> pageSkill = this.skillRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();

        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageSkill.getNumber() + 1);
        meta.setPageSize(pageSkill.getSize());
        meta.setPages(pageSkill.getTotalPages());
        meta.setTotal(pageSkill.getTotalElements());

        rs.setMeta(meta);

        // remove sensitive data
        List<Skill> listSkill = pageSkill.getContent()
                .stream()
                .map(item -> new Skill(
                        item.getId(),
                        item.getName(),
                        item.getCreatedAt(),
                        item.getUpdatedAt(),
                        item.getCreatedBy(),
                        item.getUpdatedBy(),
                        null))
                .collect(Collectors.toList());

        rs.setResult(listSkill);

        return rs;
    }
}
