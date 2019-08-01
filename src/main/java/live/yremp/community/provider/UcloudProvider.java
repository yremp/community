package live.yremp.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import live.yremp.community.exception.PeculiarException;
import live.yremp.community.exception.PeculiarExceptionCodeAndMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

//ucloud上传文件
@Service
public class UcloudProvider {
    private String buckname = "yremp";
    @Value("${ucloud.publickey}")
    private String publickey;
    @Value("${ucloud.privatekey}")
    private String privatekey;


    public String upload(InputStream fileStream, String mimeType, String filename) {
        String fileName;
        String[] FilleName = filename.split("\\.");
        if (FilleName.length > 1) {
            fileName = UUID.randomUUID().toString() + "." + FilleName[FilleName.length - 1];

        } else {
            return null;
        }
        try {
            //    授权
            ObjectAuthorization OBJECT_AUTHORIZER = new UfileObjectLocalAuthorization(publickey, privatekey);
            //    配置
            ObjectConfig config = new ObjectConfig("hk", "ufileos.com");
            PutObjectResultBean response = UfileClient.object(OBJECT_AUTHORIZER, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(fileName)
                    .toBucket(buckname)
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })

                    .execute();
            if (response != null && response.getRetCode() == 0) {
                String url = UfileClient.object(OBJECT_AUTHORIZER, config)
                        .getDownloadUrlFromPrivateBucket(fileName, buckname, 24 * 60 * 60 * 365 *10)
                        .createUrl();
                return url;
            } else {
                throw new PeculiarException(PeculiarExceptionCodeAndMessage.FILE_UPLOAD_ERROR);
            }
        } catch (UfileClientException e) {
            throw new PeculiarException(PeculiarExceptionCodeAndMessage.FILE_UPLOAD_ERROR);
        } catch (UfileServerException e) {
            throw new PeculiarException(PeculiarExceptionCodeAndMessage.FILE_UPLOAD_ERROR);
        }
    }

}
