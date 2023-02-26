package com.example.demo;

import com.example.demo.utils.HtmlToPDF;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestDemoApplicationTests {

    @Test
    void contextLoads() {

        HtmlToPDF htmlToPDF = new HtmlToPDF();
        htmlToPDF.html2pdf("https://blog.csdn.net/sinat_34074514/article/details/80812109");
    }

}
