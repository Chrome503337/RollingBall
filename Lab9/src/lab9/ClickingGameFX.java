package lab9;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClickingGameFX extends Application {

	private final int SCENE_HEIGHT = 800;
	private final int SCENE_WIDTH = 750;
	private final int BUTTON_WIDTH = 60;
	private final int BALL_DELAY = 300;
	private final int XVELOCITY = 3;
	
	private Pane root;
	private Circle ball;
	private Button reset;
	private Button pause;
	private Text gameovertxt;
	private boolean gameover = false;
	
	
	private Text hitmisstxt;
	private int hit = 0;
	private int miss = 0;
	
	
	private BallAnimation ballanimation;
	private int xVelocity = XVELOCITY;
	private int tempxVelocity = 0;
	private int startCountDown = BALL_DELAY;
	private double x;
	private double y;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		root = new Pane();
		
		//Black background
		Rectangle background = new Rectangle(0,0,750,750);
		background.setFill(Color.BLACK);
		
		//Hit-miss text on the top right of the screen
		hitmisstxt = new Text("hits: " + hit + " missed: " + miss);
		hitmisstxt.setX(40);
		hitmisstxt.setY(25);
		hitmisstxt.setFill(Color.WHITE);
		hitmisstxt.setFont(Font.font("Verdana", 16));
		
		//ball moving around
		ball = new Circle(-50,375,50);
		ball.setFill(Color.WHITE);
		
		
		//Pause and reset button
		PauseEventHandler peh = new PauseEventHandler();
		
		pause = new Button("Pause");
		pause.setMinWidth(BUTTON_WIDTH);
		pause.setOnAction(peh);
		
		ResetEventHandler reh = new ResetEventHandler();
		
		reset = new Button("Reset");
		reset.setMinWidth(BUTTON_WIDTH);
		reset.setOnAction(reh);
		

		
		//circle click handler
		CircleClickEventHandler cceh = new CircleClickEventHandler();
		ball.setOnMouseClicked(cceh);
		
		//ball animation
		ballanimation = new BallAnimation();
		ballanimation.start();
		
		//Count down timer animation
		CountDownTimer timer = new CountDownTimer();
		timer.start();
		
		
		HBox resetpause = new HBox(5,pause,reset);
		resetpause.setPadding(new Insets(10));
		resetpause.setAlignment(Pos.CENTER_RIGHT);
		
		VBox mainPane = new VBox(10,root,resetpause);
		
		
		
		root.getChildren().addAll(background,ball,hitmisstxt);
		Scene scene = new Scene(mainPane,SCENE_WIDTH,SCENE_HEIGHT);
		primaryStage.setTitle("Clicking Game FX");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	private class BallAnimation extends AnimationTimer{

		@Override
		public void handle(long arg0) {
			
			if(startCountDown < 0) {
				x = ball.getCenterX();
				
				
				if(x + xVelocity > 800) {
					resetBall();
					
					miss++;
					hitmisstxt.setText("hits: " + hit + " missed: " + miss);
					
				}
				x += xVelocity;
				ball.setCenterX(x);
				
				if(miss == 5) {
					gameovertxt = new Text("Game Over");
					gameovertxt.setX(260);
					gameovertxt.setY(375);
					gameovertxt.setFont(Font.font("Verdana", 36));
					gameovertxt.setFill(Color.WHITE);
					gameover = true;
					
					root.getChildren().remove(ball);
					
					root.getChildren().add(gameovertxt);
					xVelocity = 0;
					miss = 0;
					
					
				}
			}
			
		}
		
	}
	
	
	private class CountDownTimer extends AnimationTimer{

		@Override
		public void handle(long arg0) {
			countDown();
		}
		
	}
	
	private class CircleClickEventHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent cursor) {
			
			resetBall();
			
			xVelocity++;
			hit++;
			
			hitmisstxt.setText("hits: " + hit + " missed: " + miss);
			
		}
		
	}
	
	private class PauseEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {

			if(xVelocity > 0) {
				tempxVelocity = xVelocity;
				xVelocity = 0;
				reset.setMouseTransparent(true);
				ball.setMouseTransparent(true);
				
			}
			else {
				xVelocity = tempxVelocity;
				ball.setMouseTransparent(false);
				reset.setMouseTransparent(false);
			}
			
		}
		
	}
	
	private class ResetEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			
			startCountDown = BALL_DELAY;
			
			if(gameover) {
				root.getChildren().add(ball);
				root.getChildren().remove(gameovertxt);
				gameover = false;
			}
			
			startingPoint();
			
		}
		
	}
	/*
	 * generates a random integer value between 75-700
	 */
	private int randomYValue() {
		Random r = new Random();
		return 75 + r.nextInt(625);
	}
	
	/*
	 * resets all the data to the same as the beginning
	 */
	private void startingPoint() {
		
		startCountDown = BALL_DELAY;
		xVelocity = XVELOCITY;
		hit = 0;
		miss = 0;
		
		ball.setCenterX(-50);
		y = randomYValue();
		ball.setCenterY(y);
		
		hitmisstxt.setText("hits: " + hit + " missed: " + miss);
		
	}
	
	/*
	 * subtracts the startCountDown variable by an integer between 1-5
	 */
	private void countDown() {
		Random r = new Random();
		startCountDown -= r.nextInt(5);
	}
	
	private void resetBall() {
		x = -50;
		ball.setCenterX(-50);
		y = randomYValue();
		ball.setCenterY(y);
	}
	
	
}
