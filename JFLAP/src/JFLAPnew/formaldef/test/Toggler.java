package JFLAPnew.formaldef.test;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import JFLAPnew.JFLAPpreferences;
import JFLAPnew.formaldef.gui.IUpdate;
import JFLAPnew.formaldef.gui.definitionpanel.DefinitionPanel;

public class Toggler extends JPanel {

	private List<Component> myUpdates;

	
	public Toggler(){
		myUpdates = new ArrayList<Component>();
		this.add(new ToggleButton(this, this.getSize()));
		this.setVisible(true);
	}
	
	private class ToggleButton extends JButton{
		
		private Toggler myToggler;

		public Toggler getToggler() {
			return myToggler;
		}
		
		public ToggleButton(Toggler t, Dimension d){
			myToggler = t;
			this.setSize(d);
			this.setLabel();
			this.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					JFLAPpreferences.setUserDefinedAlphabet(!JFLAPpreferences.isUserDefinedAlphabet());
					((ToggleButton) e.getComponent()).setLabel();
					((ToggleButton) e.getComponent()).getToggler().updateAll();
				}
				
			});
		}

		public void setLabel(){
			if (JFLAPpreferences.isUserDefinedAlphabet())
				this.setText("User is defining\nThe Alphabet(s)");
			else
				this.setText("Default Alphabet(s)\nBeing Used");
		}

	}

	public void addUpdatable(Component update) {
		myUpdates.add(update);		
	}

	protected void updateAll() {
		for (Component up : myUpdates)	
			up.repaint();
	}
}
