package example;

import org.junit.Test;
import org.mockito.Mockito;

public class ComputerTestUsingSetter {

    @Test
    public void testPlayVideo() {
        Computer computer = new Computer();
        MotherBoard motherBoard = new MotherBoard();
        SoundCard soundCard = new SoundCard();
        VideoCard videoCard = new VideoCard();
        SoundSystem soundSystem = Mockito.mock(SoundSystem.class);
        DisplaySystem displaySystem = Mockito.mock(DisplaySystem.class);

        computer.setMotherBoard(motherBoard);
        motherBoard.setSoundCard(soundCard);
        motherBoard.setVideoCard(videoCard);
        soundCard.setConnectedExternalSystem(soundSystem);
        videoCard.setConnectedExternalSystem(displaySystem);

        computer.playVideo();

        Mockito.verify(soundSystem).playAudio(Mockito.any());
        Mockito.verify(displaySystem).playVideo(Mockito.any());
    }

}
