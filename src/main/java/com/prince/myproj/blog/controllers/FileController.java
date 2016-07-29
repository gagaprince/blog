package com.prince.myproj.blog.controllers;

import com.prince.util.fileutil.FileUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;

/**
 * Created by zidong.wang on 2016/7/25.
 */
@Controller
@RequestMapping("/blog")
public class FileController {
    public static final Logger logger = Logger.getLogger(FileController.class);

    @RequestMapping("/file/replaceFontSize")
    @ResponseBody
    public String ReplaceFont() throws IOException {

        String path = "D:\\work\\gitfrontTemp\\hybrid_fe\\h5\\src\\index\\css";
        File allPath = new File(path);
        doReplace(allPath);

        return "";
    }

    private void doReplace(File f) throws IOException {
        String rootPath = "D:\\work\\工作记录\\car\\首页改版\\less\\";
        if(f.isDirectory()){
            File[] fs = f.listFiles();
            for(File file:fs){
                doReplace(file);
            }
        }else {
            if(f.getName().endsWith(".less")){
                String name = f.getName();
                String filePath = rootPath+name;
                BufferedReader br = new BufferedReader(new FileReader(f));
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
                String line = "";
                while((line=br.readLine())!=null){
                    if(line.indexOf("font-size")!=-1 && line.indexOf("px")!=-1){
                        logger.info(line);
                        String fontSize = line.split(":")[1].replace("px","").replace(";","").trim();
                        float fonsSizeF = Float.parseFloat(fontSize);
                        line = "font-size:"+fonsSizeF/10+"rem;";
                        logger.info(line);
                        logger.info("----------------------------------");
                    }
                    bw.write(line);
                    bw.write("\n");
                }
                bw.close();
                br.close();
            }
        }
    }
}
