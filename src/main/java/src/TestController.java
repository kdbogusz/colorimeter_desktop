package src;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.concurrent.TimeUnit;

public class TestController {
    @FXML
    public Spinner<Integer> timeSpinner;

    private AppController appController;

    private ObservableList<Color> colorQueue;

    @FXML
    private TextField rText;

    @FXML
    private TextField gText;

    @FXML
    private TextField bText;

    @FXML
    private Canvas canvas;

    @FXML
    private Label scorePlayer;

    private Serial main;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private void initialize() {
        colorQueue = FXCollections.observableArrayList();
        setCanvasColor(Color.WHITE);
//        initializeSerial();
    }

    private void initializeSerial() {
        Serial main = new Serial();
        main.initialize();
    }

    @FXML
    private void handleRgbAction(ActionEvent event) {
        try {
            Color color = Color.rgb(Integer.parseInt(rText.getText()), Integer.parseInt(gText.getText()), Integer.parseInt(bText.getText()));
            colorQueue.add(color);
            scorePlayer.setText(String.valueOf(colorQueue.size()));
        } catch (IllegalArgumentException ignored) {}
    }

    @FXML
    public void handleRandomAction(ActionEvent event) {
        for(int i = 0; i < timeSpinner.getValue(); i++){
            colorQueue.add(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }
        scorePlayer.setText(String.valueOf(colorQueue.size()));
    }

    private void sendMessage(Color color) {
        Platform.runLater(() -> {
            setCanvasColor(color);
            scorePlayer.setText(String.valueOf(colorQueue.size()));
        });
    }

    @FXML
    private void handleExecuteAction() {
        new Thread(() -> {
            while (!colorQueue.isEmpty()) {
                Color color = colorQueue.remove(0);
                sendMessage(color);
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        new Thread() {
//            public void run() {
//                while (true){
//                    while(main.waiting){
//                        try{
//                            wait();
//                        } catch(InterruptedException ignored){}
//                    }
//
//                    try {
//                        main.output.write("Hello".getBytes());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    main.waiting = true;
//                }
//            }
//        }.start();
    }

    private void setCanvasColor (Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
