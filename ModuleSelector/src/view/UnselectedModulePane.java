package view;

import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Block;
import model.Course;
import model.Module;
import model.StudentProfile;


public class UnselectedModulePane  extends GridPane{
	
	private ListView<Module>  reserveblock3_4, lvUnselectedBlock3_4, lvSelectedReserve;
	private Button btnAdd2, btnRemove2, btnConfirm;
	private Label optionalmodule;
	
	
	public UnselectedModulePane(){
		
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(15);
		
		lvUnselectedBlock3_4 = new ListView<>();
		reserveblock3_4 = new ListView<>();
		
		
		lvUnselectedBlock3_4.setPrefSize(250, 150);
		reserveblock3_4.setPrefSize(250, 150);
		
		Label lblUnselectedModules3_4 = new Label("Unseleceted Modules 3/4");
		Label lblReserveblock3_4 = new Label("Reserve Module 3/4");
		
		
		Button btnAdd2 = new Button("Add");
		Button btRemove2 = new Button("Remove");
		Button btnConfirm = new Button("Confirm");
		
		Label lbOptionalModule = new Label("Reserve one optional module");
		
		
		this.add(lblUnselectedModules3_4, 0, 1);
		this.add(lvUnselectedBlock3_4, 0, 2);
		
		
		this.add(lblReserveblock3_4, 1, 1);
		this.add(reserveblock3_4, 1,2);
		
		VBox buttonBox = new VBox(10, btnAdd2, btnRemove2);
		buttonBox.setAlignment(Pos.CENTER);
		this.add(buttonBox, 0, 3);
		this.add(btnConfirm, 1, 3);
		
		
		
		
		
		
		
	}
	
	public ListView<Module> getLvUnselectedBlock3_4(){ 
		return lvUnselectedBlock3_4;
	}
	
	public ListView<Module> getReserveBlock3_4(){ 
		return reserveblock3_4;
		
	}
	
	public Button getBtnAddReserve() { 
		return btnAdd2; 
		}
	
	
    public Button getBtnRemoveReserve() { 
    	return btnRemove2; 
    	}
    
    
    public Button getBtnConfirmReserves() { 
    	return btnConfirm; 
    	}
	

}
