package com.example.petplaystore.Controller;

import com.example.petplaystore.payload.ReqMobileApplication;
import com.example.petplaystore.payload.ReqMobileApplicationFilter;
import com.example.petplaystore.payload.Response;
import com.example.petplaystore.service.MobileApplicationService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mobileApplication")
public class MobileApplicationController {

    private final MobileApplicationService mobileApplicationService;

    public MobileApplicationController(MobileApplicationService mobileApplicationService) {
        this.mobileApplicationService = mobileApplicationService;
    }

    @GetMapping
    public HttpEntity<?> list(){
        Response list = mobileApplicationService.getList();
        return ResponseEntity.ok(list);
    }

    @PostMapping()
    public HttpEntity<?> updateAdd(@RequestBody ReqMobileApplication reqMobileApplication){
        Response response = mobileApplicationService.addUpdate(reqMobileApplication);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filter")
    public HttpEntity<?> filterList(@RequestBody ReqMobileApplicationFilter reqMobileApplicationFilter){
        Response response = mobileApplicationService.filter(reqMobileApplicationFilter);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete")
    public HttpEntity<?> deleteApp(@RequestParam Long appId){
        Response response = mobileApplicationService.delete(appId);
        return ResponseEntity.ok(response);
    }
}
