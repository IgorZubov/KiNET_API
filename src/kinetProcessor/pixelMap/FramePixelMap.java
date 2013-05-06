package kinetProcessor.pixelMap;

public class FramePixelMap {	
	private int m_nFrameWidth;
	private int m_nFrameHeight;
	private PixelInfo m_pixelMap[][];
	
	public FramePixelMap(int width, int height) {
		m_nFrameWidth = width;
		m_nFrameHeight = height;
		m_pixelMap = new PixelInfo[m_nFrameHeight][m_nFrameWidth];
		for (int row = 0; row < m_nFrameHeight; row++) {
			for (int col = 0; col < m_nFrameWidth; col++) {
				m_pixelMap[row][col] = new PixelInfo();
			}
		}
	}
	
	public void dropPixels() {
		for (int row = 0; row < m_nFrameHeight; row++) {
			for (int col = 0; col < m_nFrameWidth; col++) {
				m_pixelMap[row][col].setPixelInfo((byte)0, (byte)0, (byte)0);
			}
		}
	}
	
	public int getWidth () {
		return m_nFrameWidth;
	}
	
	public int getHeight () {
		return m_nFrameHeight;
	}

	public void initPixel(int map_iterator, byte red, byte green, byte blue) {
		int x = map_iterator / m_nFrameWidth;
		int y = map_iterator % m_nFrameWidth;
		m_pixelMap[x][y].setPixelInfo(red, green, blue);
	}

	public byte getRedAt(int row, int col) {
		try {
			return m_pixelMap[row][col].getRed();
		} catch (NullPointerException e) {
			return 0;
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
	}

	public byte getGreenAt(int row, int col) {
		try {
			return m_pixelMap[row][col].getGreen();
		} catch (NullPointerException e) {
			return 0;
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
	}

	public byte getBlueAt(int row, int col) {
		try {
			return m_pixelMap[row][col].getBlue();
		} catch (NullPointerException e) {
			return 0;
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
	}
	
	public PixelInfo[][] getPixelMap () {
		return m_pixelMap;
	}
	
	public void initWithPixelInfoMap (PixelInfo[][] pixelMap) {
		m_pixelMap = pixelMap;
	}
}
