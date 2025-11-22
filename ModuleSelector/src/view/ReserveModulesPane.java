package view;

import java.util.Set;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Block;
import model.Course;
import model.Module;
import model.StudentProfile;

public class ReserveModulesPane extends BorderPane {

    private ListView<Module> lvAvailableReserves, lvSelectedReserves;
    private Button btnAddReserve, btnRemoveReserve, btnConfirmReserves;
    private Label lblReserveInfo;

    public ReserveModulesPane() {
        
        this.setPadding(new Insets(20));

        
        lblReserveInfo = new Label("Select up to 2 reserve modules:");
        //BorderPane.setAlignment(lblReserveInfo, Pos.CENTER);
        //this.setTop(lblReserveInfo);

        // Center: Two ListViews side by side
        lvAvailableReserves = new ListView<>();
        lvSelectedReserves = new ListView<>();

        lvAvailableReserves.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        lvSelectedReserves.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        VBox leftBox = new VBox(new Label("Unselected Block 3/4 Modules:"), lvAvailableReserves);
        VBox rightBox = new VBox(new Label("Reserved Block 3/4 Modules:"), lvSelectedReserves);

        leftBox.setSpacing(5);
        rightBox.setSpacing(5);
        leftBox.setPrefWidth(300);
        rightBox.setPrefWidth(300);
        VBox.setVgrow(lvAvailableReserves, Priority.ALWAYS);
        VBox.setVgrow(lvSelectedReserves, Priority.ALWAYS);

        HBox centerBox = new HBox(20, leftBox, rightBox);
        centerBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(leftBox, Priority.ALWAYS);
        HBox.setHgrow(rightBox, Priority.ALWAYS);
        this.setCenter(centerBox);

        
        btnAddReserve = new Button("Add");
        btnRemoveReserve = new Button("Remove");
        //HBox buttonBox = new HBox(10, btnAddReserve, btnRemoveReserve);
        //buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        //this.setBottom(buttonBox);

        
        btnConfirmReserves = new Button("Confirm");
        HBox bottomBox = new HBox(10, lblReserveInfo, btnAddReserve, btnRemoveReserve, btnConfirmReserves);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomBox.setPadding(new Insets(10, 0, 0, 0));
        this.setBottom(bottomBox);
    }

    
    public ListView<Module> getLvAvailableReserves() {
    	return lvAvailableReserves;
    	}
    
    public ListView<Module> getLvSelectedReserves() { 
    	return lvSelectedReserves; 
    	}
    
    public Button getBtnAddReserve() {
    	return btnAddReserve;
    	}
    
    public Button getBtnRemoveReserve() {
    	return btnRemoveReserve; 
    	}
    
    public Button getBtnConfirmReserves() {
    	return btnConfirmReserves; 
    	}
    
    public void reset(Course course, Set<Module> selectedModules) {
        lvAvailableReserves.getItems().clear();
        lvSelectedReserves.getItems().clear();

        for (Module m : course.getAllModulesOnCourse()) {
            if (!selectedModules.contains(m) && m.getRunPlan() == Block.BLOCK_3_4) {
                lvAvailableReserves.getItems().add(m);
            }
        }
       }
    
    
    public void handleAddReserve() {
        Module selected = lvAvailableReserves.getSelectionModel().getSelectedItem();
        if (selected != null && lvSelectedReserves.getItems().size() < 2) {
            lvAvailableReserves.getItems().remove(selected);
            lvSelectedReserves.getItems().add(selected);
        } else if (lvSelectedReserves.getItems().size() >= 2) {
            showAlert("Yoouve reached the limit.", "You can only select 2 reserve modules.");
        }
    }
    
    
    public void handleRemoveReserve() {
        Module selected = lvSelectedReserves.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lvSelectedReserves.getItems().remove(selected);
            lvAvailableReserves.getItems().add(selected);
        }
    }
    
    
    
    public boolean handleConfirm(StudentProfile profile) {
        if (lvSelectedReserves.getItems().size() == 2) {
            profile.clearReservedModules();
            profile.getAllReservedModules().addAll(lvSelectedReserves.getItems());
            return true;
        } else {
            showAlert("You havent selected 2 reserve modules", "Please select exactly 2 reserve modules.");
            return false;
        }
        
    }


    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
  



    
    
    
    
    
}
