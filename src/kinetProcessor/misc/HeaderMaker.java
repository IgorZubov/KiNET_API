package kinetProcessor.misc;

import java.nio.ByteBuffer;

/*
 * 
 * 
 * 
Header for KiNET Protocol:
[
0x0401dc4a, //"magic" 
0x0100, //"version" 
0x0801, //"type" 
0x00000000, //"sequence" 
0xff, //"port" 
0xff, //"padding" 
0xffff, //"flags" 
0x01, //"uni" 
0x00000000, //"timer" 
0x02, //"added1" (missed in source of protocol http://www.opendmx.net/index.php/KiNet )
0x0000 //"added2" (missed in source of protocol http://www.opendmx.net/index.php/KiNet )
]
*
*
*
*/

public class HeaderMaker {
	private int m_nMagic =  0x0401DC4A;
	private short m_shVersion =  0x0100;
	private short m_shType  = 0x0801;
	private int m_nSequence =  0x00000000;
	private byte m_bPort  = (byte) 0xff;
	private byte m_bPadding =  (byte) 0xff;
	private short m_shFlags  = (short) 0xffff;
	private byte  m_bUni = 0x01;
	private int m_nTimer  = 0x00000000;
	private byte m_bAdded1 =   0x02;
	private short m_shAdded2  =  0x0000;
	
	public HeaderMaker() {
	}
	
	public void setBlock(byte BlockNumber) {
		m_bUni = BlockNumber;
	}
	
	public byte[] getByteHeader() {
		ByteBuffer header = ByteBuffer.allocate(24);
		header.putInt(m_nMagic);
		header.putShort(m_shVersion);
		header.putShort(m_shType);
		header.putInt(m_nSequence);
		header.put(m_bPort);
		header.put(m_bPadding);
		header.putShort(m_shFlags);
		header.put(m_bUni);
		header.putInt(m_nTimer);
		header.put(m_bAdded1);
		header.putShort(m_shAdded2);
		return header.array();
	}
}
