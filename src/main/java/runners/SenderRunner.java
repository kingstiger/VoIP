package runners;

import models.ConnectionDetails;
import sound_utils.Microphone;
import udp_communication.SingleClientVoiceSender;
import udp_communication.VoiceSender;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.InetAddress;

public class SenderRunner {

    public static void main(String[] args) throws
                                           IOException,
                                           LineUnavailableException {
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
        ConnectionDetails receiver = new ConnectionDetails(InetAddress.getByName("192.168.0.104"), 5555, 1024);
        Microphone microphone = new Microphone(format);

        VoiceSender sender = new SingleClientVoiceSender(receiver, microphone);
        sender.startSending();
    }
}
