package kinetProcessor.misc;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import kinetProcessor.configurations.BlockConfiguration;
import kinetProcessor.configurations.PanelConfiguration;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParser {
	private ArrayList<PanelConfiguration> m_panels;
	public int m_nFrameWidth;
	public int m_nFrameHeight;
	
	
	public XmlParser () {
		m_panels = new ArrayList<PanelConfiguration>();
	}
	
	public void test () {
		try {
            // Получить SAX Parser Factory
            SAXParserFactory factory = SAXParserFactory.newInstance();
            // Включить проверку корректности, выключить пространство имен
            factory.setValidating(true);
            factory.setNamespaceAware(false);
            SAXParser parser = factory.newSAXParser();
            File f = new File("settings.xml");
            parser.parse(f, new MyHandler(m_panels));
        } catch (ParserConfigurationException e) {
            System.out.println("The underlying parser does not support " +
                               " the requested features.");
        } catch (FactoryConfigurationError e) {
            System.out.println("Error occurred obtaining SAX Parser Factory.");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public ArrayList<PanelConfiguration> getPanels () {
		return m_panels;		
	}
}

/*
 * 
 * 
<?xml version="1.1" encoding='UTF-8'?>
<frame>
	<frame_dimensions height="18" width="40"></frame_dimensions>
	<panel name="Panel1">
		<ip_address value="192.168.0.197"></ip_address>
		<start_coordinates x="0" y="20"></start_coordinates>
		<panel_dimensions height="18" width="20"></panel_dimensions>
		<blocks>
			<block pixels="168" id="1"></block>
			<block pixels="168" id="2"></block>
			<block pixels="24" id="3"></block>
		</blocks>
	</panel>
	<panel name="Panel2">
		<ip_address value="192.168.0.198"></ip_address>
		<start_coordinates x="0" y="0"></start_coordinates>
		<panel_dimensions height="18" width="20"></panel_dimensions>
		<blocks>
			<block pixels="168" id="1"></block>
			<block pixels="168" id="2"></block>
			<block pixels="24" id="3"></block>
		</blocks>
	</panel>
</frame>
 * 
 * 
 * */

class MyHandler extends DefaultHandler {
	private ArrayList<PanelConfiguration> m_panelList;
	private int m_nPanelCounter = 0;
	
    public MyHandler(ArrayList<PanelConfiguration> panelLis) {
    	if (m_panelList == null) {
    		m_panelList = new ArrayList<PanelConfiguration>();
    	}
    	m_panelList = panelLis;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("ip_address")) {
			m_panelList.add(new PanelConfiguration(attributes.getValue("value")));
		}
		if (qName.equals("start_coordinates")) {
			m_panelList.get(m_nPanelCounter).setStartCoordinates(Integer.decode(attributes.getValue("x")), Integer.decode(attributes.getValue("y")));
		}
		if (qName.equals("panel_dimensions")) {
			m_panelList.get(m_nPanelCounter).setPanelDimensions(Integer.decode(attributes.getValue("width")), Integer.decode(attributes.getValue("height")));
		}
		if (qName.equals("block")) {
			BlockConfiguration block = new BlockConfiguration(Integer.decode(attributes.getValue("pixels")), Integer.decode(attributes.getValue("id")));
			m_panelList.get(m_nPanelCounter).putBlock(block);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("panel")) {
			m_nPanelCounter++;
		}
	}
}