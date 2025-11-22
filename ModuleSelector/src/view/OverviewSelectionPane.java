package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class OverviewSelectionPane extends GridPane {

    private TextArea txtSelectedModules, txtReservedModules, txtStudentDetails;
    
    private Button btnSaveOverview;

    public OverviewSelectionPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(15);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        this.getColumnConstraints().addAll(col1, col2);

        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.NEVER); // label
        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.SOMETIMES); // student info
        RowConstraints row3 = new RowConstraints();
        row3.setVgrow(Priority.ALWAYS);
        RowConstraints row4 = new RowConstraints();
        row4.setVgrow(Priority.NEVER); 
        this.getRowConstraints().addAll(row1, row2, row3, row4);


        
        
        txtStudentDetails = new TextArea();
        txtStudentDetails.setEditable(false);
        txtStudentDetails.setWrapText(true);
        txtStudentDetails.setPrefSize(600, 100);
        this.add(new Label("Student Information:"), 0, 0);
        this.add(txtStudentDetails, 0, 1, 2, 1);
        
        
        txtSelectedModules = new TextArea();
        txtReservedModules = new TextArea();
      
        txtSelectedModules.setEditable(false);
        txtReservedModules.setEditable(false);
        txtSelectedModules.setWrapText(true);
        txtReservedModules.setWrapText(true);

        txtSelectedModules.setPrefSize(300, 250);
        txtReservedModules.setPrefSize(300, 250);
        txtStudentDetails.setPrefSize(300, 100);
        
        txtStudentDetails.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        txtSelectedModules.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        txtReservedModules.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        GridPane.setHgrow(txtStudentDetails, Priority.ALWAYS);
        GridPane.setVgrow(txtStudentDetails, Priority.ALWAYS);
        VBox.setVgrow(txtSelectedModules, Priority.ALWAYS);
        VBox.setVgrow(txtReservedModules, Priority.ALWAYS);

        

        VBox selectedBox = new VBox(new Label("Selected modules"), txtSelectedModules);
        VBox reservedBox = new VBox(new Label("Reserved modules"), txtReservedModules);
        
        selectedBox.setSpacing(5);
        reservedBox.setSpacing(5);
        

        HBox moduleBox = new HBox(20, selectedBox, reservedBox);
        moduleBox.setAlignment(Pos.CENTER);
        this.add(moduleBox, 0, 2, 2, 1);
        
        HBox.setHgrow(selectedBox, Priority.ALWAYS);
        HBox.setHgrow(reservedBox, Priority.ALWAYS);


        // Save button
        btnSaveOverview = new Button("Save Overview");
        HBox buttonBox = new HBox(btnSaveOverview);
        buttonBox.setAlignment(Pos.BASELINE_CENTER);
        this.add(buttonBox, 0, 3);
        HBox.setHgrow(buttonBox, Priority.ALWAYS);
    }
    
    

    // Setters to populate drequired detailss
    public void setStudentInfo(String name, String pnumber, String email, String date, String course) {
        String info = String.format(
                "Name: %s%nP Number: %s%nEmail: %s%nEnd Date: %s%nCourse: %s",
                name, pnumber, email, date, course
            );
            txtStudentDetails.setText(info);
        }

    public void setSelectedModulesText(String text) {
        txtSelectedModules.setText(text);
    }

    public void setReservedModulesText(String text) {
        txtReservedModules.setText(text);
    }
    
   

    public Button getBtnSaveOverview() {
        return btnSaveOverview;
    }
    
    
    public String getStudentInfoText() {
        return txtStudentDetails.getText();
    }

    public String getSelectedModulesText() {
        return txtSelectedModules.getText();
    }

    public String getReservedModulesText() {
        return txtReservedModules.getText();
    }

	public void setStudentInfoText(String content) {
		// TODO Auto-generated method stub
		txtStudentDetails.setText(content);
		
		
	}

    
    
    
}
