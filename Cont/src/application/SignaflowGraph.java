package application;

import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.swing.plaf.synth.SynthStyle;

import Logic.Data;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class SignaflowGraph {
	LinkedList<Map<String, String>> input = new LinkedList<>();
	LinkedList< Map<String, String>> allInputs = new LinkedList<>();
	LinkedList<Map<String, String>> drawNodes = new LinkedList<>();
	Data data = new Data();
	LinkedList<String> inputNodes = new LinkedList<>();
	LinkedList<String> outputNodes = new LinkedList<>();

	int flagin = 0;
	int flagout = 0;
	int counerNode = 1;
	int heightUp = 300;
	int heightDown = 500;
	int flagHeight = 0;
	
	
	public void TakeInputGUI(String dataInput) {
		Map<String, String> returnMap = new HashMap<>();
			String[] array = dataInput.split(" ");
			returnMap = new HashMap<>();
			returnMap.put("in", array[0]);
			returnMap.put("out", array[1]);
			returnMap.put("branch", array[2]);
			input.add(returnMap);
			allInputs.add(returnMap);
			if(!inputNodes.contains(array[0])) {
				inputNodes.add(array[0]);
			}
			if(!outputNodes.contains(array[1])) {
				outputNodes.add(array[1]);
			}
			
	}
	
	public void draw(AnchorPane canvas) {			
			Group g = new Group();
			for (int i = 0; i < input.size(); i++) {
			String in = input.get(i).get("in");
			String out = input.get(i).get("out");
			Double branch = Double.valueOf(input.get(i).get("branch"));
			Double startX = 0.0;
			Double startY = 0.0;
			Double endX = 0.0;
			Double endY = 0.0;
			int numberIn = 0;
			int numberOut = 0;
			Map<String, String> nodes = new HashMap<>();
			for (int j = 0; j < drawNodes.size(); j++) {
				if (drawNodes.size() != 0) {
					if (drawNodes.get(j).get("node").equals(in)) {
						flagin = 1;
						startX = Double.valueOf(drawNodes.get(j).get("x"));
						startY = Double.valueOf(drawNodes.get(j).get("y"));
						numberIn = Integer.valueOf(drawNodes.get(j).get("number"));
						
					}
					if(drawNodes.get(j).get("node").equals(out)) {
						flagout = 1;
						endX = Double.valueOf(drawNodes.get(j).get("x"));
						endY = Double.valueOf(drawNodes.get(j).get("y"));
						numberOut = Integer.valueOf(drawNodes.get(j).get("number"));
					}
				}
			}
			if (flagin == 0) {
				Ellipse e = new Ellipse();
				e.setRadiusX(40);
				e.setRadiusY(40);
				e.setCenterX(counerNode * 200);
				e.setCenterY(400);
				e.setFill( javafx.scene.paint.Color.LIGHTSKYBLUE);
	    	    e.setStroke(javafx.scene.paint.Color.DEEPPINK);
				nodes =  new HashMap<>();
				nodes.put("node", in);
				nodes.put("x", Double.toString(e.getCenterX()));
				nodes.put("y",Double.toString(e.getCenterY()));
				nodes.put("number", String.valueOf(counerNode));
				numberIn = counerNode;
				startX = e.getCenterX();
				startY = e.getCenterY();
				Text text = new Text();
				text.setX(e.getCenterX());
				text.setY(e.getCenterY());
				text.setText(in);
				text.setStroke(javafx.scene.paint.Color.BLACK);
				g.getChildren().add(e);
				g.getChildren().add(text);
				drawNodes.add(nodes);
				counerNode++;
			}
			if (flagout == 0) {
				Ellipse e = new Ellipse();
				e.setRadiusX(40);
				e.setRadiusY(40);
				e.setCenterX(counerNode * 200);
				e.setCenterY(400);
				e.setFill( javafx.scene.paint.Color.LIGHTSKYBLUE);
	    	    e.setStroke(javafx.scene.paint.Color.DEEPPINK);
				nodes = new HashMap<>();
				nodes.put("x", Double.toString(e.getCenterX()));
				nodes.put("y",Double.toString(e.getCenterY()));
				nodes.put("node", out);
				nodes.put("number", String.valueOf(counerNode));
				numberOut = counerNode;
				endX = e.getCenterX() ;
				endY = e.getCenterY();
				Text text = new Text();
				text.setX(e.getCenterX());
				text.setY(e.getCenterY());
				text.setText(out);
				text.setStroke(javafx.scene.paint.Color.BLACK);
				g.getChildren().add(e);
				g.getChildren().add(text);
				drawNodes.add(nodes);
				counerNode++;
			}
			Text branchText = new Text();
			Polygon arrow  = new Polygon();
			if((numberOut - numberIn == 1 || numberIn - numberOut == 1) &&  !(flagin == 1 && flagout == 1))
			{
				Line line = new Line();
				line.setStartY(startY);
				line.setEndY(endY);
				if(numberOut > numberIn) {
					line.setStartX(startX + 40);
					line.setEndX(endX - 40);
					branchText.setX((line.getEndX() + line.getStartX())/2);
					branchText.setY(line.getStartY() - 5 );
					arrow.getPoints().addAll(new Double[]{        
						branchText.getX(), line.getStartY() + 4, 
						branchText.getX(), line.getStartY() - 4, 
						branchText.getX() + 7,line.getStartY() , 
						});
				}
				else {
					line.setStartX(startX - 40);
					line.setEndX(endX + 40);
					branchText.setX((line.getStartX() + line.getEndX())/2);
					branchText.setY(line.getStartY() - 5 );
					arrow.getPoints().addAll(new Double[]{        
							branchText.getX(), line.getStartY() + 4, 
							branchText.getX(), line.getStartY() - 4, 
							branchText.getX() - 7,line.getStartY() , 
							});
				}
				line.setStroke(javafx.scene.paint.Color.DEEPPINK);
				g.getChildren().add(line);
			}
			else if(numberOut - numberIn ==0) {
				Line lineY = new Line();
				Line lineX = new Line();
				Line lineY2 = new Line();
				if(flagHeight ==0) {
					lineY.setStartX(startX + 20);
					lineY.setStartY(startY - 40);
					lineY.setEndX(startX + 20);
					lineY.setEndY(heightUp);
					lineX.setStartX(startX + 20);
					lineX.setStartY(heightUp);
					lineX.setEndX(endX - 20);
					lineX.setEndY(heightUp);
					lineY2.setStartX(endX - 20);
					lineY2.setStartY(heightUp);
					lineY2.setEndX(endX - 20);
					lineY2.setEndY(endY - 40);
					branchText.setX((lineX.getEndX() + lineX.getStartX())/2);
					branchText.setY(lineY2.getStartY() - 5 );
						arrow.getPoints().addAll(new Double[]{        
								branchText.getX(), lineX.getStartY() + 4, 
								branchText.getX(), lineX.getStartY() - 4, 
								branchText.getX() - 7,lineX.getStartY() , 
								});
					heightUp  = heightUp - 20;
					flagHeight = 1;
				}
				else {
					lineY.setStartX(startX + 20);
					lineY.setStartY(startY + 40);
					lineY.setEndX(startX + 20);
					lineY.setEndY(heightDown);
					lineX.setStartX(startX + 20);
					lineX.setStartY(heightDown);
					lineX.setEndX(endX - 20);
					lineX.setEndY(heightDown);
					lineY2.setStartX(endX - 20);
					lineY2.setStartY(heightDown);
					lineY2.setEndX(endX - 20);
					lineY2.setEndY(endY + 40);
					branchText.setX((lineX.getEndX() + lineX.getStartX())/2);
					branchText.setY(lineX.getEndY() - 5 );	
					arrow.getPoints().addAll(new Double[]{        
							branchText.getX(), lineX.getStartY() + 4, 
							branchText.getX(), lineX.getStartY() - 4, 
							branchText.getX() - 7,lineX.getStartY() , 
							});
					heightDown = heightDown + 20;
					flagHeight = 0;
				}
				lineY.setStroke(javafx.scene.paint.Color.DEEPPINK);
				lineX.setStroke(javafx.scene.paint.Color.DEEPPINK);
				lineY2.setStroke(javafx.scene.paint.Color.DEEPPINK);
				g.getChildren().add(lineY);
				g.getChildren().add(lineX);
				g.getChildren().add(lineY2);
			}
			else {
				Line lineY = new Line();
				Line lineX = new Line();
				Line lineY2 = new Line();
				if(flagHeight ==0) {
					lineY.setStartX(startX);
					lineY.setStartY(startY - 40);
					lineY.setEndX(startX);
					lineY.setEndY(heightUp);
					lineX.setStartX(startX);
					lineX.setStartY(heightUp);
					lineX.setEndX(endX);
					lineX.setEndY(heightUp);
					lineY2.setStartX(endX);
					lineY2.setStartY(heightUp);
					lineY2.setEndX(endX);
					lineY2.setEndY(endY - 40);
					branchText.setX((lineX.getEndX() + lineX.getStartX())/2);
					branchText.setY(lineY2.getStartY() - 5 );
					if(numberOut > numberIn) {	
						arrow.getPoints().addAll(new Double[]{        
								branchText.getX(), lineX.getStartY() + 4, 
								branchText.getX(), lineX.getStartY() - 4, 
								branchText.getX() + 7,lineX.getStartY() , 
								});
					}
					else {
						arrow.getPoints().addAll(new Double[]{        
								branchText.getX(), lineX.getStartY() + 4, 
								branchText.getX(), lineX.getStartY() - 4, 
								branchText.getX() - 7,lineX.getStartY() , 
								});
					}
					
					heightUp  = heightUp - 20;
					flagHeight = 1;
				}
				else {
					lineY.setStartX(startX);
					lineY.setStartY(startY + 40);
					lineY.setEndX(startX);
					lineY.setEndY(heightDown);
					lineX.setStartX(startX);
					lineX.setStartY(heightDown);
					lineX.setEndX(endX);
					lineX.setEndY(heightDown);
					lineY2.setStartX(endX);
					lineY2.setStartY(heightDown);
					lineY2.setEndX(endX);
					lineY2.setEndY(endY + 40);
					branchText.setX((lineX.getEndX() + lineX.getStartX())/2);
					branchText.setY(lineX.getEndY() - 5 );
					if(numberOut > numberIn) {	
						arrow.getPoints().addAll(new Double[]{        
								branchText.getX(), lineX.getStartY() + 4, 
								branchText.getX(), lineX.getStartY() - 4, 
								branchText.getX() + 7,lineX.getStartY() , 
								});
					}
					else {
						arrow.getPoints().addAll(new Double[]{        
								branchText.getX(), lineX.getStartY() + 4, 
								branchText.getX(), lineX.getStartY() - 4, 
								branchText.getX() - 7,lineX.getStartY() , 
								});
					}
					heightDown = heightDown + 20;
					flagHeight = 0;
				}
				lineY.setStroke(javafx.scene.paint.Color.DEEPPINK);
				lineX.setStroke(javafx.scene.paint.Color.DEEPPINK);
				lineY2.setStroke(javafx.scene.paint.Color.DEEPPINK);
				g.getChildren().add(lineY);
				g.getChildren().add(lineX);
				g.getChildren().add(lineY2);
			}
			flagin = 0;
			flagout = 0;
			arrow.setFill( javafx.scene.paint.Color.DEEPPINK);
			g.getChildren().add(arrow);
			branchText.setText(String.valueOf(branch));
			g.getChildren().add(branchText);
			data.segmentsGains = new double[counerNode - 1][counerNode - 1];
					}
			(canvas).getChildren().add(g);
			input.clear();
			data.numOfNodes = counerNode;
			for (int i = 0; i < data.segmentsGains.length; i++) {
				for (int j = 0; j < data.segmentsGains[0].length; j++) {
					data.segmentsGains[i][j] = 0;
				}
			}
			for(int k = 0; k < allInputs.size(); k++) {
				data.segmentsGains[Integer.valueOf(allInputs.get(k).get("in")) -1][Integer.valueOf(allInputs.get(k).get("out")) - 1] =Double.valueOf(allInputs.get(k).get("branch"));
			}
			
	}
}
