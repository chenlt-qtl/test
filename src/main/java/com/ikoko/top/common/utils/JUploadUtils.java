package com.ikoko.top.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ikoko.top.common.config.JConfig;
import com.ikoko.top.common.exception.WordException;

/**
 * 上传文件服务工具类
 * @author iutils.cn
 */
public class JUploadUtils {
    
    private static Map<String,String> DIR_NAMES = new HashMap<String,String>(){{
        put("1", "photo");
        put("2", "mp3");
        put("3", "ph_mp3");
    }};

    /**
     * 文件保存 默认保存在工程同级目录下面
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    public static File save(MultipartFile file,String path,String ip) throws Exception{
        
        String fileName =  getRandomFileName(file.getOriginalFilename(),ip);

        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        file.transferTo(targetFile);
        return targetFile;
    }

    /**
     * 文件保存 自定义保存路径，在upload下面自定义文件
     * @param fileType 自定义文件分类
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    public static File save(String fileType,MultipartFile file,String path,String ip) throws Exception{
        // 获取本地存储路径
        String dirName = DIR_NAMES.get(fileType);
        if(StringUtils.isBlank(dirName)){
            throw new WordException("上传类型参数错误");
        }
        path = path+File.separator+dirName;
        String fileName =  getRandomFileName(file.getOriginalFilename(),ip);

        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        file.transferTo(targetFile);
        return targetFile;
    }
    
    
    /**
     * 文件保存 自定义保存路径，在upload下面自定义文件
     * @param fileType 自定义文件分类
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    public static String save(String fileType,String urlStr,String path,String ip) throws Exception{
        // 获取本地存储路径
        String dirName = DIR_NAMES.get(fileType);
        if(StringUtils.isBlank(dirName)){
            throw new WordException("上传类型参数错误");
        }
        path = path+File.separator+dirName;
        
        String fileName = getRandomFileName(urlStr,ip);
        
        URL url = new URL(urlStr);    
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
                //设置超时间为3秒  
        conn.setConnectTimeout(3*1000);  
        //防止屏蔽程序抓取而返回403错误  
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
  
        //得到输入流  
        InputStream inputStream = conn.getInputStream();    
        //获取自己数组  
        byte[] data = readInputStream(inputStream);      
  
        //文件保存位置  
        File saveDir = new File(path);  
        if(!saveDir.exists()){  
            saveDir.mkdir();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(data);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }  

        return fileName;
    }
    
    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    } 

    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        // 取得后缀
       return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    
    /**
     * 获取随机的文件名
     * @param fileName
     * @param ip
     * @return
     */
    public static String getRandomFileName(String fileName,String ip){
     // 取得后缀
        String suffixString = getSuffix(fileName);
        ip = ip.replaceAll(":", "_");//移除文件名中的冒号
        // 取得IP地址+时间戳 作为文件名 防止文件名重复
        String randomFileName = ip + "_" + System.currentTimeMillis();
        // 文件名称
        return randomFileName + "." + suffixString;
    }
    
    /**
     * 获取文件存放位置
     * @param request
     * @return
     * @throws WordException 
     */
    public static String getUploadPath(HttpServletRequest request) throws WordException{
        String path = request.getSession().getServletContext().getRealPath("/");
        path = path.substring(0,path.substring(0,path.length()-1).lastIndexOf(File.separator)+1);
        return path+JConfig.getConfig(JConfig.FILEUPLOAD);
    }
    
    public static void del(String fileType,String fileName,HttpServletRequest request) throws Exception{
        String dirName = DIR_NAMES.get(fileType);
        if(StringUtils.isBlank(dirName)){
            throw new WordException("上传类型参数错误");
        }
        String path = getUploadPath(request)+File.separator+dirName;

        File targetFile = new File(path, fileName);
        if (targetFile.exists()) {
            targetFile.delete();
        }
    }

}
