package com.example.petplaystore.service.impl;

import com.example.petplaystore.entity.Type;
import com.example.petplaystore.payload.ReqType;
import com.example.petplaystore.payload.Response;
import com.example.petplaystore.payload.Status;
import com.example.petplaystore.repository.TypeRepository;
import com.example.petplaystore.service.TypeService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    private final JdbcTemplate jdbcTemplate;

    public TypeServiceImpl(TypeRepository typeRepository, JdbcTemplate jdbcTemplate) {
        this.typeRepository = typeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Response saveType(ReqType reqType) {
        Response response = new Response();
        Type type = new Type(reqType.getName());
        typeRepository.save(type);
        response.setStatus(new Status(0, "Progress Successful"));
        return response;
    }

    @Override
    public Response updateType(Long id, ReqType reqType) {
        Response response = new Response();
        Optional<Type> byId = typeRepository.findById(id);
        if (byId.isPresent()) {
            Type type = byId.get();
            type.setName(reqType.getName());
            typeRepository.save(type);
            response.setStatus(new Status(0, "Progress Completed"));
        } else {
            response.setStatus(new Status(404, "Type not found"));
        }
        return response;
    }

    @Override
    public Response deleteType(Long typeId) {
        Response response = new Response();
        Optional<Type> byId = typeRepository.findById(typeId);
        if (byId.isPresent()) {
            try {
                typeRepository.deleteById(typeId);
                response.setStatus(new Status(0, "Progress Completed"));
            } catch (Exception e) {
                response.setStatus(new Status(1, "This object any early related"));
            }
        } else {
            response.setStatus(new Status(404, "Type not found"));
        }
        return response;
    }

    @Override
    public Response listType() {
        Response response = new Response();
        List<Type> all = typeRepository.findAll();
        response.setData(all);
        response.setStatus(new Status(0, "Progress Completed"));
        return response;
    }
}
