package com.example.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public interface AsyncService {
    void executeAsync(String id);

    ResponseEntity<byte[]> download(HttpServletRequest request,String id)throws IOException;
}
