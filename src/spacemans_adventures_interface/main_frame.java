package spacemans_adventures_interface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class main_frame extends JFrame implements ActionListener, MouseListener { // Extendemos nuestra clase ventana (herencia) a la clase JFrame
																				  // para usar sus métodos y atributos. Clase para realizar nuestra ventana.	
//-----> DECLARACIÓN DE LOS ATRIBUTOS DE LA CLASE.
	
	private JPanel mainpanel;
	private button_panel buttonpanel; // Creamos un atributo que sea un objeto con nuestra clase de panel de botones.
	private background_start_panel backgroundstartpanel; // Creamos un atributo que sea un objeto con nuestra clase de panel inicial.
	private matrix_panel matrixpanel; // Creamos un atributo que sea un objeto con nuestra clase de panel matriz.
	private algorithmAstar minimum_path; // Creamos un atributo que sea un objeto con nuestra clase de algorithmAstar.
	
	private int button_selection; // Creamos un atributo para usarlo en el ActionListener.
	private boolean button_spaceman_selected; // Creamos un atributo para usarlo en el ActionListener.
	private boolean button_objetive_selected; // Creamos un atributo para usarlo en el ActionListener.
	private boolean checkbox_path_checked; // Creamos un atributo para usarlo en el ActionListener. 
	private int attack_selected;
	
//-----> DECLARACIÓN DE LOS MÉTODOS DE LA CLASE.
	
	public main_frame() { // Constructor.
		
		// Inicialización de los atributos.
		mainpanel = new JPanel(); // Será como una capa superior a nuestra ventana, en la que añadiremos los elementos, ya que no se recomienda añadir estos directamente sobre nuestra ventana (frame);
		buttonpanel = new button_panel(); // Inicializamos el constructor de nuestro panel de botones.
		backgroundstartpanel = new background_start_panel(); // Inicializamos el constructor de nuestro panel inicial.	
		button_selection = 0; // Inicializamos el button_selection.
		button_spaceman_selected = false; // Inicializamos en false.
		button_objetive_selected = false; // Inicializamos en false.
		checkbox_path_checked = false; // Inicializamos en false.
		attack_selected = 0;
		
		this.add_characteristics(); // Llamada al método add_characteristics().
		this.add_components(); // Llamada al método add_components().
		this.addActionListener(); // LLamada al métdo addActionListener().
		addMouseListener(this); // Indicamos que los eventos del ratón serán sobre nuestra ventana.
		this.setVisible(true); // Por último, hacemos visible nuestra ventana.
	}

	public void add_characteristics() { // Método para definir las características de nuestra ventana.
		
		this.setLayout(null); // Desactivamos el Layout para gestionarlo manualmente.
		this.setTitle("Spaceman's Adventures"); // Establecemos un título para nuestra ventana.
		this.setSize(new Dimension(700,820)); // Llamada al método setSize(width,height) para dar una dimensión a nuestra ventana. (Añadimos 20 al alto para que el area de panel sea de 736x736)
		this.setLocationRelativeTo(null); // Llamada al método setLocationRelativeTo() para que nuestra ventana se inicie en el centro de la pantalla.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Con este método indicamos que al cerrar la ventana se de por finalizada la aplicación.

		//-----> Características del mainpanel.
		mainpanel.setSize(new Dimension(this.getWidth(),(this.getHeight()-buttonpanel.getHeight()-20))); // Establecemos el mainpanel que será panel de trabajo principal (736x684). Le restamos la altura del buttonpanel.
		mainpanel.setLocation(0,0); // Damos una localización a nuestro mainpanel.
		mainpanel.setVisible(false); // El mainpanel no será visible, simplemente está definido como zona de juego.
		JScrollPane scroll = new JScrollPane(); // Creamos un objeto de tipo JScrollPane (javax.swing.JScrollPane) para mostrar barras de desplazamiento.
		scroll.setViewportView(mainpanel); // Añadimos dichas barras de desplazamiento a nuestro panel. Es incorrecto el uso de add().
	}
	
	public void add_components() { // Método en el que añadimos todos los elementos a nuestra ventana.
		
		this.add(mainpanel); // A nuestro objeto ventana (llamada) (lo indicamos con this) le añadimos el panel.
		this.add(backgroundstartpanel); // Añadimos el panel de inicio (incluye el startpanel).
		this.add(buttonpanel); // Añadimos a nuestra ventana el buttonpanel.
	}
	
	private void initialize_matrix_panel() { // Método de inicialización del panel de la matrix e incluirlo en nuestra ventana.
		
		int scale = (int) (((backgroundstartpanel.getStartpanel().getSize_matrix_box().getSelectedIndex()) + 1) * 10); // Variable para guardar un valor de escala de nuestro mapa.
		int map_selected = (backgroundstartpanel.getStartpanel().getMap_box().getSelectedIndex()); // Variable para almacenar el mapa selecionado por el usuario.

		matrixpanel = new matrix_panel(mainpanel.getWidth(), scale); // Pasamos el tamaño del mainpanel (al ser cuadrado solo es necesario el ancho), la escala por parámetros y el tipo de mapa.
		matrixpanel.setMap_selected(map_selected); // Pasamos dicho valor al matrix_panel.
		this.add(matrixpanel); // Añadimos nuestro panel de matriz indicando su posición.
	}
	
	//-----> IMPLEMENTS ACTIONSLISTENER (startpanel y button_panel)
	
	public void addActionListener() { // Método para añadir los Action Listener a los componentes relacionados con nuestra ventana.
		
		buttonpanel.getButton_spaceman().addActionListener(this); // Añadimos un ActionListener a nuestro botón SPACEMAN.
		buttonpanel.getButton_objetive().addActionListener(this); // Añadimos un ActionListener a nuestro botón OBJETIVE.
		buttonpanel.getButton_body_to_body().addActionListener(this); // Añadimos un ActionListener a nuestro botón BODY TO BODY.
		buttonpanel.getButton_distance().addActionListener(this); // Añadimos un ActionListener a nuestro botón DISTANCE.
		buttonpanel.getButton_reset_characters().addActionListener(this); // Añadimos un ActionListener a nuestro botón RESET CHARACTERS.
		buttonpanel.getButton_reset_path().addActionListener(this); // Añadimos un ActionListener a nuestro botón RESET PATH.
		buttonpanel.getCheckbox_path().addActionListener(this); // Añadimos un ActionListener a nuestro checkbox PATH.
		buttonpanel.getButton_back_menu().addActionListener(this); // Añadimos un ActionListener a nuestro botón BACK MENÚ.
		buttonpanel.getButton_exit().addActionListener(this); // Añadimos un ActionListener a nuestro botón EXIT.
		backgroundstartpanel.getStartpanel().getButton_start().addActionListener(this); // Añadimos un Action_Listener a nuestro botón START.
		backgroundstartpanel.getStartpanel().getButton_exit().addActionListener(this); // Añadimos un Action_Listener a nuestro botón EXIT.
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { // Método para resolver los eventos del ActionListener.
		if(e.getSource() == buttonpanel.getButton_spaceman()) { // CONDICIÓN: Si el evento del botón pulsado se corresponde el con el button_spaceman.
			this.button_selection = 1; // Establecemos un valor al button_selection indicando el botón que se ha pulsado. (1)
		}
		if(e.getSource() == buttonpanel.getButton_objetive()) { // CONDICIÓN: Si el evento del botón pulsado se corresponde el con el button_objetive.
			this.button_selection = 2; // Establecemos un valor al button_selection indicando el botón que se ha pulsado. (2)
		}
		if(e.getSource() == buttonpanel.getButton_body_to_body()) { // CONDICIÓN: Si el evento del botón pulsado se corresponde el con el button_start_moving.
      		attack_selected = 1; // Ponemos un 1 en attack_selected para determinar la animación de body_to_body.
			minimum_path = new algorithmAstar(matrixpanel.getGame_board()); // Variable de tipo algorithmAstar para hacer uso de dicha clase.
      		this.begin(); // Llamada al método begin().
		}
		if(e.getSource() == buttonpanel.getButton_distance()) { // CONDICIÓN: Si el evento del botón pulsado se corresponde el con el button_start_moving.
      		attack_selected = 2; // Ponemos un 2 en attack_selected para determinar la animación de distancia.
			minimum_path = new algorithmAstar(matrixpanel.getGame_board()); // Variable de tipo algorithmAstar para hacer uso de dicha clase.
      		this.begin(); // Llamada al método begin().
		}
		if(e.getSource() == buttonpanel.getButton_reset_characters()) { // CONDICIÓN: Si el evento del botón pulsado se corresponde el con el button_reset_characters.
			this.button_selection = 0; // Reiniciamos el valor del button_selection.
			this.button_spaceman_selected = false; // Reiniciamos el valor de button_spaceman_selected.
			this.button_objetive_selected = false; // Reiniciamos el valor de button_objetive_selected.
			matrixpanel.getGame_board().reset_characters(); // Actualizamos los valores de la matriz.
			matrixpanel.repaint(); // Nueva llamada al método paint.
		}
		if(e.getSource() == buttonpanel.getButton_reset_path()) { // CONDICIÓN: Si el evento del botón pulsado se corresponde el con el button_reset_path.
			checkbox_path_checked = false; // Reiniciamos el valor del checkbox_path_checked.
			attack_selected = 0; // Reiniciamos el valor de attack_selected.
			matrixpanel.getGame_board().reset_path(); // Actualizamos los valores de la matriz.
			matrixpanel.getGame_board().set_value(minimum_path.getOrigin_x(),minimum_path.getOrigin_y(),80);
			matrixpanel.getGame_board().set_value(minimum_path.getDestiny_x(),minimum_path.getDestiny_y(),100);
			matrixpanel.repaint();
		}
		if(e.getSource() == buttonpanel.getCheckbox_path()) { // CONDICIÓN: Si el evento de pulsación se corresponde con la marca del checkbox_path.
			checkbox_path_checked = true; 
		}
		if(e.getSource() == buttonpanel.getButton_back_menu()) { // CONDICIÓN: Si el evento del botón pulsado se corresponde el con el button_back_menu.
			new main_frame(); // Creamos una nueva ventana de juego.
		}
		if(e.getSource() == buttonpanel.getButton_exit()) { // CONDICIÓN: Si el evento se registra en el botón exit del buttonpanel.
			System.exit(0); // Hacemos una salida del programa
		}
		if(e.getSource() == backgroundstartpanel.getStartpanel().getButton_start()) {
			backgroundstartpanel.setVisible(false); // Dejamos de mostrar nuestro backgroundstartpanel (en el que está incluido también el panel inicial.
			buttonpanel.setVisible(true); // Al pulsar start mostramos por pantalla nuestro buttonpanel.
			this.initialize_matrix_panel(); // Llamada al método initialize_matrix().
			matrixpanel.getGame_board().insert_obstacles(matrixpanel.select_difficulty(backgroundstartpanel.getStartpanel().getDifficulty_box().getSelectedIndex()));
			matrixpanel.repaint(); // Hacemos una nueva llamada al método paint().
		}
		if(e.getSource() == backgroundstartpanel.getStartpanel().getButton_exit()) { // CONDICIÓN: Si el evento se registra en el botón EXIT.
			System.exit(0); // Hacemos una salida del programa
		}	
	}
	
	//-----> IMPLEMENTS MOUSELISTENER.
	// Al implementar la clase MouseListener, nos vemos obligados a implementar los siguientes métodos de dicha clase.

	@Override
	public void mouseClicked(MouseEvent e) { // Método que maneja el evento que se genera cuando el usuario hace click con el mouse sobre algún componente. 
		
		int scale = matrixpanel.getScale_game(); // Establecemos una variable del tamaño del pixel.
		int coordenada_x = e.getX(); // Obtenemos la coordenada X del ratón y la almacenamos en una variable.
		int coordenada_y = e.getY(); // Obtenemos la coordenada X del ratón y la almacenamos en una variable.
		int position_x = (int)(coordenada_x/scale);
		int position_y = (int)(coordenada_y/scale);
		
		if((this.getButton_selection() == 1) && 
		   (this.button_spaceman_selected == false) &&
		   (matrixpanel.getGame_board().get_value(position_y, position_x) > 3) &&
		   (matrixpanel.getGame_board().get_value(position_y, position_x) < 80)) { // CONDICIÓN: Si el botón seleccionado es el button_spaceman y no sobreescribe otro elemento.
			matrixpanel.getGame_board().set_value(position_y,position_x,80); // Incluimos un 80 en nuestra matriz (valor reservado para el spaceman).
			this.button_spaceman_selected = true; // Ponemos true la variable para indicar que el boton se ha pulsado.
		}
		if((this.getButton_selection() == 2) && 
		   (this.button_objetive_selected == false) &&
		   (matrixpanel.getGame_board().get_value(position_y, position_x) > 3) &&
		   (matrixpanel.getGame_board().get_value(position_y, position_x) < 80)) { // CONDICIÓN: Si el botón seleccionado es el button_objetive y no sobreescribe otro elemento.
			matrixpanel.getGame_board().set_value(position_y,position_x,100); // Incluimos un 100 en nuestra matriz (valor reservado para el objetivo).
			this.button_objetive_selected = true; // Ponemos true la variable para indicar que el boton se ha pulsado.
		}
		matrixpanel.repaint(); // Nueva llamada al método paint.

	} 
	@Override
	public void mousePressed(MouseEvent e) {} // Es usado para manejar el evento que se genera al presionar un botón del mouse sobre algún componente.
	@Override
	public void mouseReleased(MouseEvent e) {} // Es usado para manejar el evento que se genera al soltar un botón del mouse sobre algún componente.
	@Override
	public void mouseEntered(MouseEvent e) {} // Es usado para manejar el evento que se genera cuando el mouse entra en algún componente.
	@Override
	public void mouseExited(MouseEvent e) {} // Es usado para manejar el evento que se genera cuando el mouse sale de algún componente.

	//-----> MÉTODOS SETTERS AND GETTERS.
	
	public matrix_panel getMatrixpanel() {
		return matrixpanel;
	}

	public void setMatrixpanel(matrix_panel matrixpanel) {
		this.matrixpanel = matrixpanel;
	}

	public int getButton_selection() {
		return button_selection;
	}

	public void setButton_selection(int button_selection) {
		this.button_selection = button_selection;
	}
	
	//-----> MÉTODOS HERENCIA THREAD (MULTIHILOS). ALGORITMO A*.
	
	public void begin() { // Método para la implementación del hilo del subproceso y su inicialización. Con la creación de este hilo se busca
						  // una mayor rapidez en el programa, ejecutando el algoritmo del movimiento en un hilo separado.

		Thread beginning = new Thread (new start_class()); // Creamos un hilo en el que se ejecutarán los métodos del algoritmo.
		beginning.start(); // Inicialización del hilo del subproceso.
	}

	public class start_class implements Runnable { // Herencia de Runnable para el uso de los métodos de la clase Thread.

		public void run() { // Método run (propio de la clase Thread) para iniciar el algoritmo A*.

			list list_solution = null; // Variable de tipo list para mostrar los nodos de la solución. Inicializada en null.
      		list_solution = minimum_path.beggining(); // Llamada al método beggining() que nos devuelve la lista con los nodos del camino mínimo.
      		int value_path = 0; // Variable para almacenar el elemento que se mostrará en el camino. Inicializada a 0.
      		
			if(checkbox_path_checked == true) { // CONDICIÓN: Si se marca el checkbox_path.
				value_path = 102; // Ponemos un 102 (valor de camino.png).
			}
			else {
				value_path = -1; // Ponemos un -1 (valor de espacio.png).
			}
			
      		if(list_solution == null) { // CONDICIÓN: Si list_solution devuelve null (no hay solución).
      			JOptionPane.showMessageDialog(null, "NO EXISTE SOLUCIÓN"); // Mostramos una alerta por pantalla indicándolo.
      		}
      		else { // CONDICIÓN: Si list_solution nos devuelve un camino mínimo.
      			
      			if(attack_selected == 1) { // CONDICIÓN: Si attack_selected es igual a 1 (ANIMACIÓN CUERPO A CUERPO).
      				for(int i = 0; i < matrixpanel.getGame_board().get_rows(); i++) {
      					for(int j = 0; j < matrixpanel.getGame_board().get_columns(); j++) {
      						if(matrixpanel.getGame_board().get_value(i,j) == 80) { // CONDICIÓN: Si el valor se corresponde con un spaceman (png).
      							matrixpanel.getGame_board().set_value(i,j,89); // Cambiamos por el spaceman.gif con la animación.
      						}
      					}
      				}
      				matrixpanel.repaint(); // Llamada al método paint() de matrixpanel.
      			
      				try { // Método try (captura) para realizar una pausa.
      					Thread.sleep(3900); // Realización de una pausa (sleep) de 3,900 segundos. Duración de la animación.
      				} catch(InterruptedException ex) {} // Método catch, para la realización de un nuevo subproceso en la detención.
      			
      				while(list_solution.empty() == false) { // Mientras la list_solution no esté vacía.
      					node auxiliary_node = list_solution.extract(); // Llamada al método extract() para extraer el primer nodo de la lista.
      					int x = auxiliary_node.get_id_x(); // Llamada al método get_id_x() para almacenar en una variable local la x del nodo.
      					int y = auxiliary_node.get_id_y(); // Llamada al método get_id_y() para almacenar en una variable local la y del nodo.
	      			
					
		      			for(int i = 0; i < matrixpanel.getGame_board().get_rows(); i++) {
		      				for(int j = 0; j < matrixpanel.getGame_board().get_columns(); j++) {
		      					if((matrixpanel.getGame_board().get_value(i,j) >= 80) && (matrixpanel.getGame_board().get_value(i,j) < 90)) { // CONDICIÓN: Para cada posición de la matriz que sea igual o mayor a 80 (spaceman).
		      						if((x < i) && (y == j)) { // CONDICIÓN: Si se produce un movimiento vertical ascendente.
			      						matrixpanel.getGame_board().set_value(i,j,value_path); // Situamos en su lugar un elemento espacio.
		      							matrixpanel.getGame_board().set_value(x,y,81); // Establecemos en la posición (x,y) dada por la lista solución un 81 (spaceman).
		      						}
		      						if((x > i) && (y == j)) { // CONDICIÓN: Si se produce un movimiento vertical descendente.
			      						matrixpanel.getGame_board().set_value(i,j,value_path); // Situamos en su lugar un elemento espacio.
		      							matrixpanel.getGame_board().set_value(x,y,82); // Establecemos en la posición (x,y) dada por la lista solución un 82 (spaceman).
		      						}
		      						if((x == i) && (y > j)) { // CONDICIÓN: Si se produce un movimiento horizontal ascendente.
			      						matrixpanel.getGame_board().set_value(i,j,value_path); // Situamos en su lugar un elemento espacio.
		      							matrixpanel.getGame_board().set_value(x,y,83); // Establecemos en la posición (x,y) dada por la lista solución un 83 (spaceman).
		      						}
		      						if((x == i) && (y < j)) { // CONDICIÓN: Si se produce un movimiento horizontal descendente.
			      						matrixpanel.getGame_board().set_value(i,j,value_path); // Situamos en su lugar un elemento espacio.
		      							matrixpanel.getGame_board().set_value(x,y,84); // Establecemos en la posición (x,y) dada por la lista solución un 84 (spaceman).
		      						}
		      						if((x < i) && (y > j)) { // CONDICIÓN: Si se produce un movimiento diagonal derecho ascendente.
			      						matrixpanel.getGame_board().set_value(i,j,value_path); // Situamos en su lugar un elemento espacio.
		      							matrixpanel.getGame_board().set_value(x,y,85); // Establecemos en la posición (x,y) dada por la lista solución un 85 (spaceman).
		      						}
	      							if((x > i) && (y > j)) { // CONDICIÓN: Si se produce un movimiento diagonal derecho descendente.
	    	      						matrixpanel.getGame_board().set_value(i,j,value_path); // Situamos en su lugar un elemento espacio.
	      								matrixpanel.getGame_board().set_value(x,y,86); // Establecemos en la posición (x,y) dada por la lista solución un 87 (spaceman).
	      							}
	      							if((x < i) && (y < j)) {// CONDICIÓN: Si se produce un movimiento diagonal izquierdo ascendente.
	    	      						matrixpanel.getGame_board().set_value(i,j,value_path); // Situamos en su lugar un elemento espacio.
	      								matrixpanel.getGame_board().set_value(x,y,87); // Establecemos en la posición (x,y) dada por la lista solución un 86 (spaceman).
	      							}
	  								if((x > i) && (y < j)) {// CONDICIÓN: Si se produce un movimiento diagonal izquierdo descendente.
	  		      						matrixpanel.getGame_board().set_value(i,j,value_path); // Situamos en su lugar un elemento espacio.
	  									matrixpanel.getGame_board().set_value(x,y,88); // Establecemos en la posición (x,y) dada por la lista solución un 88 (spaceman).
	  								}	
		      					}
		      				}
		      			}
		      			matrixpanel.repaint(); // Llamada al método repaint() para hacer un nuevo paint de nuestro matrixpanel.
	
		      			try { // Método try (captura) para realizar una pausa.
		      				Thread.sleep(125); // Realización de una pausa (sleep) de 0,125 segundos.
						} catch(InterruptedException ex) {} // Método catch, para la realización de un nuevo subproceso en la detención.  			
		      		}
	      			for(int i = 0; i < matrixpanel.getGame_board().get_rows(); i++) {
	      				for(int j = 0; j < matrixpanel.getGame_board().get_columns(); j++) {
	      					if((matrixpanel.getGame_board().get_value(i,j) >= 80) && (matrixpanel.getGame_board().get_value(i,j) < 90)) { // CONDICIÓn: Si el valor se corresponde con un spaceman (png).
								if(checkbox_path_checked == true) { // CONDICIÓN: Si se marca el checkbox_path.
									matrixpanel.getGame_board().set_value(i,j,102); // Situamos en su lugar un elemento espacio.
									matrixpanel.repaint(); // Llamada al método paint() de matrixpanel.
								}
								else {
									matrixpanel.getGame_board().set_value(i,j,-1); // Situamos en su lugar un elemento espacio.
									matrixpanel.repaint(); // Llamada al método paint() de matrixpanel.
								}
	      					}
	      					if(matrixpanel.getGame_board().get_value(i,j) == 100) {
	      						matrixpanel.getGame_board().set_value(i,j,101); // Cambiamos por el spaceman.gif con la animación.
	      						matrixpanel.repaint(); // Llamada al método paint() de matrixpanel.
	      					}
	      				}
	      			}
      			}
      			
      			if(attack_selected == 2) { // CONDICIÓN: Si attack_selected es igual a 1 (ANIMACIÓN A DISTANCIA).
          			for(int i = 0; i < matrixpanel.getGame_board().get_rows(); i++) {
          				for(int j = 0; j < matrixpanel.getGame_board().get_columns(); j++) {
          					if(matrixpanel.getGame_board().get_value(i,j) == 80) { // CONDICIÓN: Si el valor se corresponde con un spaceman (png).
          						if(minimum_path.getOrigin_y() < minimum_path.getDestiny_y()) { 
          							matrixpanel.getGame_board().set_value(i,j,90); // Cambiamos por el spaceman.gif con la animación derecha.
          						}
          						if(minimum_path.getOrigin_y() > minimum_path.getDestiny_y()) { 
          							matrixpanel.getGame_board().set_value(i,j,92); // Cambiamos por el spaceman.gif con la animación izquierda.
          						}
          					}
          				}
          			}
          			matrixpanel.repaint(); // Llamada al método paint() de matrixpanel.
          			
          			try { // Método try (captura) para realizar una pausa.
          				Thread.sleep(2700); // Realización de una pausa (sleep) de 1,500 segundos. Duración de la animación.
    				} catch(InterruptedException ex) {} // Método catch, para la realización de un nuevo subproceso en la detención.
          			
          			for(int i = 0; i < matrixpanel.getGame_board().get_rows(); i++) {
          				for(int j = 0; j < matrixpanel.getGame_board().get_columns(); j++) {
          					if(matrixpanel.getGame_board().get_value(i,j) == 90) { // CONDICIÓN: Si el valor se corresponde con un spaceman (png).
          						matrixpanel.getGame_board().set_value(i,j,91); // Cambiamos por el spaceman.gif con la animación.
          					}
          					if(matrixpanel.getGame_board().get_value(i,j) == 92) { // CONDICIÓN: Si el valor se corresponde con un spaceman (png).
          						matrixpanel.getGame_board().set_value(i,j,93); // Cambiamos por el spaceman.gif con la animación.
          					}
          				}
          			}
          			matrixpanel.repaint(); // Llamada al método paint() de matrixpanel.
          			
    	      		while(list_solution.empty() == false) { // Mientras la list_solution no esté vacía.
    	      			node auxiliary_node = list_solution.extract(); // Llamada al método extract() para extraer el primer nodo de la lista.
    	      			int x = auxiliary_node.get_id_x(); // Llamada al método get_id_x() para almacenar en una variable local la x del nodo.
    			        int y = auxiliary_node.get_id_y(); // Llamada al método get_id_y() para almacenar en una variable local la y del nodo.
          			
    					matrixpanel.getGame_board().set_value(x,y,103); // Establecemos en la posición (x,y) dada por la lista solución un 81 (spaceman).
    	      			matrixpanel.repaint(); // Llamada al método repaint() para hacer un nuevo paint de nuestro matrixpanel.

    	      			try { // Método try (captura) para realizar una pausa.
    	      				Thread.sleep(125); // Realización de una pausa (sleep) de 0,125 segundos.
    					} catch(InterruptedException ex) {} // Método catch, para la realización de un nuevo subproceso en la detención.  
    	      		}
          			for(int i = 0; i < matrixpanel.getGame_board().get_rows(); i++) {
          				for(int j = 0; j < matrixpanel.getGame_board().get_columns(); j++) {
          					if(matrixpanel.getGame_board().get_value(i,j) == 100) {
          						matrixpanel.getGame_board().set_value(i,j,101); // Cambiamos por el spaceman.gif con la animación.
          						matrixpanel.repaint(); // Llamada al método paint() de matrixpanel.
          					}
          				}
          			}

      			}
	      		try { // Método try (captura) para realizar una pausa.
		      		Thread.sleep(2600); // Realización de una pausa (sleep) de 2,600 segundos.
				} catch(InterruptedException ex) {} // Método catch, para la realización de un nuevo subproceso en la detención.
      			for(int i = 0; i < matrixpanel.getGame_board().get_rows(); i++) {
      				for(int j = 0; j < matrixpanel.getGame_board().get_columns(); j++) {
      					if(matrixpanel.getGame_board().get_value(i,j) == 101) {
      						matrixpanel.getGame_board().set_value(i,j,-1); // Cambiamos por el spaceman.gif con la animación.
      						matrixpanel.repaint(); // Llamada al método paint() de matrixpanel.
      					}
      				}
      			}
	      	}
		}
	}
}



