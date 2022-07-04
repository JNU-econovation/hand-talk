package handtalkproject.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import handtalkproject.exception.EmptyFileException;
import handtalkproject.exception.FileUploadFailedException;
import handtalkproject.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private static final String FILE_UPLOAD_FAILED_MESSAGE = "파일 업로드에 실패하였습니다.";
    private static final String EMPTY_FILE_MESSAGE = "존재하지 않는 파일입니다.";

    private final AmazonS3Client amazonS3Client;

    @Value("{cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadProfile(MultipartFile multipartFile) {
        validateFileExists(multipartFile);

        String fileName = CommonUtils.buildFileName(multipartFile.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                                             .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadFailedException(FILE_UPLOAD_FAILED_MESSAGE);
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new EmptyFileException(EMPTY_FILE_MESSAGE);
        }
    }

}
