package me.silentdoer.ssmdemo.action.student;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import me.silentdoer.ssmdemo.po.Student;
import me.silentdoer.ssmdemo.service.StudentService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/21/18 2:45 PM
 */
@Scope("prototype")  // must be set prototype
@Controller("studentAction")  // Or @Component for component-scan by annotation-filter
public class StudentAction extends ActionSupport /*implements RequestAware*/ {
    private static final long serialVersionUID = 1L;  // ActionSupport implements Serializable
    private Long uid;
    private Student student;
    @Resource
    private StudentService studentService;

    // like SpringMVC InternalResourceViewResolver to search success mapping file
    @Override
    public String execute(){
        // setter for jsp usage, struts2 will purge the map to request attr when after execute().
        Map request = (Map) ActionContext.getContext().get("request");
        // request obj is generate&store by struts2, so request.put is also struts2 to put key-value to request attr.
        request.put("requestAttrKey", "value8888888");
        try {
            Student student = this.studentService.getOneUserWithLogic(this.uid);
            System.out.println(String.format("The student is: %s", student));
            this.student = student;
        }catch (Exception ex){
            // to log exception
            ex.printStackTrace();
            request.put("error", ex.getMessage());
            return ERROR;  // error page
        }
        return SUCCESS;  // normal page
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    // student will put to request attr, key is student
    public Student getStudent() {
        return student;
    }
}
