package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;


public class ModuleSelectorRootPane extends BorderPane {

	private CreateStudentProfilePane cspp;
	private ModuleSelectorMenuBar msmb;
	private ModuleSelectorTab moduleSelectorTab;
	private TabPane tp;
	private ReserveModulesPane ump;
	private OverviewSelectionPane osp;
	
	public ModuleSelectorRootPane() {
		//create tab pane and disable tabs from being closed
		tp = new TabPane();
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		//create panes
		cspp = new CreateStudentProfilePane();
		moduleSelectorTab = new ModuleSelectorTab();
		ump = new ReserveModulesPane();
		osp = new OverviewSelectionPane();
		
		//create tabs with panes added
		Tab t1 = new Tab("Create Profile", cspp);
		Tab t2 = new Tab("Select Modules", new BorderPane(moduleSelectorTab));
		Tab t3 = new Tab("Reserve Modules", new BorderPane(ump));
		Tab t4 = new Tab("Overview", new BorderPane( osp));
		
		//add tabs to tab pane
		tp.getTabs().addAll(t1,t2,t3,t4);
		
		//create menu bar
		msmb = new ModuleSelectorMenuBar();
		
		//add menu bar and tab pane to this root pane
		this.setTop(msmb);
		this.setCenter(tp);
		
	}

	//methods allowing sub-containers to be accessed by the controller.
	public CreateStudentProfilePane getCreateStudentProfilePane() {
		return cspp;
	}
	
	public ModuleSelectorMenuBar getModuleSelectorMenuBar() {
		return msmb;
	}
	
	 public ModuleSelectorTab getModuleSelectorTab() {
	        return moduleSelectorTab;
	    }
	 
	
	
	
	//method to allow the controller to change tabs
	public void changeTab(int index) {
		tp.getSelectionModel().select(index);
	}

	public ReserveModulesPane getReserveModulesPane() {
		// TODO Auto-generated method stub
		return ump;
	}
	
	public OverviewSelectionPane getOverviewSelectionPane() {
		return osp;
		
	}
}
