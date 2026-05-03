import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtil {

    public static String savePhoto(File photo) throws Exception {

        String uploadDir = "src/myuploads";

        // folder nahi hai to create kar de
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // random file name
        String randomName = System.currentTimeMillis() + "_" + photo.getName();

        File destination = new File(dir, randomName);

        Files.copy(photo.toPath(), destination.toPath(),
                   StandardCopyOption.REPLACE_EXISTING);

        return randomName;   // DB me save karne ke liye
    }
}