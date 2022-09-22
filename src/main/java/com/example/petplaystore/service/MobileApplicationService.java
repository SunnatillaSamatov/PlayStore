package com.example.petplaystore.service;

import com.example.petplaystore.payload.ReqMobileApplication;
import com.example.petplaystore.payload.ReqMobileApplicationFilter;
import com.example.petplaystore.payload.Response;

public interface MobileApplicationService {
    Response addUpdate(ReqMobileApplication reqMobileApplication);
    Response delete(Long id);
    Response getList();
    Response filter(ReqMobileApplicationFilter reqMobileApplicationFilter);

}
