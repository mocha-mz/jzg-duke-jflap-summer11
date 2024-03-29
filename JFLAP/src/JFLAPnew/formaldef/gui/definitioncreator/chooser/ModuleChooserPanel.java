package JFLAPnew.formaldef.gui.definitioncreator.chooser;

import grammar.Grammar;
import gui.errors.JFLAPError;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import JFLAPnew.formaldef.FormalDefinition;
import JFLAPnew.formaldef.FormalDefintionFactory;
import JFLAPnew.formaldef.FormallyDefinedObject;
import JFLAPnew.formaldef.alphabets.IAlphabet;
import JFLAPnew.formaldef.alphabets.specific.TerminalAlphabet;
import JFLAPnew.formaldef.alphabets.specific.VariableAlphabet;
import JFLAPnew.formaldef.gui.definitionpanel.MouseClickAdapter;

public abstract class ModuleChooserPanel extends JPanel implements ActionListener{

	
	private ArrayList<ChooserOption> myChoices;
	
	public ModuleChooserPanel(Class<? extends FormalDefinition> ... toDisable){
		myChoices = new ArrayList<ChooserOption>();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Select Modules"));
		for (Class<? extends FormalDefinition> clazz: FormalDefintionFactory.getAllGenericClasses()){
			ChooserOption op = this.createChooserOption(clazz);
			myChoices.add(op);
			this.add(op);
		}
		for (Class<? extends FormalDefinition> clazz : toDisable){
			this.setEnableVisibleBy(clazz, false, true);
		}
		JButton button = new JButton("Continue");
		button.addActionListener(this);
		this.add(button);
		
	}

	private ChooserOption createChooserOption(Class<? extends FormalDefinition> def) 
	{
		return new ChooserOption(FormalDefintionFactory.getGenericName(def),
								 FormalDefintionFactory.getHotkey(def),
								 def,
								 false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		HashSet<Class<? extends FormalDefinition>> selection = new HashSet<Class<? extends FormalDefinition>>();
		for (ChooserOption op: myChoices){
			if (op.isSelected() && op.isEnabled())
				selection.add(op.getDefinitionClass());
		}
		if (selection.isEmpty()){
			JFLAPError.show("You must select at least one Module", "Error");
			return;
		}
		else{
			this.onContinueAction(selection);
		}
	}

	public abstract void onContinueAction(HashSet<Class<? extends FormalDefinition>> selection);

	
	public  List<ChooserOption> getOptions() {
		return myChoices;
	}
	
	private void setEnableVisibleBy(Class<? extends FormalDefinition> clazz,
			boolean enable, boolean visible) {
		for (ChooserOption op: this.getOptions()){
			if (op.getDefinitionClass().isAssignableFrom(clazz)){
				op.setEnabled(enable);
				op.setSelected(visible);
			}
		}
	}
	
}
