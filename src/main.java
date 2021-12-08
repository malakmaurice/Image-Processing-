import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class main {

	  public static final String SOURCE_FILE = "./resources/many-flowers.jpg";
	    public static final String DESTINATION_FILE = "./out/many-flowers.jpg";
	    
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedImage originalImage=ImageIO.read(new File(SOURCE_FILE));
		BufferedImage resultImage =new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		long startTime=System.currentTimeMillis();
		recolorSingleThreaded(originalImage, resultImage);
		long endTime=System.currentTimeMillis();
		System.out.println(endTime-startTime);
		ImageIO.write(resultImage, "jpg", new File(DESTINATION_FILE));
		
	}
	public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }
	public static void recolorImage(BufferedImage originalimage, BufferedImage resultImage, int leftCorner,
			int topCorner, int width, int height) {
		for(int x=leftCorner;x< leftCorner+width && x <originalimage.getWidth();x++) {
			for(int y=topCorner;y<topCorner+height&&y<originalimage.getHeight();y++) {
					recolorPixel(originalimage, resultImage, x, y);
			}
		}
	}

	public static void recolorPixel(BufferedImage originalimage, BufferedImage resultImage, int x, int y) {
		int rgb = originalimage.getRGB(x, y);

		int red = getRed(rgb);
		int blue = getBlue(rgb);
		int green = getGreen(rgb);

		int newRed, newBlue, newGreen;

		if (isShadeOfGray(red, green, blue)) {
			newRed = Math.min(255, red + 10);
			newGreen = Math.max(0, green - 80);
			newBlue = Math.max(0, blue - 20);
		} else {
			newRed = red;
			newBlue = blue;
			newGreen = green;
		}
		int newRGB = creatRGBFromColors(newRed, newBlue, newGreen);
		resultImage.getRaster().setDataElements(x, y, resultImage.getColorModel().getDataElements(newRGB, null));
	}

	public static boolean isShadeOfGray(int red, int green, int blue) {

		return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
	}

	public static int creatRGBFromColors(int red, int blue, int green) {
		int rgb = 0;
		rgb |= blue;
		rgb |= (green << 8);
		rgb |= (red << 16);
		rgb |= 0xFF000000;
		return rgb;
	}

	public static int getRed(int rgb) {
		return (rgb & 0x00FF0000) >> 16;
	}

	public static int getGreen(int rgb) {
		return (rgb & 0x0000FF00) >> 8;
	}

	public static int getBlue(int rgb) {
		return rgb & 0x000000FF;
	}
}
