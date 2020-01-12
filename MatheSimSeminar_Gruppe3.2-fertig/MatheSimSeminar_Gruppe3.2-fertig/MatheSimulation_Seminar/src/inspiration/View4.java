package inspiration;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class View4 extends javax.swing.JFrame implements MouseMotionListener, MouseListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage bi;
    private Ball[] balls = new Ball[10];
    private Color bgcolor = new Color(10, 127, 10);
    private Timer timer = new Timer();
    
    private class Ball {

        String name;
        double r;
        Vector p;
        Vector v;
        Vector vp;
        Color color;

        public Ball(String name, double px, double py, double vx, double vy, double r, Color color) {
            this.name = name;
            this.r = r;
            p = new Vector(px, py, Color.BLACK);
            v = new Vector(vx, vy, Color.BLACK);
            vp = new Vector(0, 0, Color.BLACK);
            this.color = color;
        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.fillOval((int) (p.getX() - r / 2), (int) (p.getY() - r / 2), (int) r, (int) r);
            v.draw(g, p);
            g.setColor(Color.BLACK);
            g.drawString(name, (int) p.getX(), (int) p.getY());
        }

        public boolean collides(Ball b) {
            return p.sub(b.p, color).getSize() <= (r / 2 + b.r / 2);
        }

        public double collisionDif(Ball b) {
            return (p.sub(b.p, color).getSize() - (r / 2 + b.r / 2));
        }

        public void transferEnergy(Ball b) {
            
            // novo v2
            Vector nv2 = p.sub(b.p, color);
            nv2.normalize();
            nv2.multiply(v.dot(nv2));

            //if (v.dot(nv2)<0) {
            //    return;
            //}

            // novo v1
            Vector nv1 = v.sub(nv2, Color.yellow);

            // novo v2b
            Vector nv2b = b.p.sub(p, color);
            nv2b.normalize();
            nv2b.multiply(b.v.dot(nv2b));

            // novo v1b
            Vector nv1b = b.v.sub(nv2b, Color.yellow);

            b.v = nv2.add(nv1b, color);
            v = nv1.add(nv2b, color);
        }

        
        
        public void update() {
            // Com CCD
            p.setX(p.getX() + v.getX());
            p.setY(p.getY() + v.getY());
            for (Ball b : balls) {
                if (this != b && collides(b)) {
                    p.setX(p.getX() - v.getX());
                    p.setY(p.getY() - v.getY());
                    transferEnergy(b);
                    break;
                }
            }
            updateWallCollision();
            applyTableFriction();
        }
        
        private void updateWallCollision() {
            // borda
            if (p.getX()-r<-getWidth()/2){
                v.setX(Math.abs(v.getX()));
            }
            else if (p.getX()+r>getWidth()/2){
                v.setX(-Math.abs(v.getX()));
            }
            if (p.getY()-r<-getHeight()/2){
                v.setY(Math.abs(v.getY()));
            }
            else if (p.getY()+r>getHeight()/2){
                v.setY(-Math.abs(v.getY()));
            }
        }
        
        private void applyTableFriction() {
            v.setX(v.getX() * 0.985);
            v.setY(v.getY() * 0.985);
        }

        @Override
        public String toString() {
            return name;
        }
        
        
    }

    /**
     * Creates new form View
     */
    public View4() {
        initComponents();
        
        bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        balls[0] = new Ball("A", 100, 60, 0, 0, 50, Color.BLUE);
        balls[1] = new Ball("B", 100, -60, 0, 0, 50, Color.RED);
        balls[2] = new Ball("C", 10, 26, 0, 0, 50, Color.ORANGE);
        balls[3] = new Ball("D", 10, -26, 0, 0, 50, Color.BLACK);
        balls[4] = new Ball("E", -200, 0, 40, 0, 50, Color.WHITE);
        
        balls[5] = new Ball("F", -200, 200, 0, 0, 50, new Color(1000));
        balls[6] = new Ball("G", -140, 200, 0, 0, 50, new Color(5000));
        balls[7] = new Ball("H",  -80, 200, 0, 0, 50, new Color(9000));
        balls[8] = new Ball("I",  -20, 200, 0, 0, 50, new Color(13000));
        balls[9] = new Ball("J",   40, 200, 0, 0, 50, new Color(17000));

        /*
        balls[0] = new Ball("A", -260, 105, 16, -10, 50, Color.BLUE);
        balls[1] = new Ball("B", 0, 0, -10, 0, 50, Color.RED);
        balls[2] = new Ball("C", 70, -190, -22, 20, 50, Color.GREEN);
        balls[3] = new Ball("D", -30, 150, 0, 0, 50, Color.BLACK);
        balls[4] = new Ball("E", -200, 20, 0, 0, 50, Color.WHITE);
        */
        
        addMouseListener(this);
        addMouseMotionListener(this);

        timer.schedule(new MainLoop(), 100, 20);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 728, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 501, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new View4().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify                     
    // End of variables declaration                   

    public void update() {
        for (Ball b : balls) {
            b.update();
        }
    }
    
    @Override
    public void paint(Graphics g) {
        draw(bi.getGraphics());
        g.drawImage(bi, 0, 0, null);
    }
    
    public void draw(Graphics g) {
        ((Graphics2D) g).setBackground(bgcolor);
        g.clearRect(0, 0, getWidth(), getHeight());
        
        g.drawString("mouse: (" + mousePointer.x + ", " + mousePointer.y + ")", 50, 50);

        g.translate(getWidth() / 2, getHeight() / 2);
        ((Graphics2D) g).scale(1, -1);

        for (Ball b : balls) {
            b.draw(g);
        }
        
        //g.setColor(Color.DARK_GRAY);
        //g.drawLine((int) balls[4].p.getX(), (int) balls[4].p.getY(), mousePointer.x, mousePointer.y);
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        if (e.getKeyCode() == 37) {
            //v1.setX(v1.getX() - 5);
        } else if (e.getKeyCode() == 39) {
            //v1.setX(v1.getX() + 5);
        }

        if (e.getKeyCode() == 38) {
            //v1.setY(v1.getY() + 5);
        } else if (e.getKeyCode() == 40) {
            //v1.setY(v1.getY() - 5);
        }
        repaint();
    }

    private class MainLoop extends TimerTask {

        @Override
        public void run() {
            update();
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        repaint();
                    }
                });
            } catch (Exception ex) {
                Logger.getLogger(View4.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // --- Mouse ---
    
    private Point mousePointer = new Point(0, 0);

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePointer.x = e.getX() - getWidth() / 2;
        mousePointer.y = getHeight() / 2 - e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
         Vector bm = new Vector(mousePointer.x, mousePointer.y, Color.BLACK);
        balls[4].v = bm.sub(balls[4].p, Color.BLACK);
        //balls[4].v.normalize();
        balls[4].v.multiply(0.1);
        if (balls[4].v.getSize() > 100) {
            balls[4].v.multiply(100 / balls[4].v.getSize());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}