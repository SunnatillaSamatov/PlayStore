package com.example.petplaystore.Controller;

import com.example.petplaystore.payload.ReqUserAccount;
import com.example.petplaystore.payload.Response;
import com.example.petplaystore.service.MobileApplicationService;
import com.example.petplaystore.service.UserAccountService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userAccount")
public class UserAccountController {

    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/add")
    public HttpEntity<?> userAccountSave(@RequestBody ReqUserAccount reqUserAccount) {
        Response response = userAccountService.saveUserAccount(reqUserAccount);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/{id}")
    public HttpEntity<?> categorySave(@PathVariable Long id, @RequestBody ReqUserAccount reqUserAccount) {
        Response response = userAccountService.updateUserAccount(id, reqUserAccount);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public HttpEntity<?> userAccountDelete(@RequestParam Long userAccountId, @RequestParam(required = false) Long id) {
        Response response = userAccountService.deleteUserAccount(userAccountId);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public HttpEntity<?> userAccountList() {
        Response response = userAccountService.listUserAccount();
        return ResponseEntity.ok(response);
    }
}
