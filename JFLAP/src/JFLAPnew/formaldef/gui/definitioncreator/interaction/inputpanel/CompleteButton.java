package JFLAPnew.formaldef.gui.definitioncreator.interaction.inputpanel;

import gui.errors.BooleanWrapper;
import gui.errors.JFLAPError;

import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JDialog;

import JFLAPnew.formaldef.FormalDefinition;
import JFLAPnew.formaldef.gui.GUIHelper;
import JFLAPnew.formaldef.gui.definitioncreator.MultiDefitionPanel;

public class CompleteButton extends JButton {

	private MultiDefitionPanel myPanel;
	
	public CompleteButton(MultiDefitionPanel multiDefPanel, final boolean saveOnClose) {
		super("Finish");
		myPanel = multiDefPanel;
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (CompleteButton.this.checkComplete()){
					if (saveOnClose){
						//TODO: PROMPT FOR SAVE
					}
					GUIHelper.closeAncestorFrame(CompleteButton.this);						
				}
			}
		});
	}

	protected boolean checkComplete() {
		Map<String, BooleanWrapper> incomplete = new HashMap<String, BooleanWrapper>();
		for (FormalDefinition fd : myPanel.getMetaDefinition()){
			BooleanWrapper completed = fd.isComplete();
			if (completed.isFalse())
				incomplete.put(fd.getName(), completed);
		}
		
		if (!incomplete.isEmpty())
			JFLAPError.show("The following components are still incompleted:\n" + 
					this.createErrorLog(incomplete), 
					"Incomplete");
		return incomplete.isEmpty();
	}

	public String createErrorLog(Map<String, BooleanWrapper> incomplete) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, BooleanWrapper> error: incomplete.entrySet()){
			sb.append(error.getKey() + ": " + error.getValue().getMessage() + "\n");
		}
		return sb.toString();
		
	}
		
}

