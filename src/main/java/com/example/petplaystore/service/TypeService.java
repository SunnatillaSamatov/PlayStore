package com.example.petplaystore.service;

import com.example.petplaystore.payload.ReqType;
import com.example.petplaystore.payload.Response;


public interface TypeService {
    Response saveType(ReqType reqType);

    Response updateType(Long id, ReqType reqType);

    Response deleteType(Long typeId);

    Response listType();
}
