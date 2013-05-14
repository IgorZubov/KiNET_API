package kinetProcessor.main;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import kinetProcessor.configurations.PanelConfiguration;
import kinetProcessor.misc.FrameSettings;
import kinetProcessor.misc.KinetRgbParser;
import kinetProcessor.pixelMap.FramePixelMap;

import cc.creativecomputing.graphics.CCGraphics;
import cc.creativecomputing.graphics.CCRenderBuffer;

public class RenderBufferWithKinnet {
	private KinetClient m_kinetClient;
	private FramePixelMap m_pixelMap;
	private CCRenderBuffer m_renderBuffer;
	private ArrayList<PanelConfiguration> m_panelList;
	private int m_nCurrentPanel = 0;

	public RenderBufferWithKinnet(CCGraphics theGraphics, String settingsPathName) {
		FrameSettings frameSettings = new FrameSettings();
	    frameSettings.readSettings(settingsPathName);
	    m_pixelMap = new FramePixelMap(frameSettings.m_nFrameWidth, frameSettings.m_nFrameHeight);
	    m_panelList = frameSettings.getPanels();
	    m_kinetClient = new KinetClient();
	    for (PanelConfiguration panel : m_panelList) {
	    	m_kinetClient.putPanel(panel);
		}	    
	    m_renderBuffer = new CCRenderBuffer(theGraphics, frameSettings.m_nFrameWidth, frameSettings.m_nFrameHeight);
	}
	
	public CCRenderBuffer getRenderBuffer () {
		return m_renderBuffer;
	}
	
	public void beginDraw() {
		m_renderBuffer.beginDraw();
	}
	
	public void setGammaCorrection (float red, float green, float blue) {
		KinetRgbParser.setBlueGammaCorrection(blue);
		KinetRgbParser.setGreenGammaCorrection(green);
		KinetRgbParser.setRedGammaCorrection(red);
	}
	
	public void setPanelNumberToShow (int number) {
		if (m_nCurrentPanel != number) {
			m_nCurrentPanel  = number;
			number = (m_nCurrentPanel-1) * 2;
			m_kinetClient.removePanels();
			m_kinetClient.putPanel(m_panelList.get(number));
			m_kinetClient.putPanel(m_panelList.get(number+1));
		}
	}

	public void endDraw() {
		m_renderBuffer.endDraw();
		m_renderBuffer.attachment(0).bind();
		m_pixelMap.initWithPixelInfoMap(KinetRgbParser.getRGBexceptA(((ByteBuffer) m_renderBuffer.attachment(0).data().buffer()).array(), m_renderBuffer.attachment(0).format().toString(),
				m_renderBuffer.width(), m_pixelMap.getWidth(), m_pixelMap.getHeight()));
		m_kinetClient.turnOnFrame(m_pixelMap);
		m_renderBuffer.attachment(0).unbind();
	}
}
