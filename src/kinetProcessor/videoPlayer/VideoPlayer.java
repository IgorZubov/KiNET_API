package kinetProcessor.videoPlayer;

import java.awt.image.BufferedImage;
import java.io.File;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;
import kinetProcessor.TestMainClass;
import kinetProcessor.main.KinetClient;
import kinetProcessor.misc.KinetRgbParser;
import kinetProcessor.pixelMap.FramePixelMap;

public class VideoPlayer extends Thread {
	private final static long FIVE_SECONDS_TIMEOUT = 5000;
	private KinetClient m_client;
	private String m_sVideoPath;
	private int m_frameWidth;
	private int m_frameHeight;
	
	
	public VideoPlayer(KinetClient client, String videoPath, int frameWidth, int frameHeight) {
		m_client = client;
		m_sVideoPath = videoPath;
		m_frameWidth = frameWidth;
		m_frameHeight = frameHeight;
		System.setProperty("jna.library.path", "C://Program Files/VideoLAN/VLC");//TODO: set library path
	}
	
	@Override
	public void run() {
		try {
			playVideo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void playVideo () throws Exception {
		File checkVideoExists = new File(m_sVideoPath);
		if (checkVideoExists.exists()) {
			play();
		} else {
			m_client.turnOff();
			System.out.println("Video file doesn't exist. Check pathname.");
		}
	}

	private void play() {
		String[] VLC_ARGS = {
                "--vout", "dummy",
//                "--no-audio",
                "--no-video-title-show", 
                "--no-stats",               
                "--no-sub-autodetect-file" 
        };
		MediaPlayerFactory factory = new MediaPlayerFactory(VLC_ARGS);
	    HeadlessMediaPlayer mediaPlayer = factory.newHeadlessMediaPlayer();
	    mediaPlayer.playMedia(m_sVideoPath); //"C:\\movie.wmv"
		FramePixelMap map = new FramePixelMap(m_frameWidth, m_frameHeight);
		long time = System.currentTimeMillis();
		while (!mediaPlayer.isPlaying()) {
			if (System.currentTimeMillis() - time > FIVE_SECONDS_TIMEOUT) {
				System.out.println("Timeout expired. Check slashes");
				break;
			}
		}
		while (mediaPlayer.isPlaying()) {
			if (TestMainClass.FINISHED) {
				break;
			}		
			BufferedImage img = mediaPlayer.getSnapshot(m_frameWidth, m_frameHeight);
			if (img != null) {
				map.initWithPixelInfoMap(KinetRgbParser.getRGB(img, m_frameWidth, m_frameHeight));
				m_client.turnOnFrame(map);
				img.flush();
			}
		}
		mediaPlayer.stop();
		mediaPlayer.release();
		mediaPlayer = null;
	}	
}