package com.example.petplaystore.service.impl;

import com.example.petplaystore.entity.MobileApplication;
import com.example.petplaystore.entity.UserAccount;
import com.example.petplaystore.payload.ReqUserAccount;
import com.example.petplaystore.payload.Response;
import com.example.petplaystore.payload.Status;
import com.example.petplaystore.repository.MobileApplicationRepository;
import com.example.petplaystore.repository.UserAccountRepository;
import com.example.petplaystore.service.MobileApplicationService;
import com.example.petplaystore.service.UserAccountService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final JdbcTemplate jdbcTemplate;
    private final MobileApplicationRepository mobileApplicationRepository;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, JdbcTemplate jdbcTemplate, MobileApplicationService mobileApplicationService, MobileApplicationRepository mobileApplicationRepository) {
        this.userAccountRepository = userAccountRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.mobileApplicationRepository = mobileApplicationRepository;
    }


    public Response saveUserAccount(ReqUserAccount reqUserAccount) {
        Optional<MobileApplication> byId = mobileApplicationRepository.findById(reqUserAccount.getMobileApplicationId());
        Response response = new Response();
        if (byId.isPresent()) {
            UserAccount userAccount = new UserAccount();
            userAccount.setUsername(reqUserAccount.getUsername());
            userAccount.setMobileApplication(byId.get());
            userAccountRepository.save(userAccount);
            response.setStatus(new Status(0, "Company added Successfully"));
        }else{
            response.setStatus(new Status(404,"UserAccount id not find"));
        }


        return response;
    }

    @Override
    public Response updateUserAccount(Long id, ReqUserAccount reqUserAccount) {
        Response response = new Response();
        Optional<UserAccount> byId = userAccountRepository.findById(id);
        if (byId.isPresent()) {
            Optional<MobileApplication> byId1 = mobileApplicationRepository.findById(reqUserAccount.getMobileApplicationId());
            if (byId1.isPresent()) {
                UserAccount userAccount = byId.get();
                userAccount.setUsername(reqUserAccount.getUsername());
                userAccount.setMobileApplication(byId1.get());
                userAccount.setDate(userAccount.getDate());
                userAccountRepository.save(userAccount);
                response.setStatus(new Status(0, "Progress completed"));
            }else {
                response.setStatus(new Status(404, "Application not found"));
            }
        }else {
            response.setStatus(new Status(404, "UserAccount not found"));
        }
        return response;
    }

    public Response deleteUserAccount(Long id) {
        Response response = new Response();
        Optional<UserAccount> byId = userAccountRepository.findById(id);
        if (byId.isPresent()) {
            try {
                userAccountRepository.deleteById(id);
                response.setStatus(new Status(0, "Success. Deleted"));
            } catch (Exception e) {
                response.setStatus(new Status(1, "This object any early related"));
            }
        } else {
            response.setStatus(new Status(404, "UserAccount not found"));
        }
        return response;
    }

    public Response listUserAccount() {
        Response response = new Response();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select ua.id, ua.username, ua.date, ua.mobile_application_id, ma.name from user_account ua left join mobile_application ma on ma.id = ua.mobile_application_id order by ua.id asc");
        response.setData(maps);
        response.setStatus(new Status(0, "Progress completed"));
        return response;
    }

}
