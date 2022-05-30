import java.awt.*;
import java.util.*;

public class NebeskoTelo
{

	private int masa = 0;
	private int  dijametar = 0;
    private double xLoc = 0; 
    private double yLoc = 0;
    private double velX = 0; 
    private double velY = 0;
    private double brzina = 0;
    Color boja;
    private double ubrzanje =0;
    private double dirX = 0;
    private double dirY = 0;
    private double rastojanje = 0;
    private double initial=1000;
    private double max=0;
    boolean vidljivost;
    int tackeOrbite[][] = new int[1000][2];
    int brojac = 0;
    /**
     * Konstruktor za objekte
     */
    public NebeskoTelo(double x, double y, double xVelocity, double yVelocity, int masaTela, int dijametarTela, Color bojaTela, double brzinaTela)
    {
       xLoc = x;
       yLoc = y;
       velX = xVelocity;
       velY = yVelocity;
       masa = masaTela;
        dijametar = dijametarTela;
       boja = bojaTela;
        brzina = brzinaTela;
    }
    public double getXPozicija(){
    	return xLoc;
    	}
    public double getYPozicija(){
    	return yLoc;
    	}
    public int getMasa(){
    	return masa;
    	}
    public int getDijametar(){
    	return  dijametar;
    	}
    public boolean getDescVidljivost() {
    	return vidljivost;
    	}
    public void setDescVidljivost(boolean b) {
    	vidljivost = b;
    	}
    
    public void move()
    {
        xLoc += velX;
        yLoc += velY;
    }
    
    
    public boolean hitPlanet(int x, int y, double scale)
    {
        return (x>600+(getXPozicija()- dijametar-600)*scale && x<600+(getXPozicija()+ dijametar-600)*scale && 
                y>400+(getYPozicija()- dijametar-400)*scale && y<400+(getYPozicija()+ dijametar-400)*scale);
    }
 
 
    public void update(double ZvezdaX, double ZvezdaY, int MasaZvezde)
    {
        if (vidljivost){
            tackeOrbite[brojac][0]=(int)(xLoc+.5);
            tackeOrbite[brojac][1]=(int)(yLoc+.5);
            brojac = (brojac+1)%1000;
        }
        else{
            tackeOrbite = new int[1000][2];
            brojac = 0;
        }
        rastojanje = Math.sqrt((ZvezdaX - xLoc)*(ZvezdaX - xLoc) + (ZvezdaY - yLoc)*(ZvezdaY - yLoc));
        initial = Math.min(rastojanje,initial);
        max = Math.max(rastojanje,max);
        
        ubrzanje = MasaZvezde/rastojanje/rastojanje;
        
        dirX = (ZvezdaX-xLoc)/rastojanje;
        dirY = (ZvezdaY-yLoc)/rastojanje;
        
        velX += dirX * ubrzanje;
        velY += dirY * ubrzanje;
        move();
       
    }
    public void draw(Graphics g, double velicina)
    {
        g.setColor(boja);
        g.fillOval((int)(650+(xLoc- dijametar/2-650)*velicina), (int)(500+(yLoc- dijametar/2-500)*velicina),
                    (int)( dijametar*velicina), (int)( dijametar*velicina));
    }
    public void disDes(Graphics g, double scale)
    {
        g.setColor(boja);
        for (int[] orbit : tackeOrbite)
            g.drawLine(orbit[0],orbit[1],orbit[0],orbit[1]);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.setColor(Color.MAGENTA);
        
        g.drawString((Math.round(rastojanje*100.0)/100.0) * 1000000 + " km", 
                     dijametar+(int)(600+(xLoc- dijametar/2-600)*scale), 16+(int)(400+(yLoc- dijametar/2-400)*scale)+ dijametar);
        
    }
}


