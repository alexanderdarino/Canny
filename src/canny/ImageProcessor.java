package canny;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageProcessor {


    public static void main(String[] args) {

        if (args.length < 2) System.exit(1);

        


        final String filename = args[0];
        final File fin = new File(filename);
        if (!fin.exists()) System.exit(1);
        assert fin.exists();
        
        BufferedImage imgIn = null;
        try {
            imgIn = ImageIO.read(fin);
            //imgIn.
        }
        catch (IOException ex) {
            Logger.getLogger(ImageProcessor.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        assert imgIn != null;
        
       
        
        
        
        ImageViewer viewer = new ImageViewer(imgIn);

        viewer.setExtendedState(JFrame.MAXIMIZED_BOTH);
        viewer.setVisible(true);
        //viewer.pack();
        viewer.setDefaultCloseOperation(ImageViewer.EXIT_ON_CLOSE);
        //displayImage(imgIn, gradX, gradY);
        
        
        

    }
    
    protected static BufferedImage detectEdges(BufferedImage gradX, BufferedImage gradY, BufferedImage magnitude, int hi, int lo)
    {
        boolean[][] peak = new boolean[magnitude.getHeight()][magnitude.getWidth()];
        boolean[][] edge = new boolean[magnitude.getHeight()][magnitude.getWidth()];
        //boolean[][] peak = new boolean[magnitude.getHeight()][magnitude.getWidth()];
        
        for (int y = 1; y < gradX.getHeight() - 1; y++)
        {
            for (int x = 1; x < gradX.getWidth() - 1; x++)
            {
                final int v = magnitude.getRaster().getSample(x, y, 0);
                
                final double theta;
                final double piThird = Math.PI/3;
                
                {
                    final int dx = gradX.getRaster().getSample(x, y, 0);
                    final int dy = gradY.getRaster().getSample(x, y, 0);

                    if (dx == 0) // Vertical
                        theta = Math.PI/2;
                    else
                        theta = Math.atan2(dy, dx);
                }
                
                if (theta >= 2 * piThird || theta <= -2 * piThird) // Vertical
                {
                    final int s = magnitude.getRaster().getSample(x, y - 1, 0);
                    final int n = magnitude.getRaster().getSample(x, y + 1, 0);
                    if (v > n && v > s)
                        peak[y][x] = true;
                }
                else if (theta >= piThird && theta < 2 * piThird) // SW-NE
                {
                    final int sw = magnitude.getRaster().getSample(x - 1, y - 1, 0);
                    final int ne = magnitude.getRaster().getSample(x + 1, y + 1, 0);
                    if (v > ne && v > sw)
                        peak[y][x] = true;
                    
                }
                else if (theta >= -1 * piThird && theta < piThird) // Horizontal
                {
                    final int w = magnitude.getRaster().getSample(x - 1, y, 0);
                    final int e = magnitude.getRaster().getSample(x + 1, y, 0);
                    if (v > w && v > e) 
                        peak[y][x] = true;
                }
                else // SE - NW
                {
                    final int se = magnitude.getRaster().getSample(x + 1, y - 1, 0);
                    final int nw = magnitude.getRaster().getSample(x - 1, y + 1, 0);
                    if (v > se && v > nw) 
                        peak[y][x] = true;
                }
            }
        }
        
        for (int y = 1; y < gradX.getHeight() - 1; y++)
        {
            for (int x = 1; x < gradX.getWidth() - 1; x++)
            {
                if (!peak[y][x])
                    continue;
                final int v = magnitude.getRaster().getSample(x, y, 0);
                if (v < lo)
                {
                    edge[y][x] = false;
                    peak[y][x] = false;
                    continue;
                }
                else if (v >= hi)
                {
                    //System.out.println(x + ", " + y);
                    edge[y][x] = true;
                    peak[y][x] = false;
                    continue;
                }
            }
        }
        
        
        boolean done = false;
        while (!done)
        {
            done = true;
            for (int y = 1; y < gradX.getHeight() - 1; y++)
            {
                for (int x = 1; x < gradX.getWidth() - 1; x++)
                {
                    final int v = magnitude.getRaster().getSample(x, y, 0);
                    if (!edge[y][x])
                        continue;

                    //boolean neighborhoodDone = false;
                    //while (!neighborhoodDone)
                    //{
                        //neighborhoodDone = true;
                        for (int dy = -1; dy <= 1; dy++)
                        {
                            for (int dx = -1; dx <= 1; dx++)
                            {
                                if (peak[y + dy][x + dx])
                                {
                                    peak[y + dy][x + dx] = false;
                                    edge[y + dy][x + dx] = true;
                                    done = false;
                                    //neighborhoodDone = false;
                                }
                            }
                        }
                    //}
                }
            }
        }
        
        BufferedImage r_val = new BufferedImage(magnitude.getWidth(), magnitude.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        final WritableRaster raster = r_val.getRaster();
        
        for (int y = 0; y < r_val.getHeight(); y++)
        {
            for (int x = 0; x < r_val.getWidth(); x++)
            {
                if (edge[y][x])
                    raster.setSample(x, y, 0, 255);
            }
        }
        return r_val;
    }
    
    protected static BufferedImage magnitude(BufferedImage gradX, BufferedImage gradY)
    {
        assert gradX.getWidth() == gradY.getWidth() && gradX.getHeight() == gradY.getHeight();


        double mag[][] = new double[gradX.getHeight()][gradX.getWidth()];


        WritableRaster gradXRaster = gradX.getRaster(), gradYRaster = gradY.getRaster();
        double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        for (int y = 0; y < gradX.getHeight(); y++)
        {
            for (int x = 0; x < gradX.getWidth(); x++)
            {
                //int v = (int)Math.round((imgOut[y][x] - min) / (max - min) * 255);
                //raster.setSample(x, y, 0, Math.round(Math.sqrt(Math.pow(128 - gradXRaster.getSample(x, y, 0), 2) + Math.pow(128 - gradYRaster.getSample(x, y, 0), 2)))/ Math.sqrt(2));
                mag[y][x] = Math.sqrt(Math.pow(gradXRaster.getSample(x, y, 0), 2) + Math.pow(gradYRaster.getSample(x, y, 0), 2));
                if (mag[y][x] < min)
                    min = mag[y][x];
                else if (mag[y][x] > max)
                    max = mag[y][x];
            }
        }

        BufferedImage r_val = new BufferedImage(gradX.getWidth(), gradX.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = r_val.getRaster();

        for (int y = 0; y < r_val.getHeight(); y++)
        {
            for (int x = 0; x < r_val.getWidth(); x++)
            {
                raster.setSample(x, y, 0, Math.round((mag[y][x] - min) / (max - min) * 255));
            }
        }
        return r_val;
    }
    
    public static BufferedImage convolve(BufferedImage imgIn, double[][] mask)
    {     
        if (mask == null || imgIn == null) return null;
        if (mask.length >= imgIn.getWidth() || mask.length >= imgIn.getHeight()) return null;
        assert mask.length < imgIn.getWidth() || mask.length < imgIn.getHeight();
        
        double[][] imgOut = new double[imgIn.getHeight() - mask.length + 1][imgIn.getWidth() - mask[0].length + 1];
        
        double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        
        for (int y = 0; y <= imgIn.getHeight() - mask.length; y++)
        {
            for (int x = 0; x <= imgIn.getWidth() - mask[0].length; x++)
            {
                double v = 0;
                for (int maskY = 0; maskY < mask.length; maskY++)
                {
                    for (int maskX = 0; maskX < mask[0].length; maskX++)
                    {
                        v += mask[maskY][maskX] * imgIn.getRGB(x + maskX, y + maskY);
                        if (v < min)
                            min = v;
                        else if (v > max)
                            max = v;
                    }
                }
                imgOut[y][x] = v;
            }
        }
        
        BufferedImage r_val = new BufferedImage(imgIn.getWidth() - mask[0].length + 1, imgIn.getHeight() - mask.length + 1, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < r_val.getHeight(); y++)
        {
            for (int x = 0; x < r_val.getWidth(); x++)
            {
                
                int v = (int)Math.round((imgOut[y][x] - min) / (max - min) * 255);
                //Color rgb = new Color(v, v, v);
                
                r_val.getRaster().setSample(x, y, 0, v);
                
                //r_val.setRGB(x, y, rgb.getRGB());

            }
        }
        
        return r_val;           
    }
    
    public static double[][] createGaussianXDerivativeMask(int sigma)
    {
        System.out.println("GUASSIAN X");
        assert sigma > 0;
        double[][] r_val = new double [1 + 2 * sigma][1 + 2 * sigma];
        for (int y = 0; y < 1 + 2 * sigma; y++)
        {
            for (int x = 0; x < 1 + 2 * sigma; x++)
            {           
                r_val[y][x] = gaussianDerivative(sigma - x, sigma - y);
                System.out.print(r_val[y][x] + " ");
                
            }
            System.out.println();
        }
        return r_val;
    }
    
    public static double[][] createGaussianYDerivativeMask(int sigma)
    {
        System.out.println("GUASSIAN Y");
        assert sigma > 0;
        double[][] r_val = new double [1 + 2 * sigma][1 + 2 * sigma];
        for (int y = 0; y < 1 + 2 * sigma; y++)
        {
            for (int x = 0; x < 1 + 2 * sigma; x++)
            {
                r_val[y][x] = gaussianDerivative(sigma - y, sigma - x);
                System.out.print(r_val[y][x] + " ");
            }
            System.out.println();
        }
        return r_val;
    }

    private static double gaussianDerivative(double i, double d)
    {
        return -i * Math.exp(-1 * (Math.pow(i, 2) + Math.pow(d, 2)));
    }

}
