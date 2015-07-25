/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.wc3data.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * 
 * This class is able to load and save war3map.shd files.
 * 
 * @author Karsten
 */
public class ShadowMap {

    private boolean[][] shadowMap;

    public ShadowMap() {
    }

    public ShadowMap(boolean[][] shadowMap) {
        this.shadowMap = shadowMap;
    }

    public void save(File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        save(out);
    }

    public void save(OutputStream stream) throws IOException {
        for (int y = shadowMap[0].length - 1; y >= 0; y--) {
            for (int x = 0; x < shadowMap.length; x++) {
                if (shadowMap[x][y]) {

                    stream.write(0xFF);
                } else {
                    stream.write(0x00);
                }
            }
        }

        stream.flush();
    }

    /**
     * 
     * @param file The war3map.shd to load.
     * @param sizeTerrainX The X size of the terrain (w3e)
     * @param sizeTerrainY The Y size of the terrain (w3e)
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void load(File file, int sizeTerrainX, int sizeTerrainY) throws FileNotFoundException, IOException {
        InputStream stream = new FileInputStream(file);
        load(stream, sizeTerrainX, sizeTerrainY);
    }

    /**
     * 
     * @param stream
     * @param sizeTerrainX The X size of the terrain (w3e)
     * @param sizeTerrainY The Y size of the terrain (w3e)
     * @throws IOException 
     */
    public void load(InputStream stream, int sizeTerrainX, int sizeTerrainY) throws IOException {
        shadowMap = new boolean[sizeTerrainX*4][sizeTerrainY*4];

        for (int y = shadowMap[0].length - 1; y >= 0; y--) {

            byte b[] = new byte[shadowMap.length];
            stream.read(b);

            for (int x = 0; x < shadowMap.length; x++) {
                
                if (b[x] == -1) {
                    shadowMap[x][y] = true;
                } else {
                    shadowMap[x][y] = false;
                }
            }
        }
    }

    /**
     * Converts the shadowmap into an BufferedImage. Black for shadow, white for no shadow.
     * @return 
     */
    public BufferedImage convert2BufferedImage() {
        BufferedImage image = new BufferedImage(shadowMap.length, shadowMap[0].length, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();

        for (int x = 0; x < shadowMap.length; x++) {
            for (int y = 0; y < shadowMap[0].length; y++) {

                if (shadowMap[x][y]) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x, y, 1, 1);
            }
        }
        g.dispose();
        return image;
    }

    /**
     * Converts an image into a shadowmap. Dark colors for shadow, bright colors for no shadow.
     * @param image 
     */
    public void fromBufferedImage(BufferedImage image) {
        shadowMap = new boolean[image.getWidth()][image.getHeight()];

        for (int x = 0; x < shadowMap.length; x++) {
            for (int y = 0; y < shadowMap[0].length; y++) {
                int clr = image.getRGB(x, y);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;

                if (red + green + blue < 128 * 3) {
                    shadowMap[x][y] = true;
                } else {
                    shadowMap[x][y] = false;
                }
            }
        }
    }
}
