package sound_utils;

import models.MicrophoneData;

import javax.sound.sampled.*;

public class Microphone {
    private TargetDataLine microphoneLine;
    private static int CHUNK_SIZE = 1024;

    public Microphone() throws
                             LineUnavailableException {
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);

        microphoneLine = AudioSystem.getTargetDataLine(format);

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        microphoneLine = (TargetDataLine) AudioSystem.getLine(info);
        microphoneLine.open(format);

        microphoneLine.start();
    }

    /** . */
    public MicrophoneData read() {
        byte[] data = new byte[microphoneLine.getBufferSize() / 5]; // // TODO: 2020-04-29  check if we can replace with CHUNK_SIZE
        int numBytesRead = microphoneLine.read(data, 0, CHUNK_SIZE);

        return new MicrophoneData(data, numBytesRead);
    }
}
