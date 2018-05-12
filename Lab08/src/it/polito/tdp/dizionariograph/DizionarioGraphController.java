/**
 * Sample Skeleton for 'DizionarioGraph.fxml' Controller Class
 */

package it.polito.tdp.dizionariograph;

import java.net.URL;

import java.util.ResourceBundle;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.dizionariograph.model.Model;
import it.polito.tdp.dizionariograph.model.Parola;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {
	
	private Model model; 

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtNumeroLettere"
    private TextField txtNumeroLettere; // Value injected by FXMLLoader

    @FXML // fx:id="txtParola"
    private TextField txtParola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doGeneraGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	txtParola.clear();
    	
    	try{
    		int numeroLettere = Integer.parseInt(txtNumeroLettere.getText()); 
    		model.createGraph(numeroLettere);
    		txtResult.setText(model.getGrafo().toString()+"\n");
    	}
    	catch(NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.setText("Inserire una matricola nel formato corretto\n");
    	}
    	catch(RuntimeException e) {
    		e.printStackTrace();
    		txtResult.setText("Errore di connessione al DB\n");    		
    	}    	
    }

    @FXML
    void doReset(ActionEvent event) {
    	
    	txtResult.clear();
    	txtParola.clear();
    	txtNumeroLettere.clear();

    }

    @FXML
    void doTrovaGradoMax(ActionEvent event) {
    	
    	int gradoMax = model.findMaxDegree(); 
    	Parola parolaMax = model.trovaVerticeMax(gradoMax); 
		Set <DefaultEdge> paroleVicine = model.displayNeighbours(parolaMax.getParola());
		txtResult.appendText("Il vertice "+parolaMax.getParola()+" è il grado massimo\n");
		txtResult.appendText("Grado= "+gradoMax+"\n");
		for (DefaultEdge p: paroleVicine) {
			txtResult.appendText(p+"\n");
		}


    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    	
		int numeroLettere = Integer.parseInt(txtNumeroLettere.getText()); 
		String parola = txtParola.getText(); 
		
		if(parola.length() != numeroLettere) {
			txtResult.setText("Il numero di lettere della parola non corrisponde");
			return; 
		}
		
		model.createGraph(numeroLettere);
		Set <DefaultEdge> paroleVicine = model.displayNeighbours(parola);
		for (DefaultEdge p: paroleVicine) {
			txtResult.appendText(p+"\n");
		}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtNumeroLettere != null : "fx:id=\"txtNumeroLettere\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

    }

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model = model; 
	}
}
