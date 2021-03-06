package tableurTest;

import org.junit.Test;

import tableur.*;

public class TestInterpreter {

	@Test
	public void testEvaluerTexte() {
		Cellule c = new Cellule("A1---test");
		CellContainer cells = new CellContainer();
		cells.add(c);
		Interpreter i =  new Interpreter(cells);
		assert(i.evaluer(c.getContenu())==null);
	}
	
	@Test
	public void testEvaluerDouble() {
		Cellule c = new Cellule("A1---7.5");
		CellContainer cells = new CellContainer();
		cells.add(c);
		Interpreter i =  new Interpreter(cells);
		assert((Double)(i.evaluer(c.getContenu()).getValeur())==7.5);
	}
	
	@Test
	public void testEvaluerEntier() {
		Cellule c = new Cellule("A1---3");
		CellContainer cells = new CellContainer();
		cells.add(c);
		Interpreter i =  new Interpreter(cells);
		assert((Integer)(i.evaluer(c.getContenu()).getValeur())==3);
	}
	
	@Test
	public void testEvaluerAutreCellule() {
		Interpreter i =  new Interpreter();
		Cellule c = new Cellule("A1---A2");
		Cellule c2 = new Cellule("A2---5");
		c2.affecterValeur(i.evaluer(c2.getContenu()));
		CellContainer cells = new CellContainer();
		cells.add(c);
		cells.add(c2);
		i.setCells(cells);
		assert((Integer)(i.evaluer(c.getContenu()).getValeur())==5);
	}
	
	@Test
	public void testEvaluerFonction() {
		Interpreter i =  new Interpreter();
		Cellule c = new Cellule("A1---$max(A2,B1)");
		Cellule c2 = new Cellule("A2---5");
		Cellule c3 = new Cellule("B1---12");
		c2.affecterValeur(i.evaluer(c2.getContenu()));
		c3.affecterValeur(i.evaluer(c3.getContenu()));
		CellContainer cells = new CellContainer();
		cells.add(c);
		cells.add(c2);
		cells.add(c3);
		i.setCells(cells);
		assert((Integer)(i.evaluer(c.getContenu()).getValeur())==12);
	}
	
	@Test
	public void testEvaluerFonctionsEmboitees() {
		Interpreter i =  new Interpreter();
		Cellule c = new Cellule("A1---$max(A2,$max(B1,B2))");
		Cellule c2 = new Cellule("A2---5");
		Cellule c3 = new Cellule("B1---12");
		Cellule c4 = new Cellule("B2---7");
		c2.affecterValeur(i.evaluer(c2.getContenu()));
		c3.affecterValeur(i.evaluer(c3.getContenu()));
		c4.affecterValeur(i.evaluer(c4.getContenu()));
		CellContainer cells = new CellContainer();
		cells.add(c);
		cells.add(c2);
		cells.add(c3);
		cells.add(c4);
		i.setCells(cells);
		assert((Integer)(i.evaluer(c.getContenu()).getValeur())==12);
	}
	
	@Test
	public void testEvaluerErreurContenu() {
		Interpreter i =  new Interpreter();
		Cellule c = new Cellule("A1---$max(3,5,7)");
		assert(((String)(i.evaluer(c.getContenu()).getValeur())).equals("!ERREUR!"));
	}
	
	@Test
	public void testIsDependantePasDependante() {
		Interpreter i =  new Interpreter();
		Cellule c = new Cellule("A1---7.5");
		CellContainer cells = new CellContainer();
		cells.add(c);
		i.setCells(cells);
		assert(!i.isDependante(c.getContenu(),"A2"));
	}
	
	@Test
	public void testIsDependanteDependante() {
		Interpreter i =  new Interpreter();
		Cellule c = new Cellule("A1---A2");
		Cellule c1 = new Cellule("A2---3");
		CellContainer cells = new CellContainer();
		cells.add(c);
		cells.add(c1);
		i.setCells(cells);
		assert(i.isDependante(c.getContenu(),"A2"));
	}
		
	@Test
	public void testIsDependanteDependanteEnCascade() {
		Interpreter i =  new Interpreter();
		Cellule c = new Cellule("A1---A3");
		Cellule c1 = new Cellule("A3---A2");
		Cellule c2 = new Cellule("A2---4");
		CellContainer cells = new CellContainer();
		cells.add(c);
		cells.add(c1);
		cells.add(c2);
		i.setCells(cells);
		assert(i.isDependante(c.getContenu(),"A2"));
	}	
	
	@Test
	public void testTransformer() {
		Interpreter i =  new Interpreter();
		Cellule c = new Cellule("A1---B1");
		assert(i.transformer(c.getContenu(),c.getNom().getFullName(),"A2").equals("B2"));
	}
}
