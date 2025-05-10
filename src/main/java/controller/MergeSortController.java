package controller;

import domain.Complex;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import util.FXUtility;

import java.util.ArrayList;

/**
 * Controlador para la visualización del algoritmo de ordenamiento MergeSort
 */
public class MergeSortController {

    @FXML
    private TextField txtArraySize;
    @FXML
    private TextField txtLowBound;
    @FXML
    private TextField txtHighBound;
    @FXML
    private TableView<ArrayRow> noSortedView;
    @FXML
    private TableView<ArrayRow> tempArrayView;
    @FXML
    private TableView<ArrayRow> sortedView;
    @FXML
    private Pane buttonPane;
    @FXML
    private TextArea lowTA;
    @FXML
    private TextArea highTA;
    @FXML
    private TextArea recursiveTA;

    private int[] array;
    private Complex complex;

    public static class ArrayRow {
        private Integer[] values;

        public ArrayRow(Integer[] values) {
            this.values = values;
        }

        public Integer getValue(int index) {
            if (index >= 0 && index < values.length) {
                return values[index];
            }
            return null;
        }
    }

    @FXML
    private void initialize() {
        this.complex = new Complex();

        txtArraySize.setText("");
        txtLowBound.setText("");
        txtHighBound.setText("");

        // Configurar barras de desplazamiento para las tablas
        configureTableView(noSortedView);
        configureTableView(tempArrayView);
        configureTableView(sortedView);

        // Inicializar TextAreas
        lowTA.setText("");
        highTA.setText("");
        recursiveTA.setText("0");

        buttonPane.setDisable(true);
    }

    /**
     * Configura la tabla para mostrar barras de desplazamiento
     */
    private void configureTableView(TableView<?> tableView) {
        // Configuración para mostrar scroll horizontal y vertical
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Establecer altura suficiente para que aparezca la barra de desplazamiento vertical
        tableView.setPrefHeight(100);
        tableView.setMinHeight(60);
        tableView.setMaxHeight(200);
    }

    /**
     * Configura una tabla con columnas dinámicas según el tamaño del array
     */
    private void setupTableColumns(TableView<ArrayRow> tableView, int size) {
        tableView.getColumns().clear();

        for (int i = 0; i < size; i++) {
            final int colIndex = i;
            TableColumn<ArrayRow, Integer> column = new TableColumn<>(String.valueOf(i));

            column.setCellValueFactory(cellData -> {
                ArrayRow row = cellData.getValue();
                if (row != null) {
                    Integer value = row.getValue(colIndex);
                    return new SimpleIntegerProperty(value != null ? value : 0).asObject();
                }
                return null;
            });

            // Configuración personalizada de la celda para mostrar correctamente los valores
            column.setCellFactory(new Callback<TableColumn<ArrayRow, Integer>, TableCell<ArrayRow, Integer>>() {
                @Override
                public TableCell<ArrayRow, Integer> call(TableColumn<ArrayRow, Integer> param) {
                    return new TableCell<ArrayRow, Integer>() {
                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                setText(item.toString());
                            }
                        }
                    };
                }
            });

