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





package gui.grammar;

import grammar.Grammar;
import grammar.Production;
import gui.HighlightTable;
import gui.SelectingEditor;
import gui.TableTextSizeSlider;
import gui.environment.Universe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.*;

import debug.EDebug;

/**
 * The <CODE>GrammarTable</CODE> is a simple extension to the <CODE>JTable</CODE>
 * that standardizes how grammar tables look.
 * 
 * @author Thomas Finley
 */

public class GrammarTable extends HighlightTable {

	/**
	 * Instantiates a <CODE>GrammarTable</CODE> with a given table model.
	 * 
	 * @param model
	 *            the table model for the new grammar table
	 */
	public GrammarTable(GrammarTableModel model) {
		super(model);
		initView();
	}

	/**
	 * Handles the highlighting of a particular row.
	 * 
	 * @param row
	 *            the row to highlight
	 */
	public void highlight(int row) {
		highlight(row, 0);
		highlight(row, 2);
	}

	/**
	 * This constructor helper function customizes the view of the table.
	 */
	private void initView() {
		setTableHeader(new JTableHeader(getColumnModel()));
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(true);
		for(int i=0; i<super.getColumnCount(); i++)
			super.getColumnModel().getColumn(i).setCellEditor(new SelectingEditor());
		TableColumn lhs = getColumnModel().getColumn(0);
		TableColumn arrows = getColumnModel().getColumn(1);
		TableColumn rhs = getColumnModel().getColumn(2);
		lhs.setHeaderValue("LHS");
		getTableHeader().resizeAndRepaint();
		rhs.setHeaderValue("RHS");
		getTableHeader().resizeAndRepaint();
		getColumnModel().getColumn(0).setPreferredWidth(70);
		lhs.setMaxWidth(200);
		//lhs.setMinWidth(200);
		arrows.setMaxWidth(30);
		arrows.setMinWidth(30);
		getColumnModel().getColumn(1).setPreferredWidth(30);
		setShowGrid(true);
		setGridColor(Color.lightGray);
		this.rowHeight = 30;
		this.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		
		getColumnModel().getColumn(2).setCellRenderer(RENDERER);
		add(new TableTextSizeSlider(this), BorderLayout.NORTH);
	}

	/**
	 * Returns the model for this grammar table.
	 * 
	 * @return the grammar table model for this table
	 */
	public GrammarTableModel getGrammarModel() {
		return (GrammarTableModel) super.getModel();
	}

	/**
	 * Returns the grammar that has been defined through this <CODE>GrammarTable</CODE>,
	 * where the grammar is an instance of the class passed into this function.
	 * 
	 * @param grammarClass
	 *            the type of grammar that is passed in
	 * @return a grammar of the variant returned by this grammar, or <CODE>null</CODE>
	 *         if some sort of error with a production is encountered
	 * @throws IllegalArgumentException
	 *             if the grammar class passed in could not be instantiated with
	 *             an empty constructor, or is not even a subclass of <CODE>Grammar</CODE>.
	 */
	//TODO: check this out - everything is going to be added dynamically and then updated, so 
	//this method will no longer be needed. Make sure it is all ok.
	public Grammar getGrammar() {
//		Grammar grammar = null;
//		try {
//			grammar = (Grammar) grammarClass.newInstance();
//		} catch (NullPointerException e) {
//            EDebug.print("Throwing a Null Pointer Back at YOU.");
//			throw e;
//		} catch (Throwable e) {
//			throw new IllegalArgumentException("Bad grammar class "
//					+ grammarClass);
//		}
//		GrammarTableModel model = getGrammarModel();
//		// Make sure we're not editing anything anymore.
//		if (getCellEditor() != null)
//			getCellEditor().stopCellEditing();
//		// Add the productions.
//		for (int row = 0; row < model.getRowCount(); row++) {
//			Production p = model.getProduction(row);
//			if (p == null)
//				continue;
//			try {
//				grammar.addProduction(p);
//				if (grammar.getStartVariable() == null)
//					grammar.setStartVariable(p.getLHS());
//			} catch (IllegalArgumentException e) {
//				setRowSelectionInterval(row, row);
//				JOptionPane.showMessageDialog(this, e.getMessage(),
//						"Production Error", JOptionPane.ERROR_MESSAGE);
//				return null;
//			}
//		}
//		return grammar;
		return ((GrammarTableModel) this.getModel()).getAssociatedGrammar();
	}

	/** Modified to use the set renderer highlighter. */
	public void highlight(int row, int column) {
		highlight(row, column, THRG);
	}


	/** The built in highlight renderer generator, modified. */
	private static final gui.HighlightTable.TableHighlighterRendererGenerator THRG = new TableHighlighterRendererGenerator() {
		public TableCellRenderer getRenderer(int row, int column) {
			if (renderer == null) {
				renderer = new DefaultTableCellRenderer();
				renderer.setBackground(new Color(255, 150, 150));
			}
			return renderer;
		}

		private DefaultTableCellRenderer renderer = null;
	};

	/** The lambda cell renderer. */
	private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();
}
