package controller;

import domain.Complex;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import util.FXUtility;

/**
 * Controlador para la visualización del algoritmo de ordenamiento Radix Sort
 */
public class RadixSortController {

    @FXML
    private TextField txtArraySize;
    @FXML
    private TextField txtLowBound;
    @FXML
    private TextField txtHighBound;
    @FXML
    private TableView<ArrayRow> noSortedView;
    @FXML
    private TableView<ArrayRow> counterView;
    @FXML
    private TableView<ArrayRow> sortedView;
    @FXML
    private Pane buttonPane;

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

        // Configurar scroll horizontal para las tablas
        enableHorizontalScrolling(noSortedView);
        enableHorizontalScrolling(counterView);
        enableHorizontalScrolling(sortedView);

        buttonPane.setDisable(true);
    }

    /**
     * Habilita el desplazamiento horizontal para una tabla
     */
    private void enableHorizontalScrolling(TableView<?> tableView) {
        // Asegurarse de que la tabla tenga habilitado el scroll horizontal
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Configura una tabla con columnas dinámicas según el tamaño del array
     */
    private void setupTableColumns(TableView<ArrayRow> tableView, int size) {
        tableView.getColumns().clear();

        for (int i = 0; i < size; i++) {
            final int colIndex = i;
            TableColumn<ArrayRow, Integer> column = new TableColumn<>(String.valueOf(i));

            column.setCellValueFactory(param -> {
                ArrayRow row = param.getValue();
                if (row != null) {
                    Integer value = row.getValue(colIndex);
                    return new SimpleIntegerProperty(value != null ? value : 0).asObject();
                }
                return null;
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

            complex.radixSort(arrayCopy, arrayCopy.length);

            displaySortedArray(arrayCopy);
            displayCounterArray(complex.getCounterRadix());

        } catch (Exception e) {
            FXUtility.showErrorAlert("Error", "Ocurrió un error al ordenar: " + e.getMessage());
        }
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

            displayOriginalArray();
            clearCounterView();
            clearSortedView();

            buttonPane.setDisable(false);

        } catch (Exception e) {
            FXUtility.showErrorAlert("Error", "Ocurrió un error al generar el array: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        txtArraySize.setText("");
        txtLowBound.setText("");
        txtHighBound.setText("");

        array = null;

        clearNoSortedView();
        clearCounterView();
        clearSortedView();

        buttonPane.setDisable(true);
    }

    private void displayOriginalArray() {
        setupTableColumns(noSortedView, array.length);

        Integer[] values = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            values[i] = array[i];
        }

        ArrayRow row = new ArrayRow(values);

        ObservableList<ArrayRow> data = FXCollections.observableArrayList();
        data.add(row);
        noSortedView.setItems(data);
    }

    private void displayCounterArray(int[] counterArray) {
        if (counterArray == null || counterArray.length == 0) {
            clearCounterView();
            return;
        }

        setupTableColumns(counterView, 10);

        Integer[] values = new Integer[10];
        for (int i = 0; i < Math.min(counterArray.length, 10); i++) {
            values[i] = counterArray[i];
        }

        ArrayRow row = new ArrayRow(values);

        ObservableList<ArrayRow> data = FXCollections.observableArrayList();
        data.add(row);
        counterView.setItems(data);
    }

    private void displaySortedArray(int[] sortedArray) {
        setupTableColumns(sortedView, sortedArray.length);

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

    private void clearCounterView() {
        if (counterView.getItems() != null) {
            counterView.getItems().clear();
        }
    }

    private void clearSortedView() {
        if (sortedView.getItems() != null) {
            sortedView.getItems().clear();
        }
    }
}