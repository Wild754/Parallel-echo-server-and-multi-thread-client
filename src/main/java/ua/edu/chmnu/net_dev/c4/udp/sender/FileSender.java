package ua.edu.chmnu.net_dev.c4.udp.sender;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ua.edu.chmnu.net_dev.c4.udp.shared.FileMeta;
import ua.edu.chmnu.net_dev.c4.udp.shared.ReceiverHost;
import ua.edu.chmnu.net_dev.c4.udp.utils.ObjectDatagram;

import java.net.DatagramSocket;

@AllArgsConstructor
public class FileSender {

    private final ReceiverHost receiverHost;

    private final String fileName;

    @SneakyThrows
    public void send() {

        try (var socket = new DatagramSocket()) {

            var fileSize = 12402412L;

            var fileMeta = new FileMeta(fileName, fileSize);

            var datagram = ObjectDatagram.to(fileMeta, receiverHost.inetSocketAddress());

            socket.send(datagram);

        }
    }

    public static void main(String[] args) {
        FileSender sender = new FileSender(
                new ReceiverHost("localhost", 5559),
                "file1.txt"
        );

        sender.send();
    }
}
