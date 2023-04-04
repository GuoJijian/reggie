package com.guojijian.reggie.controller;

import com.guojijian.reggie.commons.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String path;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public R upload(@RequestBody MultipartFile file) throws IOException {
        //获取上传文件的后缀
        String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //设置文件名称
        String fileName=UUID.randomUUID().toString().replaceAll("-","")+suffix;
        //创建File，设置上传文件相关参数
        File newFile=new File(path+fileName);
        if(!newFile.exists()){
            newFile.mkdirs();
        }

        //持久化上传文件
        file.transferTo(newFile);

        return R.success(fileName);
    }

    /**
     * 文件下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name,HttpServletResponse response){
        FileInputStream is=null;
        ServletOutputStream outputStream = null;

        try {
            //获取输入流，设置文件读取配置
            is=new FileInputStream(new File(path+name));
            //获取输出流
            outputStream = response.getOutputStream();
            //边读边写
            int len=0;
            byte[] bytes=new byte[1024*5];
            while((len=is.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
