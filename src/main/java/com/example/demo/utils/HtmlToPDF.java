package com.example.demo.utils;

import com.itextpdf.html2pdf.HtmlConverter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ***
 * <p>
 * html 转 pdf
 */
public class HtmlToPDF {
    private static final String ORIG = "C:\\Users\\user\\Desktop\\44444.html";
    private static final String OUTPUT_FOLDER = "/myfiles/";

    public static void main(String[] args) throws IOException {
        File htmlSource = new File(ORIG);
        File pdfDest = new File("C:\\Users\\user\\Desktop\\5555.pdf");
        HtmlConverter.convertToPdf(new FileInputStream(htmlSource), new FileOutputStream(pdfDest));
        List<String> strings = new ArrayList<>();
    }



    /**
     * 下载
     * @param url
     * @return
     * @throws Exception
     */
    public byte[] urlDownload(String url) throws Exception {
        //下载文件
        URL httpUrl = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) httpUrl.openConnection();
        // 使用 URL 连接进行输出
        httpConn.setDoOutput(true);
        // 使用 URL 连接进行输入
        httpConn.setDoInput(true);
        // 忽略缓存
        httpConn.setUseCaches(false);
        // 设置URL请求方法
        httpConn.setRequestMethod("GET");
        //可设置请求头
        httpConn.setRequestProperty("Content-Type", "application/octet-stream");
        // 维持长连接
        httpConn.setRequestProperty("Connection", "Keep-Alive");
        httpConn.setRequestProperty("Charset", "UTF-8");
        byte[] bytes = input2byte(httpConn.getInputStream());
        return bytes;
    }

    /**
     * @param inStream
     * @return
     * @throws IOException
     */
    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
}
