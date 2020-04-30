package security_utils;

import models.MicrophoneData;

public interface Encryptor {
    MicrophoneData encrypt(MicrophoneData data);
}
