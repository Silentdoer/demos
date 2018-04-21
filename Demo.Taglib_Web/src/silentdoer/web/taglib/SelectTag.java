package silentdoer.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class SelectTag extends SimpleTagSupport {
    private String id;
    private String name;
    private Boolean allowMulti;
    private Integer showLines;
    private List<String> options;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAllowMulti() {
        return allowMulti;
    }

    public void setAllowMulti(Boolean allowMulti) {
        this.allowMulti = allowMulti;
    }

    public Integer getShowLines(){
        // 这里会自动拆箱, Boolean 和 boolean比较在这里测试确实会自动拆箱
        if(this.allowMulti){
            return showLines;
        }else{
            return null;
        }
    }

    public void setShowLines(Integer showLines){
        this.showLines = showLines;
    }

    // 不能有List<String> getOptions()，不然Tomcat会依据getter的返回值是List<String>而直接调用参数是List<String>的setOptions(..)
    // 或者getter返回值也是Object，然后在调用getter的地方强制转换一下。
    /*public List<String> getOptions() {
        return options;
    }*/

    /*public void setOptions(List<String> options) {
        System.err.println("come in in");
        this.options = options;
    }*/

    // 由此可知setter和getter方法也可以抛出异常，且在setter和getter不成对的前提下是通过setter或getter名来调用的，而不是内部设置的值的名字，且设置的值类型可以是任意类型而不是一定要和参数的一样。
    @SuppressWarnings("unchecked")
    public void setOptions(Object options) throws ClassCastException, IllegalArgumentException {
        JspContext jspContext = this.getJspContext();
        if(options instanceof String) {
            String options0 = options.toString().trim();
            this.options = (List<String>) jspContext.getAttribute(options0);
            if (this.options == null) {
                HttpServletRequest request = (HttpServletRequest) ((PageContext) jspContext).getRequest();
                this.options = (List<String>) request.getAttribute(options0);
                if (this.options == null) {
                    HttpSession session = ((PageContext) jspContext).getSession();
                    this.options = (List<String>) session.getAttribute(options0);
                }
            }
        }else if(options instanceof List){
            this.options = (List<String>)options;
        }else{
            throw new IllegalArgumentException("options参数不合法");
        }
    }

    /**
     * throws中的异常类型是要包括函数体中可能会抛出的异常，对方法重写后甚至可以不throws
     */
    @Override
    public void doTag() throws JspException, IOException, IllegalArgumentException {
        //super.doTag();  // 若是不加这句则throws中的异常类型可以不包括super.doTag()中的异常类型
        if(this.options == null || this.options.size() < 1)
            throw new IllegalArgumentException("select的option不能少于一个");

        JspContext jspContext = this.getJspContext();
        JspWriter jw = jspContext.getOut();
        String id = getId() == null ? "" : " id=\"" + getId() + "\"";
        String name = getName() == null ? "" : " name=\"" + getName() + "\"";
        String multiple = getAllowMulti() == null || getAllowMulti() == false ? "" : " multiple";
        String size = getShowLines() == null ? "" : " size=\"" + getShowLines() + "\"";

        jw.println("<select" + id + name + multiple + size + ">");
        //this.getJspBody().invoke(null);  // null默认输出到jsp页面，输出位置在<select...>之后在<option>之前
        for(Iterator<String> iterator = this.options.iterator();iterator.hasNext();){
            jw.println("<option>" + iterator.next() + "</option>");
        }
        jw.println("</select>");

        /**-----------------------------------------------------------------------------*/
        jspContext.setAttribute("elTest", "哈哈哈哈");  // 可以在content里用${elTest}输出此值
        // 或者将数据先输出到StringWriter里，然后再通过JspWriter将StringWriter的数据（content）输出到jsp页面
        this.getJspBody().invoke(null);  // null默认输出到jsp页面，调用此方法的代码位置也决定了输出位置是在</select>之后
    }
}
