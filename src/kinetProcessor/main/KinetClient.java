package kinetProcessor.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import kinetProcessor.configurations.PanelConfiguration;
import kinetProcessor.misc.HeaderMaker;
import kinetProcessor.pixelMap.FramePixelMap;


public class KinetClient {
	ArrayList<DatagramSocket> m_socketsList = null;
	ArrayList<PanelConfiguration> m_panelsList = null;
	private final int serverPort = 6038;
 
	public KinetClient() {
		m_socketsList = new ArrayList<DatagramSocket>();
		m_panelsList = new ArrayList<PanelConfiguration>();
	}
	
	public void putPanel (PanelConfiguration panel) {
		try {
			m_panelsList.add(panel);
			DatagramSocket socket = new DatagramSocket();
			InetAddress theServer = InetAddress.getByName(panel.getPanelAddress());
			socket.connect(theServer, serverPort);
			m_socketsList.add(socket);
		} catch (SocketException ExceSocket) {
			System.out.println("Socket creation error  : "+ExceSocket.getMessage());
		} catch (UnknownHostException ExceHost) {
			System.out.println("Socket host unknown : "+ExceHost.getMessage());
		}
	}
	
	private void packageSender (ArrayList<ByteBuffer> dataToSend) throws IOException {
		DatagramPacket theSendPacket = null;
		InetAddress theServerAddress = null;
		ByteBuffer data = ByteBuffer.allocate(536);
		HeaderMaker header = new HeaderMaker();
		for (DatagramSocket socket : m_socketsList) {
			PanelConfiguration panel = m_panelsList.get(m_socketsList.indexOf(socket));
			ByteBuffer buffer = dataToSend.get(m_socketsList.indexOf(socket));
			buffer.clear();
			for (int i = 1; i <= panel.getBlocksAmount(); i++) {
				data.clear();
				header.setBlock((byte)panel.getBlock(i).getBlockNumber());
				data.put(header.getByteHeader());
				int colorByteInfo = panel.getBlock(i).getPixelsAmount() * 3;
				byte[] arrayData;
				try {
					arrayData = new byte[colorByteInfo];
					buffer.get(arrayData);
				} catch (IndexOutOfBoundsException e1) {
					arrayData = new byte[512];
				} catch (BufferUnderflowException e2) {
					arrayData = new byte[512];
				}
				data.put(arrayData);
				theServerAddress = socket.getInetAddress();
				theSendPacket = new DatagramPacket(data.array(), data.capacity(), theServerAddress, serverPort);
				socket.send(theSendPacket);
			}
		}
	}
	
	public void turnOff () {
		FramePixelMap pixelMap = new FramePixelMap(40, 18);
		pixelMap.dropPixels();
		turnOnFrame(pixelMap);
	}
	
	public void turnOnAll () {
		FramePixelMap pixelMap = new FramePixelMap(40, 18);
		pixelMap.dropPixels();
		for (int i = 0; i < pixelMap.getHeight() * pixelMap.getWidth(); i++) {
			pixelMap.initPixel(i, (byte)255, (byte)255, (byte)255);
		}
		turnOnFrame(pixelMap);
	}
	
	public void removePanels() {
		m_panelsList.clear();
		m_socketsList.clear();
	}
	
	public void closeSocket() {
		for (DatagramSocket s : m_socketsList) {
			s.close();
		}
	}

	public void turnOnFrame(FramePixelMap pixelMap) {
		ArrayList<ByteBuffer> data = new ArrayList<ByteBuffer>();
		for (PanelConfiguration p : m_panelsList) {
			ByteBuffer buffer = ByteBuffer.allocate(p.getPanelHeight() * p.getPanelWidth() * 3);
			int actualHeight = p.getPanelHeight() + p.getStartY();
			for (int row = p.getStartY(); row < actualHeight; row++) {
				int actualWidth = p.getPanelWidth() + p.getStartX();
				for (int col = p.getStartX(); col < actualWidth; col++) {
					buffer.put(pixelMap.getRedAt(row, col));
					buffer.put(pixelMap.getGreenAt(row, col));
					buffer.put(pixelMap.getBlueAt(row, col));
				}
			}
			data.add(buffer);
		}
		try {
			packageSender(data);
		} catch (IOException e) {
			System.out.println("Client getting data error : " + e.getMessage());
		}
	}
}
