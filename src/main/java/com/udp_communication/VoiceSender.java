package com.udp_communication;

import java.io.IOException;

public interface VoiceSender {
    void startSending() throws
                        IOException;
    void stopSending();
}
