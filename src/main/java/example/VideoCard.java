package example;

import com.google.inject.Inject;

public class VideoCard {
    
    @Inject
    private DisplaySystem displaySystem;
    
    public void setConnectedExternalSystem(DisplaySystem displaySystem) {
        this.displaySystem = displaySystem;
    }

    public void displayImage(Object image) {
        displaySystem.displayImage(image);
    }

    public void playVideo(Object stream) {
        displaySystem.playVideo(stream);
    }

}
