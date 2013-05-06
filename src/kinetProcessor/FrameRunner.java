package kinetProcessor;

import kinetProcessor.pixelMap.FramePixelMap;

public class FrameRunner extends Thread {
	private KinetClient m_client;
	private FramePixelMap m_pixelMap;
	private int m_npixelAmount = 0;
	
	public FrameRunner(KinetClient client, FramePixelMap map) {
		m_client = client;
		m_pixelMap = map;
		m_pixelMap.dropPixels();
	}
	
	private void setWhitePixel (int frameIter) {
		m_pixelMap.dropPixels();
		m_pixelMap.initPixel(frameIter, (byte)255, (byte)255, (byte)255);
	}
	
	@Override
	public void run() {
		m_npixelAmount = m_pixelMap.getHeight() * m_pixelMap.getWidth();
		int counter = 0;
		while (!TestMainClass.FINISHED) {
			setWhitePixel(counter);
			counter++;
			if (counter >= m_npixelAmount) {
				counter = 0;
			}
			m_client.turnOnFrame(m_pixelMap);
			try {
				sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
