package application;

import Logic.pro;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller {
@FXML
private AnchorPane anchor;
@FXML
Button bt;
@FXML
TextField txt;
@FXML
TextField forwardSolver;
@FXML
TextField loopsSolver;
@FXML
TextField nonTouchingLoopsSolver;
@FXML
TextField oaTF;
@FXML
TextField delta;
SignaflowGraph sfg = new SignaflowGraph();
pro pr = new pro();

public void drawGUI () {
	sfg.TakeInputGUI(txt.getText());
	sfg.draw(anchor);
	txt.clear();
}
public void forward() {
	pr.intialize();;
	forwardSolver.setText(pr.getdata().toString());
}
public void loops() {
	pr.intialize();;
	loopsSolver.setText(pr.getLoop().toString());
}
public void nonForwardPaths() {
	pr.intialize();;
	nonTouchingLoopsSolver.setText(pr.getNonTouchingLoop().toString());
}
public void tFunction() {
	pr.intialize();;
	oaTF.setText(String.valueOf(pr.getOverAllTF()));
}
public void delta() {
	delta.setText(pr.getDelta().toString());
}
/*public void takeInput() {
	sfg.TakeInputGUI(txt.getText());
	txt.clear();
	}*/
}

