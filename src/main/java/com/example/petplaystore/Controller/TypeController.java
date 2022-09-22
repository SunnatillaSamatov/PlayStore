package com.example.petplaystore.Controller;

import com.example.petplaystore.payload.ReqType;
import com.example.petplaystore.payload.Response;
import com.example.petplaystore.service.TypeService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("type")
public class TypeController {
    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public HttpEntity<?> getType() {
        Response response = typeService.listType();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public HttpEntity<?> typeSave(@RequestBody ReqType reqType) {
        Response response = typeService.saveType(reqType);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/{id}")
    public HttpEntity<?> typeUpdate(@PathVariable Long id, @RequestBody ReqType reqType) {
        Response response = typeService.updateType(id, reqType);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public HttpEntity<?> deleteRemove(@RequestParam Long typeId) {
        Response response = typeService.deleteType(typeId);
        return ResponseEntity.ok(response);
    }

}
