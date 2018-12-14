import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class Entrance {
    public static void main(String[] args) throws IOException {
        /*File file = new File("E:\\Desktop\\Images\\mao.jpg");
        byte[] bytes = FileUtils.readFileToByteArray(file);
        String s = Base64.getUrlEncoder().encodeToString(bytes);
        System.out.println(s);*/
        //byte[] decode = Base64.getDecoder().decode(data);
        byte[] bytes = Base64.getMimeDecoder().decode(data.replace(" ", "+"));

        FileUtils.writeByteArrayToFile(new File(System.getProperty("user.dir").concat("/mmm.jpg")), bytes);
        System.out.println("OK");
    }


    private static String data = "/9j/4AAQSkZJRgABAQAA2ADYAAD/4QCMRXhpZgAATU0AKgAAAAgABQESAAMAAAABAAEAAAEaAAUAAAABAAAASgEbAAUAAAABAAAAUgEoAAMAAAABAAIAAIdpAAQAAAABAAAAWgAAAAAAAADYAAAAAQAAANgAAAABAAOgAQADAAAAAQABAACgAgAEAAAAAQAAAC2gAwAEAAAAAQAAAC0AAAAA/ 0AOFBob3Rvc2hvcCAzLjAAOEJJTQQEAAAAAAAAOEJJTQQlAAAAAAAQ1B2M2Y8AsgTpgAmY7PhCfv/AABEIAC0ALQMBIgACEQEDEQH/xAAfAAABBQEBAQEBAQAAAAAAAAAAAQIDBAUGBwgJCgv/xAC1EAACAQMDAgQDBQUEBAAAAX0BAgMABBEFEiExQQYTUWEHInEUMoGRoQgjQrHBFVLR8CQzYnKCCQoWFxgZGiUmJygpKjQ1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4eLj5OXm5 jp6vHy8/T19vf4 fr/xAAfAQADAQEBAQEBAQEBAAAAAAAAAQIDBAUGBwgJCgv/xAC1EQACAQIEBAMEBwUEBAABAncAAQIDEQQFITEGEkFRB2FxEyIygQgUQpGhscEJIzNS8BVictEKFiQ04SXxFxgZGiYnKCkqNTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqCg4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2dri4 Tl5ufo6ery8/T19vf4 fr/2wBDAAEBAQEBAQIBAQIDAgICAwQDAwMDBAUEBAQEBAUGBQUFBQUFBgYGBgYGBgYHBwcHBwcICAgICAkJCQkJCQkJCQn/2wBDAQEBAQICAgQCAgQJBgUGCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQn/3QAEAAP/2gAMAwEAAhEDEQA/AP72/E3ibQ/B h3HiPxHcLbWdsu6SRvyAAHJJPAA5Jr8zvih 1x438U3Ulh4HZtG04EhXXH2mQerPyEz1wnI/vGj9rj4oXXinxu3gewkI07Rm2uoPElzj52PrszsGeh3etfHGo6jYaRYTapqkyW9tboZJJJCFVVUZJJPQCv5V8UfFHEzxM8DganJThpKS0ba316Jbab h/bXgx4MYSnhKeZZlT9pVqWcYtXUU9tOsnvrtokk9 j1HXtd1eY3GrXs91Ix3FppGck pLEnNdJ4a JvxB8ITi48OaxdW2OdgkLRnH96NsofxFfnT4n/AG2/Cem6g1p4Y0ifU4kOPOklFurY7qNkjEemQp9q9U FH7Svgb4pXq6GivpmpsMpbzkESYGSI3HDEDsQp9Aa/mXLfE7LqmMVLC4v97fSzau/KWib9Gf2NnHg3m9HAOvjMD 5tqmouy843bS73St1P3C B37Vln4zvIfCfxAWOy1KUhILhOIZ2PAVgfuOe3O1jxwcA/Z1fz8AlSGU4I6Gv17/AGZPifc/Ej4erHq8hk1LSmFtcOxy0i4zHIfdl4JPJZSe9f2b4TeI9bHzeXY93mleMurS3T81vfqr313/AM fHPwkw WU1m2WR5abdpR6Rb2a7JvRro7W0en/0P6Bte1GbV9dvdWuCWkup5JmLdSXYsSffJr4N/bb8T6hpvhPSPDFoxSHU55ZJsfxLbhMKfYs4bHqor9Fvib4an8IfEHWPDlwMfZrqQJnjMbHdG34oQfxr49/aU FF78UvAypoahtT01zPbocDzARh4wTwCwwR7gDvX XfidluMqZdi8LST9rqrdXZ 8vVpNH 0Hg3nGAo5vgcZXa9jo0 ivH3X6JtO/Tc/H rVjfXemXsWo6fI0M8DrJG6nDKynIIPqDRfWN7pl3Jp owvBPCxV45FKsrDqCDyDXo/wp FPiT4reJItI0iJltVYfarrHyQp3JPQsR91epPtk1/DOAwGIr4iNDDxbm3ZJb3/4H4H k ZZlhcNhZYnEySppXbe1v1v J yng/WZPEfhHSvEMy7Xv7OC4YehljVyP1r3v4YfEPWPAX27 yZZIvtflb/AC88 XvxnH 8a8b06wtdK0 DTLFdkNtGsUa ioAqj8AK y/2Yvg/H8Q9O1fVtQcwwQyQxRttzucBmcfgCv51/pZwLl2Or42lRwsv3tnr6Rd2f5AeJObZbhsBXxGNj 5utPWSsuu2n3H/0f7Kv2rPgdeeM7NfiB4ThMuo2cey4gQZaeEchlA6unPHVl4HIAP5jEFSVYYI6iv6B6 c/if zJ8PfiRcyavGraVqUhLPcWwG2Rj3kjPyse5I2se5NfhHiP4TTx9Z4/LmlN/FF6Jvun0fe j3v3/prwk8cqeWYeOWZsm6cfhktXFdmt2l0a1W1mtvxg1nwf4R8RyLN4h0qzv3X7rXEEcpH0Lqa17DTtP0q1Wx0yCO2hT7scShFH0VQAK9k Ifww/4QLWJdJ 3fa/Kk8vf5Xl55xnG9v517P8AB79mLTfiJG oatq8kMEJXdFFEA7Z9HZiB/3wa/njLuBcbXxzwtGkva9dYr73fU/q7NvEjAYbLY43E137HppJ/crab R83eCPBHiL4heIoPDXhqAzTzEbmwdkaZ5dz/Cq9z AySBX7RfDjwJpfw28HWfhHSTvS2X55CMGSRuXc9ep6DJwMDtR4E HHg74baWdJ8I2a2yPgyP96SQju7nk w6DsBXcV/Vvhx4cQyWDrVmpVpKza2S7L9Wfw/4teLVTiGpHD4eLhQg7pPeT7vtbotd38v/Z\t";
}
