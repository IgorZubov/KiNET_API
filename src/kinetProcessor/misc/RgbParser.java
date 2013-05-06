package kinetProcessor.misc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import kinetProcessor.pixelMap.PixelInfo;

public final class RgbParser {	
	private static float m_rRedGammaCorrection = 1f;
	private static float m_rGreeenGammaCorrection = 1f;
	private static float m_rBlueGammaCorrection = 1f;

	public static void setRedGammaCorrection (float gamma) {
		m_rRedGammaCorrection = gamma;
	}
	
	public static void setGreenGammaCorrection (float gamma) {
		m_rGreeenGammaCorrection = gamma;	
	}

	public static void setBlueGammaCorrection (float gamma) {
		m_rBlueGammaCorrection = gamma;
	}
	
	public static PixelInfo[][] getRGB(BufferedImage image, int frameWidth, int frameHeight) {
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int pictureWidth = image.getWidth();
		final PixelInfo[][] bytePixelMap = new PixelInfo[frameHeight][frameWidth];
		final int pixelLength = 3;
		int pictureRow = 0, pictureLine = 0, frameRow = 0, frameLine = 0;
		for (int pixel = 0; (frameLine < frameHeight) && (pixel < pixels.length); pixel += pixelLength) {			
			byte blue = gammaCorrection (pixels[pixel], m_rBlueGammaCorrection);		//blue
			byte green = gammaCorrection (pixels[pixel + 1], m_rGreeenGammaCorrection);	//green
			byte red = gammaCorrection (pixels[pixel + 2], m_rRedGammaCorrection);  	//red
			bytePixelMap[frameLine][frameRow] = new PixelInfo(red, green, blue);
			pictureRow++;
			frameRow++;
			if (pictureRow >= pictureWidth) {
				pictureRow = 0;
				pictureLine++;
			}
			if (frameRow >= frameWidth) {
				frameRow = 0;
				frameLine++;
			}
			if (frameLine > pictureLine) {
				pictureLine = frameLine;
				pixel = pictureLine * pictureWidth * pixelLength;
				pictureRow = 0;
				frameRow = 0;
			} else if (frameLine < pictureLine) {
				frameLine = pictureLine;
				frameRow = 0;
				pictureRow = 0;
			}
		}
		return bytePixelMap;
	}
	
	private static byte gammaCorrection (byte color, float gammaCorrection) {
		int colorInByte = (int) color & 0xff;
		double normalizedColor = (double) colorInByte / 255;
		normalizedColor = Math.pow(normalizedColor, gammaCorrection);
		return (byte) Math.round(normalizedColor * 255);
	}
}
