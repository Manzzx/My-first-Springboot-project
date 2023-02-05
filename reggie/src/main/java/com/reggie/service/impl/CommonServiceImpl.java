package com.reggie.service.impl;

import com.reggie.common.R;
import com.reggie.exception.SystemException;
import com.reggie.service.CommonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Service
public class CommonServiceImpl implements CommonService {

    @Value("${reggie.basePath}")
    private String basePath;

    /**
     * 上传图片到服务器
     *
     * @param file
     * @return
     */
    @Override
    public R<String> uploadImg(MultipartFile file) {
        //将上传的图片存进项目资源下
        String originalFilename = file.getOriginalFilename();
        //从最后一个符号为‘.’包括‘.’开始截（后缀）
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")); //结果为".***"
        //使用UUID重新生成文件名（不包括后缀）来避免有重复名称的图片
        String filName = UUID.randomUUID().toString() + suffix;//结果为"*******.***"
        //判断配置文件中配置的包是否存在
        File dir = new File(basePath);
        if (!dir.exists()) {
            //目录不存在需要创建
            dir.mkdirs();
        }
        //将图片copy到本地目录
        try {
            file.transferTo(new File(basePath + filName));
        } catch (IOException e) {
            throw new SystemException("图片上传失败", e);
        }
        //将文件名返回给前端，到时候提交菜品会将文件名存进数据库
        return R.success(filName);
    }

    /**
     * 将本地图片传输给浏览器
     *
     * @param name
     * @param response
     */
    @Override
    public void downloadImg(String name, HttpServletResponse response) {
        try {
            //获取图片输入流
            BufferedInputStream fileInputStream = new BufferedInputStream(
                    new FileInputStream(new File(basePath + name)));
            //获取响应流
            ServletOutputStream fileOutPutStream = response.getOutputStream();
            //设置响应数据类型为图片类型
            response.setContentType("image/jpeg");

            byte[] bytes = new byte[1024];
            int len = 0;//读多少响应多少，防止读脏数据
            while ((len = fileInputStream.read(bytes)) != -1) {
                fileOutPutStream.write(bytes, 0, len);
                fileOutPutStream.flush();//将管道的数据压出去
            }

            fileInputStream.close();
            fileOutPutStream.close();

        } catch (IOException e) {
            throw new SystemException("图片下载失败", e);
        }
    }
}
