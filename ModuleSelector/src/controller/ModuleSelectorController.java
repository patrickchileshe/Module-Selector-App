package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import model.Block;
import model.Course;
import model.Module;
import model.Name;
import model.StudentProfile;
import view.ModuleSelectorRootPane;
import view.ModuleSelectorTab;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.CreateStudentProfilePane;
import view.ModuleSelectorMenuBar;

public class ModuleSelectorController {

	//fields to be used throughout class
	private ModuleSelectorRootPane view;
	private StudentProfile model;
	private ModuleSelectorTab mst;
	
	private CreateStudentProfilePane cspp;
	private ModuleSelectorMenuBar msmb;
	private ReserveModulesPane ump;
	private OverviewSelectionPane osp;

	public ModuleSelectorController(ModuleSelectorRootPane view, StudentProfile model) {
		//initialise view and model fields
		this.view = view;
		this.model = model;
		
		//initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		msmb = view.getModuleSelectorMenuBar();
		mst = view.getModuleSelectorTab();
		ump = view.getReserveModulesPane();
		osp = view.getOverviewSelectionPane();

		//add courses to combobox in create student profile pane using the createModulesAndCourses helper method below
		cspp.addCourseDataToComboBox(createModulesAndCourses());

		//attach event handlers to view using private helper method
		this.attachEventHandlers();
	}

	
	//helper method - used to attach event handlers
	private void attachEventHandlers() {
		//attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		
		
		msmb.addExitHandler(e -> System.exit(0));
		
		msmb.addSaveHandler(new SaveProfileHandler());
		msmb.addLoadHandler(new LoadProfileHandler());
		
		msmb.addAboutHandler(e -> {
		    Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
		    aboutAlert.setTitle("About Module Selector");
		    aboutAlert.setHeaderText("Module Selector Application");
		    aboutAlert.setContentText(
		        "Version: 1.00\n" +
		        "Author: Patrick\n\n" +
		        "This application allows students to select and reserve modules for their course, " +
		        "track credit totals, and save/load their profile data. It features a responsive multi-tab layout, " 
		    );
		    aboutAlert.showAndWait();
		});

		
		
		mst.getBtnAdd().setOnAction(e -> mst.handleAddModule());
		mst.getBtnRemove().setOnAction(e -> mst.handleRemoveModule());
		mst.getBtnReset().setOnAction(e -> {
		    Course currentCourse = model.getStudentCourse();
		    if (currentCourse != null) {
		        mst.reset(currentCourse); 
		    } else {
		        Alert alert = new Alert(Alert.AlertType.WARNING);
		        alert.setTitle("No Course Selected");
		        alert.setHeaderText(null);
		        alert.setContentText("Please create a student profile first.");
		        alert.showAndWait();
		    }
		});

		mst.getBtnSubmit().setOnAction(e -> {
		    boolean success = mst.handleSubmit(model);
		    if (success) {
		        
		        ReserveModulesPane rmp = view.getReserveModulesPane();
		        rmp.reset(model.getStudentCourse(), model.getAllSelectedModules());

		        
		        view.changeTab(2);
		    }
		    
		    
		});
		
		
		ump.getBtnAddReserve().setOnAction(e -> ump.handleAddReserve());
		ump.getBtnRemoveReserve().setOnAction(e -> ump.handleRemoveReserve());
		ump.getBtnConfirmReserves().setOnAction(e -> {
		    boolean confirmed = ump.handleConfirm(model);
		    if (confirmed) {
		        populateOverviewTab();
		        view.changeTab(3);     
		    }
		});
		
		
		osp.getBtnSaveOverview().setOnAction(e -> {
		    FileChooser fileChooser = new FileChooser();
		    fileChooser.setTitle("Save Overview");
		    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		    File file = fileChooser.showSaveDialog(null);

		    if (file != null) {
		        try (PrintWriter writer = new PrintWriter(file)) {
		            writer.println(osp.getStudentInfoText());
		            
		            writer.println();
		            
		            writer.println("Selected Modules:");
		            
		            writer.println(osp.getSelectedModulesText());
		            
		            writer.println();
		            
		            writer.println("Reserved Modules:");
		            
		            writer.println(osp.getReservedModulesText());
		        } catch (IOException ex) {
		            Alert alert = new Alert(Alert.AlertType.ERROR);
		            alert.setTitle("Save Failed");
		            alert.setHeaderText(null);
		            alert.setContentText("Could not save the overview: " + ex.getMessage());
		            alert.showAndWait();
		        }
		    }
		});

		
		
		
		
		
		    



	}
	
	

