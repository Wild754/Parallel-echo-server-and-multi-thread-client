package ua.edu.chmnu.net_dev.c4.url.simple_downloader;

import ua.edu.chmnu.net_dev.c4.url.core.Downloader;

public class SimpleDownloaderApp {
    public static void main(String[] args) {
        var sourceUrl = "https://github.com/mguludag/book-1/blob/master/%5BJAVA%5D%5BJava%20Network%20Programming%2C%204th%20Edition%5D.pdf";

        Downloader downloader = new SimpleDownloader(sourceUrl, ".");

        downloader.download();
    }
}
