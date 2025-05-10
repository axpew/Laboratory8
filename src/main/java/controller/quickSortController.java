package controller;

import domain.Complex;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import util.FXUtility;

import java.util.ArrayList;
import java.util.List;

public class quickSortController {
    @javafx.fxml.FXML
    private Text txtMessage11;
    @javafx.fxml.FXML
    private TextField lowBoundTF;
    @javafx.fxml.FXML
    private TableView sortedView;
    @javafx.fxml.FXML
    private Text txtMessage1;
    @javafx.fxml.FXML
    private TextArea lowTA;
    @javafx.fxml.FXML
    private Pane buttonPane;
    @javafx.fxml.FXML
    private Text txtMessage131;
    @javafx.fxml.FXML
    private Text txtMessage132;
    @javafx.fxml.FXML
    private Text txtMessage133;
    @javafx.fxml.FXML
    private Text txtMessage111;
    @javafx.fxml.FXML
    private TextArea recursiveTA;
    @javafx.fxml.FXML
    private Text txtMessage1311;
    @javafx.fxml.FXML
    private Text txtMessage13;
    @javafx.fxml.FXML
    private Text txtMessage12;
    @javafx.fxml.FXML
    private Text txtMessage;
    @javafx.fxml.FXML
    private Pane mainPain;
    @javafx.fxml.FXML
    private Pane buttonPane111;
    @javafx.fxml.FXML
    private Pane buttonPane11;
    @javafx.fxml.FXML
    private TableView noSortedView;
    @javafx.fxml.FXML
    private TextArea pivotTA;
    @javafx.fxml.FXML
    private Button createButton;
    @javafx.fxml.FXML
    private TextField highBoundTF;
    @javafx.fxml.FXML
    private TextField arrayLengthTF;
    @javafx.fxml.FXML
    private TextArea highTA;

    private int[] arraytoSort; // almacena el array a ordenar
    private Complex complex;

    @javafx.fxml.FXML
    public void initialize() {
        this.complex = new Complex();
        buttonPane.setDisable(true);
    }

    @javafx.fxml.FXML
    public void startOnAction(ActionEvent actionEvent) {
        if (arraytoSort == null || arraytoSort.length == 0) {
            FXUtility.showErrorAlert("Error", "No hay elementos para ordenar");
            return;
        }

        // Crear una copia del array
        int[] arrayCopy = new int[arraytoSort.length];
        System.arraycopy(arraytoSort, 0, arrayCopy, 0, arraytoSort.length);

        // Resetear el seguimiento
        complex.resetTracking();

        // Ejecutar QuickSort
        complex.quickSort(arrayCopy, 0, arrayCopy.length - 1);

        // Mostrar arreglo ordenado
        displaySortedArray(arrayCopy);

        // Mostrar valores de low, high, pivot y llamadas recursivas
        displayTrackingInfo();
    }

    private void displayTrackingInfo() {
        // Obtener valores de seguimiento
        int[] lowValues = complex.getLowValues();
        int[] highValues = complex.getHighValues();
        int[] pivotValues = complex.getPivotValues();
        int recursiveCalls = complex.getRecursiveCalls();

        // Mostrar valores en TextAreas
        StringBuilder lowBuilder = new StringBuilder();
        StringBuilder highBuilder = new StringBuilder();
        StringBuilder pivotBuilder = new StringBuilder();

        // Mostrar solo los primeros 30 valores (según requisitos)
        int count = Math.min(30, lowValues.length);
        for (int i = 0; i < count; i++) {
            if (i > 0) {
                lowBuilder.append(" ");
                highBuilder.append(" ");
                pivotBuilder.append(" ");
            }
            if (lowValues[i] != 0 || i == 0) {
                lowBuilder.append(lowValues[i]);
            }
            if (highValues[i] != 0 || i == 0) {
                highBuilder.append(highValues[i]);
            }
            if (pivotValues[i] != 0 || i == 0) {
                pivotBuilder.append(pivotValues[i]);
            }
        }

        lowTA.setText(lowBuilder.toString());
        highTA.setText(highBuilder.toString());
        pivotTA.setText(pivotBuilder.toString());
        recursiveTA.setText(String.valueOf(recursiveCalls));
    }

