package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import model.Block;
import model.Course;
import model.Module;
import model.StudentProfile;



public class ModuleSelectorTab extends GridPane{
	
	
	    private ListView<Module> lvSelectedBlock1, lvSelectedBlock2, lvSelectedBlock3_4, lvUnselectedBlock3_4;
	    private Button btnAdd, btnRemove, btnReset, btnSubmit;
	    private Label lblCredits;

	    public ModuleSelectorTab() {
	        
	    	
	        this.setPadding(new Insets(20));
	        this.setHgap(20);
	        this.setVgap(15);
	        
	        ColumnConstraints col1 = new ColumnConstraints();
	        col1.setHgrow(Priority.ALWAYS);
	        ColumnConstraints col2 = new ColumnConstraints();
	        col2.setHgrow(Priority.ALWAYS);
	        this.getColumnConstraints().addAll(col1, col2);
	        
	        //unconvential method but seems to work justfine
	        for (int i = 0; i <= 6; i++) {
	            RowConstraints row = new RowConstraints();
	            if (i == 1 || i == 4) {
	                row.setVgrow(Priority.ALWAYS);
	            } else {
	                row.setVgrow(Priority.SOMETIMES); 
	            }
	            this.getRowConstraints().add(row);
	        }
	        
	        ListView<Module> lv = new ListView<>();
	        lv.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	        GridPane.setHgrow(lv, Priority.ALWAYS);
	        GridPane.setVgrow(lv, Priority.ALWAYS);


	        
	        lvSelectedBlock1 = new ListView<>();
	        lv.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	        GridPane.setHgrow(lv, Priority.ALWAYS);
	        GridPane.setVgrow(lv, Priority.ALWAYS);
	        
	        lvSelectedBlock2 = new ListView<>();
	        lv.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	        GridPane.setHgrow(lv, Priority.ALWAYS);
	        GridPane.setVgrow(lv, Priority.ALWAYS);
	        
	        lvSelectedBlock3_4 = new ListView<>();
	        lv.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	        GridPane.setHgrow(lv, Priority.ALWAYS);
	        GridPane.setVgrow(lv, Priority.ALWAYS);
	        lvUnselectedBlock3_4 = new ListView<>();

	        
	        lvSelectedBlock1.setPrefSize(300, 100);
	        lvSelectedBlock2.setPrefSize(300, 150);
	        lvSelectedBlock3_4.setPrefSize(300, 100);
	        lvUnselectedBlock3_4.setPrefSize(300, 150);
	        
	        


	        // Labels
	        Label lblBlock1 = new Label("Selected Block 1 Modules:");
	        Label lblBlock2 = new Label("Selected Block 2 Modules:");
	        Label lblSelectedBlock3_4 = new Label("Selected Block 3/4 Modules:");
	        Label lblUnselectedBlock3_4 = new Label("Unselected Block 3/4 Modules:");
	        //Label lbAddRemoveBtn = new Label("Block 3/4");
	    	
	        btnAdd = new Button("Add");
	        btnRemove = new Button("Remove");
	        btnReset = new Button("Reset");
	        btnSubmit = new Button("Submit");
	    	
	        lblCredits = new Label("Current credits: 0");

	        // Layout positioning
	        this.add(lblBlock1, 0, 0);
	        this.add(lvSelectedBlock1, 0, 1);

	        this.add(lblBlock2, 0, 3);
	        this.add(lvSelectedBlock2, 0, 4);

	        this.add(lblUnselectedBlock3_4, 1, 0);
	        this.add(lvUnselectedBlock3_4, 1, 1);
	        
	        this.add(lblSelectedBlock3_4, 1, 3);
	        this.add(lvSelectedBlock3_4, 1, 4);
	        
	       // this.add(lbAddRemoveBtn, 1, 2);
	        HBox buttonBox = new HBox(10, btnAdd, btnRemove);
	        //buttonBox.setAlignment(Pos.CENTER_RIGHT);
	        
	        this.add(buttonBox, 1, 2);
	    


	        

	        HBox bottomBox = new HBox(20, btnReset, btnSubmit);
	        bottomBox.setAlignment(Pos.BOTTOM_CENTER);
	        this.add(bottomBox, 1, 5);
	        
	        HBox.setHgrow(bottomBox, Priority.ALWAYS);
	        
	        
	        HBox bottomBox2 = new HBox(lblCredits);
	        bottomBox2.setAlignment(Pos.BOTTOM_CENTER);
	        this.add(bottomBox2, 1, 6);
	        
	        HBox.setHgrow(bottomBox2, Priority.ALWAYS);
	        
	      
	    }
	    
	    
	    public ListView<Module> getLvSelectedBlock1() { 
	    	return lvSelectedBlock1; 
	    	}
	    
