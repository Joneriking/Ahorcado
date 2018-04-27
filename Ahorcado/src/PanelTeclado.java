import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingConstants;

public class PanelTeclado extends JPanel {
	public static final int ALTO=25;
	public static final int ANCHO=25;
	public static final int SEP=10;
	
	private JLabel lblPalabra;
	private JButton[] arrayTeclado;
	private ArrayList<String> arrayPalabras;
	private JButton btnNueva;
	private String palabra;
	private int numFallos;
	private AreaDibujo areaDibujo;
	/**
	 * Create the panel.
	 */
	public PanelTeclado(AreaDibujo areaDibujo) {
		setLayout(null);
		this.areaDibujo=areaDibujo;
		lblPalabra = new JLabel("- - - - - - - - ");
		lblPalabra.setHorizontalAlignment(SwingConstants.LEFT);
		lblPalabra.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPalabra.setBounds(10, 38, 201, 25);
		this.add(lblPalabra);
		
		btnNueva = new JButton("Nueva palabra");
		btnNueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(arrayPalabras.size()>0){
					numFallos=0;
					areaDibujo.setNumFallos(0);
					areaDibujo.repaint();
					palabra=elegirPalabra();
					estadoBotones(true);
					System.out.println(palabra);
				}
				else{
					estadoBotones(false);
					JOptionPane.showMessageDialog(null, 
						"No se puede continuar. No hay más palabras");
				}
				
			}
		});
		btnNueva.setBounds(0, 349, 121, 23);
		add(btnNueva);
		
		crearTeclado();
		cargarPalabras();
		palabra=elegirPalabra();
		estadoBotones(true);

	}//FINAL DEL CONSTRUCTOR

	private void cargarPalabras() {
		//CARGAR TODAS LAS PALBRAS DEL FICHERO EN 
		//EL ARRAYLIST DE PALABRAS
		BufferedReader br;
		String palabra;
		String palAux="";
		arrayPalabras=new ArrayList<String>();
		try {
			br=new BufferedReader(
					new FileReader("palabras.txt"));
			
			while((palabra=br.readLine())!=null){
				arrayPalabras.add(palabra);
			}
			
			/*do{
				*palabra=br.readLine();
				if(palabra!=null){
				
				}
				palAux=palAux+palabra+"-";
			}while(palabra!=null);*/
			//JOptionPane.showMessageDialog(null, palAux);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void crearTeclado() {
		JButton btnAux;
		arrayTeclado=new JButton[27];
		int aux=0;
		//PARA CADA BOTON
			//CREARLO
			//POSICION y TAMAÑO
			//CONTENIDO
			//REGISTRO EVENTO
			//AÑADIRLO AL PANEL
			//AÑADIRLO AL ARRAY
		for(int i=0;i<27;i++){
			btnAux=new JButton();
			if(i==0){//PRIMER BOTON
				btnAux.setBounds(25, 75, ANCHO, ALTO);
			}
			else if(i%5==0){//BOTONES DE LA COLUMNA1
				btnAux.setBounds(arrayTeclado[0].getX(),
						arrayTeclado[i-5].getY()+ALTO+SEP, ANCHO, ALTO);
			}
			else{
				btnAux.setBounds(arrayTeclado[i-1].getX()+ANCHO+SEP,
						arrayTeclado[i-1].getY(), ANCHO, ALTO);
			}
			if((char)(i+65)=='O'){   //i==14)
				btnAux.setText("Ñ");
				aux=-1;
			}
			else{
				btnAux.setText((char)(i+65+aux)+"");
			}
			btnAux.setMargin(new Insets(0, 0, 0, 0));
			this.add(btnAux);
			
			btnAux.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					pulsarLetra(e);
				}
			});
			arrayTeclado[i]=btnAux;
		}
	}

	protected void pulsarLetra(ActionEvent e) {
		JButton btn;
		boolean esta=false;
		String letra;
		StringBuffer lblBuffer;
		int pos=-1;
		btn=(JButton)(e.getSource());
		letra=btn.getText();
		//JOptionPane.showMessageDialog(this, btn.getText());
		//DESACTIVAR ESA TECLA
		btn.setEnabled(false);
		//BUSCAR LA LETRA PULSADA EN LA PALABRA
		//Y SUSTITUIR SUS APARICIONES EN EL LABEL
		while((pos=palabra.indexOf(letra, pos+1))>-1){
			esta=true;
			//sustituir el guión.....
			lblBuffer=new StringBuffer(lblPalabra.getText());
			lblBuffer.setCharAt(pos*2, letra.charAt(0));
			lblPalabra.setText(lblBuffer.toString());
		}
		//PONERLO ROJO O VERDE
		if(esta){
			btn.setBackground(Color.green);
		}
		else{
			numFallos++;
			//PASAR NUMERO DE FALLOS AL CANVAS
			areaDibujo.setNumFallos(numFallos);
			areaDibujo.repaint();
			btn.setBackground(Color.red);
		}
		//CONTROLAR LA PALABRA: ACERTADA O FALLADA
		if(!lblPalabra.getText().contains("-")){
			numFallos=-1;
			areaDibujo.setNumFallos(-1);
			areaDibujo.repaint();
			estadoBotones(false);
			//imagen en el canvas
		}
		if(numFallos==6){
			estadoBotones(false);
			//imagen en el canvas
		}
	}

	public String elegirPalabra(){
		//SELECCIONA UNA PALABRA AL AZAR DEL ARRAY
		//DE PALABRAS, Y PONE TANTOS GUIONES EN EL
		//LABEL COMO LETRAS TENGA LA PALABRA
		//Y BORRARLA DEL ARRAYLIST
		Random r=new Random();
		int pos;
		String palAux;
		pos=r.nextInt(arrayPalabras.size());
		palAux=arrayPalabras.get(pos);
		arrayPalabras.remove(pos);
		
		//PONER GUIONES
		lblPalabra.setText("");
		for(int i=0;i<palAux.length();i++){
			lblPalabra.setText(lblPalabra.getText()+
					"- ");
		}
		return palAux;
	}
	
	public void estadoBotones(boolean estado){
		//HABILITAR O DESHABILITAR SEGUN EL ESTADO
		for(int i=0;i<arrayTeclado.length;i++){
			arrayTeclado[i].setEnabled(estado);
			//SI LOS ESTAMOS HABILITANDO, RESTAURAR EL COLOR
			if(estado==true){
				arrayTeclado[i].setBackground(null);
			}
		}
		btnNueva.setEnabled(!estado);
	}
}








