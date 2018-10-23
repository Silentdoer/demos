import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 通过ServiceManager管理的话，这个类对象将是一个单例
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/17/2018 4:02 PM
 */
@State(name = "SearchJar", storages = {@Storage("$APP_CONFIG$/searchJar.xml")})
public class PersistentState implements PersistentStateComponent<Element> {

    private String path;

    private PersistentState() {}

    public static PersistentState getInstance() {
        // 服务管理可以获取所有
        return ServiceManager.getService(PersistentState.class);
    }

    /**
     * 目前就这个方法不知道idea是什么时候调用的，貌似是实时调用？？（或者插件的Setting都有Ok和Apply按钮，只要某个插件的这两个按钮点击生效，就调用相关插件的这个方法？？
     * idea之所以能识别是因为会配置PersistentState到对应的插件里，所以对于插件的Ok或Apply点击生效后idea就找到对应的applicationService的相关方法来执行）
     * @return
     */
    @Nullable
    @Override
    public Element getState() {
        // 这个元素会被idea修改，本来这个name是元素的名字而非它的name属性的值
        // 但是后续的子元素则不会
        Element element = new Element("SearchJar");
        element.setAttribute("path", getSearchPath());
        return element;
    }

    public String getSearchPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // 这个方法应该是idea启动后就会自动调用的方法，前提是对应的插件是启用状态
    @Override
    public void loadState(@NotNull Element element) {
        this.path = element.getAttributeValue("path");
    }
}
