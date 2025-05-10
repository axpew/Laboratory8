package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import ucr.laboratory8.HelloApplication;

import java.io.IOException;


public class HelloController {

    @FXML
    private Text txtMessage;
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;


    private void load(String form) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(form));
        this.bp.setCenter(fxmlLoader.load());
    }

    @FXML
    public void Home(ActionEvent actionEvent) {
        this.bp.setCenter(ap);
        this.txtMessage.setText("Laboratory No. 8");
    }

    @FXML
    public void bubbleSortView(ActionEvent actionEvent) {

        try {
            load("bubbleSort-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void selectionSortView(ActionEvent actionEvent) {
        try {
            load("selectionSort-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void impBubbleSortView(ActionEvent actionEvent) {

        try {
            load("impBubbleSort-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void countingSortView(ActionEvent actionEvent) {


        try {
            load("countingSort-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void shellSortView(ActionEvent actionEvent) {
        try {
            load("shellSort-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mergeSortView(ActionEvent actionEvent) {
        try {
            load("mergeSort-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void radixSortView(ActionEvent actionEvent) {

        try {
            load("radixSort-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void quickSortView(ActionEvent actionEvent) {
        try {
            load("quickSort-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void Exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Deprecated
    public void loadingOnMousePressed(Event event)  {
        this.txtMessage.setText("Estamos cargando tu vista...");
    }


}