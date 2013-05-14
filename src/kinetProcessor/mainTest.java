package kinetProcessor;

import kinetProcessor.main.RenderBufferWithKinnet;
import cc.creativecomputing.CCApp;
import cc.creativecomputing.CCApplicationManager;
import cc.creativecomputing.control.CCControl;
import cc.creativecomputing.graphics.texture.CCTextureData;
import cc.creativecomputing.graphics.texture.CCTextureIO;

public class mainTest extends CCApp {	
	@CCControl(name = "gamma red", min = 0.01f, max = 5.0f)
	private float m_nGammaRed = 1.0f;
	
	@CCControl(name = "gamma green", min = 0.01f, max = 5.0f)
	private float m_nGammaGreen = 1.0f;
	
	@CCControl(name = "gamma blue", min = 0.01f, max = 5.0f)
	private float m_nGammaBlue = 1.0f;
	
	@CCControl(name = "panel number", min = 1, max = 7)
	private int m_panelNumber = 1;
	
	private RenderBufferWithKinnet m_renderBuffer;
	
	private CCTextureData _myTextureData;

	public void setup() {
		_myTextureData = CCTextureIO.newTextureData("template.png");
		m_renderBuffer = new RenderBufferWithKinnet(g, "settings.xml");
		addControls("Kinet", "Kinet", this);
		
		m_nGammaRed = 1f;
		m_nGammaGreen = 1f;
		m_nGammaBlue = 1f;
	}

	public void draw() {
		g.clearColor(0);
		g.clear();

		m_renderBuffer.setPanelNumberToShow(m_panelNumber);
		m_renderBuffer.setGammaCorrection(m_nGammaRed, m_nGammaGreen, m_nGammaBlue);
		m_renderBuffer.beginDraw();
		m_renderBuffer.getRenderBuffer().attachment(0).data(_myTextureData);
		m_renderBuffer.endDraw();
		g.color(255);
		
		g.image(m_renderBuffer.getRenderBuffer().attachment(0),0,0);
	}

	public static void main(String[] args) {
		CCApplicationManager myManager = new CCApplicationManager(mainTest.class);
		myManager.settings().size(500, 500);
		myManager.start();
	}
}