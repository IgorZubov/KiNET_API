package kinetProcessor.configurations;

import java.util.ArrayList;


public class PanelConfiguration {
	private String m_sIpAddress;
	private int m_nXstart;
	private int m_nYstart;
	private int m_nPanelWidth;
	private int m_nPanelHeight;
	private ArrayList<BlockConfiguration> m_BlockList;
	
	public PanelConfiguration (String ipAddress) {
		m_sIpAddress = ipAddress;
		m_BlockList = new ArrayList<BlockConfiguration>();
	}
	
	public void setStartCoordinates (int x, int y) {
		m_nXstart = x;
		m_nYstart = y;
	}
	
	public void setPanelDimensions (int width, int height) {
		m_nPanelWidth = width;
		m_nPanelHeight = height;
	}
	
	public void putBlock (BlockConfiguration block) {
		m_BlockList.add(block);
	}
	
	public BlockConfiguration getBlock (int blockNum) {
		for (BlockConfiguration block : m_BlockList) {
			if (block.getBlockNumber() == blockNum)
				return block;
		}
		return null;
	}
	
	public String getPanelAddress () {
		return m_sIpAddress;
	}
	
	public int getBlocksAmount () {
		return m_BlockList.size();
	}
	
	public int getStartX () {
		return m_nXstart;
	}
	
	public int getStartY () {
		return m_nYstart;
	}
	
	public int getPanelWidth () {
		return m_nPanelWidth;
	}
	
	public int getPanelHeight () {
		return m_nPanelHeight;
	}
}
