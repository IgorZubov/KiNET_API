package kinetProcessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import kinetProcessor.configurations.PanelConfiguration;
import kinetProcessor.main.KinetClient;
import kinetProcessor.misc.KinetRgbParser;
import kinetProcessor.misc.FrameSettings;
import kinetProcessor.pixelMap.FramePixelMap;
import kinetProcessor.videoPlayer.VideoPlayer;

public class TestMainClass {
	volatile public static boolean FINISHED = false;
	
	public static void main(String[] args) {
		KinetRgbParser.setBlueGammaCorrection(0.82f);
		KinetRgbParser.setGreenGammaCorrection(0.82f);
		KinetRgbParser.setRedGammaCorrection(1.03f);

		FrameSettings frameSettings = new FrameSettings();
	    frameSettings.readSettings("settings.xml");
	    FramePixelMap pixelMap = new FramePixelMap(frameSettings.m_nFrameWidth, frameSettings.m_nFrameHeight);
	    ArrayList<PanelConfiguration> panelList = frameSettings.getPanels();

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
		        	System.out.println("Show pic");
		        	pixelMap.initWithPixelInfoMap(KinetRgbParser.getRGB(image, pixelMap.getWidth(), pixelMap.getHeight()));
		        	client.turnOnFrame(pixelMap);
		            break;
		        case 2:
		    		try {
		    		    File sourceimage = new File("img_test_1.jpg");
		    		    image = ImageIO.read(sourceimage);
		    		} catch (IOException e) {
		    		}
		        	System.out.println("Show rasta");
		        	pixelMap.initWithPixelInfoMap(KinetRgbParser.getRGB(image, pixelMap.getWidth(), pixelMap.getHeight()));
		        	client.turnOnFrame(pixelMap);
		            break;
		        case 3:
		    		try {
		    		    File sourceimage = new File("img_test_2.jpg");
		    		    image = ImageIO.read(sourceimage);
		    		} catch (IOException e) {
		    		}
		        	System.out.println("Show russia");
		        	pixelMap.initWithPixelInfoMap(KinetRgbParser.getRGB(image, pixelMap.getWidth(), pixelMap.getHeight()));
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
		        	VideoPlayer testPlayer = new VideoPlayer(client, scanner.nextLine(), pixelMap.getWidth(), pixelMap.getHeight());
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
		    		    	pixelMap.initWithPixelInfoMap(KinetRgbParser.getRGB(image, pixelMap.getWidth(), pixelMap.getHeight()));
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
