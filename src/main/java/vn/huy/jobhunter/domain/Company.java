package vn.huy.jobhunter.domain;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import vn.huy.jobhunter.util.SecurityUtil;

@Entity
@Table(name = "companies")
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên công ty không được để trống")
    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private String address;

    private String logo;

    private Instant createdAt;

    private Instant updatedAt;

    private String createBy;

    private String updateBy;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    List<User> users;

    @PrePersist
    public void handleBeforeCreateAt() {
        this.createdAt = Instant.now();

        this.createBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
    }

    @PreUpdate
    public void handleBeforeUpdateAt() {
        this.updatedAt = Instant.now();
        this.updateBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
    }
}
