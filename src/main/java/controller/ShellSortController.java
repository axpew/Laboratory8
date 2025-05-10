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
import javafx.scene.text.Text;
import javafx.util.Callback;
import util.FXUtility;

/**
 * Controlador para la visualización del algoritmo de ordenamiento ShellSort
 */
public class ShellSortController {

    @FXML
    private TextField txtArraySize;
    @FXML
    private TextField txtLowBound;
    @FXML
    private TextField txtHighBound;
    @FXML
    private TableView<ArrayRow> noSortedView;
    @FXML
    private TableView<ArrayRow> sortedView;
    @FXML
    private Pane buttonPane;
    @FXML
    private TextArea gapTA;
    @FXML
    private TextArea subarray1TA;
    @FXML
    private TextArea subarray2TA;
    @FXML
    private TextArea subarray3TA;

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
        configureTableView(sortedView);

        // Inicializar TextAreas
        gapTA.setText("");
        subarray1TA.setText("");
        subarray2TA.setText("");
        subarray3TA.setText("");

        buttonPane.setDisable(true);
    }

    /**
     * Configura la tabla para mostrar barras de desplazamiento
     */
    private void configureTableView(TableView<?> tableView) {
        // Configuración para mostrar scroll horizontal y vertical
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Configurar la altura de la tabla
        tableView.setPrefHeight(100);
        tableView.setMinHeight(60);
        tableView.setMaxHeight(200);

        // Forzar la visualización de las barras de desplazamiento
        tableView.setStyle("-fx-background-color: -fx-box-border, -fx-control-inner-background; -fx-background-insets: 0, 1; -fx-padding: 1;"
                + " -fx-show-vbar: always; -fx-show-hbar: always;");

        // Ajustar el ScrollPane dentro del TableView para siempre mostrar barras
        javafx.scene.control.ScrollPane sp = (javafx.scene.control.ScrollPane) tableView.lookup("VirtualFlow");
        if (sp != null) {
            sp.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS);
            sp.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS);
        }
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

            // Resetear contadores
            complex.resetTracking();

            // Ejecutar ShellSort
            complex.shellSort(arrayCopy);

            // Mostrar el arreglo ordenado
            displaySortedArray(arrayCopy);

            // Mostrar valores de gap y subarreglos
            updateTrackingDisplay();

        } catch (Exception e) {
            FXUtility.showErrorAlert("Error", "Ocurrió un error al ordenar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateTrackingDisplay() {
        // Mostrar valores de gap
        int[] gapValues = complex.getGapValues();
        int gapCount = complex.getGapCount();

        StringBuilder gapBuilder = new StringBuilder();

        System.out.println("Mostrando " + gapCount + " valores de gap"); // Para depuración

        for (int i = 0; i < gapCount; i++) {
            gapBuilder.append(gapValues[i]);
            if (i < gapCount - 1) {
                gapBuilder.append(" ");
            }
        }

        gapTA.setText(gapBuilder.toString());

        // Mostrar subarreglos
        int[][] subArrays = complex.getGapSubArrays();

        // Subarreglo 1
        StringBuilder sub1Builder = new StringBuilder();
        for (int i = 0; i < subArrays[0].length && subArrays[0][i] != 0; i++) {
            if (i > 0) sub1Builder.append(" ");
            sub1Builder.append(subArrays[0][i]);
        }
        subarray1TA.setText(sub1Builder.toString());

        // Subarreglo 2
        StringBuilder sub2Builder = new StringBuilder();
        for (int i = 0; i < subArrays[1].length && subArrays[1][i] != 0; i++) {
            if (i > 0) sub2Builder.append(" ");
            sub2Builder.append(subArrays[1][i]);
        }
        subarray2TA.setText(sub2Builder.toString());

        // Subarreglo 3
        StringBuilder sub3Builder = new StringBuilder();
        for (int i = 0; i < subArrays[2].length && subArrays[2][i] != 0; i++) {
            if (i > 0) sub3Builder.append(" ");
            sub3Builder.append(subArrays[2][i]);
        }
        subarray3TA.setText(sub3Builder.toString());

        // Imprimir para diagnóstico
        System.out.println("Gap values: " + gapBuilder.toString());
        System.out.println("Subarray 1: " + sub1Builder.toString());
        System.out.println("Subarray 2: " + sub2Builder.toString());
        System.out.println("Subarray 3: " + sub3Builder.toString());
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
            setupTableColumns(sortedView, size);

            displayOriginalArray();
            clearSortedView();

            // Limpiar TextAreas
            gapTA.setText("");
            subarray1TA.setText("");
            subarray2TA.setText("");
            subarray3TA.setText("");

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
        clearSortedView();

        // Limpiar TextAreas
        gapTA.setText("");
        subarray1TA.setText("");
        subarray2TA.setText("");
        subarray3TA.setText("");

        buttonPane.setDisable(true);
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

    private void clearSortedView() {
        if (sortedView.getItems() != null) {
            sortedView.getItems().clear();
        }
    }
}