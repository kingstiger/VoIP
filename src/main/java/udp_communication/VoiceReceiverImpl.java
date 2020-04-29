package udp_communication;

import models.ConnectionDetails;

public class VoiceReceiverImpl implements VoiceReceiver {
    private ConnectionDetails connectionDetails;


    public VoiceReceiverImpl(ConnectionDetails connectionDetails) {
        this.connectionDetails = connectionDetails;
    }

    @Override
    public void startListening() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void pauseListening() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void stopListening() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
