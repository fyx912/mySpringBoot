package com.ding.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.MessageDigest;
import java.time.LocalDate;

/**
 * @Auther: ding
 * @Date: 2019-06-12 17:37
 * @Description: 文件处理
 */
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    public static String IMAGES_TYPE = "jpg,png,tif,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,WMF,webp";
    public static String MOVIE = "mp4,3gp,mov,m4v,avi,dat,mkv,flv,vob,wmv,asf,asx,rm,rmvb";
    public static String APP = "apk,ipa";
    public static String DOC = "doc,docx,xls,xlsx,ppt,pptx,pdf";

    public static String getFileMD5(File file){
        String s = null;
        // 用来将字节转换成 16 进制表示的字符
        char[] hexDigits={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BufferedInputStream inFile = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inFile.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            byte[] tmp=md5.digest();// MD5 的计算结果是一个 128 位的长整数,用字节表示就是 16 个字节
            char[] str=new char[16*2];// 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16 进制需要 32 个字符
            int k=0;// 表示转换结果中对应的字符位置
            for(int i=0;i<16;i++){// 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte b = tmp[i];// 取第 i 个字节
                str[k++] = hexDigits[b >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[b & 0xf];     // 取字节中低 4 位的数字转换
            }
            s=new String(str);// 换后的结果转换为字符串
            inFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String getFileMD5(InputStream inputStream){
        String s = null;
        // 用来将字节转换成 16 进制表示的字符
        char[] hexDigits={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = bufferedInputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            byte[] tmp=md5.digest();// MD5 的计算结果是一个 128 位的长整数,用字节表示就是 16 个字节
            char[] str=new char[16*2];// 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16 进制需要 32 个字符
            int k=0;// 表示转换结果中对应的字符位置
            for(int i=0;i<16;i++){// 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte b = tmp[i];// 取第 i 个字节
                str[k++] = hexDigits[b >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[b & 0xf];     // 取字节中低 4 位的数字转换
            }
            s=new String(str);// 换后的结果转换为字符串
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     *
     * @Description:  文件目录是否存在不存在则创建
     * @param filePath  文件目录路径
     * @return:
     * @auther: ding
     * @date: 2019-06-12
     */
    public static void dirExists(String filePath){
        File file = new File(filePath);
        if (!file.isDirectory()&&!file.exists()){
            file.mkdirs();
        }
    }

    /**
     *
     * @Description:  文件目录是否存在不存在则创建
     * @param fileTypeName 文件类型名称
     * @param fileDirPath  文件目录保存路径
     * @return:
     * @auther: ding
     * @date: 2019-06-12
     */
    public static String dirExists(String fileTypeName, String fileDirPath){
        String saveFilePath = saveFileDirPath(fileTypeName,fileDirPath);
        File file = new File(saveFilePath);
        if (!file.isDirectory()&&!file.exists()){
            file.mkdirs();
        }
        return saveFileDirPath(fileTypeName,null);
    }

    /**
     * 判断当前文件夹是否存在，不存在则创建，返回当前的决定路径
     * @return
     */
    public static String fileDirPathAll(String fileTypeName, String fileDirPath){
        String saveFilePath = saveFileDirPath(fileTypeName,fileDirPath);
        File file = new File(saveFilePath);
        if (!file.isDirectory()&&!file.exists()){
            file.mkdirs();
        }
        return saveFileDirPath(fileTypeName,fileDirPath);
    }

    /**
     *
     * @Description: 不是文件返回JSON
     * @auther: tinTin
     * @date: 2019-06-13
     * @return: json
     */
    public static String isNotFile() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);
        jsonObject.put("msg", "failed,not is file!");
        return jsonObject.toJSONString();
    }

    /**
     * 获取当前日期,格式yyyy/MM/dd
     * @return
     */
    public static String dateMonthDay(){
        LocalDate date = LocalDate.now();
        StringBuffer sb = new StringBuffer();
        sb.append(File.separator).append(date.getYear()).append(File.separator).append(date.getMonthValue());
//                .append(File.separator).append(date.getDayOfMonth());
        return sb.toString();
    }

    /**
     *文件保存路径
     * @param fileTypeName 文件名类型
     * @param fileDirPath 保存路径
     * @return fileDirPath + ${fileType} +/年/月
     */
    public static String saveFileDirPath(String fileTypeName, String fileDirPath){
        if (fileDirPath!=null){
            return  fileDirPath+saveFileType(fileTypeName)+dateMonthDay();
        }else {
            return  saveFileType(fileTypeName)+dateMonthDay();
        }

    }

    /**
     * 获取文件后缀名
     * @param fileName 文件名
     * @return
     */
    public static String suffixName(String fileName){
       return  fileName.substring(fileName.toLowerCase().lastIndexOf(".")+1,fileName.length());
    }

    /**
     *  判断文件类型
     * @param fileName 文件名
     * @return
     */
    public static String saveFileType(String fileName){
        String base= File.separator;
        String fileType =suffixName(fileName);
        if (IMAGES_TYPE.contains(fileType))
            return base+"images";
        if (MOVIE.contains(fileType))
            return base+"video";
        if (APP.contains(fileType))
            return base+"app";
        if (DOC.contains(fileType))
            return base+"doc";
        return base+"default";
    }

    /**
     *
     * @Description: 保存文件
     * @param filePath 源文件路径
     * @param newFileName 保存的文件名
     * @param fileDirPath  保存目标文件的路径
     * @return: json
     * @auther: ding
     * @date:  2019-06-12
     */
    public static String saveFile(String filePath, String newFileName, String fileDirPath) throws IOException {
        File file = new File(filePath);
        if (file.isFile()){
            return  saveFile(file,newFileName,fileDirPath);
        }
        return isNotFile();
    }

    /**
     *
     * @Description: 保存文件
     * @param file 源文件
     * @param newFileName 保存的文件名
     * @param fileDirPath  保存目标文件的路径
     * @return: json
     * @auther: ding
     * @date:  2019-06-12
     */
    public static String saveFile(File file, String newFileName, String fileDirPath) throws IOException {
        if (file.isFile()) {
            return  saveFile(new FileInputStream(file), newFileName, fileDirPath);
        }
        logger.info("this is not file! file:{}",newFileName);
        return isNotFile();
    }

    /**
     *
     * @Description: 保存文件
     * @param inputStream 文件流
     * @param newFileName 保存的文件名
     * @param fileDirPath 保存目标文件
     * @return:
     * @auther: ding
     * @date: 2019-06-12
     */
    public static String saveFile(InputStream inputStream, String newFileName, String fileDirPath){
        BufferedOutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        JSONObject jsonObject = new JSONObject();
        try {
            int len;
            String saveFileDirPath=  fileDirPathAll(newFileName,fileDirPath);
            String filePath = saveFileDirPath + File.separator + newFileName;
            bufferedInputStream = new BufferedInputStream(inputStream);
            outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] bs = new byte[1024];
            while ((len = bufferedInputStream.read(bs)) != -1) {
                outputStream.write(bs, 0, len);
            }
            String appFilePath = dirExists(newFileName,fileDirPath) + File.separator + newFileName;
            jsonObject.put("code", 0);
            jsonObject.put("msg", "success");
            jsonObject.put("data",appFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Save file failed. exception message:{}", e.getMessage());
            logger.error("Exception:", e);
            jsonObject.put("code", 1);
            jsonObject.put("msg", "文件上传失败,"+e.getCause().getMessage());
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                logger.error(" outputStream or inputStream close failed. exception message:{}", e.getMessage());
                logger.error("Exception:", e);
                e.printStackTrace();
            }
        }
        logger.info("save file json info :{}",jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    /**
     * 删除文件,不删除目录
     * @param filePath
     * @return
     */
    public static String deleteFile(String filePath) {
    	File file = new File(filePath);
    	 JSONObject jsonObject = new JSONObject();
    	if(file.isFile()) {
    		file.delete();
    		 jsonObject.put("code", 0);
             jsonObject.put("msg", "success");
    	}else {
    		jsonObject.put("code", 1);
    		jsonObject.put("msg", "failed");
    	}
    	return jsonObject.toJSONString();
    }

    /**
     * 删除文件,不删除目录
     * @param file
     * @return
     */
    public static String deleteFile(File file) {
    	 JSONObject jsonObject = new JSONObject();
    	if(file.isFile()) {
    		file.delete();
    		 jsonObject.put("code", 0);
             jsonObject.put("msg", "success");
    	}else {
    		jsonObject.put("code", 1);
    		jsonObject.put("msg", "failed");
    	}
    	return jsonObject.toJSONString();
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static void main(String[] args) throws IOException {
//        String filePath = "/Users/ding/app/test.jpg";
        String filePath = "/Users/ding/Downloads/IMG_0596.MOV";
//        String dirPath = "/Users/ding/app/bee/";
        File file = new File(filePath);
//        System.out.println(saveFileDirPath(file.getName(),dirPath));
//        String flag = null;
//        try {
//            flag = FileUtils.saveFile(new FileInputStream(file),file.getName(),dirPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(flag);
//
//        String delFile = "/Users/ding/app/bee/default";
//        deleteDir(new File(delFile));
        for (int i = 0; i < 10; i++) {
            String value =  FileUtils.getFileMD5(new FileInputStream(file));
            if (value.length()!=32){
                System.out.println(value.length());
            }else {
                System.out.println(value+"\t"+value.length());
            }

        }

    }
}
