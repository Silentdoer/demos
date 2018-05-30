package me.silentdoer.springbootmultipart.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@RestController
@Slf4j
public class FileController {

    /**
     * 成功，file就是<input type="file" name="file">的name值，也就是说如果name不一样需要多个MultiPartFile的参数？？
     * @param files
     * @return
     */
    @PostMapping("/upload")
    public String upload(@RequestPart("file") MultipartFile[] files){
        // 0
        log.info("upload in." + files.length);

        // currentRequestAttributes()和下面的是一回事，下面的效率略微高一些
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        /*HttpServletResponse response = requestAttributes.getResponse();*/
        String realPath = request.getServletContext().getRealPath("/");
        String contextPath = request.getContextPath();
        // C:\Users\liqi.wang\AppData\Local\Temp\tomcat-docbase.8677821132225813541.8080\,
        log.info("{},{}", realPath, contextPath);

        /*
        if(!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            File fileAbs = new File(realPath, fileName);
            // check dir is exists
            if(!fileAbs.getParentFile().exists()){
                fileAbs.getParentFile().mkdirs();
            }
            file.transferTo(fileAbs);
        }
         */

        for(MultipartFile file : files){
            log.info("文件名：{}，大小{}", file.getName(), file.getSize());
        }
        return "{\"file_id\":\"".concat(UUID.randomUUID().toString().replace("-", "")).concat("\"}");
    }
}
