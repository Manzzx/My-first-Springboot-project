package com.reggie.service;

import com.reggie.common.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface CommonService {

    R<String> uploadImg(MultipartFile file);

    void downloadImg(String name, HttpServletResponse response);
}
