/**
 * Project Name: sdm-upgradetools-web
 * File Name: FTP
 * Package Name: com.example.sdmupgradetools.utils
 * Date: 2020/5/12 14:36
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.example.sdmupgradetools.utils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.scheduling.annotation.Async;
import java.io.IOException;
import java.io.InputStream;
import static com.example.sdmupgradetools.utils.FtpClientUtil.ftpConnection;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 14:36 2020/5/12
 */
@Async
public class FTPConnect {

    String productversion;

    String produceName;

    public FTPClient getFtpClient() {
        return ftpClient;
    }
    FTPClient ftpClient;

    public void setproduceName(String produceName) {
        this.produceName = produceName;
    }


    public void setProductversion(String productversion) {
        this.productversion = productversion;
    }
    private String getRemotePath(){
        String remotePath = "/"+produceName+"/"+productversion+"/";
        return remotePath;
    }

    public FTPConnect() throws IOException {
        this.ftpClient = ftpConnection();
    }

    public  String getResource()  {
        String relativePath = "/"+produceName+"/"+productversion;
        String produce = "";
        //转移到FTP服务器目录
        try {
            boolean chg=ftpClient.changeWorkingDirectory(new String(relativePath.getBytes("GBK"),"ISO-8859-1"));
            if (chg){
                FTPFile[] files =  ftpClient.listFiles();
                if (files.length>0){
                    for (FTPFile ff:files){
                        String suffix = ff.getName().substring(ff.getName().lastIndexOf(".") + 1);
                        if (suffix.equals("zip")){
                            produce=ff.getName();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return produce;
    }
    /**
     *
     * @return 上传的状态
     * @throws IOException
     */
    public  InputStream getInputStream(long fileChipCursor) throws IOException {
        //设置被动模式
        ftpClient.enterLocalPassiveMode();
        ftpClient.changeWorkingDirectory(new String(getRemotePath().getBytes("GBK"),"ISO-8859-1"));
        ftpClient.setRestartOffset(fileChipCursor);
        return ftpClient.retrieveFileStream(new String(getResource().getBytes("GBK"),"iso-8859-1"));
    }

    public  long getRemoteFileSize() throws IOException {
        //检查远程文件是否存在 并设置编码格式 防止中文名称的 文件下载为空
        FTPFile[] files = ftpClient.listFiles(new String(getRemotePath().getBytes("GBK"),"iso-8859-1"));
        long lRemoteSize=0L;
        if (files.length>0){
            for (FTPFile ff:files){
                String suffix = ff.getName().substring(ff.getName().lastIndexOf(".") + 1);
                if (suffix.equals("zip")){
                    lRemoteSize=ff.getSize();
                }
            }
        }

        return lRemoteSize;
    }

    public static void main(String[] args) throws IOException {
        FTPClient ftpClient = ftpConnection();
        System.out.println(ftpClient);
    }
}
