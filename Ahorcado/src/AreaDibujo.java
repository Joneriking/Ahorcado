import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;

public class AreaDibujo extends Canvas {
	//DATOS
	private int numFallos;
	private BufferedImage img;
	private Image subImg;
	//GETTERS Y SETTERS
	public int getNumFallos() {
		return numFallos;
	}
	/*
    //GETTER Y SETTER
    public Timer getReloj() {
        return reloj;
    }
    public void setReloj(Timer reloj) {
        this.reloj = reloj;
    }
*/

	public void setNumFallos(int numFallos) {
		this.numFallos = numFallos;
	}

	public AreaDibujo(){
		numFallos=0;
		try {
            img = ImageIO.read(new File("Images/Ahorcadoassets.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
		/*distancia=0;
        reloj= new Timer(40, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();

            }
        });
        */
	}
	


	
	@Override
	public void paint(Graphics g) { 
		setBackground(null);
		//DIBUJAR TODOS LOS ELEMENTOS GRÁFICOS QUE 
		//FORMAN PARTE DEL "JUEGO"
		dibujarImg(g);
		//dibujarAhorcadoGraph(g);
	}

	private void dibujarImg(Graphics g) {
		int x=0,y=0,i= numFallos;
		super.paint(g);
		
		
		if(i<0){
			i=7;
			x= 215 +(3*739);
			y=416;
		}
		
		if(i<4){
			x= 215 +(i*739);
			y=0;
		}
		
		if(i==6){
			x= 150 +((i-4)*739);
			y=416;
		}
		
		
		if(i>3 && i<6){
			x= 215 +((i-4)*739);
			y=416;
		}
		
		subImg = img.getSubimage(x, y, 259 , 416);
		g.drawImage(subImg, 0, 0, null);
	}
	
	
}
