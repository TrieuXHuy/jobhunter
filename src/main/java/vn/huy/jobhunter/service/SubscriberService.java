package vn.huy.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.huy.jobhunter.domain.Skill;
import vn.huy.jobhunter.domain.Subscriber;
import vn.huy.jobhunter.repository.SkillRepository;
import vn.huy.jobhunter.repository.SubscriberRepository;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final SkillRepository skillRepository;

    public SubscriberService(SubscriberRepository subscriberRepository,
            SkillRepository skillRepository) {
        this.subscriberRepository = subscriberRepository;
        this.skillRepository = skillRepository;
    }

    public Subscriber handleCreateSubcriber(Subscriber reqSubscriber) {

        // lấy ra list id từ request
        if (reqSubscriber.getSkills() != null) {
            List<Long> skillIds = reqSubscriber.getSkills()
                    .stream()
                    .map(Skill::getId)
                    .collect(Collectors.toList());

            List<Skill> skillsFromDb = skillRepository.findByIdIn(skillIds);
            reqSubscriber.setSkills(skillsFromDb);
        }

        return this.subscriberRepository.save(reqSubscriber);
    }

    public Subscriber handleUpdateSubscriber(Subscriber reqSubscriber) {
        Optional<Subscriber> subscriberOptional = this.subscriberRepository.findById(reqSubscriber.getId());

        if (subscriberOptional.isPresent()) {
            Subscriber currentSubscriber = subscriberOptional.get();

            // Cập nhật skills
            List<Long> skillIds = reqSubscriber.getSkills()
                    .stream()
                    .map(Skill::getId)
                    .collect(Collectors.toList());
            List<Skill> skillsFromDb = skillRepository.findByIdIn(skillIds);
            currentSubscriber.setSkills(skillsFromDb);

            // Lưu Subscriber
            return this.subscriberRepository.save(currentSubscriber);
        }

        return null;
    }

    public boolean isEmailExists(String email) {
        return subscriberRepository.existsByEmail(email);
    }

    public boolean isIdExists(long id) {
        return subscriberRepository.existsById(id);
    }
}