	//event handler (currently empty), which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			
			Course selectedCourse = cspp.getSelectedCourse();

			String pNumber = cspp.getStudentPnumber().trim();

			Name studentName = cspp.getStudentName();

			String email = cspp.getStudentEmail().trim();

			// some simple Validation

			if (selectedCourse == null || pNumber.isEmpty() || email.isEmpty() ||

			studentName.getFirstName().isEmpty() || studentName.getFamilyName().isEmpty()) { showAlert("Error", "Please fill in all required fields"); return;

			}
		
			
			// Get student data from form
	        model.setStudentCourse(cspp.getSelectedCourse());
	        model.setStudentPnumber(cspp.getStudentPnumber());
	        model.setStudentName(cspp.getStudentName());
	        model.setStudentEmail(cspp.getStudentEmail());
	        model.setSubmissionDate(cspp.getStudentDate());

	        // Populate SelectModulesPane with course modules
	        mst.reset(model.getStudentCourse());

	        // Switch to Select Modules tab
	        view.changeTab(1);
			
			
		}
		
		
	}
	
	


	//helper method - creates modules and course data and returns courses within an array
	private Course[] createModulesAndCourses() {
		Module ctec3701 = new Module("CTEC3701", "Software Development: Methods & Standards", 30, true, Block.BLOCK_1);

		Module ctec3702 = new Module("CTEC3702", "Big Data and Machine Learning", 30, true, Block.BLOCK_2);
		Module ctec3703 = new Module("CTEC3703", "Mobile App Development and Big Data", 30, true, Block.BLOCK_2);

		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Block.BLOCK_3_4);

		Module ctec3704 = new Module("CTEC3704", "Functional Programming", 30, false, Block.BLOCK_3_4);
		Module ctec3705 = new Module("CTEC3705", "Advanced Web Development", 30, false, Block.BLOCK_3_4);

		Module imat3711 = new Module("IMAT3711", "Privacy and Data Protection", 30, false, Block.BLOCK_3_4);
		Module imat3722 = new Module("IMAT3722", "Fuzzy Logic and Inference Systems", 30, false, Block.BLOCK_3_4);

		Module ctec3706 = new Module("CTEC3706", "Embedded Systems and IoT", 30, false, Block.BLOCK_3_4);


		Course compSci = new Course("Computer Science");
		compSci.addModule(ctec3701);
		compSci.addModule(ctec3702);
		compSci.addModule(ctec3451);
		compSci.addModule(ctec3704);
		compSci.addModule(ctec3705);
		compSci.addModule(imat3711);
		compSci.addModule(imat3722);

		Course softEng = new Course("Software Engineering");
		softEng.addModule(ctec3701);
		softEng.addModule(ctec3703);
		softEng.addModule(ctec3451);
		softEng.addModule(ctec3704);
		softEng.addModule(ctec3705);
		softEng.addModule(ctec3706);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}
	
	public ReserveModulesPane getReserveModulesPane() {
	    return ump;
	}
	
	
	private void populateOverviewTab() {
	    OverviewSelectionPane osp = view.getOverviewSelectionPane();

	    // Student info
	    osp.setStudentInfo(
	    	    model.getStudentName().getFullName(),
	    	    model.getStudentPnumber(),
	    	    model.getStudentEmail(),
	    	    model.getSubmissionDate().toString(),
	    	    model.getStudentCourse().getCourseName()
	    	);
	    
	    StringBuilder selectedText = new StringBuilder();
	    for (Module m : model.getAllSelectedModules()) {
	        selectedText.append(String.format(
	        	"%n Module code: %s, Module name: %s, Credits: %d, Mandatory on your course? %s, Block: %s%n%n",
	            m.getModuleCode(), m.getModuleName(), m.getModuleCredits(),
	            m.isMandatory() ? "Yes" : "No", m.getRunPlan()
	        ));
	    }
	    
	    osp.setSelectedModulesText("=========== "+ selectedText.toString());

	    
	    StringBuilder reservedText = new StringBuilder();
	    for (Module m : model.getAllReservedModules()) {
	        reservedText.append(String.format(
	        		
	            "%n Module code: %s, Module name: %s, Credits: %d, Mandatory on your course? %s, Block: %s%n%n",
	            m.getModuleCode(), m.getModuleName(), m.getModuleCredits(),
	            m.isMandatory() ? "Yes" : "No", m.getRunPlan()
	        ));
	    }
	    osp.setReservedModulesText("========= " + reservedText.toString());
	}
	
	
	//make an event handler for your menu bar
	private class SaveProfileHandler implements EventHandler<ActionEvent> {
	    public void handle(ActionEvent e) {
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Save Student Profile");
	        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
	        File file = fileChooser.showSaveDialog(null);

	        if (file != null) {
	            try (PrintWriter writer = new PrintWriter(file)) {
	                // Write student info
	                writer.println("Name: " + model.getStudentName().getFullName());
	                writer.println("PNo: " + model.getStudentPnumber());
	                writer.println("Email: " + model.getStudentEmail());
	                writer.println("End Date: " + model.getSubmissionDate());
	                writer.println("Course: " + model.getStudentCourse().getCourseName());
	                writer.println();

	                // Selected modules
	                writer.println("Selected Modules:");
	                for (Module m : model.getAllSelectedModules()) {
	                    writer.printf("  %s - %s (%d credits) [%s, Block %s]%n",
	                        m.getModuleCode(), m.getModuleName(), m.getModuleCredits(),
	                        m.isMandatory() ? "Mandatory" : "Optional", m.getRunPlan());
	                }
	                
	                writer.println();

	                // Reserved modules
	                writer.println("Reserved Modules:");
	                for (Module m : model.getAllReservedModules()) {
	                    writer.printf("  %s - %s (%d credits) [%s, Block %s]%n",
	                        m.getModuleCode(), m.getModuleName(), m.getModuleCredits(),
	                        m.isMandatory() ? "Mandatory" : "Optional", m.getRunPlan());
	                }

	            } catch (IOException ex) {
	                Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving profile: " + ex.getMessage());
	                alert.showAndWait();
	            }
	        }
	    }
	    
	    

	}
	//loading data
	
	private class LoadProfileHandler implements EventHandler<ActionEvent> {
	    public void handle(ActionEvent e) {
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Load Saved Profile");
	        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
	        File file = fileChooser.showOpenDialog(null);

	        if (file != null) {
	            try {
	                List<String> lines = Files.readAllLines(file.toPath());
	                String content = String.join("\n", lines);
	                osp.setStudentInfoText(content); // Display in Overview tab
	                view.changeTab(3);
	            } catch (IOException ex) {
	                Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading profile: " + ex.getMessage());
	                alert.showAndWait();
	            }
	        }
	    }
	}
	
	//Pop up alerts
	 private void showAlert(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	 
	 
	 private class AboutHandler implements EventHandler<ActionEvent> {

			public void handle(ActionEvent e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("IO GUI v1.1");
				alert.showAndWait();
			}
		}
	    







	

}
