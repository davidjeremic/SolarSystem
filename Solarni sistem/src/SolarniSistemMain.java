import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;

public class SolarniSistemMain extends JPanel
{
    Model model;
    NebeskoTelo[] nebeskaTela = new NebeskoTelo[9];
    boolean[] descriptionSeen = new boolean[9];
    
    final static int DELAY = 50; 
    double velicina = 1;
    BufferedImage[] buffimgs = new BufferedImage[9];
    String[][] description;
    boolean stop = false;
    int clicked = -1;
        
    public SolarniSistemMain()
    {
          model = new Model();
          model.setPreferredSize(new Dimension(1200, 1200));
          add(model);
          
           nebeskaTela[0] = new NebeskoTelo(600, 450, -4.7, 0, 9, 8, Color.GRAY, 1000); //Merkur
           nebeskaTela[1] = new NebeskoTelo(752, 400, 0, 2.5, 900, 12, new Color(207,153,52), 1000); //Venera
           nebeskaTela[2] = new NebeskoTelo(600, 150, 1.8, 0, 900, 11, Color.BLUE, 2000); //Zemlja
           nebeskaTela[3] = new NebeskoTelo(650, -50, 1.2, 0, 900, 7, Color.RED, 2000); //Mars
           nebeskaTela[4] = new NebeskoTelo(600, -100, 1.2, 0, 900, 20, new Color(255,140,0), 2000); //Jupiter
           nebeskaTela[5] = new NebeskoTelo(600, -150, 1.2, 0, 900, 15, new Color(112,128,144), 2000); //Saturn
           nebeskaTela[6] = new NebeskoTelo(600, -175, 1.2, 0, 900, 15, new Color(196,233,238), 2000); //Uran
           nebeskaTela[7] = new NebeskoTelo(0, 400, 0, -1.2, 900, 13, new Color(66, 98, 243), 2000);//Neptun
            
           nebeskaTela[8] = new NebeskoTelo(600, 400, .1, 0, 1000, 30, Color.ORANGE, 0);//Sunce
            
                      
                    
          setBackground(Color.BLACK);
         
          description = new String[][]{ 
              {"Merkur"," dijametar: " + nebeskaTela[0].getDijametar()*1058 + " kilometara",
                  "masa: 0.330 x 10^(24) kg",
                  "Tip atmosfere: Tanak",
                  "Prosecna temperatura: 167  C",
                  "Prosecno trajanje dana: 3.1 dan planete Zemlje",
                  "Najbliza suncu"},
              {"Venera"," dijametar: " + nebeskaTela[1].getDijametar()*1058 + " kilometara",
                  	"masa: 4.87 x 10^(24) kg",
                    "Tip atmosfere: Srednje Tanak",
                    "Prosecna temperatura: 464  C",                             
                    "Prosecno trajanje dana: 9 dana planete Zemlje",
                    "Poznata kao blizanac planete zemlje"
                  },
              { "Planeta Zemlja"," dijametar: " + nebeskaTela[2].getDijametar()*1058 + " kilometara",
                      "masa: 5.97 x 10^(24) kg",
                      "Tip atmosfere: Tanak",
                      "Prosecna temperatura: 15  C",
                      "Prosecno trajanje dana: 1 dan planete Zemlje",
                      "Nas dom"	
                 },
              { "Mars"," dijametar: " + nebeskaTela[3].getDijametar()*1058 + " kilometara",
                  "masa: 0.642 x 10^(24) kg",
                  "Tip atmosfere: Srednje debeo",
                  "Prosecna temperatura: -65  C",
                  "Prosecno trajanje dana: 8 dana planete Zemlje",
                  "Poznata kao crvena planeta"
                  },
              {"Jupiter"," dijametar: " + nebeskaTela[4].getDijametar()*1058 + " kilometara",
                      "masa: 1898 x 10^(24) kg",
                      "Tip atmosfere: Debeo",
                      "Prosecna temperatura: -110  C",
                      "Prosecno trajanje dana: 6 dana planete Zemlje",
                      "Najveca planeta u solarnom sistemu"},
                  {"Saturn"," dijametar: " + nebeskaTela[5].getDijametar()*3058 + " kilometara",
                      "masa: 568 x 10^(24) kg",
                      "Prosecna temperatura: -140  C",
                      "Tip atmosfere: Debeo",
                      "Poznata po svojim prstenovima"}, 
                  {"Uran"," dijametar: " + nebeskaTela[6].getDijametar()*3058 + " kilometara",
                          "masa: 86.8 x 10^(24) kg",
                          "Tip atmosfere: Debeo",
                          "Prosecna temperatura: -195  C",
                          "Sastavljena od leda i stena"},
              {"Neptun"," dijametar: " + nebeskaTela[7].getDijametar()*1058 + " kilometara",
                  "masa: 102 x 10^(24) kg",
                  "Tip atmosfere: Tanak",
                  "Tip atmosfere: Debeo",
                  "Prosecna temperatura: -200  C",
                  "Prosecno trajanje dana: 6 dana planete Zemlje",
                  "Jedina planeta pronadjena matematickim predvidjanjem"},
              {"Sunce"," dijametar: " + nebeskaTela[8].getDijametar()*3058 + " kilometara",
                  "masa: 1.989 × 10^30 kg",
                  "Tip atmosfere: Debeo",
                  "Prosecna temperatura: 5505  C",
                  "Najvece nebesko telo u solarnom sistemu"},
           };

           buffimgs[0] = getSlika("Merkur.jpg"); 
           buffimgs[1] = getSlika("Venera.jpg");
           buffimgs[2] = getSlika("PlanetaZemlja.jpg");
           buffimgs[3] = getSlika("Mars.jpg");
           buffimgs[4] = getSlika("Jupiter.jpg");
           buffimgs[5] = getSlika("Saturn.jpg");
           buffimgs[6] = getSlika("Uran.jpg");
           buffimgs[7] = getSlika("Neptun.jpg");
           buffimgs[8] = getSlika("Sunce.jpg");
           

          Thread thread =  new Thread() {
     
            @Override
             public void run() {
                gameLoop();
             }
          }; 
          
          thread.start();
    }
   
    
    public static BufferedImage getSlika(String ref) {  
        BufferedImage bimg = null;  
        try {  
  
            bimg = ImageIO.read(new File(ref));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bimg;  
    }  
    private void gameLoop() {
    	
      while (true) {
            if (!stop)
            {
                for(int i = 0; i <nebeskaTela.length-1; i++)
                {
                	 nebeskaTela[i].update( nebeskaTela[8].getXPozicija(), nebeskaTela[8].getYPozicija(), nebeskaTela[8].getMasa());
                }
            }
         repaint();

         try {
            Thread.sleep(DELAY);
         } catch (InterruptedException ex) { }
      }
   }
    

    class Model extends JPanel implements KeyListener, MouseListener {
      public Model() {

         setFocusable(true); 
         requestFocus();
         addKeyListener(this);
         addMouseListener(this);
      }

      
      public void paintComponent(Graphics g) {


         for(NebeskoTelo body :nebeskaTela)
            body.draw(g,velicina);
         
         
         
         for(int count=0;count<=1000;count++) {
        	 g.setColor(Color.WHITE); 
        	 
        	 g.drawOval(50*count,100*count,1,1);
        	g.drawOval(75*count,100*count,1,1);
        	
        	 g.drawOval(100*count,200*count,1,1);
        	 g.drawOval(150*count,200*count,1,1);
        	 g.drawOval(200*count,200*count,1,1);
        	 g.drawOval(250*count,200*count,1,1);
        	 g.drawOval(300*count,200*count,1,1);
        	 g.drawOval(350*count,200*count,1,1);
        	 g.drawOval(400*count,100*count,1,1);
        	 g.drawOval(450*count,100*count,1,1);
        	 g.drawOval(500*count,100*count,1,1);
        	 g.drawOval(550*count,300*count,1,1);
        	 g.drawOval(600*count,300*count,1,1);
        	 g.drawOval(700*count,300*count,1,1);
        	 g.drawOval(800*count,300*count,1,1);
        	 g.drawOval(900*count,300-count,1,1);
        	 g.drawOval(1000*count,300-count,1,1);



        	 }
         //
         
         for (int i = 0; i <nebeskaTela.length; i++)
         {
             if ( nebeskaTela[i].getDescVidljivost())
            	nebeskaTela[i].disDes(g,velicina);
         }
         if (clicked > -1)
         {
             g.drawImage(buffimgs[clicked],0,0,200,200,Color.WHITE,null);
             g.setFont(new Font("Arial", Font.PLAIN, 20));
             g.setColor(Color.WHITE);
             for(int i = 0; i < description[clicked].length; i++)
             {
                 g.drawString(description[clicked][i], 0, 210+i*30);
             }
         }
         
          
          
          String legend = "Solarni sistem legenda";
        	String zoomIn = "+ KEY  =  Zoom in";
        	String zoomOut = "- KEY  =  Zoom Out";
        	 String spaceBar = "SPACEBAR = Pauza/Start";
        	 String mouseClick =  "MOUSECLICK = Vise informacija o planeti";
        	String escape = "ESC = Izlaz";
      
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.setColor(Color.WHITE);
         g.drawString(legend,950,60);
         g.drawString(zoomIn, 950, 100);
         g.drawString(zoomOut, 950, 120);
         g.drawString(spaceBar, 950, 140);
         g.drawString(mouseClick, 950, 160);
         g.drawString(escape, 950, 220);
         
         
        nebeskaTela[0].disDes(g,velicina);
        nebeskaTela[1].disDes(g,velicina);
        nebeskaTela[2].disDes(g,velicina);
        nebeskaTela[3].disDes(g,velicina);
        nebeskaTela[4].disDes(g,velicina);
        nebeskaTela[5].disDes(g,velicina);
        nebeskaTela[6].disDes(g,velicina);
        nebeskaTela[7].disDes(g,velicina);
        nebeskaTela[8].disDes(g,velicina);

      }
      
      public void keyTyped(KeyEvent e) {
    	  
      }
      public void mousePressed(MouseEvent e) {
    	  
      }
      public void mouseReleased(MouseEvent e) {
          for(int i = 0; i <nebeskaTela.length; i++)
              if (nebeskaTela[i].hitPlanet(e.getX(), e.getY(), velicina))
              {
                  
            	 nebeskaTela[i].setDescVidljivost(!nebeskaTela[i].getDescVidljivost());
                  if(nebeskaTela[i].getDescVidljivost()) {
                	  clicked = i;
                  }
                  else  {
                	  clicked = -1;
                  }
              }
      }
      public void mouseEntered(MouseEvent e) { 
    	  
      }
      public void mouseExited(MouseEvent e) { 
    	  
      }
      public void mouseClicked(MouseEvent e) { 
    	  
      }
      
      public void keyPressed(KeyEvent e) {
    	  
      }
      
      @Override
      public void keyReleased(KeyEvent e) { 

          if(e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS)
        	  velicina += .1;
          
    	  if(e.getKeyCode() == KeyEvent.VK_MINUS && velicina > 0)
        	  velicina -= .1;
    	  
          if(e.getKeyCode() == KeyEvent.VK_SPACE)
          {
              stop = !stop;
          }
          if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
          {
              System.exit(0);
          }
         
      }
   

   }
    
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            JFrame frame = new JFrame("Solarni Sistem");
            frame.setContentPane(new SolarniSistemMain());  
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); 
            frame.setVisible(true);            
         }
      });
    }
}




