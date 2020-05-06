package com.udp_communication;

import com.models.ConnectionDetails;
import com.models.MicrophoneData;
import com.security_utils.Encryptor;
import com.sound_utils.Microphone;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Objects;

public class SingleClientVoiceSender implements VoiceSender {
    private static final Logger logger = Logger.getLogger(SingleClientVoiceSender.class);

    private ConnectionDetails connectionDetails;
    private DatagramSocket socket;
    private Microphone microphone;
    private boolean sendVoice = true;

    private Encryptor encryption;

    public SingleClientVoiceSender(ConnectionDetails connectionDetails, Microphone microphone) throws
                                                                SocketException {
        this.connectionDetails = connectionDetails;
        this.socket = new DatagramSocket();
        this.microphone = microphone;
    }

    public SingleClientVoiceSender(ConnectionDetails connectionDetails, Microphone microphone, Encryptor encryptor) throws
                                                                                                                 SocketException {
        this.connectionDetails = connectionDetails;
        this.socket = new DatagramSocket();
        this.microphone = microphone;
        this.encryption = encryptor;
    }

    @Override
    public void startSending() {
        sendVoice = true;

        new Thread(() -> {
            while (sendVoice) {
                MicrophoneData microphoneData = microphone.read();

                if (Objects.nonNull(encryption)) {
                    microphoneData = encryption.encrypt(microphoneData); // TODO: 2020-04-30
                    throw new UnsupportedOperationException("Not implemented yet.");
                }

                byte[] data = microphoneData.getData();
                int numBytesRead = microphoneData.getNumBytesRead();

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
        sendVoice = false;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void stopSending() {
        sendVoice = false;
        socket.close();
    }
}
