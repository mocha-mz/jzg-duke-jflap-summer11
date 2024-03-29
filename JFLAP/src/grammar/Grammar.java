/*
 *  JFLAP - Formal Languages and Automata Package
 * 
 * 
 *  Susan H. Rodger
 *  Computer Science Department
 *  Duke University
 *  August 27, 2009

 *  Copyright (c) 2002-2009
 *  All rights reserved.

 *  JFLAP is open source software. Please see the LICENSE for terms.
 *
 */





package grammar;

import gui.environment.EnvironmentFrame;
import gui.errors.BooleanWrapper;

import java.io.Serializable;
import java.util.*;

import JFLAPnew.formaldef.FormalDefinition;
import JFLAPnew.formaldef.FormallyDefinedObject;
import JFLAPnew.formaldef.IFormallyDefined;
import JFLAPnew.formaldef.alphabets.AbstractAlphabet;
import JFLAPnew.formaldef.alphabets.specific.TerminalAlphabet;
import JFLAPnew.formaldef.alphabets.specific.VariableAlphabet;
import JFLAPnew.formaldef.symbols.Symbol;
import JFLAPnew.formaldef.symbols.SymbolString;
import JFLAPnew.formaldef.symbols.terminal.Terminal;
import JFLAPnew.formaldef.symbols.variable.Variable;

/**
 * The grammar object is the root class for the representation of all forms of
 * grammars, including regular and context-free grammars. This object simply
 * maintains a structure that holds and maintains the data pertinent to the
 * definition of a grammar.
 * 
 * @author Ryan Cavalcante
 */

public abstract class Grammar extends FormalDefinition implements Serializable, Cloneable{
	/**
	 * Creates an instance of <CODE>Grammar</CODE>. The created instance has
	 * no productions, no terminals, no variables, and specifically no start
	 * variable.
	 */
	public Grammar() {
		 myProductions = new ArrayList<Production>();
	}
	

	public abstract boolean isConverted();
	
	/**
	 * Returns a copy of the Grammar object.
	 * 
	 * @return a copy of the Grammar object.
	 */
	public Grammar clone() {
		Grammar g = (Grammar) super.clone();


		Production[] productions = getProductions();
		for (Production p: this.getProductions()) {
			g.addProduction(p.clone());
		}

		return g;
	}

	/**
	 * Changes the start variable to <CODE>variable</CODE>.
	 * 
	 * @param variable
	 *            the new start variable.
	 */
	public BooleanWrapper setStartVariable(Variable variable) {
		return this.getVariableAlphabet().setStartVariable(variable);
	}

	/**
	 * Returns the start variable.
	 * 
	 * @return the start variable.
	 */
	public Variable getStartVariable() {
		return this.getVariableAlphabet().getStartVariable();
	}

