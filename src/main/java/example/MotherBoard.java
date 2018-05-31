package example;

import com.google.inject.Inject;

public class MotherBoard {
    
    @Inject
    private SoundCard soundCard;
    
    @Inject
    private VideoCard videoCard;

    public void setSoundCard(SoundCard soundCard) {
        this.soundCard = soundCard;
    }

    public void setVideoCard(VideoCard videoCard) {
        this.videoCard = videoCard;
    }

    public void playAudio(Object stream) {
        soundCard.playAudio(stream);
    }

    public void displayImage(Object image) {
        videoCard.displayImage(image);
    }

    public void playVideo(Object stream) {
        videoCard.playVideo(stream);
    }
    
}
