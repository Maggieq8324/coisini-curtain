package com.coisini.curtain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 美佳窗帘布艺
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.coisini.curtain"})
@MapperScan(basePackages = {"com.coisini.curtain.mapper"})
@RestController
public class CurtainManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurtainManageApplication.class, args);
    }

    @RequestMapping("/")
    public String index() {
        return "<style type=\"text/css\">*{ padding: 0; margin: 0; } div{ padding: 4px 48px;} a{color:#2E5CD5;cursor:" +
                "pointer;text-decoration: none} a:hover{text-decoration:underline; } body{ background: #fff; font-family:" +
                "\"Century Gothic\",\"Microsoft yahei\"; color: #333;font-size:18px;} h1{ font-size: 100px; font-weight: normal;" +
                "margin-bottom: 12px; } p{ line-height: 1.6em; font-size: 42px }</style><div style=\"padding: 24px 48px;\"><p>" +
                "Coisini <br/><span style=\"font-size:30px\">美佳窗帘布艺</span></p></div> ";
    }
}
