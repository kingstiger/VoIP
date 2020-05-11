package microphone_tests;

import com.models.MicrophoneData;
import com.security_utils.DecryptorImpl;
import com.security_utils.EncryptorImpl;
import org.junit.Assert;
import org.junit.Test;

import java.net.DatagramPacket;

public class EncryptorTest {

    @Test
    public void encryptorTest() {
        String key = "to jestjakistamklucz";

        byte[] dataToEncrypt = "data to encrypt".getBytes();
        MicrophoneData microphoneDataBeforeEncryption = new MicrophoneData(dataToEncrypt, dataToEncrypt.length);

        MicrophoneData microphoneDataEncrypted = new EncryptorImpl(key)
                .encrypt(microphoneDataBeforeEncryption);


        DatagramPacket datagramPacketEncryptedMicData = new DatagramPacket(microphoneDataEncrypted.getData(),
                                                                           microphoneDataBeforeEncryption.getNumBytesRead());

        DatagramPacket decrypt = new DecryptorImpl(key).decrypt(datagramPacketEncryptedMicData);

        Assert.assertNotEquals(microphoneDataBeforeEncryption.getData(), decrypt.getData());
        Assert.assertEquals(new String(dataToEncrypt), new String(decrypt.getData()));
    }
}
