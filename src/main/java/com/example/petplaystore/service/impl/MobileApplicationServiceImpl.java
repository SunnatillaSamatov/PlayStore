package com.example.petplaystore.service.impl;

import com.example.petplaystore.entity.Category;
import com.example.petplaystore.entity.Company;
import com.example.petplaystore.entity.MobileApplication;
import com.example.petplaystore.entity.Type;
import com.example.petplaystore.payload.ReqMobileApplication;
import com.example.petplaystore.payload.ReqMobileApplicationFilter;
import com.example.petplaystore.payload.Response;
import com.example.petplaystore.payload.Status;
import com.example.petplaystore.repository.CategoryRepository;
import com.example.petplaystore.repository.CompanyRepository;
import com.example.petplaystore.repository.MobileApplicationRepository;
import com.example.petplaystore.repository.TypeRepository;
import com.example.petplaystore.service.MobileApplicationService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MobileApplicationServiceImpl implements MobileApplicationService {

    private final MobileApplicationRepository mobileApplicationRepository;
    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;
    private final TypeRepository typeRepository;
    private final JdbcTemplate jdbcTemplate;

    public MobileApplicationServiceImpl(MobileApplicationRepository mobileApplicationRepository, CategoryRepository categoryRepository, CompanyRepository companyRepository, TypeRepository typeRepository, JdbcTemplate jdbcTemplate) {
        this.mobileApplicationRepository = mobileApplicationRepository;
        this.categoryRepository = categoryRepository;
        this.companyRepository = companyRepository;
        this.typeRepository = typeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Response addUpdate(ReqMobileApplication reqMobileApplication) {
        Response response = new Response();
        MobileApplication mobileApplication = new MobileApplication();
        Optional<Category> byId1 = categoryRepository.findById(reqMobileApplication.getCategoryId());
        if (byId1.isPresent()) {
            Optional<Company> byId2 = companyRepository.findById(reqMobileApplication.getCompanyId());
            if (byId2.isPresent()) {
                if (reqMobileApplication.getId() != null) {
                    Optional<MobileApplication> byId = mobileApplicationRepository.findById(reqMobileApplication.getId());
                    if (byId.isPresent()) {
                        mobileApplication = byId.get();
                    } else {
                        response.setStatus(new Status(404, "MobileApplication not found"));
                        return response;
                    }
                }
                mobileApplication.setCategory(byId1.get());
                mobileApplication.setCompany(byId2.get());
                mobileApplication.setDate(reqMobileApplication.getDate());
                mobileApplication.setDescription(reqMobileApplication.getDescription());
                mobileApplication.setName(reqMobileApplication.getName());
                mobileApplication.setSize(reqMobileApplication.getSize());
                List<Type> allById = typeRepository.findAllById(reqMobileApplication.getTypeId());
                mobileApplication.setType(allById);
                mobileApplicationRepository.save(mobileApplication);
                response.setStatus(new Status(0, "Progress Completed"));
            } else {
                response.setStatus(new Status(404, "Company not found"));
            }
        } else {
            response.setStatus(new Status(404, "Category not found"));
        }
        return response;
    }

    public Response delete(Long id) {
        Response response = new Response();
        Optional<MobileApplication> byId = mobileApplicationRepository.findById(id);
        if (byId.isPresent()) {
            try {
                mobileApplicationRepository.deleteById(id);
                response.setStatus(new Status(0, "Progress Completed"));
            } catch (Exception e) {
                response.setStatus(new Status(1, "This object any early related"));
            }
        } else {
            response.setStatus(new Status(404, "MobileApplication not found"));
        }
        return response;
    }

    public Response getList() {
        Response response = new Response();
            List<Map<String, Object>> maps = jdbcTemplate.queryForList("select ma.id, ma.name, (select coalesce(round(sum(r.rating), 2), 0) / nullif(coalesce(round(count(r.rating), 2), 0), 0) from review r where r.mobile_application_id = ma.id) as rating, (select count(*) from user_account au where au.mobile_application_id = ma.id) as users ,ma.size, ma.description, ma.date, c.id as company_id, c.name as company_name, c2.id as category_id, c2.name as category_name from mobile_application ma left join company c on c.id = ma.company_id left join category c2 on c2.id = ma.category_id");
        for (Map<String, Object> map : maps) {
            List<Map<String, Object>> maps1 = jdbcTemplate.queryForList("select t.id, t.name from mobile_application_type mat left join type t on t.id = mat.type_id where mat.mobile_application_id = ?", map.get("id"));
            map.put("type", maps1);
        }
        response.setData(maps);
        response.setStatus(new Status(0, "Progress Completed"));
        return response;
    }

    public Response filter(ReqMobileApplicationFilter reqMobileApplicationFilter) {
        Response response = new Response();
        String sql = "";
        if (reqMobileApplicationFilter.getSize() != null) {
            sql += "where ma.size = '" + reqMobileApplicationFilter.getSize() + "'";
        }
        if (reqMobileApplicationFilter.getName() != null) {
            if (sql.length() > 0) {
                sql += " and ma.name = '" + reqMobileApplicationFilter.getName() + "'";
            } else {
                sql += "where ma.name = '" + reqMobileApplicationFilter.getName() + "'";
            }
        }
        if (reqMobileApplicationFilter.getCategoryId() != null) {
            if (sql.length() > 0) {
                sql += " and c2.id = '" + reqMobileApplicationFilter.getCategoryId() + "'";
            } else {
                sql += "where c2.id = '" + reqMobileApplicationFilter.getCategoryId() + "'";
            }
        }
        if (reqMobileApplicationFilter.getCompanyId() != null) {
            if (sql.length() > 0) {
                sql += " and c.id = '" + reqMobileApplicationFilter.getCompanyId() + "'";
            } else {
                sql += "where c.id = '" + reqMobileApplicationFilter.getCompanyId() + "'";
            }
        }
        if (reqMobileApplicationFilter.getFromDate() != null && reqMobileApplicationFilter.getToDate() != null) {
            if (sql.length() > 0) {
                sql += " and (ma.date between '" + reqMobileApplicationFilter.getFromDate() + "' and '" + reqMobileApplicationFilter.getToDate() + "' )";
            }else {
                sql += "where (ma.date between '" + reqMobileApplicationFilter.getFromDate() + "' and '" + reqMobileApplicationFilter.getToDate() + "' )";
            }
        }
        if (reqMobileApplicationFilter.getRating() != null) {
            if (sql.length() > 0) {
                sql += " and (select coalesce(round(sum(r.rating), 2), 0) / nullif(coalesce(round(count(r.rating), 2), 0),0) from review r where r.mobile_application_id = ma.id) = '" + reqMobileApplicationFilter.getRating() + "' ";
            }else {
                sql += "where (select coalesce(round(sum(r.rating), 2), 0) / nullif(coalesce(round(count(r.rating), 2), 0), 0) from review r where r.mobile_application_id = ma.id) = '" + reqMobileApplicationFilter.getRating() + "' ";
            }
        }
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select ma.id, ma.name, (select coalesce(round(sum(r.rating), 2), 0) / nullif(coalesce(round(count(r.rating), 2), 0), 0) from review r where r.mobile_application_id = ma.id) as rating, (select count(*) from user_account au where au.mobile_application_id = ma.id) as users ,ma.size, ma.description, ma.date, c.id as company_id, c.name as company_name, c2.id as category_id, c2.name as category_name from mobile_application ma left join company c on c.id = ma.company_id left join category c2 on c2.id = ma.category_id " + sql);
        for (Map<String, Object> map : maps) {
            List<Map<String, Object>> maps1 = jdbcTemplate.queryForList("select t.id, t.name from mobile_application_type mat left join type t on t.id = mat.type_id where mat.mobile_application_id = ?", map.get("id"));
            map.put("type", maps1);
        }
        response.setData(maps);
        response.setStatus(new Status(0, "Progress Completed"));
        return response;
    }
}
