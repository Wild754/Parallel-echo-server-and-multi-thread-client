package ua.edu.chmnu.net_dev.c4.url.simple_downloader;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.edu.chmnu.net_dev.c4.url.core.Downloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

@Data
@AllArgsConstructor
public class SimpleDownloader implements Downloader, Runnable {

    private final String sourceUrl;

    private final String targetPath;

    @Override
    public void run() {
        download();
    }

    @Override
    public void download() {
        try {
            URL url = new URL(sourceUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            var sourcePath = URLDecoder.decode(url.getPath(), "UTF-8");

            try (var is = new BufferedInputStream(urlConnection.getInputStream())) {
                fetchFile(is, getFileName(sourcePath), this.targetPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileName(String path) {
        var idx = path.lastIndexOf('/');
        return idx == -1 ? path : path.substring(idx + 1);
    }

    private void fetchFile(InputStream is, String fileName, String targetPath) throws IOException {
        byte[] buffer = new byte[4 * 1024];

        int read;

        if (!new File(targetPath).exists()) {
            new File(targetPath).mkdirs();
        }

        targetPath = targetPath + File.separator + fileName;

        try (var os = new FileOutputStream(targetPath)) {
            while ((read = is.read(buffer)) != -1) {
                os.write(buffer, 0, read);
            }

            os.flush();
        }
    }
}
