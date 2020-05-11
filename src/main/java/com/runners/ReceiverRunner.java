package com.runners;

import com.models.ConnectionDetails;
import com.security_utils.Decryptor;
import com.security_utils.DecryptorImpl;
import com.sound_utils.Speaker;
import com.udp_communication.VoiceReceiver;
import com.udp_communication.VoiceReceiverImpl;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ReceiverRunner {

    public static void main(String[] args) throws
                                           UnknownHostException,
                                           LineUnavailableException,
                                           SocketException {
        Decryptor decryptor = new DecryptorImpl("testowyklucz");

        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
        ConnectionDetails sender = new ConnectionDetails(InetAddress.getByName("localhost"), 5555, 1024);
        Speaker speaker = new Speaker(format);

        VoiceReceiver voiceReceiver = new VoiceReceiverImpl(sender, speaker, decryptor);
        voiceReceiver.startListening();
    }
}
