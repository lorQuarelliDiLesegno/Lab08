package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {
	
	private List <Parola> parole = new ArrayList<Parola>(); 
	private Graph <Parola, DefaultEdge> grafo; 
	
	public Graph<Parola, DefaultEdge> getGrafo() {
		return grafo;
	}

	public void createGraph(int numeroLettere) {
		
		//leggo la lista degli oggetti
		
		this.parole = this.inizializzaParole(numeroLettere); 
		
		//creo il grafo
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
	
		//aggiungo i vertici
		
		Graphs.addAllVertices(this.grafo, this.parole); 
		
		//aggiungo gli archi tramite query SQL 
		
		/*
		for (Parola p: this.parole) {
			List <Parola> paroleConnesse = new ArrayList<Parola>(); 
			paroleConnesse = this.aggiungiArchi(p, numeroLettere); 
			for (Parola pa: paroleConnesse) {
				this.grafo.addEdge(p, pa); 
			}
		}
		
		*/
		
		//aggiungo gli archi manualmente
		
		for (Parola p1: this.parole) {
			for (Parola p2 :this.parole) {
				if(!p1.equals(p2)) {
					if(sonoConnessi(p1, p2)) {
						// System.out.println(""+p1+" collegata a "+p2);        // per verificare tramite la consolle che effettivamente 
																				// gli archi sono corretti
						grafo.addEdge(p1, p2); 
					}
				}
			}
		}		
	}

	private boolean sonoConnessi(Parola p1, Parola p2) {
		char[] c1 = p1.getParola().toCharArray();								//NON USARE LO SPLIT: rallenta notevolmente la velcità di esecuzione
		char[] c2 = p2.getParola().toCharArray();								//                    del programma! 
		int cnt = 0; 															
		
		for (int i = 0; i<c1.length; i++) {
			if(c1[i] != c2[i]) {
				cnt++; 
			}
		}
		
		if (cnt>1) {
			return false; 
		}
		return true;
	}

	private List<Parola> aggiungiArchi(Parola p, int numeroLettere) {
		// TODO Auto-generated method stub
		WordDAO dao = new WordDAO(); 
		String[] caratteri = p.getParola().split(""); 
		List <Parola> paroleAssociate = new ArrayList<Parola>(); 
		for (int i=0; i<numeroLettere; i++) {
			String s = null;
			caratteri[i]="_"; 
			for (int j=0; j<caratteri.length; j++) {
				s+=caratteri[j]; 
			}
			paroleAssociate = dao.aggiungiArchiTramiteQuery(p.getParola(), s); 
		}
		return paroleAssociate;
		
	}

	private List<Parola> inizializzaParole(int numeroLettere) {
		WordDAO dao = new WordDAO();
		parole.removeAll(parole);
		for (String s: dao.getAllWordsFixedLength(numeroLettere)) {
			parole.add(new Parola(s)); 
		}
		return parole;
	}

	public Set<DefaultEdge> displayNeighbours(String parolaInserita) {
		Set <DefaultEdge> paroleVicine = new HashSet<DefaultEdge>(); 
		for (Parola p: grafo.vertexSet()) {
			if(p.getParola().compareTo(parolaInserita)==0) {
				paroleVicine = grafo.edgesOf(p); 
			}
		}
		return paroleVicine;
	}

	public int findMaxDegree() {
		int cnt = 0; 
		for (Parola p: grafo.vertexSet()) {
			int grado = grafo.degreeOf(p); 
			if(grado>cnt) {
				cnt=grado; 
			}
		}
		return cnt;
	}
	
	public Parola trovaVerticeMax(int gradoMax) {
		for (Parola p: grafo.vertexSet()) {
			int grado = grafo.degreeOf(p); 
			if (grado==gradoMax) {
				return p; 
			}
		}
		return null;
	}
	

}
