package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public List<Company> fetchAllCompany() {
        return this.companyRepository.findAll();
    }

    public Company fetchCompanyById(Long id) {
        Optional<Company> companyOptional = this.companyRepository.findById(id);
        return companyOptional.isPresent() ? companyOptional.get() : null;
    }

    public Company handleUpdateCompany(Company requestCompany) {
        Company currentCompany = this.fetchCompanyById(requestCompany.getId());
        if (currentCompany != null) {
            currentCompany.setName(requestCompany.getName());
            currentCompany.setDescription(requestCompany.getDescription());
            currentCompany.setAddress(requestCompany.getAddress());
            currentCompany.setLogo(requestCompany.getLogo());
            this.companyRepository.save(currentCompany);
        }
        return null;

    }

    public void handleDeleteCompany(long id) {
        this.companyRepository.deleteById(id);
    }
}
