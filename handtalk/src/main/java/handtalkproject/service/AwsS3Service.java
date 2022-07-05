package handtalkproject.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import handtalkproject.exception.EmptyFileException;
import handtalkproject.exception.FileUploadFailedException;
import lombok.RequiredArgsConstructor;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private static final String FILE_UPLOAD_FAILED_MESSAGE = "파일 업로드에 실패하였습니다.";
    private static final String EMPTY_FILE_MESSAGE = "존재하지 않는 파일입니다.";

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadProfile(MultipartFile profileImageFile) {
        validateFileExists(profileImageFile);

        String fileName = createFileName(profileImageFile.getOriginalFilename());
        String fileFormatName = profileImageFile.getContentType()
                                                .substring(profileImageFile.getContentType()
                                                                           .lastIndexOf("/") + 1);

        MultipartFile resizedFile = resizeProfileImageFile(fileName, fileFormatName, profileImageFile);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(resizedFile.getSize());
        objectMetadata.setContentType(profileImageFile.getContentType());

        try (InputStream inputStream = resizedFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                                             .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadFailedException(FILE_UPLOAD_FAILED_MESSAGE);
        }

        return amazonS3Client.getUrl(bucketName, fileName)
                             .toString();
    }

    private void validateFileExists(MultipartFile profileImageFile) {
        if (profileImageFile.isEmpty()) {
            throw new EmptyFileException(EMPTY_FILE_MESSAGE);
        }
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID()
                   .toString()
                   .concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    private MultipartFile resizeProfileImageFile(String fileName, String fileFormatName, MultipartFile profileImageFile) {
        try {
            BufferedImage image = ImageIO.read(profileImageFile.getInputStream());

            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            if (originWidth <= 600 && originHeight <= 600) {
                return profileImageFile;
            }

            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", 600);
            scale.setAttribute("newHeight", 600);
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, fileFormatName, baos);
            baos.flush();

            MockMultipartFile resizedFile = new MockMultipartFile(fileName, baos.toByteArray());
            BufferedImage resizedImage = ImageIO.read(resizedFile.getInputStream());

            return new MockMultipartFile(fileName, baos.toByteArray());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 리사이즈에 실패했습니다.");
        }
    }

}
