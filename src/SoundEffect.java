import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class SoundEffect {

    private final AudioInputStream errorSound;
    private final AudioInputStream successSound;

    public SoundEffect() throws UnsupportedAudioFileException, IOException {
        errorSound =
                AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("ErrorSoundEffect.wav")));
        successSound =
                AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("SuccessSoundEffect.wav")));
    }

    public void playErrorSound() {
        try {
            playSoundHelper(errorSound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSuccessSound() {
        try {
            playSoundHelper(successSound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playSoundHelper( AudioInputStream audio ) throws LineUnavailableException, IOException {
        Clip clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();
        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                clip.close();
            }
        });
    }

}
