package controller;

import domain.Elementary;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import util.FXUtility;

import java.util.ArrayList;
import java.util.List;

public class selectionSortController {


    @javafx.fxml.FXML
    private Text txtMessage11;
    @javafx.fxml.FXML
    private Text txtMessage1331;
    @javafx.fxml.FXML
    private TextField lowBoundTF;
    @javafx.fxml.FXML
    private TableView sortedView;
    @javafx.fxml.FXML
    private Text txtMessage1;
    @javafx.fxml.FXML
    private TextArea changesTA;
    @javafx.fxml.FXML
    private Text txtMessage1311;
    @javafx.fxml.FXML
    private Text txtMessage13;
    @javafx.fxml.FXML
    private Text txtMessage12;
    @javafx.fxml.FXML
    private Pane mainPain;
    @javafx.fxml.FXML
    private Button createButton;
    @javafx.fxml.FXML
    private TextField highBoundTF;
    @javafx.fxml.FXML
    private TextField arrayLengthTF;
    @javafx.fxml.FXML
    private Text txtMessage131;
    @javafx.fxml.FXML
    private Text txtMessage132;
    @javafx.fxml.FXML
    private TextArea minIndexTA;
    @javafx.fxml.FXML
    private Text txtMessage133;
    @javafx.fxml.FXML
    private Text txtMessage111;
    @javafx.fxml.FXML
    private Text txtMessage;
    @javafx.fxml.FXML
    private TextArea iterationsTA;
    @javafx.fxml.FXML
    private TextArea minValueTA;
    @javafx.fxml.FXML
    private Pane buttonPane111;
    @javafx.fxml.FXML
    private Pane buttonPane11;
    @javafx.fxml.FXML
    private TableView noSortedView;
    @javafx.fxml.FXML
    private Pane PaneInferior;
    @javafx.fxml.FXML
    private Button startButton;
    @javafx.fxml.FXML
    private Button randomButton;

    private int[] arraytoSort; //almacena el array a ordenar


    @javafx.fxml.FXML
    public void startOnAction(ActionEvent actionEvent) {

        Elementary e = new Elementary();

        //Ordeno el array
        e.selectionSort(arraytoSort);

        //Y lo muestro
        sortedView.getColumns().clear();

        //Convertir array a una lista de Strings para que se vea en fila
        List<String> row = new ArrayList<>();
        for (int i = 0; i < arraytoSort.length; i++)
            row.add(String.valueOf(arraytoSort[i]));

        //Una columna para cada valor del array
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        data.add(row);

        //Crear una columna por cada valor (índice del array)
        for (int i = 0; i < arraytoSort.length; i++) {
            final int colIndex = i;
            TableColumn<List<String>, String> column = new TableColumn<>("[" + i + "]");
            column.setCellValueFactory(indexValue -> new SimpleStringProperty(indexValue.getValue().get(colIndex)));
            sortedView.getColumns().add(column); //Añade las columnas al tableview
        }//end for

        sortedView.setItems(data); //Muestra la fila en el noSortedView

        //Mostrar iteraciones y cambios
        iterationsTA.setText(String.valueOf(e.getTotalIteractions()));
        changesTA.setText(String.valueOf(e.getTotalChanges()));

        //Mostrar minIndex y minValue
        String minValue = printArray(e.minValues);
        String minIndex = printArray(e.minIndexes);

        minValueTA.setText(minValue);
        minIndexTA.setText(minIndex);


        lowBoundTF.clear();
        highBoundTF.clear();
        arrayLengthTF.clear();

        startButton.setText("Start");


    }

