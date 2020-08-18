package com.example.sdmupgradetools.utils;

import java.io.*;


/**
 * @Description TODO
 * @Auther zhoujinqiao
 **/
public class pointUtils {
    private static int position = -1;
    /**
     *  输入输出流
     */
    FileInputStream fis = null;
    FileOutputStream fos = null;
    // 数据缓冲区
    byte[] buf = new byte[10];
    public  void upLoad(String sourcePath,String localPath){
        File sourceFile = new File(sourcePath);
        File targetFile = new File(localPath);
        try {
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(targetFile);
            // 数据读写
            while (fis.read(buf) != -1) {
                fos.write(buf);
                // 当已经上传了30字节的文件内容时，网络中断了
                if (targetFile.length() == 30) {
                    position = 30;
                    throw new FileAccessException();
                }
            }
        }catch (FileAccessException e){
            keepGoing(sourceFile,targetFile, position);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("指定文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭输入输出流
                if (fis != null)
                    fis.close();

                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void keepGoing(File sourceFile, File targetFile, int position) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            RandomAccessFile readFile = new RandomAccessFile(sourceFile, "rw");
            RandomAccessFile writeFile = new RandomAccessFile(targetFile, "rw");
            readFile.seek(position);
            writeFile.seek(position);
            // 数据缓冲区
            byte[] buf = new byte[10];
            // 数据读写
            while (readFile.read(buf) != -1) {
                writeFile.write(buf);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        pointUtils pointUtils = new pointUtils();
        String resourcePath = "D:\\sdm-upgradetools.zip";
        String localPath = "E:\\testupload\\sdm-upgradetools.zip";
        pointUtils.upLoad(resourcePath,localPath);

    }

    }



