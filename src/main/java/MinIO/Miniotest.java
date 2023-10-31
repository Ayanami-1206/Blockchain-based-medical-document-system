package MinIO;
//import io.minio.*;

public class Miniotest {
    //public static MinioUtil minioUser;
    public static void main(String[] args) {
        MinioUtil minioUser = new MinioUtil();
        minioUser.init("0001");
        minioUser.uploadFile("smile.jpeg", "/home/bupt/Downloads/test.txt", "0001");
        //System.exit(0);
        System.out.println("smile");
        minioUser.uploadFile("test.txt", "/home/bupt/Downloads/test.txt", "0001");
        System.out.println("txt");
        minioUser.ListObject("0001");
        
    }
}