package com.wei.velocity.generator;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class Generator {

    protected static final Logger logger = LoggerFactory.getLogger(Generator.class);
    private VelocityEngine velocityEngine;
    //模板加载类型 1，resource模板加载   2、绝对路径模板加载  3、jar包模板价值
    private Integer type;
    private String jarPath;

    public Generator() {
    }

    public Generator( String jarPath) {
        this.jarPath = jarPath;
    }
    /**
     *
     * @param templateType  模板加载方式
     * @param templatePath  模板路径
     * @param filePath  生成的文件路径
     * @param parameterMap 模板变量
     * @throws Exception
     */
    public void generator(Integer templateType, String templatePath, String filePath, Map<String, Object> parameterMap) throws Exception{
        type=templateType;
        if(type!=1&&type!=3){
            throw  new Exception("模板加载方式错误！");
        }
        init();
        writer(templatePath,filePath,parameterMap);
    }
    /**
     *
     * @param templateType 模板加载方式
     * @param filePathMap  文件路径集合，key为模板路径，value为生成的文件路径
     * @param parameterMap 模板变量
     * @throws Exception
     */
    public void generator(Integer templateType,Map<String, String> filePathMap,Map<String, Object> parameterMap) throws Exception{
        type=templateType;
        if(templateType==2){
            writer(filePathMap,parameterMap);
        }else{
            init();
            for(Map.Entry<String, String> entry : filePathMap.entrySet()){
                writer(entry.getKey(),entry.getValue(),parameterMap);
            }
        }
    }
    /**
     * 扫描模板文件夹，生成文件夹下的所有模板
     * @param dirPath 文件夹路径
     * @param replaceMap 需要对文件路径替换的内容，将key替换成value
     * @param parameterMap 模板变量
     * @throws Exception
     */
    public void generator(String dirPath,Map<String, String>replaceMap,Map<String, Object> parameterMap)throws Exception{
        Map<String, String> filePathMap=new HashMap<>();
        List<File> fileList=new ArrayList<>();
        scan(dirPath,fileList);
        filePathMap=constructFilePathMap(fileList,replaceMap);
        generator(2,filePathMap,parameterMap);
    }
    private void init(){
        if (null == velocityEngine) {
            Properties p = new Properties();
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "");
            p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
            p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
            p.setProperty("file.resource.loader.unicode", "true");
            if(type==3){
                p.setProperty("jar.resource.loader.path", jarPath);
            }
            velocityEngine = new VelocityEngine(p);
        }
    }
    private void writer(Map<String, String>filePathMap,Map<String, Object> parameterMap) throws Exception {
        Properties p = new Properties();
        p.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, "");
        p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        p.setProperty("output.encoding", "UTF-8");
        Velocity.init(p);
        for(Map.Entry<String, String> entry : filePathMap.entrySet()){
            writer1(entry.getKey(),entry.getValue(), parameterMap);
        }
    }
    private void writer(String templatePath,String filePath,Map<String, Object> parameterMap) throws Exception{
        if(StringUtils.isBlank(templatePath)){
            throw new NullPointerException("templatePath 不能为空！");
        }
        if(StringUtils.isBlank(filePath)){
            throw new NullPointerException("filePath 不能为空！");
        }
        Template template = velocityEngine.getTemplate(templatePath, "UTF-8");
        File file=new File(filePath);
        if(!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(filePath);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
        template.merge(new VelocityContext(parameterMap), writer);
        writer.close();
        logger.info("模板:" + templatePath + ";  输出文件:" + filePath);
    }
    private void writer1(String templatePath,String filePath,Map<String, Object> parameterMap) throws Exception{
        Template template = Velocity.getTemplate(templatePath,"UTF-8");
        File file=new File(filePath);
        if(!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(filePath);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
        template.merge(new VelocityContext(parameterMap), writer);
        writer.close();
        logger.info("模板:" + templatePath + ";  文件:" + templatePath);
    }
    private void scan(String dirPath,List<File> fileList){
        File dirFile=new File(dirPath);
        for(File file:dirFile.listFiles()) {
            if(file.isDirectory()) {
                scan(file.getPath(),fileList);
            }else{
                fileList.add(file);
            }
        }
    }
    private Map<String, String> constructFilePathMap(List<File> fileList,Map<String, String>replaceMap){
        Map<String, String> filePathMap=new HashMap<>();
        for(File file:fileList){
            String templatePath=file.getPath();
            String filePath=file.getPath();
            for(Map.Entry<String, String> entry : replaceMap.entrySet()){
                filePath=filePath.replace(entry.getKey(),entry.getValue());
            }
            filePathMap.put(templatePath,filePath);
        }
        return filePathMap;
    }
    public static Logger getLogger() {
        return logger;
    }
    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getJarPath() {
        return jarPath;
    }
    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }
}
