/**
 * Project Name: sdm-upgradetools-web
 * File Name: httpController
 * Package Name: com.example.sdmupgradetools.Controller
 * Date: 2020/5/12 14:16
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.example.sdmupgradetools.Controller;


import com.example.sdmupgradetools.model.VersionInfo;
import com.example.sdmupgradetools.utils.FTPConnect;
import com.example.sdmupgradetools.utils.LoadVersionInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 14:16 2020/5/12 
 */
@Controller
public class httpController {

    @RequestMapping(value = "/download/{path1}/{path2}", method = RequestMethod.GET)
    public void download(@PathVariable("path1") String path1,@PathVariable("path2") String path2, HttpServletRequest request, HttpServletResponse response) {
        try{
            //EasyConnect6301.zip
            FTPConnect Ftp = new FTPConnect();
            Ftp.setproduceName(path1);
            Ftp.setProductversion(path2);
            String headerInfo = request.getHeader("Range");
            if (headerInfo != null) {
                response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
            }
            // 表示下载范围的pojo
            ResponseContentRange range = getRange(Ftp.getRemoteFileSize(), headerInfo);
            String fileName = "EasyConnect6301.zip";
            response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
            if (request.getHeader(HttpHeaders.USER_AGENT).contains("MSIE")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            response.setContentType("application/octet-stream");
            response.setContentLengthLong(Ftp.getRemoteFileSize());
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Content-Range", "bytes " + range.getStartIndex() + "-" + (range.getStartIndex()
                    + range.getContentSize() - 1) + "/" + Ftp.getRemoteFileSize());
            byte[] buffer = new byte[1024*1024];
            int n;
            int writeCount = 0;
            OutputStream outputStream = response.getOutputStream();
            InputStream in =Ftp.getInputStream((int) range.getStartIndex());
            while ((n = in.read(buffer))!= -1 && writeCount < range.getContentSize()) {
                    outputStream.write(buffer, 0, n);
                     writeCount += n;
            }
            in.close();
            Ftp.getFtpClient().completePendingCommand();
            Ftp.getFtpClient().logout();
            Ftp.getFtpClient().disconnect();
        } catch (IOException ignore) {
        }
    }

    /**
     * 根据给定的rangeInfo，解析出回复的内容的范围
     *
     * @param maxSize   范围的最大值
     * @param rangeInfo rangeInfo
     * @return
     */
    private ResponseContentRange getRange(long maxSize, String rangeInfo) {
        long startIndex = 0L, contentLength = maxSize;
        if (rangeInfo != null && rangeInfo.trim().length() > 0) {
            String rangBytes = rangeInfo.replaceAll("bytes=", "");
            if (rangBytes.endsWith("-")) {
                startIndex = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
                contentLength = maxSize - startIndex;
            } else if (rangBytes.startsWith("-")) {
                startIndex = Long.parseLong(rangBytes.substring(rangBytes.indexOf("-") + 1));
                contentLength = maxSize - startIndex;
            } else {
                String[] indexs = rangBytes.split("-");
                startIndex = Long.parseLong(indexs[0]);
                contentLength = Long.parseLong(indexs[1]) - startIndex + 1;
            }
        }
        return new ResponseContentRange(startIndex, contentLength);
    }
    class ResponseContentRange {
        private long startIndex;
        private long ContentSize;

        public long getStartIndex() {
            return startIndex;
        }
        ResponseContentRange(long startIndex,long contentLength){
            this.ContentSize=contentLength;
            this.startIndex=startIndex;
        }
        public void setStartIndex(long startIndex) {
            this.startIndex = startIndex;
        }

        public long getContentSize() {
            return ContentSize;
        }

        public void setContentSize(long contentLength) {
            this.ContentSize = contentLength;
        }


    }
}
