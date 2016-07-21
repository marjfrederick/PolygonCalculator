/**********************************************************************************
 * Course: CSC 240 Java Programming                                               *
 * Instructor: Carmella Garcia                                                    *
 * Term Project: Right Polygon Area Calculator                                    *
 * Due 08/08/15                                                                   *
 * Author: Marj Frederick                                                         *
 *                                                                                *
 * This program welcomes the user and gives information about the program         *
 *  Inputs required by the user: number of sides and one other parameter:         *
 *                                                                                *
 *  Check button Button 1: Side Length and input field to enter the side length   *
 *  Check button Button 2: Circum-radius and input field to enter the radius      *
 *  Check button Button 3: Apothem and input field to enter the apothem           *
 *                                                                                * 
 *  The user then selects the calculate button to calculate the area and display  *
 *  the polygon with the parameter dimensions and parameter lines shown on the    *
 *  polygon with a color legend and the area calculation displayed.               *
 *                                                                                *
 *  To quit the user selects the Quit button or presses the Escape key.           *
 *                                                                                *                                                                            
 * ********************************************************************************/
package PolygonAreaCalculator;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class PolygonAreaCalculator extends Application
{	
	final int BPANE_WIDTH = 600;
	final int BPANE_HEIGHT = 500;
	final int LR = 25;  // border pane side margins
	final int TB = 5;  // border pane top an bottom margins
	
	BorderPane bPane;  // border pane to hold the calculator
	Pane pPane;  // pane to hold the polygon	
	MyPolygon polygon = new MyPolygon ();   // default polygon
	
	// GUI data fields
	final String[] sideText = {"3","4","5","6","7","8","9","10","11","12"};
	final Label sideLabel = new Label("Enter number of sides");
	
	final String[] paramText = {"S","R", "A"};
	final Label paramLabel = new Label("Select parameter you provide");
	
	// Use a fixed size to display the polygon based on the size of border pane
	final double WIDTH = (BPANE_WIDTH - (2 * LR)) / 2;;   // width of the polygon in pixels
	final double HEIGHT = 200;  // height of the polygon in pixels
	double centerX = WIDTH;
	double centerY = (HEIGHT + 20) / 2;
	double radius = Math.min(WIDTH,  HEIGHT) * 0.5;
	

	// default constructor to create a polygon area calculator
	public PolygonAreaCalculator()
	{
		bPane = new BorderPane();
		bPane.setPrefSize(BPANE_WIDTH, BPANE_HEIGHT);
		bPane.setPadding(new Insets(TB, LR, TB, LR)); // top-left-bottom-right
	}
	// constructor to create a polygon area calculator
	public PolygonAreaCalculator(int n)
	{		
		// create the border pane that contains the buttons & polygon
		bPane = new BorderPane();
		bPane.setPrefSize(BPANE_WIDTH, BPANE_HEIGHT);
		bPane.setPadding(new Insets(TB, LR, TB, LR)); // top-left-bottom-right
		
		// Create a pane to hold the polygon
		pPane = createPolygonPane(n);
	}  // end constructor

//------------------------------------------------------------------------------		
	@Override  // Override the start method in the Application class
    public void start (Stage primaryStage)
    {
		// Greet the User & Give information about the program at the console
		greetUser();
		
		// Create a default area polygon calculator
		new PolygonAreaCalculator ();		
		
		//Place nodes in the border pane
		bPane.setTop(getTop());  // nodes for calculator
		Button btn = getQuit(primaryStage);  // quit button
		bPane.setBottom(btn);
		bPane.setAlignment(btn, Pos.CENTER);  // center the quit button
						
		// Create a scene and place it in the stage
		Scene scene = new Scene(bPane);
		
		// Create an register an event handler to end with the ESC key
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
		{
		     @Override
		     public void handle(KeyEvent ke)
		     {
		         if(ke.getCode()==KeyCode.ESCAPE)
		         {
		             primaryStage.close();  // close the stage
		         }
		      }
		 });
		
		primaryStage.setTitle("Right Polygon Area Calculator");  // set the stage title"
		primaryStage.setScene(scene);  // place the scene in the state
		primaryStage.show();  // Display the stage		
    }

//--------------------------------------------------------------------------
	// method to greet the user
	private void greetUser()
	{
		// Greet the user at the command line and give basic information about the program
		System.out.println ("Welcome to the Right Polygon Area Calculator");
		System.out.println ("This program will calculate the area of a polygon given the number of sides");
		System.out.println ("and one other required parameter of the polygon: side length, circum-radius");
		System.out.println("(center to vertex) or apothem (center to center of a side) that you select.");
		System.out.println ("The results of all the parameters will be displayed on the drawn polygon.");
		System.out.println ("\nYou can quit using the Quit button or the Escape (ESC) key");
	}

