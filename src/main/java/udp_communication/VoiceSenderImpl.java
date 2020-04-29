package udp_communication;

import models.ConnectionDetails;
import models.MicrophoneData;
import org.apache.log4j.Logger;
import sound_utils.Microphone;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class VoiceSenderImpl implements VoiceSender {
    private static final Logger logger = Logger.getLogger(VoiceSenderImpl.class);

    private ConnectionDetails connectionDetails;
    private DatagramSocket socket;
    private Microphone microphone;
    private boolean sendVoice = true;

    public VoiceSenderImpl(ConnectionDetails connectionDetails, Microphone microphone) throws
                                                                SocketException {
        this.connectionDetails = connectionDetails;

        socket = new DatagramSocket();
        this.microphone = microphone;
    }

    @Override
    public void startSending() {
        new Thread(() -> {
            while (sendVoice) {
                MicrophoneData read = microphone.read();
                byte[] data = read.getData();
                int numBytesRead = read.getNumBytesRead();

                DatagramPacket request = new DatagramPacket(data, numBytesRead, connectionDetails.getHostUrl(), connectionDetails.getPort());

                try {
                    socket.send(request);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }).start();
    }

    @Override
    public void pauseSending() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void stopSending() {
        sendVoice = false;
    }
}
