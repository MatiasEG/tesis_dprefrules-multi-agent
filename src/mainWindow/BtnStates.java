package mainWindow;

import javax.swing.JOptionPane;

public class BtnStates {

	private int state;
	private MainWindow mw;
	
	public BtnStates(MainWindow mw) {
		state = -1;
		this.mw = mw;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void checkState(boolean isEditing) {
		switch (state) {
	        case 0:
	            // Must indicate the name and project folder
	            setState0(isEditing);
	            break;
	        case 1:
	            // Must indicate the participants
	        	setState1(isEditing);
	            break;
	        case 2:
	            // Must indicate the criteria
	        	setState2(isEditing);
	            break;
	        case 3:
	            // Must indicate the evidence
	        	setState3(isEditing);
	            break;
	        case 4:
	            // Must indicate the preference rules
	        	setState4(isEditing);
	            break;
	        case 5:
	            // Must indicate the preference rules
	        	setState5(isEditing);
	            break;
	        default:
	            // Error
	        	JOptionPane.showMessageDialog(null, "Estado invalido", "Advertencia", JOptionPane.WARNING_MESSAGE);
			}
	}
	
	// The user start the program
	// > Must indicate the name and project folder
	public void setState0(boolean isEditing) {
		state = 0;
		if(isEditing) {
			mw.btnEditNameAndFolder.setEnabled(false);
		}else {
			mw.btnEditNameAndFolder.setEnabled(true);
		}
		mw.btnViewNameAndFolder.setEnabled(false);
	    
		mw.btnEditParticipants.setEnabled(false);
		mw.btnViewParticipants.setEnabled(false);
	    
		mw.btnEditCriteria.setEnabled(false);
		mw.btnViewCriteria.setEnabled(false);
	    
		mw.btnEditEvidence.setEnabled(false);
		mw.btnViewEvidence.setEnabled(false);
	    
		mw.btnEditRules.setEnabled(false);
		mw.btnViewRules.setEnabled(false);
	    
		mw.btnSaveFiles.setEnabled(false);
	}
	
	// The user indicate name and project folder
	// > Must indicate the participants
	public void setState1(boolean isEditing) {
		state = 1;
		if(isEditing) {
			mw.btnEditNameAndFolder.setEnabled(false);
			mw.btnEditParticipants.setEnabled(false);
		}else {
			mw.btnEditNameAndFolder.setEnabled(true);
			mw.btnEditParticipants.setEnabled(true);
		}
		
		mw.btnViewNameAndFolder.setEnabled(true);
	    
		mw.btnViewParticipants.setEnabled(false);
	    
		mw.btnEditCriteria.setEnabled(false);
		mw.btnViewCriteria.setEnabled(false);
	    
		mw.btnEditEvidence.setEnabled(false);
		mw.btnViewEvidence.setEnabled(false);
	    
		mw.btnEditRules.setEnabled(false);
		mw.btnViewRules.setEnabled(false);
	    
		mw.btnSaveFiles.setEnabled(true);
	}
	
	// The user indicate the participants and they priorities
	// > Must indicate the criteria
	// [Here the participants can be edited but never can be 0]
	public void setState2(boolean isEditing) {
		state = 2;
		if(isEditing) {
			mw.btnEditNameAndFolder.setEnabled(false);
			mw.btnEditParticipants.setEnabled(false);
			mw.btnEditCriteria.setEnabled(false);
		}else {
			mw.btnEditNameAndFolder.setEnabled(true);
			mw.btnEditParticipants.setEnabled(true);
			mw.btnEditCriteria.setEnabled(true);
		}
		
		mw.btnViewNameAndFolder.setEnabled(true);
	    
		mw.btnViewParticipants.setEnabled(true);
	    
		mw.btnViewCriteria.setEnabled(false);
	    
		mw.btnEditEvidence.setEnabled(false);
		mw.btnViewEvidence.setEnabled(false);
	    
		mw.btnEditRules.setEnabled(false);
		mw.btnViewRules.setEnabled(false);
	    
		mw.btnSaveFiles.setEnabled(true);
	}
	
	// The user indicate the criteria
	// > Must indicate the evidence
	// [Here the criteria can be edited but never can be 0]
	public void setState3(boolean isEditing) {
		state = 3;
		if(isEditing) {
			mw.btnEditNameAndFolder.setEnabled(false);
			mw.btnEditParticipants.setEnabled(false);
			mw.btnEditCriteria.setEnabled(false);
			mw.btnEditEvidence.setEnabled(false);
		}else {
			mw.btnEditNameAndFolder.setEnabled(true);
			mw.btnEditParticipants.setEnabled(true);
			mw.btnEditCriteria.setEnabled(true);
			mw.btnEditEvidence.setEnabled(true);
		}
		
		mw.btnViewNameAndFolder.setEnabled(true);
	    
		mw.btnViewParticipants.setEnabled(true);
	    
		mw.btnViewCriteria.setEnabled(true);
	    
		mw.btnViewEvidence.setEnabled(false);
	    
		mw.btnEditRules.setEnabled(false);
		mw.btnViewRules.setEnabled(false);
	    
		mw.btnSaveFiles.setEnabled(true);
	}
	
	// The user indicate the evidence
	// > Must indicate the preference rules
	// [Here the rules can be edited but never can be less than 1]
	public void setState4(boolean isEditing) {
		state = 4;
		if(isEditing) {
			mw.btnEditNameAndFolder.setEnabled(false);
			mw.btnEditParticipants.setEnabled(false);
			mw.btnEditCriteria.setEnabled(false);
			mw.btnEditEvidence.setEnabled(false);
			mw.btnEditRules.setEnabled(false);
		}else {
			mw.btnEditNameAndFolder.setEnabled(true);
			mw.btnEditParticipants.setEnabled(true);
			mw.btnEditCriteria.setEnabled(true);
			mw.btnEditEvidence.setEnabled(true);
			mw.btnEditRules.setEnabled(true);
		}
		
		mw.btnViewNameAndFolder.setEnabled(true);
	    
		mw.btnViewParticipants.setEnabled(true);
	    
		mw.btnViewCriteria.setEnabled(true);
	    
		mw.btnViewEvidence.setEnabled(true);
	    
		mw.btnViewRules.setEnabled(false);
	    
		mw.btnSaveFiles.setEnabled(true);
	}
	
	// The user indicate the rules
	// > This is a finish state
	// [Here you can do something with the files or data?]
	public void setState5(boolean isEditing) {
		state = 5;
		if(isEditing) {
			mw.btnEditNameAndFolder.setEnabled(false);
			mw.btnEditParticipants.setEnabled(false);
			mw.btnEditCriteria.setEnabled(false);
			mw.btnEditEvidence.setEnabled(false);
			mw.btnEditRules.setEnabled(false);
		}else {
			mw.btnEditNameAndFolder.setEnabled(true);
			mw.btnEditParticipants.setEnabled(true);
			mw.btnEditCriteria.setEnabled(true);
			mw.btnEditEvidence.setEnabled(true);
			mw.btnEditRules.setEnabled(true);
		}
		
		mw.btnViewNameAndFolder.setEnabled(true);
	    
		mw.btnViewParticipants.setEnabled(true);
	    
		mw.btnViewCriteria.setEnabled(true);
	    
		mw.btnViewEvidence.setEnabled(true);
	    
		mw.btnViewRules.setEnabled(true);
	    
		mw.btnSaveFiles.setEnabled(true);
	}
}
