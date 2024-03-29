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

import grammar.Grammar;
import grammar.Production;

import java.io.Serializable;

import JFLAPnew.formaldef.symbols.SymbolString;

/**
 * Class to determine the whether loaded grammar is Turing Converted Grammar or not
 * @author Kyung Min (Jason) Lee
 *
 *	As of the SymbolPackage addition, this class should no longer be needed as every 
 *	grammar will have a standard format
 */
@Deprecated
public class TuringChecker {

//	private static final SymbolString LHS_DEFAULT={"S", "S", "S"};
//	private static final SymbolString RHS_DEFAULT={"V(==)S", "SV(==)", "T"};
	
	/**
	 * Checks whether loaded grammar is Turing Converted Grammar or not.
	 * @param object
	 * @return boolean whether grammar is a Turing Converted Grammar.
	 */
	public static boolean check(Serializable object) {
		// TODO Auto-generated method stub
		if (object instanceof Grammar)
		{
			Grammar g=(Grammar) object;
		
			Production[] p=g.getProductions();
			//check first 3 productions to make sure this grammar is from conversion
			int count=0;
			if (p.length < 3)
				return false;
			for (int i=0; i<3; i++)
			{
				SymbolString lhs=p[i].getLHS();
				SymbolString rhs=p[i].getRHS();
//				if (lhs.equals(LHS_DEFAULT[i]) && rhs.equals(RHS_DEFAULT[i]))
//				{
//					count++;
//				}
			}
			if (count==3)
				return true;
			else
				return false;
			
		}

		return false;
	}

	
}