//--------------------------------------------------------------------------
	// method to create the polygon pane and put the polygon into the pane
	public Pane createPolygonPane(int n)
	{	
		pPane = new Pane();  // pane for the polygon
		double [] vertexX = new double[n]; // x-values of the vertices
		double [] vertexY = new double[n]; // y-values of the vertices
		
		// Create the default polygon and add it to the pane & set properties
		Polygon polygonDraw = new Polygon();
		pPane.getChildren().add(polygonDraw);
		polygonDraw.setFill(Color.WHITE);
		polygonDraw.setStroke(Color.BLACK);
		   
		// Get a list of points for the polygon
		ObservableList<Double> list = polygonDraw.getPoints();
		   
		//Add points to the polygon list
		for (int i=0; i<n; i++)
        {
			vertexX[i] = centerX + radius * Math.cos(2 * i * Math.PI / n);
			list.add(vertexX[i]);
			vertexY[i] = centerY + radius * Math.sin(2 * i * Math.PI / n);
			list.add(vertexY[i]);
		}
		
		// Add the radius line at the 1st vertex
		Line radiusLine = new Line(centerX, centerY, vertexX[0], vertexY[0]);
	    radiusLine.setStroke(Color.RED);
	    pPane.getChildren().add(radiusLine);
	    
	    // Add the radius length displayed as a label located half way
	    // from center along the radius
	    double radiusValue = polygon.calculateRadius();
		radiusValue = Math.round (radiusValue * 10.0) / 10.0; // one decimal
	    Label radiusLabel = new Label (Double.toString(radiusValue));
	    radiusLabel.setTextFill(Color.web("#ff0000"));  // red
	    
	    // Translate the label and place it half way along the radius with
	    // an offset in the Y to put it above the line
	    radiusLabel.setTranslateY(centerY + (0.5 * radius * Math.sin(0.0)) - 15);
	    radiusLabel.setTranslateX(centerX + 0.5 * radius * Math.cos(0.0));
	    pPane.getChildren().add(radiusLabel);	    
	   
	    // Add the apothem line
	    double apothemValue = polygon.calculateApothem();
	    double ratio = (apothemValue * radius) / radiusValue;
	    double apX = centerX + ratio * Math.cos(Math.PI / n);
	    double apY = centerY + ratio * Math.sin(Math.PI / n);
		Line apothemLine = new Line(centerX, centerY, apX, apY);
	    apothemLine.setStroke(Color.BLUE);
	    pPane.getChildren().add(apothemLine);	    
	    
	    // Add the apothem length displayed as a label located about half way
	    // from the center along the radius
	    apothemValue = Math.round (apothemValue * 10.0) / 10.0; // one decimal
	    Label apothemLabel = new Label (Double.toString(apothemValue));
	    apothemLabel.setTextFill(Color.web("#0000ff")); // blue"
	    apothemLabel.setTranslateX(centerX + 0.4 * ratio * Math.cos(Math.PI / n));
	    apothemLabel.setTranslateY(centerY + 0.4 * ratio * Math.sin(Math.PI / n));
	    apothemLabel.setRotate(180 / n); // rotate label along the apothem
	    pPane.getChildren().add(apothemLabel);
	    
	    // Add the side length label to a side. Follow the full length of the
	    // apothem and rotate the label another 90 degrees
	    double sValue = polygon.calculateSideLength();
	    sValue = Math.round(sValue * 10.0) / 10.0; // one decimal
	    Label sLabel = new Label (Double.toString(sValue));
	    sLabel.setTextFill(Color.web("#8b4513")); // saddle-brown"
	    sLabel.setTranslateX(centerX + ratio * Math.cos(Math.PI / n));
	    sLabel.setTranslateY(centerY + ratio * Math.sin(Math.PI / n));
	    sLabel.setRotate((180 / n) + 90);
	    pPane.getChildren().add(sLabel);
	    
	    // Add a color Legend to distinguish the colors
	    Label rColorLabel = new Label ("radius");
	    rColorLabel.setTextFill(Color.web("#ff0000"));  // red
	    pPane.getChildren().add(rColorLabel);
	    Label apColorLabel = new Label ("apothem");
	    apColorLabel.setTextFill(Color.web("#0000ff"));
	    apColorLabel.setTranslateY(20);
	    pPane.getChildren().add(apColorLabel);
	    Label sColorLabel = new Label ("side length");
	    sColorLabel.setTextFill(Color.web("#8b4513")); // sandle-brown
	    sColorLabel.setTranslateY(40);
	    pPane.getChildren().add(sColorLabel);
	    
		return pPane;
		
	}  // end of createPolygonPane method
