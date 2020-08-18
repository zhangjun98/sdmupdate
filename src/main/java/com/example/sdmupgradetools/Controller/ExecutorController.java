package com.example.sdmupgradetools.Controller;


import com.example.service.AsyncServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequestMapping("/ececutor")
@Controller
public class ExecutorController {

    @Autowired
    private AsyncServiceImpl asyncService;
   @GetMapping("/ecec/{id}")
   @ResponseBody
    public String submit(@PathVariable("id") String id){

        //logger.info("start submit");

        //调用service层的任务
        asyncService.executeAsync(id);


        //logger.info("end submit");
//http://localhost:8080/ececutor/DownLoad/11
        return "success";

    }
    @RequestMapping(value = "/DownLoad/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(HttpServletRequest request,@PathVariable("id") String id) throws  IOException {
       return asyncService.download(request,id);
    }

}
