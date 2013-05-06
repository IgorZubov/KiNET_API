package kinetProcessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import kinetProcessor.configurations.ConstTestConfiguration;
import kinetProcessor.configurations.PanelConfiguration;
import kinetProcessor.misc.RgbParser;
import kinetProcessor.misc.XmlParser;
import kinetProcessor.pixelMap.FramePixelMap;
import kinetProcessor.videoPlayer.VideoPlayer;

public class TestMainClass {

	
	volatile public static boolean FINISHED = false;
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RgbParser.setBlueGammaCorrection(0.82f);
		RgbParser.setGreenGammaCorrection(0.82f);
		RgbParser.setRedGammaCorrection(1.03f);

		XmlParser p = new XmlParser();
	    p.readSettings("settings.xml");
	    FramePixelMap pixelMap = new FramePixelMap(p.m_nFrameWidth, p.m_nFrameHeight);
	    ArrayList< PanelConfiguration> panelList =  p.getPanels();
	    
	    
	    KinetClient client = new KinetClient();
	    client.putPanel(panelList.get(0));
	    client.putPanel(panelList.get(1));
	    BufferedImage image = null;
	    boolean exit = false;
	    while (!exit) {
	    	@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
	 	    int choice = scanner.nextInt();
	 	    FINISHED = true;
	 	    image = null;
		    switch (choice) {
		        case 1:
		    		try {
		    		    File sourceimage = new File("img_test.jpg");
		    		    image = ImageIO.read(sourceimage);
		    		} catch (IOException e) {
		    		}
		        	System.out.println("Show skull");
		        	pixelMap.initWithPixelInfoMap(RgbParser.convertTo2DWithoutUsingGetRGB(image, ConstTestConfiguration.PANEL_WIDTH*2, ConstTestConfiguration.PANEL_HEIGHT));
		        	client.turnOnFrame(pixelMap);
		            break;
		        case 2:
		    		try {
		    		    File sourceimage = new File("img_test_1.jpg");
		    		    image = ImageIO.read(sourceimage);
		    		} catch (IOException e) {
		    		}
		        	System.out.println("Show rasta");
		        	pixelMap.initWithPixelInfoMap(RgbParser.convertTo2DWithoutUsingGetRGB(image, ConstTestConfiguration.PANEL_WIDTH*2, ConstTestConfiguration.PANEL_HEIGHT));
		        	client.turnOnFrame(pixelMap);
		            break;
		        case 3:
		    		try {
		    		    File sourceimage = new File("img_test_2.jpg");
		    		    image = ImageIO.read(sourceimage);
		    		} catch (IOException e) {
		    		}
		        	System.out.println("Show russia");
		        	pixelMap.initWithPixelInfoMap(RgbParser.convertTo2DWithoutUsingGetRGB(image, ConstTestConfiguration.PANEL_WIDTH*2, ConstTestConfiguration.PANEL_HEIGHT));
		        	client.turnOnFrame(pixelMap);
		            break;
		        case 4:
		        	System.out.println("Turn on all");
		        	client.turnOnAll();
		            break;
		        case 5:
		        	System.out.println("Turn off");
		        	client.turnOff();
		            break;
		        case 6:
		        	System.out.println("Run test case");
		        	FrameRunner runner = new FrameRunner(client, pixelMap);
		        	FINISHED = false;
		        	runner.start();
		            break;
		        case 7:
		        	System.out.println("Run video. Enter path name:__________");
		        	scanner = new Scanner(System.in);
		        	VideoPlayer testPlayer = new VideoPlayer(client, scanner.nextLine(), ConstTestConfiguration.PANEL_WIDTH*2, ConstTestConfiguration.PANEL_HEIGHT);
		        	FINISHED = false;
		        	testPlayer.start();
		            break;
		        case 8:
		        	System.out.println("Show picture. Enter path name:__________");
		        	scanner = new Scanner(System.in);
		        	String pathName = scanner.nextLine();
		        	try {
		    		    File sourceimage = new File(pathName);
		    		    if (sourceimage.exists()) {
		    		    	image = ImageIO.read(sourceimage);
		    		    	pixelMap.initWithPixelInfoMap(RgbParser.convertTo2DWithoutUsingGetRGB(image, ConstTestConfiguration.PANEL_WIDTH*2, ConstTestConfiguration.PANEL_HEIGHT));
		    		    } else {
		    		    	pixelMap.dropPixels();
		    		    }
		    		} catch (IOException e) {
		    		}
		        	client.turnOnFrame(pixelMap);
		        	break;
		        case 9:
		        	exit = true;
		        	client.turnOff();
		        	client.closeSocket();
		        	System.out.println("Exit");
		            break;
		        default:
		    }
	    }		
	}
}
