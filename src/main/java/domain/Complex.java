package domain;

public class Complex {
    //quickSort, radixSort, mergeSort, shellSort.

    private int counterRadix[];
    private int recursiveCalls;
    private int[] tempArray; // Para almacenar el arreglo temporal de mergeSort

    // Arrays para seguimiento de quickSort y mergeSort
    private int[] lowValues = new int[50];  // Aumentado a 50 para capturar más valores
    private int[] highValues = new int[50]; // Aumentado a 50 para capturar más valores
    private int[] pivotValues = new int[30];
    private int trackIndex = 0;

    private int[] gapValues = new int[20];
    private int gapCount = 0;

    public int[] getGapValues() {
        return gapValues;
    }

    public int getGapCount() {
        return gapCount;
    }

    // Arrays para seguimiento de shellSort
    private int[][] gapSubArrays = new int[3][12]; // Para almacenar 3 subarreglos con 12 elementos cada uno

    // Getters para acceso a variables de seguimiento
    public int getRecursiveCalls() {
        return recursiveCalls;
    }

    public int[] getCounterRadix() {
        return counterRadix;
    }

    public int[] getLowValues() {
        return lowValues;
    }

    public int[] getHighValues() {
        return highValues;
    }

    public int[] getPivotValues() {
        return pivotValues;
    }

    public int[] getTempArray() {
        return tempArray;
    }

    public int getTrackIndex() {
        return trackIndex;
    }

    public int[][] getGapSubArrays() {
        return gapSubArrays;
    }

    // Reiniciar variables de seguimiento
    public void resetTracking() {
        recursiveCalls = 0;
        trackIndex = 0;
        lowValues = new int[50];  // Aumentado a 50 para capturar más valores
        highValues = new int[50]; // Aumentado a 50 para capturar más valores
        pivotValues = new int[30];
        gapSubArrays = new int[3][12];
        tempArray = null;
    }

    //Quick Sort
    public void quickSort(int arr[], int low, int high){
        // Guardar valores para seguimiento (solo los primeros 30)
        if (trackIndex < 30) {
            lowValues[trackIndex] = low;
            highValues[trackIndex] = high;
            pivotValues[trackIndex] = arr[(low+high)/2];
            trackIndex++;
        }

        int i=low;
        int j=high;
        int pivot=arr[(low+high)/2];
        do{
            while(arr[i]<pivot) i++;
            while(arr[j]>pivot) j--;
            if(i<=j){
                int aux=arr[i];
                arr[i]=arr[j];
                arr[j]=aux;
                i++;j--;
            }//if
        }while(i<=j);//do

        if(low<j){
            recursiveCalls++;
            quickSort(arr,low,j);
        }

        if(i<high){
            recursiveCalls++;
            quickSort(arr,i,high);
        }
    }

    //Radix Sort
    public void radixSort(int a[], int n){
        // Find the maximum number to know number of digits
        int m = util.Utility.maxArray(a); //va de 0 hasta el elemento maximo

        // Do counting sort for every digit. Note that instead
        // of passing digit number, exp is passed. exp is 10^i
        // where i is current digit number
        for (int exp = 1; m/exp > 0; exp *= 10){
            recursiveCalls++;
            countSort(a, n, exp);
        }
    }

    // A function to do counting sort of a[] according to
    // the digit represented by exp.
    private void countSort(int a[], int n, int exp){
        int output[] = new int[n]; // output array
        int i;
        int count[] = new int[10];

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++) {
            count[(a[i] / exp) % 10]++;
        }

        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            output[count[ (a[i]/exp)%10 ] - 1] = a[i];
            count[ (a[i]/exp)%10 ]--;
        }

        // Copy the output array to a[], so that a[] now
        // contains sorted numbers according to curent digit
        for (i = 0; i < n; i++)
            a[i] = output[i];

        counterRadix=count;
    }

    //Merge Sort - Versión corregida para seguimiento apropiado
    public void mergeSort(int a[], int low, int high){
        // Inicializar el arreglo temporal si es la primera llamada
        if (tempArray == null || tempArray.length != a.length) {
            tempArray = new int[a.length];
            trackIndex = 0; // Reiniciar índice de seguimiento
        }

        // Guardar los valores de low y high para seguimiento
        if (trackIndex < lowValues.length) { // Usamos .length para evitar desbordamiento
            lowValues[trackIndex] = low;
            highValues[trackIndex] = high;
            trackIndex++;
        }

        if(low < high){
            int center = (low+high)/2;

            mergeSort(a, low, center);
            mergeSort(a, center+1, high);
            merge(a, low, center+1, high);
            recursiveCalls++; // Incrementar las llamadas recursivas
        }
    }

    private void merge(int a[], int lowIndex, int highIndex, int endIndex){
        int leftEnd = highIndex - 1;
        int tmpPos = lowIndex;
        int numElements = endIndex - lowIndex + 1;

        // Copiar segmento actual a tempArray
        for (int i = lowIndex; i <= endIndex; i++) {
            tempArray[i] = a[i];
        }

        while (lowIndex <= leftEnd && highIndex <= endIndex) {
            if (tempArray[lowIndex] <= tempArray[highIndex]) {
                a[tmpPos++] = tempArray[lowIndex++];
            } else {
                a[tmpPos++] = tempArray[highIndex++];
            }
        }

        while (lowIndex <= leftEnd) {
            a[tmpPos++] = tempArray[lowIndex++];
        }

        while (highIndex <= endIndex) {
            a[tmpPos++] = tempArray[highIndex++];
        }
    }

    //Shell Sort
    public void shellSort(int a[]) {
        int n = a.length;
        int gapIndex = 0;

        // Pre-calcular todos los valores de gap posibles
        // Para n = 40, tendríamos [20, 10, 5, 2, 1]
        int[] gapValues = new int[20]; // Más que suficiente para cualquier tamaño práctico

        // Paso 1: Calcular y almacenar todos los valores de gap
        for (int gap = n/2; gap > 0; gap /= 2) {
            if (gapIndex < gapValues.length) {
                gapValues[gapIndex++] = gap;
                System.out.println("Registrando gap: " + gap); // Para depuración
            }
        }

        // Inicializar subarrays para cada gap
        int[][] subArrays = new int[3][50];

        // Paso 2: Ejecutar el algoritmo y almacenar los subarrays
        for (int g = 0; g < gapIndex; g++) {
            int gap = gapValues[g];

            // Para cada gap, registrar hasta 3 subarrays diferentes
            for (int sa = 0; sa < 3 && sa < gap; sa++) {
                // Registrar un subarray desde la posición sa, con paso = gap
                int idx = 0;
                for (int i = sa; i < n && idx < 50; i += gap) {
                    subArrays[sa][idx++] = a[i];
                }
            }

            // Ahora continuar con el ordenamiento para este gap
            for (int i = gap; i < n; i++) {
                int temp = a[i];
                int j;
                for (j = i; j >= gap && a[j - gap] > temp; j -= gap)
                    a[j] = a[j - gap];
                a[j] = temp;
            }
        }

        // Guardar los valores para acceso externo
        this.gapValues = gapValues;
        this.gapSubArrays = subArrays;
        this.gapCount = gapIndex;
    }

}