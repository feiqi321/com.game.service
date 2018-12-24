package com.ovft.configure.sys.web;

import com.ovft.configure.sys.service.IConfigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by looyer on 2018/12/4.
 */
@RestController
@RequestMapping(value = "/config/soft")
public class ConfConfigureController {

    private static final Logger logger = LoggerFactory.getLogger(ConfConfigureController.class);

    /**
     *  登陆
     *
     * @return
     */
    @GetMapping(value = "/api")
    public void api(HttpServletRequest request, HttpServletResponse response)  {

        try {
            String xml = "<xml><isPlay>true</isPlay><animationID>1</animationID><musicID>1</musicID></xml>";
            response.getWriter().write(xml);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
