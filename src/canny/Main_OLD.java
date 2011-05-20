//package canny;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.GridLayout;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//
//public class Main_OLD {
//
//
//    public static void main(String[] args) {
//
//        if (args.length < 2) System.exit(1);
//
//        final int sigma = Integer.parseInt(args[1]);
//        if (sigma <= 0) System.exit(1);
//        assert sigma > 0;
//
//        double[][] maskX = createGaussianXDerivativeMask(sigma);
//        double[][] maskY = createGaussianYDerivativeMask(sigma);
//        assert maskX[0].length == maskY[0].length && maskX.length == maskY.length;
//        
//        
////        for (double[] row : maskX)
////        {
////            for (double v : row)
////                System.out.print(v + " ");
////            System.out.println();
////        }
//
//        final String filename = args[0];
//        final File fin = new File(filename);
//        if (!fin.exists()) System.exit(1);
//        assert fin.exists();
//        
//        BufferedImage imgIn = null;
//        try {
//            imgIn = ImageIO.read(fin);
//            //imgIn.
//        }
//        catch (IOException ex) {
//            Logger.getLogger(Main_OLD.class.getName()).log(Level.SEVERE, null, ex);
//            System.exit(1);
//        }
//        assert imgIn != null;
//        
//        if (maskX.length >= imgIn.getWidth() || maskX.length >= imgIn.getHeight()) System.exit(1);
//        assert maskX.length < imgIn.getWidth() || maskX.length < imgIn.getHeight();
//        
//        
//        BufferedImage gradX = convolve(imgIn, maskX);
//        BufferedImage gradY = convolve(imgIn, maskY);
//        
//        assert gradX.getWidth() == gradY.getWidth() && gradX.getHeight() == gradY.getHeight();
//        
//        for (int y = 1; y < gradX.getHeight() - 1; y++)
//        {
//            for (int x = 1; y < gradX.getWidth() - 1; x++)
//            {
//                Color colorX = new Color(gradX.getRGB(x, y));
//                Color colorY = new Color(gradY.getRGB(x, y));
//                
//                if (colorX.getRed() == 0) // Vertical
//                {
//                    if ()
//                    break;
//                }
//                    
//                final double theta = Math.atan2(colorY.getRed(), colorX.getRed());
//                final double piThird = Math.PI/3;
//                if (theta >= 2 * piThird || theta <= -2 * piThird) // Vertical
//                {
//                    
//                }
//                else if (theta >= piThird && theta < 2 * piThird) // SW-NE
//                {
//                    
//                }
//                else if (theta >= -1 * piThird && theta < piThird) // Horizontal
//                {
//                    
//                }
//                else // SE - NW
//                {
//                    
//                }
//            }
//        }
//        
//        displayImage(gradX, gradY);
//        
//        
//        
//
//    }
//    
//    
//    public static BufferedImage convolve(BufferedImage imgIn, double[][] mask)
//    {     
//        if (mask == null || imgIn == null) return null;
//        if (mask.length >= imgIn.getWidth() || mask.length >= imgIn.getHeight()) return null;
//        assert mask.length < imgIn.getWidth() || mask.length < imgIn.getHeight();
//        
//        double[][] imgOut = new double[imgIn.getHeight() - mask.length + 1][imgIn.getWidth() - mask[0].length + 1];
//        
//        double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
//        
//        for (int y = 0; y <= imgIn.getHeight() - mask.length; y++)
//        {
//            for (int x = 0; x <= imgIn.getWidth() - mask[0].length; x++)
//            {
//                double v = 0;
//                for (int maskY = 0; maskY < mask.length; maskY++)
//                {
//                    for (int maskX = 0; maskX < mask[0].length; maskX++)
//                    {
//                        v += mask[maskY][maskX] * imgIn.getRGB(x + maskX, y + maskY);
//                        if (v < min)
//                            min = v;
//                        else if (v > max)
//                            max = v;
//                    }
//                }
//                imgOut[y][x] = v;
//            }
//        }
//        
//        BufferedImage r_val = new BufferedImage(imgIn.getWidth() - mask[0].length + 1, imgIn.getHeight() - mask.length + 1, BufferedImage.TYPE_BYTE_GRAY);
//        for (int y = 0; y < r_val.getHeight(); y++)
//        {
//            for (int x = 0; x < r_val.getWidth(); x++)
//            {
//                
//                int v = (int)Math.round((imgOut[y][x] - min) / (max - min) * 255);
//                Color rgb = new Color(v, v, v);
//                
//                //int rgb = (255 << 24) + (v << 16) + (v << 8) + v;
//                //System.out.println(Integer.toBinaryString(rgb.getRGB()));
//                //System.out.println(Integer.toBinaryString(rgb));
//                
//                r_val.setRGB(x, y, rgb.getRGB());
//
//            }
//        }
//        
//        return r_val;           
//    }
//    
//    public static void displayImage(final BufferedImage img, final BufferedImage img2)
//    {
//        
//        JFrame frame = new JFrame();
//        frame.setLayout(new GridLayout(1,2));
//        frame.add(new JLabel(new ImageIcon(img)));
//        frame.add(new JLabel(new ImageIcon(img2)));
//        frame.setVisible(true);
//    }
//    
//    public static double[][] createGaussianXDerivativeMask(int sigma)
//    {
//        assert sigma > 0;
//        double[][] r_val = new double [1 + 2 * sigma][1 + 2 * sigma];
//        for (int y = 0; y < 1 + 2 * sigma; y++)
//        {
//            for (int x = 0; x < 1 + 2 * sigma; x++)
//            {
//                r_val[y][x] = gaussianDerivative(x - sigma, y - sigma);
//            }
//        }
//        return r_val;
//    }
//    
//    public static double[][] createGaussianYDerivativeMask(int sigma)
//    {
//        assert sigma > 0;
//        double[][] r_val = new double [1 + 2 * sigma][1 + 2 * sigma];
//        for (int y = 0; y < 1 + 2 * sigma; y++)
//        {
//            for (int x = 0; x < 1 + 2 * sigma; x++)
//            {
//                r_val[y][x] = gaussianDerivative(y - sigma, x - sigma);
//            }
//        }
//        return r_val;
//    }
//
//    private static double gaussianDerivative(double i, double d)
//    {
//        return -i * Math.exp(-1 * (Math.pow(i, 2) + Math.pow(d, 2)));
//    }
//
//}