//--------------------------------------------------------------------------
	// method to create the vBox that contains the welcome, program info
	// and the polygon
	private VBox getTop()
	{
		VBox vBox = new VBox(5); // spacing = 15
		vBox.setPadding(new Insets(5, 15, 5, 15)); // top-left-bottom-right
		Label wLabel = new Label ("                       Welcome to the Right"
				+ " Polygon Area Calculator");
		vBox.getChildren().add (wLabel);
		vBox.getChildren().add (new Label ("This program will calculate the area"
				+ " of a polygon given the number of sides"));
		vBox.getChildren().add (new Label ("and one other required parameter of "
				+ "the polygon: side length, circum-radius"));
		vBox.getChildren().add (new Label ("(center to vertex) or apothem (center"
				+ " to center of a side) that you select."));
		vBox.getChildren().add (new Label ("The results of all the parameters will"
				+ " be displayed on the drawn polygon."));
		
		// space between the welcome & polygon
		vBox.getChildren().add (new Label (" ")); 
		
		// polygon and parameters
		vBox.getChildren().add(getGrid());
		return vBox;
	}
//--------------------------------------------------------------------------    
	// method to create the grid pane that contains the 3 top buttons
	private GridPane getGrid()
	{
		GridPane gPane = new GridPane();
		gPane.setAlignment(Pos.CENTER);
		gPane.setPadding(new Insets(5, 15, 5, 15)); // top-left-bottom-right
		gPane.setHgap(20);
		gPane.setVgap(10);
	    
		// Create a drop down choice box for valid number of sides
	    ChoiceBox sidecb = new ChoiceBox();
	    sidecb.getItems().addAll("3","4","5","6","7","8","9","10","11","12");
	    
	    sidecb.getSelectionModel().selectedIndexProperty().
	        addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
               int index = new_value.intValue();
               sideLabel.setText(sideText[index]);
               polygon.setNumSides(Integer.parseInt(sideText[index]));
            }
        });
	    
	    // Create a drop down choice box for valid parameter types
	    ChoiceBox paramcb = new ChoiceBox();
	    paramcb.getItems().addAll("S: side length", "R: radius", "A: apothem");
	    
	    paramcb.getSelectionModel().selectedIndexProperty().
	        addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                int index = new_value.intValue();
                paramLabel.setText(paramText[index]);
                polygon.setParameterType(paramText[index].toString());
            }
        });
	    	    
	    // Add the selection items & the parameter field to the grid pane
	    // add.(Node, colIndx, rowIndx, colSpan, rowSpan)
	    gPane.add(new Label ("Enter number of sides"), 0, 0); // col 0, row 0
		gPane.add(sidecb, 0, 1);  // col 0, row 1
		gPane.add(new Label ("Select parameter"), 1, 0); // col 1, row 0
		gPane.add(paramcb, 1, 1);  // column 1, row 1
		gPane.add(new Label ("Enter Parameter value"), 2, 0); // col 2, row 0
		TextField tf = new TextField();
		gPane.add (tf, 2, 1);  // col 2, row 1
		Button calcBtn = new Button ("Calculate Area"); 
		gPane.add (calcBtn, 1, 2); // col 1, row 2
		
		// Create and register the handler for the text field		
		tf.textProperty().addListener(new ChangeListener<String>() 
		{
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue)
		    {
		        polygon.setParameterValue(Double.parseDouble(newValue));	
		    }
		});
		
		// Create and register the handler for the calculate button
		calcBtn.setOnAction(e -> calculateAndDisplayPolygon ());
		
		return gPane;
	}
//--------------------------------------------------------------------------
	// method to create the quit button at the bottom
	private Button getQuit(Stage primaryStage)
	{
		Button quitBtn = new Button ("Quit");
		
		// process the quit event
		quitBtn.setOnAction(e->primaryStage.close());
		
		return quitBtn;		
	}
//--------------------------------------------------------------------------
	// calculate the Area and display the polygon and parameters
	private void calculateAndDisplayPolygon ()
	{
		// Calculate the are of the polygon
		double area = polygon.calculateArea();
		
		// Create a pane to hold the polygon
		pPane = createPolygonPane(polygon.getNumSides());
		
		// Put the polygon in the center of the border Pane
		bPane.setCenter(pPane);
		bPane.setAlignment(pPane, Pos.CENTER);
		
		// Round the area to one decimal place and make it a label
		String areaStr = "Area = ".concat(Double.toString
				(Math.round (area * 10.0) / 10.0));
		String fullAreaStr = areaStr.concat(" units sq.");
		Label areaLabel = new Label (fullAreaStr);
		bPane.setRight(areaLabel);
		bPane.setAlignment (areaLabel, Pos.BOTTOM_LEFT);
	}
//--------------------------------------------------------------------------	
	// The main method is only needed for the IDE	
	public static void main(String[] args)
	{
		Application.launch (args);
	}  // end main

}  // end JavaFXMatrixEnhanced class
//-------------------------------------------------------------------------

