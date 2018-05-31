package example;

import com.google.inject.Inject;

public class Computer {
    
    @Inject
    private MotherBoard motherBoard;

    public void setMotherBoard(MotherBoard motherBoard) {
        this.motherBoard = motherBoard;
    }
    
    public void playTestSound() {
        Object testAudioStream = new Object();

        motherBoard.playAudio(testAudioStream);
    }

    public void showDesktop() {
        Object desktopImage = new Object();

        motherBoard.displayImage(desktopImage);
    }

    public void playVideo() {
        Object audioStream = new Object();
        Object videoStream = new Object();

        motherBoard.playAudio(audioStream);
        motherBoard.playVideo(videoStream);
    }
    
}
