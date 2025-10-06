package vn.huy.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.huy.jobhunter.domain.Company;
import vn.huy.jobhunter.domain.User;
import vn.huy.jobhunter.domain.response.ResCreateUserDTO;
import vn.huy.jobhunter.domain.response.ResUpdateUserDTO;
import vn.huy.jobhunter.domain.response.ResUserDTO;
import vn.huy.jobhunter.domain.response.ResultPaginationDTO;
import vn.huy.jobhunter.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CompanyService companyService;

    public UserService(UserRepository userRepository,
            CompanyService companyService) {
        this.userRepository = userRepository;
        this.companyService = companyService;
    }

    public User handleCreateUser(User user) {

        if (user.getCompany() != null) {
            Company company = this.companyService.fetchCompanyById(user.getCompany().getId());
            if (company != null) {
                user.setCompany(company);
            }
        }
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(long id) {

        this.userRepository.deleteById(id);
    }

    public User fetchUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.isPresent() ? userOptional.get() : null;
    }

    public ResultPaginationDTO fetchAllUser(Specification<User> spec, Pageable pageable) {

        Page<User> pageUser = this.userRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();

        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageUser.getNumber() + 1);
        meta.setPageSize(pageUser.getSize());
        meta.setPages(pageUser.getTotalPages());
        meta.setTotal(pageUser.getTotalElements());

        rs.setMeta(meta);

        // remove sensitive data
        List<ResUserDTO> listUser = pageUser.getContent()
                .stream()
                .map(item -> new ResUserDTO(
                        item.getId(),
                        item.getName(),
                        item.getEmail(),
                        item.getGender(),
                        item.getAddress(),
                        item.getAge(),
                        item.getCreatedAt(),
                        item.getUpdatedAt(),
                        new ResUserDTO.CompanyUser(
                                item.getCompany() != null ? item.getCompany().getId() : 0,
                                item.getCompany() != null ? item.getCompany().getName() : null)))
                .collect(Collectors.toList());

        rs.setResult(listUser);

        return rs;
    }

    public User handleUpdateUser(User reqUser) {
        User currentUser = this.fetchUserById(reqUser.getId());

        if (reqUser.getCompany() != null) {
            Company company = this.companyService.fetchCompanyById(reqUser.getCompany().getId());
            if (company != null) {
                currentUser.setCompany(company);
            }
        }

        if (currentUser != null) {
            currentUser.setName(reqUser.getName());
            currentUser.setGender(reqUser.getGender());
            currentUser.setAge(reqUser.getAge());
            currentUser.setAddress(reqUser.getAddress());
            this.userRepository.save(currentUser);

        }
        return currentUser;

    }

    public User handleGetUserByUsername(String email) {
        return this.userRepository.findByEmail(email);
    }

    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public boolean isIdExist(long id) {
        return this.userRepository.existsById(id);
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User savedUser) {
        ResCreateUserDTO resCreateUserDTO = new ResCreateUserDTO();
        ResCreateUserDTO.CompanyUser companyUser = new ResCreateUserDTO.CompanyUser();

        resCreateUserDTO.setId(savedUser.getId());
        resCreateUserDTO.setName(savedUser.getName());
        resCreateUserDTO.setEmail(savedUser.getEmail());
        resCreateUserDTO.setGender(savedUser.getGender());
        resCreateUserDTO.setAddress(savedUser.getAddress());
        resCreateUserDTO.setAge(savedUser.getAge());
        resCreateUserDTO.setCreatedAt(savedUser.getCreatedAt());

        if (savedUser.getCompany() != null) {
            companyUser.setId(savedUser.getCompany().getId());
            companyUser.setName(savedUser.getCompany().getName());
            resCreateUserDTO.setCompany(companyUser);
        }

        return resCreateUserDTO;
    }

    public ResUpdateUserDTO convertToResUpdateUserDTO(User savedUser) {
        ResUpdateUserDTO resUpdateUserDTO = new ResUpdateUserDTO();
        ResUpdateUserDTO.CompanyUser companyUser = new ResUpdateUserDTO.CompanyUser();

        resUpdateUserDTO.setId(savedUser.getId());
        resUpdateUserDTO.setName(savedUser.getName());
        resUpdateUserDTO.setGender(savedUser.getGender());
        resUpdateUserDTO.setAddress(savedUser.getAddress());
        resUpdateUserDTO.setAge(savedUser.getAge());
        resUpdateUserDTO.setUpdatedAt(savedUser.getUpdatedAt());

        if (savedUser.getCompany() != null) {
            companyUser.setId(savedUser.getCompany().getId());
            companyUser.setName(savedUser.getCompany().getName());
            resUpdateUserDTO.setCompany(companyUser);
        }

        return resUpdateUserDTO;
    }

    public ResUserDTO convertToFetchUserDTO(User user) {
        ResUserDTO dto = new ResUserDTO();
        ResUserDTO.CompanyUser company = new ResUserDTO.CompanyUser();

        if (user.getCompany() != null) {
            company.setId(user.getCompany().getId());
            company.setName(user.getCompany().getName());
            dto.setCompany(company);
        }

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
        dto.setGender(user.getGender());
        dto.setAddress(user.getAddress());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }

    public void updateUserToken(String token, String email) {
        User currentUser = this.handleGetUserByUsername(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }

    public User getUserByRefreshTokenAndEmail(String token, String email) {
        return this.userRepository.findUserByRefreshTokenAndEmail(token, email);
    }
}
