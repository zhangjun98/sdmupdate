package com.example.sdmupgradetools.utils;


import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;


/**
 * @Description ftp文件传输工具类
 * @Auther zhoujinqiao
 * @Data 2020/4/27 10:38
 **/
public class FtpClientUtil {


    //账号
    private static String username = "adminftp";
    //密码
    private static String password = "123456";
    //默认地址
    private static String ip = "172.16.15.250";
    //默认端口号
    private static String port = "21";


    /**
     * ftp链接
     *
     * @throws IOException
     */
    public static FTPClient ftpConnection() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(0);
        try {
            ftpClient.connect(ip, Integer.parseInt(port));
            ftpClient.login(username, password);
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                ftpClient.disconnect();
                System.exit(1);
            }
            //告诉对面服务器开一个端口
            ftpClient.enterLocalPassiveMode();
            ftpClient.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");
            ftpClient.configure(conf);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * 下载方法
     *
     * @param ftpClient   FTPClient对象
     * @param newFileName 新文件名
     * @param fileName    原文件名
     * @param downUrl     下载路径
     * @return
     * @throws IOException
     */
    public static boolean downFile(FTPClient ftpClient, String newFileName, String fileName, String downUrl) throws IOException {
        boolean isTrue = false;
        OutputStream os = null;
        File localFile = new File(downUrl + "/" + newFileName);
        os = new FileOutputStream(localFile);
        isTrue = ftpClient.retrieveFile(new String(fileName.getBytes(), "ISO-8859-1"), os);
        os.close();
        close(ftpClient);
        return isTrue;
    }

    /**
     * 断开FTP连接
     *
     * @param ftpClient 初始化的对象
     * @throws IOException
     */
    public static void close(FTPClient ftpClient) throws IOException {
        if (ftpClient != null && ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }


    public static void main(String[] args) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect("172.16.15.250", Integer.parseInt("21"));
        ftpClient.login("adminftp", "123456");
        System.out.println(ftpClient);
    }


}
