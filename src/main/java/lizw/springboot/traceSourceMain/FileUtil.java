package lizw.springboot.traceSourceMain;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhang on 2018/4/17.
 */
public class FileUtil {

    /**
     * 保存文件配置
     */
    public static String save(MultipartFile file, String name) throws IOException {

        //获取当时时间
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);

        String path ="";
        //本机测试使用
        String fileFullPath = "C:/matchReadExcel/"+time + "/";
        //String fileFullPath = "/usr/local/jetty/webapps/files/matchReadExcel/"+time + "/";

        //判断目录是否存在，不存在则新建目录
        File fileDirectory = new File(fileFullPath);
        if (saveFile(fileFullPath, name, file)) {
            return fileFullPath + name ;
        }
        return "";
    }
    /**
     *
     * @param path
     * @param fileName
     * @param file
     * @return
     * @throws IOException
     */
    public static Boolean saveFile(String path, String fileName, MultipartFile file) throws IOException {

        if (file.getSize() > 0) {

            path = path.replace('\\', '/');
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath;

            if(path.endsWith("/")) {
                filePath = path + fileName;
            } else {
                filePath = path + "/" + fileName;
            }

            file.transferTo(new File(filePath));
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