            column.setPrefWidth(40);
            tableView.getColumns().add(column);
        }
    }

    @FXML
    private void start(ActionEvent event) {
        try {
            if (array == null || array.length == 0) {
                FXUtility.showErrorAlert("Error", "No hay elementos para ordenar");
                return;
            }

            int[] arrayCopy = new int[array.length];
            System.arraycopy(array, 0, arrayCopy, 0, array.length);

            // Resetear contadores y variables de seguimiento
            complex.resetTracking();

            // Ejecutar MergeSort
            complex.mergeSort(arrayCopy, 0, arrayCopy.length - 1);

            // Mostrar el arreglo ordenado
            displaySortedArray(arrayCopy);

            // Mostrar el arreglo temporal
            displayTempArray(complex.getTempArray());

            // Mostrar valores de low y high y llamadas recursivas
            updateTrackingDisplay();

        } catch (Exception e) {
            FXUtility.showErrorAlert("Error", "Ocurrió un error al ordenar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateTrackingDisplay() {
        int[] lows = complex.getLowValues();
        int[] highs = complex.getHighValues();
        int trackCount = complex.getTrackIndex();

        // Construir strings para mostrar valores de low y high
        StringBuilder lowsBuilder = new StringBuilder();
        StringBuilder highsBuilder = new StringBuilder();

        System.out.println("Total de valores rastreados: " + trackCount);

        // Mostrar todos los valores hasta el último registrado
        for (int i = 0; i < trackCount; i++) {
            lowsBuilder.append(lows[i]);
            if (i < trackCount - 1) {
                lowsBuilder.append(" ");
            }
        }

        for (int i = 0; i < trackCount; i++) {
            highsBuilder.append(highs[i]);
            if (i < trackCount - 1) {
                highsBuilder.append(" ");
            }
        }

        String lowValues = lowsBuilder.toString();
        String highValues = highsBuilder.toString();

        // Impresión para diagnóstico
        System.out.println("Low values (" + trackCount + "): " + lowValues);
        System.out.println("High values (" + trackCount + "): " + highValues);

        // Establecer los valores en los TextArea
        lowTA.setText(lowValues);
        highTA.setText(highValues);
        recursiveTA.setText(String.valueOf(complex.getRecursiveCalls()));
    }

    @FXML
    private void randomize(ActionEvent event) {
        try {
            int size = 10;
            int lowBound = 1;
            int highBound = 99;

            if (txtArraySize.getText() != null && !txtArraySize.getText().isEmpty()) {
                try {
                    size = Integer.parseInt(txtArraySize.getText());
                    if (size <= 0 || size > 200) {
                        FXUtility.showErrorAlert("Error", "El tamaño debe estar entre 1 y 200");
                        return;
                    }
                } catch (NumberFormatException e) {
                    FXUtility.showErrorAlert("Error", "Ingrese un número válido para el tamaño");
                    return;
                }
            }

            if (txtLowBound.getText() != null && !txtLowBound.getText().isEmpty()) {
                try {
                    lowBound = Integer.parseInt(txtLowBound.getText());
                } catch (NumberFormatException e) {
                    FXUtility.showErrorAlert("Error", "Ingrese un número válido para el límite inferior");
                    return;
                }
            }

            if (txtHighBound.getText() != null && !txtHighBound.getText().isEmpty()) {
                try {
                    highBound = Integer.parseInt(txtHighBound.getText());
                    if (highBound <= lowBound) {
                        FXUtility.showErrorAlert("Error", "El límite superior debe ser mayor que el límite inferior");
                        return;
                    }
                } catch (NumberFormatException e) {
                    FXUtility.showErrorAlert("Error", "Ingrese un número válido para el límite superior");
                    return;
                }
            }

            array = new int[size];
            for (int i = 0; i < size; i++) {
                array[i] = lowBound + (int)(Math.random() * (highBound - lowBound + 1));
            }

            // Resetear contadores
            complex.resetTracking();

            // Configurar y mostrar las tablas
            setupTableColumns(noSortedView, size);
            setupTableColumns(tempArrayView, size);
            setupTableColumns(sortedView, size);

            // Crear múltiples filas para simular scroll vertical
            displayOriginalArray();
            clearTempArrayView();
            clearSortedView();

            // Limpiar TextAreas
            lowTA.setText("");
            highTA.setText("");
            recursiveTA.setText("0");

            buttonPane.setDisable(false);

        } catch (Exception e) {
            FXUtility.showErrorAlert("Error", "Ocurrió un error al generar el array: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void displayOriginalArray() {
        Integer[] values = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            values[i] = array[i];
        }

        ArrayRow row = new ArrayRow(values);

        ObservableList<ArrayRow> data = FXCollections.observableArrayList();
        data.add(row);
        noSortedView.setItems(data);
    }

    @FXML
    private void clear(ActionEvent event) {
        txtArraySize.setText("");
        txtLowBound.setText("");
        txtHighBound.setText("");

        array = null;

        clearNoSortedView();
        clearTempArrayView();
        clearSortedView();

        lowTA.setText("");
        highTA.setText("");
        recursiveTA.setText("0");

        buttonPane.setDisable(true);
    }

    private void displayTempArray(int[] tempArray) {
        if (tempArray == null || tempArray.length == 0) {
            clearTempArrayView();
            return;
        }

        Integer[] values = new Integer[tempArray.length];
        for (int i = 0; i < tempArray.length; i++) {
            values[i] = tempArray[i];
        }

        ArrayRow row = new ArrayRow(values);

        ObservableList<ArrayRow> data = FXCollections.observableArrayList();
        data.add(row);
        tempArrayView.setItems(data);
    }

    private void displaySortedArray(int[] sortedArray) {
        Integer[] values = new Integer[sortedArray.length];
        for (int i = 0; i < sortedArray.length; i++) {
            values[i] = sortedArray[i];
        }

        ArrayRow row = new ArrayRow(values);

        ObservableList<ArrayRow> data = FXCollections.observableArrayList();
        data.add(row);
        sortedView.setItems(data);
    }

    private void clearNoSortedView() {
        if (noSortedView.getItems() != null) {
            noSortedView.getItems().clear();
        }
    }

    private void clearTempArrayView() {
        if (tempArrayView.getItems() != null) {
            tempArrayView.getItems().clear();
        }
    }

    private void clearSortedView() {
        if (sortedView.getItems() != null) {
            sortedView.getItems().clear();
        }
    }
}