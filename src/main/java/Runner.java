import models.ConnectionDetails;
import sound_utils.Microphone;
import udp_communication.VoiceSender;
import udp_communication.VoiceSenderImpl;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.InetAddress;

public class Runner {

    public static void main(String[] args) throws
                                           IOException,
                                           LineUnavailableException {

        ConnectionDetails receiver = new ConnectionDetails(InetAddress.getByName("192.168.0.104"), 5555, 1024);
        Microphone microphone = new Microphone();
        VoiceSender sender = new VoiceSenderImpl(receiver, microphone);

        sender.startSending();
    }

}
