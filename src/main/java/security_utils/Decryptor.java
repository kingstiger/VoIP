package security_utils;

import java.net.DatagramPacket;

public interface Decryptor {
    DatagramPacket decrypt(byte[] data);
}
