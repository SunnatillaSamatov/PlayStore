package com.example.petplaystore.service;

import com.example.petplaystore.payload.ReqUserAccount;
import com.example.petplaystore.payload.Response;

public interface UserAccountService {
    Response saveUserAccount(ReqUserAccount reqUserAccount);

    Response updateUserAccount(Long id, ReqUserAccount reqUserAccount);

    Response deleteUserAccount(Long id);

    Response listUserAccount();
}
