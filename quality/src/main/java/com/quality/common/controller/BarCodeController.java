package com.quality.common.controller;



import com.quality.common.util.CustomerBarcodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xyz on 2017/5/10.
 */
@Controller
@RequestMapping("/barcode")
public class BarCodeController {

    @RequestMapping("/bar")
    @ResponseBody
    public void getBarCodeImg(String msg, HttpServletResponse outputStream){
        try {
            OutputStream out = outputStream.getOutputStream();
            CustomerBarcodeUtil.generate(msg,out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
