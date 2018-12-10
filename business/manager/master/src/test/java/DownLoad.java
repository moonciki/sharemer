import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.UUID;

/**
 * Create by 18073 on 2018/12/10.
 */
public class DownLoad {

    @Test
    public void downLoadImage() throws Exception {
        String url = "http://p1.music.126.net/PEQ69_EwVpuaBmmSkAY0SQ==/58274116284456.jpg?param=177y177";
        //String url = "i1.hdslb.com/bfs/archive/1278d8be8424b0f735bc709dc2d54d2280f22353.jpg";
        //DownLoad.downloadPicture(url);
        DownLoad.uploadToQiniu(url);
    }

    private static void uploadToQiniu(String originName) throws Exception {
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey = "";
        String secretKey = "";
        String bucket = "";

        String suffix;
        try {
            suffix = originName.substring(originName.lastIndexOf("."), originName.lastIndexOf("?"));
        } catch (StringIndexOutOfBoundsException e) {
            suffix = originName.substring(originName.lastIndexOf("."));
        }
        String key = "media/covers/" + UUID.randomUUID().toString().replace("-", "") + suffix;
        URL url = new URL(originName);
        DataInputStream dataInputStream = new DataInputStream(url.openStream());

        byte[] buffer = new byte[1024];

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
                Response response = uploadManager.put(dataInputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(String.format("%s/%s", "http://static.sharemer.com", putRet.key));


        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println("---------------" + r.toString());
        }
    }

    //链接url下载图片
    private static void downloadPicture(String urlList) {
        URL url;

        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            String imageName = "D:/test3.jpg";

            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
