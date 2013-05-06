package kinetProcessor.pixelMap;

public class PixelInfo {
	private byte m_bRed;
	private byte m_bGreen;
	private byte m_bBlue;

	public PixelInfo() {

	}

	public PixelInfo(byte red, byte green, byte blue) {
		m_bRed = red;
		m_bGreen = green;
		m_bBlue = blue;
	}

	public void  setPixelInfo(byte red, byte green, byte blue) {
		m_bRed = red;
		m_bGreen = green;
		m_bBlue = blue;
	}

	public byte getRed() {
		return m_bRed;
	}

	public byte getGreen() {
		return m_bGreen;
	}

	public byte getBlue() {
		return m_bBlue;
	}
}