	/**
	 * Returns true if <CODE>production</CODE> is a valid production for the
	 * grammar. This method by default calls <CODE>checkProduction</CODE> and
	 * returns true if and only if the method did not throw an exception.
	 * 
	 * @param production
	 *            the production.
	 * @return <CODE>true</CODE> if the production is fine, <CODE>false</CODE>
	 *         if it is not
	 */
	public boolean isValidProduction(Production production) {
		try {
			checkProduction(production);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * If a production is invalid for the grammar, this method should throw
	 * exceptions indicating why the production is invalid. Otherwise it should
	 * do nothing. This method will be called when a production is added, and
	 * may be called by outsiders wishing to check a production without adding
	 * it to a grammar.
	 * 
	 * @param production
	 *            the production
	 * @return 
	 * @throws IllegalArgumentException
	 *             if the production is in some way faulty
	 */
	public BooleanWrapper checkProduction(Production production){
		return new BooleanWrapper(!this.containsProduction(production), 
									"The production " + production + " is already in the Grammar");
	}

	/**
	 * Adds <CODE>production</CODE> to the set of productions in the grammar.
	 * 
	 * @param production
	 *            the production to be added.
	 * @return 
	 * @throws IllegalArgumentException
	 *             if the production is unsuitable somehow
	 */
	public BooleanWrapper addProduction(Production production) {
		return this.addProduction(myProductions.size(), production);

	}

	/**
	 * Checks to see if this grammar contains the production
	 * @param production
	 * @return
	 */
	public boolean containsProduction(Production production) {
		return myProductions.contains(production);
	}


	/**
	 * Adds <CODE>productions</CODE> to grammar by calling addProduction for
	 * each production in array.
	 * 
	 * @param productions
	 *            the set of productions to add to grammar
	 */
	public void addProductions(Production[] productions) {
		for (int k = 0; k < productions.length; k++) {
			addProduction(productions[k]);
		}
	}

	/**
	 * Removes <CODE>production</CODE> from the set of productions in the
	 * grammar.
	 * 
	 * @param production
	 *            the production to remove.
	 */
	public boolean removeProduction(Production production) {
		return myProductions.remove(production);
	}

	/**
	 * Returns all productions in the grammar.
	 * 
	 * @return all productions in the grammar.
	 */
	public Production[] getProductions() {
		return (Production[]) myProductions.toArray(new Production[0]);
	}

	/**
	 * Returns all terminals in the grammar.
	 * 
	 * @return all terminals in the grammar.
	 */
	public TerminalAlphabet getTerminalAlphabet() {
		return this.getAlphabetByClass(TerminalAlphabet.class);
	}

	

	@Override
	public void purgeSymbol(Symbol s) {
		for (Production p: myProductions){
			p.removeSymbol(s);
		}
	}


	/**
	 * Returns all variables in the grammar.
	 * 
	 * @return all variables in the grammar.
	 */
	public VariableAlphabet getVariableAlphabet() {
		return this.getAlphabetByClass(VariableAlphabet.class);
	}

	/**
	 * Returns true if <CODE>production</CODE> is in the set of productions of
	 * the grammar.
	 * 
	 * @param production
	 *            the production.
	 * @return true if <CODE>production</CODE> is in the set of productions of
	 *         the grammar.
	 */
	public boolean isProduction(Production production) {
		return myProductions.contains(production);
	}

	/**
	 * Returns a string representation of the grammar object, listing the four
	 * parts of the definition of a grammar: the set of variables, the set of
	 * terminals, the start variable, and the set of production rules.
	 * 
	 * @return a string representation of the grammar object.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(super.toString());
		/** print start variable. */
		buffer.append("S: ");
		buffer.append(getStartVariable());
		buffer.append('\n');

		/** print production rules. */
		buffer.append("P: ");
		buffer.append('\n');
		Production[] productions = getProductions();
		for (int p = 0; p < productions.length; p++) {
			buffer.append(productions[p].toString());
			buffer.append('\n');
		}

		return buffer.toString();
	}
	
//	/**
//	 * Changes the environment frame this automaton is in.
//	 * @param frame the environment frame
//	 */
//	public void setEnvironmentFrame(EnvironmentFrame frame) {
//		myEnvFrame = frame;
//	}
//	/**
//	 * Gets the Environment Frame the automaton is in.
//	 * @return the environment frame.
//	 */
//	public EnvironmentFrame getEnvironmentFrame() {
//		return myEnvFrame;
//	}
    
    public void setFilePath(String name){
        fileName = name;
    }
    
    public String getFileName(){
        int last = fileName.lastIndexOf("\\");
        if(last == -1) last = fileName.lastIndexOf("/");
        
        return fileName.substring(last+1);
    }
    
    public String getFilePath(){
        int last = fileName.lastIndexOf("\\");
        if(last == -1) last = fileName.lastIndexOf("/");
        
        return fileName.substring(0, last+1);
    }

    /**
	 * Returns all productions in <CODE>grammar</CODE> that have <CODE>variable</CODE>
	 * in them, either on the rhs or lhs.
	 * 
	 * @param symbol
	 *            the symbol to seek
	 * @return all productions in <CODE>grammar</CODE> that have <CODE>variable</CODE>
	 *         in them, either on the rhs or lhs.
	 */
    public List<Production> getProductionsUsingSymbol(Symbol symbol) {
    	List<Production> productions = new ArrayList<Production>();
    	for (Production p: myProductions){
    		if (p.containsSymbol(symbol))
    			productions.add(p);
		}
    			
		return productions;
	}
    
    /**
     * Removes all of these productions from the grammar
     * @param productions
     */
    public void removeProductions(List<Production> productions) {
		myProductions.removeAll(productions);
	}
    
    
    /**
     * Retrieves the list of LHS from every component in this grammar
     * @return
     */
	public List<SymbolString> getLHSes() {
		List<SymbolString> LHSes = new ArrayList<SymbolString>();
		for (Production p: myProductions)
			LHSes.add(p.getLHS());
		return LHSes;
	}
	
	  /**
     * Retrieves the list of RHS from every component in this grammar
     * @return
     */
	public List<SymbolString> getRHSes() {
		List<SymbolString> RHSes = new ArrayList<SymbolString>();
		for (Production p: myProductions)
			RHSes.add(p.getRHS());
		return RHSes;
	}
	
	  /**
     * Iterates through all productions searching for one with an LHS
     * corresponding to the input argument
     * @param lhs - the desired LHS <CODE>SymbolString</CODE>
     * @return the first production with this <CODE>SymbolString</CODE> as an LHS
     *                 <CODE>null</CODE> if no exists
     */
	public Production getProductionWithLHS(SymbolString lhs) {
		for (Production p: myProductions)
			if (p.getLHS().equals(lhs))
				return p;
		return null;
	}

	
	
	@Override
	public Set<Symbol> getSymbolsUsed() {
		Set<Symbol> used = new HashSet<Symbol>();
		
		for (Production p: myProductions){
			used.addAll(p.getLHS());
			used.addAll(p.getRHS());
		}
		
		return used;
	}


	public Production removeProductionAtIndex(int i) {
		return myProductions.remove(i);
	}
	
	
	public BooleanWrapper addProduction(int index, Production prod) {
		BooleanWrapper isValid = checkProduction(prod);
		if (isValid.isFalse())
			return isValid;
		
		myProductions.add(index, prod);
		return isValid;
	}
	
	public int getNumProductions() {
		return myProductions.size();
	}

	public void clearProductions() {
		myProductions.clear();
	}
	
	public void sortProductions() {
		Collections.sort(myProductions, new Comparator<Production>() {

			@Override
			public int compare(Production o1, Production o2) {
				Variable start = Grammar.this.getStartVariable();
				if (o1.isStartProduction(start) && !o2.isStartProduction(start))
					return -1;
				if (!o1.isStartProduction(start) && o2.isStartProduction(start))
					return 1;
				return o1.compareTo(o2);
			}

		
		});
	}
	
//	private EnvironmentFrame myEnvFrame = null;
	private String fileName ="";

	/** Set of Production rules. */
	protected List<Production> myProductions;


}
