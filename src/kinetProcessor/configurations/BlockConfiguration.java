package kinetProcessor.configurations;

public class BlockConfiguration {
	private int m_nPixelsAmount;
	private int m_nBlockNumber;
	
	public BlockConfiguration(int pixelsAMount, int blockNumber) {
		m_nPixelsAmount = pixelsAMount;
		m_nBlockNumber = blockNumber;
	}
	
	public int getBlockNumber() {
		return m_nBlockNumber;
	}
	
	public int getPixelsAmount() {
		return m_nPixelsAmount;
	}
}
