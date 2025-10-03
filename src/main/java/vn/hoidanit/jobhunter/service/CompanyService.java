package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
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

    public ResultPaginationDTO fetchCompany(Specification<Company> spec, Pageable pageable) {
        Page<Company> pageCompany = this.companyRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();

        Meta meta = new Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageCompany.getTotalPages());
        meta.setTotal(pageCompany.getTotalElements());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(pageCompany.getContent());

        return resultPaginationDTO;
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
            return this.companyRepository.save(currentCompany);
        }
        return null;

    }

    public void handleDeleteCompany(long id) {
        this.companyRepository.deleteById(id);
    }
}
