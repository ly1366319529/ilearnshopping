package com.neuedu.controller.manage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    public  String upload(){
        return "upload";//逻辑视图    前缀加逻辑视图+后缀
    }
}
