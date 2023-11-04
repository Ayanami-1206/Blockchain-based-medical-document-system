package MinIO;

import java.util.ArrayList;
import java.util.List;

import io.minio.*;
import io.minio.messages.Item;



public class MinioUtil {
    public MinioClient minioClient;
    //public MinioConstant minioConstant;

    public void init(String bucketName){
        try {
        	//System.out.println("hi1");
            minioClient = MinioClient.builder()
                    .endpoint(MinioConstant.endpoint)
                    .credentials(MinioConstant.accessKey,MinioConstant.secretKey)
                    .build();
            //System.out.println("hi2");
            //String bucketName = MinioConstant.bucketName;
            createBucket(bucketName);
            //System.out.println("hi3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createBucket(String bucketName) {
        boolean isExist;
        try {
            isExist = minioClient.bucketExists(BucketExistsArgs.
                        builder().bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean uploadFile(String fileName, String filePath,String bucketName) {
        try {
         	//System.out.println("hi4");
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .filename(filePath) // 本地磁盘的路径
                            .build());
            //System.out.println("hi5");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String downloadFile(String fileName, String filePath,String bucketName, String upHash) {
        try {
         	//System.out.println("hi6");
            //DOWNLOAD
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .filename(filePath) // 本地磁盘的路径
                            .build());
            //System.out.println("hi7");

            //MD5 HASH
            String doHash = FileHashUtil.md5HashCode32(filePath);
            System.out.println(doHash + "：文件的md5值2");
            if(FileHashUtil.compareHash(upHash, doHash)){
                return "Success";
            }
            else{return "IsChanged";}
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }
    
    public void ListObject(String bucketName){
        List<String> listObjectNames = new ArrayList<>();
        boolean isExist;
        try {
            isExist = minioClient.bucketExists(BucketExistsArgs.
                        builder().bucket(bucketName).build());
            if (isExist) {
                Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
                for (Result<Item> result : myObjects) {
                    Item item = result.get();
                    listObjectNames.add(item.objectName());
                    System.out.println(item.objectName()+"\t"+item.lastModified()+"\t"+item.size()+"\t"+item.owner());
                    
                }
                System.out.println("集合大小："+listObjectNames.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
   

}

//public static void main(String[] args) {
    /* 
    try {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioConstant.endpoint)
                .credentials(minioConstant.accessKey,minioConstant.secretKey)
                .build();
        String bucketName = minioConstant.bucketName;
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
    */

//}
