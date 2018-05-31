package example;

import com.google.inject.Inject;

public class SoundCard {
    
    @Inject
    private SoundSystem soundSystem;
    
    public void setConnectedExternalSystem(SoundSystem soundSystem) {
        this.soundSystem = soundSystem;
    }

    public void playAudio(Object stream) {
        soundSystem.playAudio(stream);
    }

}
