package vn.huy.jobhunter.domain.response.resume;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateResumeDTO {

    private Instant updatedAt;
    private String updatedBy;
}
