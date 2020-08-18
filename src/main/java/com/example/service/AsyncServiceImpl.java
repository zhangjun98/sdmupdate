package com.example.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class AsyncServiceImpl implements AsyncService  {
    @Override
    @Async("taskExecutor")
    public void executeAsync(String id) {
        System.out.println("start executeAsync");
        System.out.println(id);
        System.out.println("end executeAsync");

    }

    @Override
    public ResponseEntity<byte[]> download(HttpServletRequest request,String id) throws IOException {
        File file = new File("D:"+File.separator+"EVCapture_3.9.8.exe");
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        int read = is.read(body);
        for(int i=0;i<10;i++){
            System.out.println(id+"---"+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<>(body, headers, statusCode);
        return entity;
    }
}
