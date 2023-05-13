/**
 * This class represents sound effects.
 * @author Giridhar Nair
 */

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class SoundEffect {

    private final AudioInputStream errorSound; // Represents the audio for an error sound effect
    private final AudioInputStream successSound; // Represents the audio for a success sound effect

    /**
     * Constructs a new SoundEffect object and initializes the errorSound and successSound
     * variables by reading in audio files from the Public folder.
     */
    public SoundEffect() throws UnsupportedAudioFileException, IOException {
        errorSound = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("ErrorSoundEffect.wav")));
        successSound = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("SuccessSoundEffect.wav")));
    }

    /**
     * Plays the error sound effect.
     */
    public void playErrorSound() {
        try {
            playSoundHelper(errorSound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the success sound effect.
     */
    public void playSuccessSound() {
        try {
            playSoundHelper(successSound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method that plays a specified audio file as a sound effect.
     * @param audio - the audio file to be played.
     * @throws LineUnavailableException - if a line cannot be opened because it is unavailable.
     * @throws IOException - if an I/O error occurs.
     */
    private void playSoundHelper(AudioInputStream audio) throws LineUnavailableException, IOException {
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