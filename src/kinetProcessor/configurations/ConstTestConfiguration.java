package kinetProcessor.configurations;

public final class ConstTestConfiguration {
	public static final String PANEL_1_ADDRESS = "192.168.0.197";
	public static final String PANEL_2_ADDRESS = "192.168.0.198";
	public static final int PANEL_WIDTH = 20;
	public static final int PANEL_HEIGHT = 18;
	public static final int BLOCK_1_PIXELS_AMOUNT = 168;
	public static final int BLOCK_2_PIXELS_AMOUNT = 168;
	public static final int BLOCK_3_PIXELS_AMOUNT = 24;
}



/*
 * 
 * 
 * 
Header for KiNET Protocol:
[
0x0401dc4a, 	//"magic"
0x0100, 		//"version"
0x0801, 		//"type"
0x00000000, 	//"sequence"
0xff, 			//"port"
0xff, 			//"padding"
0xffff, 		//"flags"
0x01, 			//"uni"
0x00000000, 	//"timer"
0x02, 			//"added1" (missed in original)
0x0000 			//"added2" (missed in original)
]
*
*
*
*/