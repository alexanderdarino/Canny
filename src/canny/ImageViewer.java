/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ImageViewer.java
 *
 * Created on May 19, 2011, 4:01:19 PM
 */
package canny;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Alexander Darino
 */
public class ImageViewer extends javax.swing.JFrame
{

    /** Creates new form ImageViewer */
    private final BufferedImage orig;
    private BufferedImage gradX, gradY, magnitude;
    public ImageViewer(BufferedImage orig)
    {
        initComponents();
        this.orig = orig;
        origImageLabel.setIcon(new ImageIcon(orig));
        createGradientImages();
    }

    private void createGradientImages()
    {
        this.gradX = ImageProcessor.convolve(orig, ImageProcessor.createGaussianXDerivativeMask((Integer)sigmaSpinner.getValue()));
        gradXImageLabel.setIcon(new ImageIcon(gradX));
        
        this.gradY = ImageProcessor.convolve(orig, ImageProcessor.createGaussianYDerivativeMask((Integer)sigmaSpinner.getValue()));
        gradYImageLabel.setIcon(new ImageIcon(gradY));
        
        this.magnitude = ImageProcessor.magnitude(gradX, gradY);
        magnitudeImageLabel.setIcon(new ImageIcon(magnitude));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        scrollPane = new javax.swing.JScrollPane();
        imagePanel = new javax.swing.JPanel();
        origPanel = new javax.swing.JPanel();
        origImageLabel = new javax.swing.JLabel();
        gradXPanel = new javax.swing.JPanel();
        gradXImageLabel = new javax.swing.JLabel();
        gradYPanel = new javax.swing.JPanel();
        gradYImageLabel = new javax.swing.JLabel();
        magnitudePanel = new javax.swing.JPanel();
        magnitudeImageLabel = new javax.swing.JLabel();
        edgesPanel = new javax.swing.JPanel();
        detectedEdgesImageLabel = new javax.swing.JLabel();
        controlsPanel = new javax.swing.JPanel();
        sigmaPanel = new javax.swing.JPanel();
        sigmaLabel = new javax.swing.JLabel();
        sigmaSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(1, 1, 1024, 1));
        updateSigmaButton = new javax.swing.JButton();
        loThreshholdPanel = new javax.swing.JPanel();
        loThreshholdLabel = new javax.swing.JLabel();
        loThreshholdSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(140, 0, 255, 1));
        hiThreshholdPanel = new javax.swing.JPanel();
        hiThreshholdLabel = new javax.swing.JLabel();
        hiThreshholdSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(160, 0, 255, 1));
        detectEdgesButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        imagePanel.setAutoscrolls(true);
        imagePanel.setLayout(new java.awt.GridLayout(2, 3));

        origPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Original Image"));
        origPanel.setLayout(new java.awt.BorderLayout());
        origPanel.add(origImageLabel, java.awt.BorderLayout.CENTER);

        imagePanel.add(origPanel);

        gradXPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("X Gradient"));
        gradXPanel.setLayout(new java.awt.BorderLayout());
        gradXPanel.add(gradXImageLabel, java.awt.BorderLayout.CENTER);

        imagePanel.add(gradXPanel);

        gradYPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Y Gradient"));
        gradYPanel.setLayout(new java.awt.BorderLayout());
        gradYPanel.add(gradYImageLabel, java.awt.BorderLayout.CENTER);

        imagePanel.add(gradYPanel);

        magnitudePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Net Gradient"));
        magnitudePanel.setLayout(new java.awt.BorderLayout());
        magnitudePanel.add(magnitudeImageLabel, java.awt.BorderLayout.CENTER);

        imagePanel.add(magnitudePanel);

        edgesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Detected Edges"));
        edgesPanel.setLayout(new java.awt.BorderLayout());
        edgesPanel.add(detectedEdgesImageLabel, java.awt.BorderLayout.CENTER);

        imagePanel.add(edgesPanel);

        controlsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Threshhold Controls"));
        controlsPanel.setLayout(new java.awt.GridBagLayout());

        sigmaLabel.setText("Sigma:");
        sigmaPanel.add(sigmaLabel);
        sigmaPanel.add(sigmaSpinner);

        updateSigmaButton.setText("Update Sigma");
        updateSigmaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateSigmaButtonActionPerformed(evt);
            }
        });
        sigmaPanel.add(updateSigmaButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        controlsPanel.add(sigmaPanel, gridBagConstraints);

        loThreshholdLabel.setText("Low Threshhold:");
        loThreshholdPanel.add(loThreshholdLabel);
        loThreshholdPanel.add(loThreshholdSpinner);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        controlsPanel.add(loThreshholdPanel, gridBagConstraints);

        hiThreshholdLabel.setText("Hi Threshhold:");
        hiThreshholdPanel.add(hiThreshholdLabel);
        hiThreshholdPanel.add(hiThreshholdSpinner);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        controlsPanel.add(hiThreshholdPanel, gridBagConstraints);

        detectEdgesButton.setText("Detect Edges");
        detectEdgesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detectEdgesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        controlsPanel.add(detectEdgesButton, gridBagConstraints);

        imagePanel.add(controlsPanel);

        scrollPane.setViewportView(imagePanel);

        getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void detectEdgesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_detectEdgesButtonActionPerformed
    {//GEN-HEADEREND:event_detectEdgesButtonActionPerformed
        int hi = (Integer)hiThreshholdSpinner.getValue();
        int lo = (Integer)loThreshholdSpinner.getValue();
        if (hi < lo) return;
        
        BufferedImage edges = ImageProcessor.detectEdges(gradX, gradY, magnitude, hi, lo);
        detectedEdgesImageLabel.setIcon(new ImageIcon(edges));
    }//GEN-LAST:event_detectEdgesButtonActionPerformed

    private void updateSigmaButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_updateSigmaButtonActionPerformed
    {//GEN-HEADEREND:event_updateSigmaButtonActionPerformed
        createGradientImages();
        detectEdgesButtonActionPerformed(null);
    }//GEN-LAST:event_updateSigmaButtonActionPerformed

//    public void addImage(Image image)
//    {
//        imagePanel.add(new JLabel(new ImageIcon(image)));
//    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JButton detectEdgesButton;
    private javax.swing.JLabel detectedEdgesImageLabel;
    private javax.swing.JPanel edgesPanel;
    private javax.swing.JLabel gradXImageLabel;
    private javax.swing.JPanel gradXPanel;
    private javax.swing.JLabel gradYImageLabel;
    private javax.swing.JPanel gradYPanel;
    private javax.swing.JLabel hiThreshholdLabel;
    private javax.swing.JPanel hiThreshholdPanel;
    private javax.swing.JSpinner hiThreshholdSpinner;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JLabel loThreshholdLabel;
    private javax.swing.JPanel loThreshholdPanel;
    private javax.swing.JSpinner loThreshholdSpinner;
    private javax.swing.JLabel magnitudeImageLabel;
    private javax.swing.JPanel magnitudePanel;
    private javax.swing.JLabel origImageLabel;
    private javax.swing.JPanel origPanel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JLabel sigmaLabel;
    private javax.swing.JPanel sigmaPanel;
    private javax.swing.JSpinner sigmaSpinner;
    private javax.swing.JButton updateSigmaButton;
    // End of variables declaration//GEN-END:variables
}