    @javafx.fxml.FXML
    public void createOnAction(ActionEvent actionEvent) {


        //Validaciones antes de convertir a entero
        if(util.Utility.isDigit(arrayLengthTF.getText()) && util.Utility.isDigit(lowBoundTF.getText()) && util.Utility.isDigit(highBoundTF.getText())) {
            int aLenght = Integer.parseInt(arrayLengthTF.getText());
            int low = Integer.parseInt(lowBoundTF.getText());
            int high = Integer.parseInt(highBoundTF.getText());


            arraytoSort = new int[aLenght];

            //Llenar el array con valores entre low y high (inclusive high)
            if (low > high) {
                FXUtility.showAlert("ERROR EN LOW", "Low no puede ser mayor a High");
                throw new IllegalArgumentException("Low no puede ser mayor a High");
            }else {

                for (int i = 0; i < aLenght; i++)
                    arraytoSort[i] = util.Utility.random(low, high);


                //Limpia las columnas por si acaso
                noSortedView.getColumns().clear();

                //Convertir array a una lista de Strings para que se vea en fila
                List<String> row = new ArrayList<>();
                for (int i = 0; i < arraytoSort.length; i++)
                    row.add(String.valueOf(arraytoSort[i]));

                //Una columna para cada valor del array
                ObservableList<List<String>> data = FXCollections.observableArrayList();
                data.add(row);

                //Crear una columna por cada valor (índice del array)
                for (int i = 0; i < arraytoSort.length; i++) {
                    final int colIndex = i;
                    TableColumn<List<String>, String> column = new TableColumn<>("[" + i + "]");
                    column.setCellValueFactory(indexValue -> new SimpleStringProperty(indexValue.getValue().get(colIndex)));
                    noSortedView.getColumns().add(column); //Añade las columnas al tableview
                }//end for

                noSortedView.setItems(data); //Muestra la fila en el noSortedView

                createButton.setText("Create");
                PaneInferior.setDisable(false);
                lowBoundTF.clear();
                highBoundTF.clear();
                arrayLengthTF.clear();
                cleanViews(sortedView);
                iterationsTA.setText("");
                changesTA.setText("");
                minValueTA.clear();
                minIndexTA.clear();

            }
        }//end if

    }//create

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {

        lowBoundTF.clear();
        highBoundTF.clear();
        arrayLengthTF.clear();

        changesTA.clear();
        iterationsTA.clear();
        minIndexTA.clear();
        minValueTA.clear();

        noSortedView.getItems().clear();
        sortedView.getItems().clear();

        noSortedView.getColumns().clear();
        sortedView.getColumns().clear();

        PaneInferior.setDisable(true);
    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) {

        //Limpia todo
        lowBoundTF.clear();
        highBoundTF.clear();
        arrayLengthTF.clear();
        changesTA.clear();
        iterationsTA.clear();
        noSortedView.getItems().clear();
        sortedView.getItems().clear();
        noSortedView.getColumns().clear();
        sortedView.getColumns().clear();
        minIndexTA.clear();
        minValueTA.clear();


        int aLenght = util.Utility.random(200+1);
        int low = util.Utility.random(200);
        int high = util.Utility.random(low, 9999);

        int arrayRandomized[] = new int[aLenght];

        //Llenar el array con valores entre low y high (inclusive high)
        for (int i = 0; i < aLenght; i++)
            arrayRandomized[i] = util.Utility.random(low, high);


        arraytoSort = util.Utility.copyArray(arrayRandomized, aLenght); //Importante paso!! actualiza la variable local

        //Limpia las columnas por si acaso
        noSortedView.getColumns().clear();

        //Convertir array a una lista de Strings para que se vea en fila
        List<String> row = new ArrayList<>();
        for (int i = 0; i < arrayRandomized.length; i++)
            row.add(String.valueOf(arrayRandomized[i]));

        //Una columna para cada valor del array
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        data.add(row);

        //Crear una columna por cada valor (índice del array)
        for (int i = 0; i < arrayRandomized.length; i++) {
            final int colIndex = i;
            TableColumn<List<String>, String> column = new TableColumn<>("[" + i + "]");
            column.setCellValueFactory(indexValue -> new SimpleStringProperty(indexValue.getValue().get(colIndex)));
            noSortedView.getColumns().add(column); //Añade las columnas al tableview
        }//end for

        noSortedView.setItems(data); //Muestra la fila en el noSortedView
        randomButton.setText("Randomize");

    }

    @javafx.fxml.FXML
    public void onMousePressed(Event event) {
        createButton.setText("WAITING!!");
    }

    @javafx.fxml.FXML
    public void startPress(Event event) {
        startButton.setText("WAITING!!");
    }

    @javafx.fxml.FXML
    public void randomPress(Event event) {
        randomButton.setText("WAITING!!");
    }

    private void cleanViews(TableView t){
        t.getItems().clear();
        t.getColumns().clear();
    }

    private String printArray(ArrayList al){

        String result = "";

        for (int i = 0; i < al.size(); i++)
            result +=  al.get(i) + " ";


        return result;
    }

}
