package me.silentdoer.updowntest.web.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Paths;

/**
 *
 * @ModelAttribute is a annotation that define a param like model, like user is a model, that user.pro is a key
 *
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/3/18 9:31 AM
 */

@Controller
@RequestMapping(name="for upload")
public class MultipartUploadController implements BeanFactoryAware {
    private AbstractAutowireCapableBeanFactory beanFactory;

    //@ModelAndView is a like RequestParam but will also store the value to request model
    @RequestMapping(path={"/upload"}, method = RequestMethod.POST)
    public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile file/*maybe can array*/
            , @RequestParam("description")
            String desc, @RequestParam(name = "_fileType", required = false) String _fileType) throws IOException {
        System.out.println(file == null);  // false
        System.out.println(desc);  // out the file is image
        System.out.println(_fileType);  // out image
        System.out.println(request.getContextPath());  // out /updownfileapp
        if(!file.isEmpty()){
            String path = request.getServletContext().getRealPath("/WEB-INF/images");
            String fileName = file.getOriginalFilename();
            File fileAbs = new File(path, fileName);
            // check dir is exists
            if(!fileAbs.getParentFile().exists()){
                fileAbs.getParentFile().mkdirs();
            }
            // TODO also can file.getInputStream() to receive file stream.
            file.transferTo(fileAbs);
            return "/success";
        }else{
            return "/error";
        }
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam("filename") String name, HttpServletRequest request) throws IOException {
        String dir = request.getServletContext().getRealPath("/WEB-INF/images/");
        final String filepath = Paths.get(dir, name).toString();
        File file = new File(filepath);
        HttpHeaders headers = new HttpHeaders();
        // this name will save to header, so can not be chinese.
        String downloadFileName = URLEncoder.encode(name, "UTF-8");
        headers.setContentDispositionFormData("attachment", downloadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  // stream data
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);  //201
    }

    @RequestMapping(path="/test", name="for test")
    public @ResponseBody String test(){
        System.out.println("hello, world");
        return "Ok";
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        try {
            this.beanFactory = ((AbstractAutowireCapableBeanFactory) beanFactory);
            DefaultListableBeanFactory beanFactory1 = (DefaultListableBeanFactory) beanFactory;
            // true, so spring will create beandefinition for annotation class.
            System.out.println(beanFactory1.containsBeanDefinition("multipartUploadController"));
            // 0, so in springmvc has default strategy just scan like @Controller, @Service, @Repository, @Component etc will load
            System.out.println(beanFactory1.getBeansOfType(AutoScanTestClass.class).size());
            // 1
            System.out.println(beanFactory1.getBeansOfType(MultipartUploadController.class).size());
            //
            System.out.println(beanFactory1.getBeansOfType(CommonsMultipartResolver.class).size());
        }catch (Throwable ex){
            ex.printStackTrace();
        }
    }
}
