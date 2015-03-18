package de.wc3data.image;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageUtils {

    /**
     * Takes an images as input and generates an array containing this image and
     * all possible mipmaps
     *
     * @param input
     * @return
     */
    public static BufferedImage[] generateMipMaps(BufferedImage input) {
        int num = 0;
        int curWidth = input.getWidth();
        int curHeight = input.getHeight();
        int pow;
        do {
            num++;
            pow = (int) Math.pow(2.0D, num - 1);
        } while ((pow < curWidth) || (pow < curHeight));
        BufferedImage[] result = new BufferedImage[num];
        result[0] = input;
        for (int i = 1; i < num; i++) {
            curWidth /= 2;
            curHeight /= 2;
            if (curHeight == 0) {
                curHeight = 1;
            }
            if (curWidth == 0) {
                curWidth = 1;
            }
            result[i] = ImageUtils.getScaledInstance(result[(i - 1)], curWidth, curHeight, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
        }
        return result;
    }

    /**
     * Scales an Image
     *
     * @param img
     * @param targetWidth
     * @param targetHeight
     * @param hint Rendering Hint
     * @param higherQuality
     * @return
     */
    public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality) {
        int type = img.getTransparency() == 1
                ? 1 : 2;
        BufferedImage ret = img;
        int h;
        int w;
        if (higherQuality) {
            w = img.getWidth();
            h = img.getHeight();
        } else {
            w = targetWidth;
            h = targetHeight;
        }
        do {
            if ((higherQuality) && (w > targetWidth)) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }
            if ((higherQuality) && (h > targetHeight)) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }
            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while ((w != targetWidth) || (h != targetHeight));
        return ret;
    }

    /**
     * An alternative way to convert an image to type_byte_indexed (paletted)
     * that avoids dithering.
     *
     * @param src
     * @return
     */
    public static BufferedImage antiDitherConvert(BufferedImage src) {
        BufferedImage convertedImage = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                convertedImage.setRGB(x, y, src.getRGB(x, y));
            }
        }
        return convertedImage;
    }

    public static BufferedImage changeImageType(BufferedImage src, int type) {
        BufferedImage img = new BufferedImage(src.getWidth(), src.getHeight(), type);
        Graphics2D g =  (Graphics2D)img.getGraphics();

        if(img.getColorModel().hasAlpha()){
            Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC);
            g.setComposite(comp);
        }
        
        g.drawImage(src, 0, 0, null);
        g.dispose();
        
        return img;
    }
    
    public static BufferedImage convertStandardImageType(BufferedImage src, boolean useAlpha){
 
        if(useAlpha && src.getType() == BufferedImage.TYPE_INT_ARGB){
            return src;
        }
        
        if(useAlpha == false && src.getType() == BufferedImage.TYPE_INT_RGB){
            return src;
        }
        
        return ImageUtils.changeImageType(src, useAlpha? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);

    }
}