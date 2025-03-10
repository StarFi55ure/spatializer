package io.spatializer.core.extern;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * See {@link org.jdesktop.swingx.graphics.BlendComposite}.
 *
 * Source: http://www.java2s.com/Code/Java/2D-Graphics-GUI/BlendCompositeDemo.htm
 *
 * @author Romain Guy <romain.guy@mac.com>
 */
public class BlendCompositeDemo extends JFrame {
    private CompositeTestPanel compositeTestPanel;
    private JComboBox combo;
    private JSlider slider;

    public BlendCompositeDemo() {
        super("Blend Composites");

        compositeTestPanel = new CompositeTestPanel();
        compositeTestPanel.setComposite(BlendComposite.Average);
        add(compositeTestPanel);

        combo = new JComboBox(BlendComposite.BlendingMode.values());
        combo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                compositeTestPanel.setComposite(
                    BlendComposite.getInstance(
                        BlendComposite.BlendingMode.valueOf(combo.getSelectedItem().toString()),
                        slider.getValue() / 100.0f
                    ));
            }
        });

        slider = new JSlider(0, 100, 100);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                BlendComposite blend = (BlendComposite) compositeTestPanel.getComposite();
                blend = blend.derive(slider.getValue() / 100.0f);
                compositeTestPanel.setComposite(blend);
            }
        });

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(combo);
        controls.add(new JLabel("0%"));
        controls.add(slider);
        controls.add(new JLabel("100%"));
        add(controls, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static class CompositeTestPanel extends JPanel {
        private BufferedImage image = null;
        private Composite composite = AlphaComposite.Src;
        private BufferedImage imageA;
        private BufferedImage imageB;
        private boolean repaint = false;

        public CompositeTestPanel() {
            setOpaque(false);
            try {
                imageA = GraphicsUtilities.loadCompatibleImage(getClass().getResource("A.jpg"));
                imageB = GraphicsUtilities.loadCompatibleImage(getClass().getResource("B.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(imageA.getWidth(), imageA.getHeight());
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (image == null) {
                image = new BufferedImage(imageA.getWidth(),
                                          imageA.getHeight(),
                                          BufferedImage.TYPE_INT_ARGB);
                repaint = true;
            }

            if (repaint) {
                Graphics2D g2 = image.createGraphics();
                g2.setComposite(AlphaComposite.Clear);
                g2.fillRect(0, 0, image.getWidth(), image.getHeight());

                g2.setComposite(AlphaComposite.Src);
                g2.drawImage(imageA, 0, 0, null);
                g2.setComposite(getComposite());
                g2.drawImage(imageB, 0, 0, null);
                g2.dispose();

                repaint = false;
            }

            int x = (getWidth() - image.getWidth()) / 2;
            int y = (getHeight() - image.getHeight()) / 2;
            g.drawImage(image, x, y, null);
        }

        public void setComposite(Composite composite) {
            if (composite != null) {
                this.composite = composite;
                this.repaint = true;
                repaint();
            }
        }

        public Composite getComposite() {
            return this.composite;
        }
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BlendCompositeDemo().setVisible(true);
            }
        });
    }
}
