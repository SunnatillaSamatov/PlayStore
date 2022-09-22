package com.example.petplaystore.Controller;

import com.example.petplaystore.payload.ReqCompany;
import com.example.petplaystore.payload.Response;
import com.example.petplaystore.service.CompanyService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("")
    public HttpEntity<?> getCompany() {
        Response response = companyService.companyList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public HttpEntity<?> companySave(@RequestBody ReqCompany reqCompany) {
        Response response = companyService.saveCompany(reqCompany);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/{id}")
    public HttpEntity<?> companySave(@PathVariable Long id, @RequestBody ReqCompany reqCompany) {
        Response response = companyService.updateCompany(id, reqCompany);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public HttpEntity<?> companyDelete(@RequestParam Long companyId, @RequestParam(required = false) Long SetApp) {
        Response response = companyService.deleteCompany(companyId);
        return ResponseEntity.ok(response);
    }
}
