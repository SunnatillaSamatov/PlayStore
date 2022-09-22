package com.example.petplaystore.service.impl;

import com.example.petplaystore.entity.Company;
import com.example.petplaystore.payload.ReqCompany;
import com.example.petplaystore.payload.Response;
import com.example.petplaystore.payload.Status;
import com.example.petplaystore.repository.CompanyRepository;
import com.example.petplaystore.service.CompanyService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final JdbcTemplate jdbcTemplate;

    public CompanyServiceImpl(CompanyRepository companyRepository, JdbcTemplate jdbcTemplate) {
        this.companyRepository = companyRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Response saveCompany(ReqCompany reqCompany) {
        Response response = new Response();
        Company company = new Company(
                reqCompany.getName(),
                reqCompany.getAddress(),
                reqCompany.getPhoneNumber());
        companyRepository.save(company);
        response.setStatus(new Status(0, "Progress Completed"));
        return response;
    }

    public Response updateCompany(Long id, ReqCompany reqCompany) {
        Response response = new Response();
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()) {
            Company company = byId.get();
            company.setName(reqCompany.getName());
            company.setAddress(reqCompany.getAddress());
            company.setPhoneNumber(reqCompany.getPhoneNumber());
            companyRepository.save(company);
            response.setStatus(new Status(0, "Progress Completed"));
        } else {
            response.setStatus(new Status(404, "Company not found"));
        }
        return response;
    }

    public Response deleteCompany(Long id) {
        Response response = new Response();
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()) {
            try {
                companyRepository.deleteById(id);
                response.setStatus(new Status(0, "Progress Completed"));
            } catch (Exception e) {
                response.setStatus(new Status(1, "This object any early related"));
            }
        } else {
            response.setStatus(new Status(404, "Company not found"));
        }
        return response;
    }

    @Override
    public Response companyList() {
        Response response = new Response();
        List<Company> all = companyRepository.findAll();
        response.setData(all);
        response.setStatus(new Status(0, "Progress Completed"));
        return response;
    }
}
