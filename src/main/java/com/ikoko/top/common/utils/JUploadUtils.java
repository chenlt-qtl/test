package com.ikoko.top.common.utils;

import java.io.File;
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
    public static File save(MultipartFile file,HttpServletRequest request) throws Exception{
        // 获取本地存储路径
        String path = getUploadPath(request);
        String fileName = file.getOriginalFilename();
        // 取得后缀
        String suffixString = fileName
                .substring(fileName.lastIndexOf(".") + 1);
        String ip = JStringUtils.getRemoteAddr(request);
        ip = ip.replaceAll(":", "_");//移除文件名中的冒号
        // 取得IP地址+时间戳 作为文件名 防止文件名重复
        String randomFileName = ip + "_" + System.currentTimeMillis();
        // 设定文件名称
        fileName = randomFileName + "." + suffixString;

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
    public static File save(String fileType,MultipartFile file,HttpServletRequest request) throws Exception{
        // 获取本地存储路径
        String dirName = DIR_NAMES.get(fileType);
        if(StringUtils.isBlank(dirName)){
            throw new WordException("上传类型参数错误");
        }
        String path = getUploadPath(request)+File.separator+dirName;
        String fileName = file.getOriginalFilename();
        // 取得后缀
        String suffixString = fileName
                .substring(fileName.lastIndexOf(".") + 1);
        String ip = JStringUtils.getRemoteAddr(request);
        ip = ip.replaceAll(":", "_");//移除文件名中的冒号
        // 取得IP地址+时间戳 作为文件名 防止文件名重复
        String randomFileName = ip + "_" + System.currentTimeMillis();
        // 设定文件名称
        fileName = randomFileName + "." + suffixString;

        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        file.transferTo(targetFile);
        return targetFile;
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
