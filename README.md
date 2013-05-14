Base JAVA implementation of KiNET protocol.

# Set whole frame configuration

## From settings.xml file

If you create settings.xml file like this

    <?xml version="1.1" encoding='UTF-8'?>
    <frame>
      <frame_dimensions height="18" width="40"></frame_dimensions>
    	<panel name="Panel1">
    		<ip_address value="192.168.0.197"></ip_address>
    		<start_coordinates x="0" y="0"></start_coordinates>
    		<panel_dimensions height="18" width="20"></panel_dimensions>
    		<blocks>
    			<block pixels="168" id="1"></block>
    			<block pixels="168" id="2"></block>
    			<block pixels="24" id="3"></block>
    		</blocks>
    	</panel>
    </frame>
    
You can just loaded all preferences from it.

    FrameSettings frameSettings = new FrameSettings();
    frameSettings.readSettings("settings.xml");
    ArrayList<PanelConfiguration> panelList =  frameSettings.getPanels();
    
## By code

You can set it manual from your code:

    PanelConfiguration panel = new PanelConfiguration("192.168.0.197");
    panel.setPanelDimensions(20, 18);
    panel.setStartCoordinates(0, 0);
    BlockConfiguration block1 = new BlockConfiguration(168, 1);
    BlockConfiguration block2 = new BlockConfiguration(168, 2);
    BlockConfiguration block3 = new BlockConfiguration(24, 3);
    panel.putBlock(block1);
    panel.putBlock(block2);
    panel.putBlock(block3);
    
# Set frame dimensions

Set by code

    FramePixelMap pixelMap = new FramePixelMap(frameSettings.m_nFrameWidth, frameSettings.m_nFrameHeight);
    
Values can be got from settings.xml in first line
    
     <frame_dimensions height="18" width="40"></frame_dimensions>
     
After reading the settings

    FrameSettings frameSettings = new FrameSettings();
    frameSettings.readSettings("settings.xml");

It stored in frameSettings.m_nFrameWidth and frameSettings.m_nFrameHeight

Or just set your values
    
    FramePixelMap pixelMap = new FramePixelMap(40, 18);
    
# Getting and sending image

Afer you set up panel and frame map you should init fame map with certain image.

You need to put image from any source to BufferedImage and get RGB information from it

    File sourceimage = new File("img_test.jpg");
    BufferedImage image = ImageIO.read(sourceimage);
    pixelMap.initWithPixelInfoMap(RgbParser.getRGB(image, pixelMap.getWidth(), pixelMap.getHeight()));
    
Then create the KiNET client and put your predefined panels

    KinetClient client = new KinetClient();
    client.putPanel(panel1));
    client.putPanel(panel2);
    
And finaly send your picture wich is saved in FramePixelMap

    client.turnOnFrame(pixelMap);


