package silentdoer.gui.utils;

import java.awt.*;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImageMaker {
    private static MediaTracker mediaTracker;  // 用于先加载完全图片，如果不先加载完全，则JComponent用到图片时才会加载，这种情况可能造成图片显示不完全
    private static Map<String, Image> cachedImage = new ConcurrentHashMap<>();

    public static void setMediaTracker(MediaTracker mediaTracker) {
        ImageMaker.mediaTracker = mediaTracker;
    }

    private static void loadImage(Image image){
        if(mediaTracker == null)
            mediaTracker = new MediaTracker(new Component() {});
        synchronized(mediaTracker)
        {
            mediaTracker.addImage(image, 0);
            try
            {
                mediaTracker.waitForID(0, 0L);
            }
            catch(InterruptedException interruptedexception) { }
            mediaTracker.statusID(0, false);
            mediaTracker.removeImage(image, 0);
        }
    }

    public static Image getCachedImage(String imagePath)
    {
        if(imagePath == null)
            return null;
        Image result = ImageMaker.cachedImage.get(imagePath);
        if(result == null) {
            result = Toolkit.getDefaultToolkit().getImage(imagePath);
            if (result != null) {
                ImageMaker.loadImage(result);
                ImageMaker.cachedImage.put(imagePath, result);
            }
        }
        return result;
    }
}