	    public ListView<Module> getLvSelectedBlock2() { 
	    	return lvSelectedBlock2; 
	    	}
	    
	    public ListView<Module> getLvSelectedBlock3_4() { 
	    	return lvSelectedBlock3_4; 
	    	}
	    
	    public ListView<Module> getLvUnselectedBlock3_4() { 
	    	return lvUnselectedBlock3_4; 
	    	}
	    

	    public Button getBtnAdd() { 
	    	return btnAdd;
	    	}
	    
	    public Button getBtnRemove() {
	    	return btnRemove;
	    	}
	    
	    public Button getBtnReset() {
	    	return btnReset; 
	    	}
	    
	    public Button getBtnSubmit() {
	    	return btnSubmit; 
	    	}

	    public Label getLblCredits() { 
	    	return lblCredits;
	    	}

	    
	    
	    public void handleAddModule() {
	        Module selected = lvUnselectedBlock3_4.getSelectionModel().getSelectedItem();
	        if (selected != null) {
	            int currentCredits = getCurrentCredits();
	            if (currentCredits + selected.getModuleCredits() <= 120) {
	                lvUnselectedBlock3_4.getItems().remove(selected);
	                lvSelectedBlock3_4.getItems().add(selected);
	                updateCreditDisplay();
	            } else {
	                showAlert("Credit limit exceeded", "You cannot exceed 120 credits.");
	            }
	        }
	    }
	    
	    
	    private void showAlert(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	    
	    public int getCurrentCredits() {
	        int total = 0;
	        for (Module m : lvSelectedBlock1.getItems()) total += m.getModuleCredits();
	        for (Module m : lvSelectedBlock2.getItems()) total += m.getModuleCredits();
	        for (Module m : lvSelectedBlock3_4.getItems()) total += m.getModuleCredits();
	        return total;
	    }

	    private void updateCreditDisplay() {
	        lblCredits.setText("Current credits: " + getCurrentCredits());
	    }



	    
	    
	    public void reset(Course course) {
	        // Clear all lists
	        lvSelectedBlock1.getItems().clear();
	        lvSelectedBlock2.getItems().clear();
	        lvSelectedBlock3_4.getItems().clear();
	        lvUnselectedBlock3_4.getItems().clear();

	        int totalCredits = 0;
	        
	        //adding modules
	        for (Module m : course.getAllModulesOnCourse()) {
	            if (m.isMandatory()) {
	                switch (m.getRunPlan()) {
	                    case BLOCK_1:
	                        lvSelectedBlock1.getItems().add(m);
	                        totalCredits += m.getModuleCredits();
	                        break;
	                    case BLOCK_2:
	                        lvSelectedBlock2.getItems().add(m);
	                        totalCredits += m.getModuleCredits();
	                        break;
	                    case BLOCK_3_4:
	                        lvSelectedBlock3_4.getItems().add(m);
	                        totalCredits += m.getModuleCredits();
	                        break;
	                }
	            } else {
	                if (m.getRunPlan() == Block.BLOCK_3_4) {
	                    lvUnselectedBlock3_4.getItems().add(m);
	                }
	            }
	        }

	        lblCredits.setText("Current credits: " + totalCredits);
	        
	        
	    }
	    
	    // removing btuttom
	    public void handleRemoveModule() {
	        Module selected = lvSelectedBlock3_4.getSelectionModel().getSelectedItem();
	        if (selected != null) {
	            lvSelectedBlock3_4.getItems().remove(selected);
	            lvUnselectedBlock3_4.getItems().add(selected);
	            updateCreditDisplay();
	        }
	        
	     }
	    
	    // submiting button
	    public boolean handleSubmit(StudentProfile profile) {
	        int totalCredits = getCurrentCredits();
	        if (totalCredits == 120) {
	            profile.clearSelectedModules(); 

	            
	            profile.getAllSelectedModules().addAll(lvSelectedBlock1.getItems());
	            profile.getAllSelectedModules().addAll(lvSelectedBlock2.getItems());
	            profile.getAllSelectedModules().addAll(lvSelectedBlock3_4.getItems());

	            return true;
	        } else {
	            showAlert("Incomplete Selection", "You didnt select exactly 120 credits before submitting.");
	            return false;
	        }
	    }


	
	

}
