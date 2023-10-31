package MinIO;

import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;


public class MinioDemo01 {
    public static MinioClient minioClient;
 
    //public static MinioConstant minioConstant;
    public static void main(String[] args) {

        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(MinioConstant.endpoint)
                    .credentials(MinioConstant.accessKey,MinioConstant.secretKey)
                    .build();
            String bucketName = MinioConstant.bucketName;
            boolean found = minioClient.bucketExists(BucketExistsArgs.
                    builder().bucket(bucketName).build());
            if (!found){
                // 新建一个桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object("smile.jpeg")
                            .filename("/home/bupt/Downloads/smile.jpeg") // 本地磁盘的路径
                            .build()
            );
            System.out.println("上传成功");
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
