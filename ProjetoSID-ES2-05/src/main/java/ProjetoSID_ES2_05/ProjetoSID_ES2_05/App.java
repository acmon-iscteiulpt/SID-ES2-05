package ProjetoSID_ES2_05.ProjetoSID_ES2_05;

import java.awt.EventQueue;

import InterfaceGrafica.GUI_Sensor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EventQueue.invokeLater(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				try {
					GUI_Sensor window = new GUI_Sensor();
					window.show();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
        	
        });
    }
}
