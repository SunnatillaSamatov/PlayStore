package com.example.petplaystore.service;

import com.example.petplaystore.payload.ReqCompany;
import com.example.petplaystore.payload.Response;

public interface CompanyService {
    Response saveCompany(ReqCompany reqCompany);

    Response updateCompany(Long id, ReqCompany reqCompany);

    Response deleteCompany(Long id);

    Response companyList();
}