    @javafx.fxml.FXML
    public void createOnAction(ActionEvent actionEvent) {
        if (util.Utility.isDigit(arrayLengthTF.getText()) && util.Utility.isDigit(lowBoundTF.getText()) && util.Utility.isDigit(highBoundTF.getText())) {
            int aLength = Integer.parseInt(arrayLengthTF.getText());
            int low = Integer.parseInt(lowBoundTF.getText());
            int high = Integer.parseInt(highBoundTF.getText());

            if (aLength <= 0 || aLength > 200) {
                FXUtility.showErrorAlert("Error", "El tamaño debe estar entre 1 y 200");
                return;
            }

            arraytoSort = new int[aLength];

            if (low > high) {
                FXUtility.showErrorAlert("Error", "El límite inferior no puede ser mayor al límite superior");
                return;
            }

            // Llenar el array con valores aleatorios
            for (int i = 0; i < aLength; i++) {
                arraytoSort[i] = util.Utility.random(low, high);
            }

            // Mostrar el array en la tabla
            displayOriginalArray();

            // Limpiar campo de array ordenado y datos de seguimiento
            clearSortedView();
            clearTrackingInfo();

            // Habilitar botones
            buttonPane.setDisable(false);
            createButton.setText("Create");
        }
    }

    private void displayOriginalArray() {
        noSortedView.getColumns().clear();

        // Convertir array a una lista de Strings para que se vea en fila
        List<String> row = new ArrayList<>();
        for (int i = 0; i < arraytoSort.length; i++) {
            row.add(String.valueOf(arraytoSort[i]));
        }

        // Una columna para cada valor del array
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        data.add(row);

        // Crear una columna por cada valor (índice del array)
        for (int i = 0; i < arraytoSort.length; i++) {
            final int colIndex = i;
            TableColumn<List<String>, String> column = new TableColumn<>("[" + i + "]");
            column.setCellValueFactory(indexValue ->
                    new SimpleStringProperty(indexValue.getValue().get(colIndex)));
            noSortedView.getColumns().add(column); // Añade las columnas al tableview
        }

        noSortedView.setItems(data); // Muestra la fila en el noSortedView
    }

    private void displaySortedArray(int[] sortedArray) {
        sortedView.getColumns().clear();

        // Convertir array a una lista de Strings para que se vea en fila
        List<String> row = new ArrayList<>();
        for (int i = 0; i < sortedArray.length; i++) {
            row.add(String.valueOf(sortedArray[i]));
        }

        // Una columna para cada valor del array
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        data.add(row);

        // Crear una columna por cada valor (índice del array)
        for (int i = 0; i < sortedArray.length; i++) {
            final int colIndex = i;
            TableColumn<List<String>, String> column = new TableColumn<>("[" + i + "]");
            column.setCellValueFactory(indexValue ->
                    new SimpleStringProperty(indexValue.getValue().get(colIndex)));
            sortedView.getColumns().add(column); // Añade las columnas al tableview
        }

        sortedView.setItems(data); // Muestra la fila en el sortedView
    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        // Limpiar campos de texto
        arrayLengthTF.clear();
        lowBoundTF.clear();
        highBoundTF.clear();

        // Limpiar tablas
        clearNoSortedView();
        clearSortedView();

        // Limpiar datos de seguimiento
        clearTrackingInfo();

        // Deshabilitar botones
        buttonPane.setDisable(true);
    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        // Generar tamaño aleatorio entre 1 y 200
        int aLength = util.Utility.random(200);
        int low = util.Utility.random(200);
        int high = util.Utility.random(low, 9999);

        // Establecer valores en campos de texto
        arrayLengthTF.setText(String.valueOf(aLength));
        lowBoundTF.setText(String.valueOf(low));
        highBoundTF.setText(String.valueOf(high));

        // Crear y llenar array
        arraytoSort = new int[aLength];
        for (int i = 0; i < aLength; i++) {
            arraytoSort[i] = util.Utility.random(low, high);
        }

        // Mostrar array
        displayOriginalArray();

        // Limpiar tabla de ordenados y datos de seguimiento
        clearSortedView();
        clearTrackingInfo();

        // Habilitar botones
        buttonPane.setDisable(false);
    }

    private void clearNoSortedView() {
        if (noSortedView.getItems() != null) {
            noSortedView.getItems().clear();
        }
        noSortedView.getColumns().clear();
    }

    private void clearSortedView() {
        if (sortedView.getItems() != null) {
            sortedView.getItems().clear();
        }
        sortedView.getColumns().clear();
    }

    private void clearTrackingInfo() {
        lowTA.clear();
        highTA.clear();
        pivotTA.clear();
        recursiveTA.clear();
    }

    @javafx.fxml.FXML
    public void onMousePressed(Event event) {
        createButton.setText("WAITING!!");
    }
}