package udp_communication;

import models.ConnectionDetails;

public class VoiceReceiverImpl implements VoiceReceiver {
    private ConnectionDetails connectionDetails;


    public VoiceReceiverImpl(ConnectionDetails connectionDetails) {
        this.connectionDetails = connectionDetails;
    }

    @Override
    public void startListening() {

    }

    @Override
    public void pauseListening() {

    }

    @Override
    public void stopListening() {

    }
}
