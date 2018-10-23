import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/17/2018 4:19 PM
 */
public class SearchBackgroundableTask extends Task.Backgroundable {

    private final Project project;

    private final String className;

    public SearchBackgroundableTask(@Nullable Project project, @Nls @NotNull String title, String className) {
        // title会显示在状态栏进度条中
        super(project, title);
        this.project = project;
        this.className = className;
    }

    @Override
    public void run(@NotNull ProgressIndicator progressIndicator) {
        // 模糊进度，即走马灯
        progressIndicator.setIndeterminate(true);
        List<String> jarFiles = searchContainTargetClassJar();
        // 核心业务，搜索jar包
        String result = buildDisplayContent(jarFiles);
        progressIndicator.setIndeterminate(false); // 关闭走马灯
        progressIndicator.setFraction(1.0); // 设置进度为100%
        // TODO 这个文案的提示是搜索到后下面的Searching jar...就变成了辣椒等分为，当关闭弹框后消失进度条（即搜索结束后1.0，run方法结束前这段时间显示下面的，否则显示的是title）
        // 上面的理解是错误的，这个text不太清除是什么时候出现，很奇怪，
        progressIndicator.setText("辣椒等分为");
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
        // 在状态栏弹出提示信息"Search Complete"（这个pop会显示5秒，与run是否结束或弹窗是否关闭无关）
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder("Search Complete", MessageType.INFO, null)
                .setFadeoutTime(5000)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atRight);

        SwingUtilities.invokeLater(() -> {
            // 没有找到任何jar包，弹出错误提示
            if (jarFiles.isEmpty()) {
                Messages.showMessageDialog(project, "Not Found.", "Search Result", Messages.getErrorIcon());
                // 测试
                /*try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                return;
            }
            Messages.showMessageDialog(project, result, "Search Result", Messages.getInformationIcon());
        });
    }

    private String buildDisplayContent(List<String> result) {
        StringBuilder sb = new StringBuilder(1024);
        for (String jarFile : result) {
            sb.append(jarFile).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private List<String> searchContainTargetClassJar() {
        /*List<File> allJars = searchAllJars();
        return allJars.stream().filter(jar -> JarClassSearcher.hasClass(className, jar.getPath()))
                .map(File::getPath).collect(Collectors.toList());*/

        List<String> result = new ArrayList<>(8);
        // TODO 这里可以添加外部jar包
        int count = ThreadLocalRandom.current().nextInt(0, 2);
        if (count != 0) {
            for (int i = 0; i < count; i++) {
                result.add(RandomStringUtils.randomAscii(3));
            }
        }
        int duration = java.util.concurrent.ThreadLocalRandom.current().nextInt(2, 4);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*private List<File> searchAllJars() {
        List<File> allJars = new ArrayList<>(8);
        JarFile
    }*/
}
