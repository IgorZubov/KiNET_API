package kinetProcessor.misc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import kinetProcessor.pixelMap.FramePixelMap;
import kinetProcessor.pixelMap.PixelInfo;

public final class RgbParser {
	public static FramePixelMap convertTo2DWithoutUsingGetRGB(BufferedImage image, int frameWidth, int frameHeight) {
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int pictureWidth = image.getWidth();
		final FramePixelMap pixelMap = new FramePixelMap(frameWidth, frameHeight);
		final int pixelLength = 3;
		final float gamma = 0.82f;
		int pictureRow = 0;
		int pictureLine = 0;
		int frameRow = 0;
		int frameLine = 0;		
		
		for (int pixel = 0, map_iterator = 0; (map_iterator < frameWidth * frameHeight) && (pixel < pixels.length); pixel += pixelLength, map_iterator++) {
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
				pixel = pictureLine * pictureWidth * 3;
				pictureRow = -1;
				frameRow = -1;
			} else if (frameLine < pictureLine) {
				frameLine = pictureLine;
				map_iterator = frameLine * frameWidth;
				frameRow = -1;
				pictureRow = -1;
			}
			byte blue = gammaCorrection (pixels[pixel], gamma); // blue
			byte green = gammaCorrection (pixels[pixel + 1], gamma); // green
			byte red = gammaCorrection (pixels[pixel + 2], gamma+0.21f); // red
			pixelMap.initPixel(map_iterator, red, green, blue);
			pictureRow++;
			frameRow++;
		}
		return pixelMap;
	}
	
	public static PixelInfo[][] convertTo2DWithoutUsingGetRGB1(BufferedImage image, int frameWidth, int frameHeight) {
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int pictureWidth = image.getWidth();
		final PixelInfo[][] bytePixelMap = new PixelInfo[frameHeight][frameWidth];
		final int pixelLength = 3;
		final float gamma = 0.82f;
		int pictureRow = 0;
		int pictureLine = 0;
		int frameRow = 0;
		int frameLine = 0;
		for (int pixel = 0; (frameLine < frameHeight) && (pixel < pixels.length); pixel += pixelLength) {			
			byte blue = gammaCorrection (pixels[pixel], gamma);			//blue
			byte green = gammaCorrection (pixels[pixel + 1], gamma);	//green
			byte red = gammaCorrection (pixels[pixel + 2], gamma+0.21f);//red
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
