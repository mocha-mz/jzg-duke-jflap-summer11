package JFLAPnew.formaldef.symbols;

import gui.environment.Universe;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import automata.Automaton;

import JFLAPnew.formaldef.FormalDefinition;
import JFLAPnew.formaldef.alphabets.IAlphabet;
import JFLAPnew.formaldef.symbols.terminal.Terminal;
import JFLAPnew.formaldef.symbols.variable.Variable;

public class SymbolString extends LinkedList<Symbol> implements Comparable<SymbolString> {

	public SymbolString(String in, FormalDefinition def){
		super();
		this.addAll(SymbolString.createFromString(in, def));
	}

	public SymbolString() {
		super();
	}

	public SymbolString(Symbol ... symbols) {
		super();
		for (Symbol s: symbols)
			this.add(s);
	}

	public SymbolString(SymbolString subList) {
		super(subList);
	}

	public <T extends Symbol> Set<T> getSymbolsOfClass(Class<T> clazz) {
		Set<T> results = new TreeSet<T>();
		for (Symbol s: this){
			if (s.getClass().isAssignableFrom(clazz))
				results.add((T) s);
		}
		
		return results;
	}
	
	public boolean concat(Symbol sym) {
		return this.add(sym);
	}

	public SymbolString reverse() {
		SymbolString reverse = new SymbolString();
		for (Symbol s: this)
			reverse.addFirst(s);
		return reverse;
	}
	
	public int indexOfSubSymbolString(SymbolString o) {
		if (o.size() > this.size())
			return -1;
		for (int i = 0; i< this.size(); i++){
			Boolean check = true;
			for (int j = 0; j + o.size() <= this.size(); j++){
				check = check && this.get(i+j).equals(o.get(j));
			}
			if (check) return i;
		}
		return -1;
	}

	public boolean startsWith(SymbolString label) {
		return this.indexOfSubSymbolString(label) == 0;
	}

	/**
	 * THIS IS NOT THE SAME AS this.toString().length in the case of an empty string
	 * @return
	 */
	public int stringLength() {
		return this.isEmpty() ? 0 : this.toString().length();
	}

	public boolean endsWith(SymbolString ss) {
		return this.indexOfSubSymbolString(ss) + ss.size() == this.size();
	}

	public boolean endsWith(Symbol s) {
		return this.getLast() == s;
	}

	public SymbolString subList(int i) {
		return (SymbolString) super.subList(i, this.size());
	}

	@Override
	public SymbolString subList(int start, int end){
		return new SymbolString(super.subList(start, end).toArray(new Symbol[0]));
	}

	public String toString(){
		String string = "";
		for (Symbol s: this){
			string += s.toString();
		}
		return string.length() == 0 ? Universe.curProfile.getEmptyStringSymbol() : string;
	}

	public boolean equals(Object o){
		if (o instanceof SymbolString)
			return this.compareTo((SymbolString) o) == 0;		
		if (o instanceof Symbol)
			return this.size() == 1 && this.getFirst() == o;
		return false;
	}

	@Override
	public int hashCode() {
		int code = 0;
		for (Symbol s: this)
			code += s.hashCode();
		return code;
	}

	@Override
	public SymbolString clone() {
		SymbolString string = new SymbolString();
		for (Symbol s: this)
			string.add(s.clone());
		return string;
	}

	@Override
	public int compareTo(SymbolString o) {
		Iterator<Symbol> me = this.iterator(),
		 		 other = o.iterator();
		while(me.hasNext() && other.hasNext()){
			Symbol sMe = me.next(),
				   sOther = other.next();
			
			if(sMe.compareTo(sOther) != 0)
					return sMe.compareTo(sOther);
		}
		
		if (!me.hasNext() && other.hasNext())
			return 1;
		if (me.hasNext() && !other.hasNext())
			return -1;
		
		return 0;
	}

	@Override
	public int indexOf(Object other){
		if (other instanceof Symbol)
			return super.indexOf(other);
		return indexOfSubSymbolString((SymbolString) other);
		
	}

	public static SymbolString createFromString(String in,
			FormalDefinition def) {
		
		String temp = "";
		SymbolString symbols = new SymbolString();
		if (in == Universe.curProfile.getEmptyStringSymbol()) return symbols;
		for (int i = 0; i < in.length(); i++){
			temp += in.charAt(i);
			for (IAlphabet alph: def){
				if (alph.containsSymbolWithString(temp)){
					symbols.add(alph.getSymbol(temp));
					temp = "";
					break;
				}
			}
		}
		return symbols;
	}

	public static SymbolString concat(SymbolString ... strings) {
		SymbolString concat = new SymbolString();
		for (SymbolString ss: strings)
			concat.concat(ss);
		return concat;
	}

	public static boolean canBeParsed(String input, FormalDefinition def) {
		return SymbolString.isEmpty(input) || createFromString(input, def).stringLength() == input.length();
	}

	public static boolean isEmpty(String input) {
		return input.length() == 0 || input.equals(Universe.curProfile.getEmptyStringSymbol());
	}


}
