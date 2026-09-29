package lk.ijse.pulsefit.memberservice.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Uploads member profile photos to a Google Cloud Storage bucket.
 * On GCE, the Storage client picks up Application Default Credentials
 * automatically from the VM's attached service account, so no key file
 * needs to be shipped with the app. Satisfies the module's mandatory
 * "at least one microservice must store files in a Cloud Storage bucket"
 * requirement.
 */
@Service
public class CloudStorageService {

    @Value("${gcp.storage.bucket-name}")
    private String bucketName;

    private volatile Storage storage;

    private Storage storage() {
        if (storage == null) {
            synchronized (this) {
                if (storage == null) {
                    storage = StorageOptions.getDefaultInstance().getService();
                }
            }
        }
        return storage;
    }

    public String uploadPhoto(Long memberId, MultipartFile file) throws IOException {
        String extension = "";
        String original = file.getOriginalFilename();
        if (original != null && original.contains(".")) {
            extension = original.substring(original.lastIndexOf('.'));
        }
        String objectName = "members/%d/%s%s".formatted(memberId, UUID.randomUUID(), extension);

        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage().create(blobInfo, file.getBytes());

        return "https://storage.googleapis.com/%s/%s".formatted(bucketName, objectName);
    }
}
