package domain;

import org.junit.jupiter.api.Test;

class SortingTest {

    // ------------------------------ ELEMENTARY TEST --------------------------------------------------------

    @Test
    void elementaryTest() {

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("------------------------------ ELEMENTARY TEST ---------------------------------");
        //bubbleSort e improvedBubbleSort con el mismo arreglo
        int[] a = util.Utility.getIntegerArray(10000);
        int[] b = util.Utility.copyArray(a, a.length); //no utilice Arrays.copyOf(a, a.length)

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(elementarySorting("bubbleSort", a, 50));
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(elementarySorting("improvedBubbleSort", b, 100));
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(
                elementarySorting("selectionSort", util.Utility.getIntegerArray(10000), 150));
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(
                elementarySorting("countingSort", util.Utility.getIntegerArray(10000), 200));
        System.out.println("--------------------------------------------------------------------------------");


    }//end test Elementary


    //Muestra el funcionamiento de los algoritmos elementales de ordenamiento
    private String elementarySorting(String algorithm, int[] array, int n) {

        String result = "";
        result += algorithm + " - Test\n";
        result += "Algorithm: " + algorithm + "\n";
        result += "Original Array: ";

        //En caso de que el array sea menor a n
        if (array.length < n) {
            //Imprime el arreglo original
            for (int i = 0; i < array.length; i++)
                result += array[i] + ", ";

        } else {
            //Imprime el arreglo original
            for (int i = 0; i < n; i++)
                result += array[i] + ", ";

        }//end if
        result += "\n" + "Sorted Array: ";

        //Se ordena el array
        util.Utility.sortElementaryArrays(algorithm, array);

        //En caso de que el array sea menor a n
        if (array.length < n) {
            //Imprime el arreglo ordenado
            for (int i = 0; i < array.length; i++)
                result += array[i] + ", ";

        } else {
            //Imprime el arreglo ordenado
            for (int i = 0; i < n; i++)
                result += array[i] + ", ";

        }//end if

        String resultCount = result;

        result += "\n" + "Total Iteractions: " + util.Utility.format(Elementary.getTotalIteractions());
        result += "\n" + "Total Changes: " + util.Utility.format(Elementary.getTotalChanges());


        //En caso de ser countingSort retorna también el contador
        if (util.Utility.compare(algorithm, "countingSort") == 0){
            resultCount += "\n" + "Counter array: ";

            if(array.length < n) {
                for (int i = 0; i < array.length; i++)
                    resultCount += Elementary.getCount()[i] + ", ";

            }else {
                for (int i = 0; i < n; i++)
                    resultCount += Elementary.getCount()[i] + ", ";

            }//end if2

            return resultCount;

        }//end if

        return result;
    }//end elementarySorting()

    // ----------------------------------------------------------------------------------------------------
    // ------------------------------ COMPLEX TEST --------------------------------------------------------

    @Test
    void complexTest() {

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("------------------------------- COMPLEX TEST -----------------------------------");


        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(complexSorting("quickSort", util.Utility.getIntegerArray(80000), 30));
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(complexSorting("radixSort", util.Utility.getIntegerArray(40000), 50));
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(complexSorting("mergeSort", util.Utility.getIntegerArray(15000), 100));
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(complexSorting("shellSort", util.Utility.getIntegerArray(5000), 150));
        System.out.println("--------------------------------------------------------------------------------");

    }//end test Complex

    private String complexSorting(String algorithm, int[] array, int n) {
        String result = "";
        result += algorithm + " - Test\n";
        result += "Algorithm: " + algorithm + "\n";
        result += "Original Array: ";

        // Mostrar el arreglo original (solo los primeros n elementos)
        int elementsToShow = Math.min(n, array.length);
        for (int i = 0; i < elementsToShow; i++) {
            result += array[i] + ", ";
        }

        result += "\n" + "Sorted Array: ";

        Complex complex = new Complex();
        complex.resetTracking();

        // Ordenar el arreglo
        if (algorithm.equals("quickSort")) {
            complex.quickSort(array, 0, array.length - 1);
        } else if (algorithm.equals("radixSort")) {
            complex.radixSort(array, array.length);
        } else if (algorithm.equals("mergeSort")) {
            complex.mergeSort(array, 0, array.length - 1);
        } else if (algorithm.equals("shellSort")) {
            complex.shellSort(array);
        }

        // Mostrar el arreglo ordenado (solo los primeros n elementos)
        for (int i = 0; i < elementsToShow; i++) {
            result += array[i] + ", ";
        }

        // Mostrar información adicional específica para cada algoritmo
        if (algorithm.equals("quickSort")) {
            // Mostrar solo los primeros 30 valores de low, high y pivot
            result += "\nLow: ";
            int[] lowValues = complex.getLowValues();
            int maxLowToShow = Math.min(30, lowValues.length);
            for (int i = 0; i < maxLowToShow; i++) {
                result += lowValues[i] + " ";
            }

            result += "\nHigh: ";
            int[] highValues = complex.getHighValues();
            int maxHighToShow = Math.min(30, highValues.length);
            for (int i = 0; i < maxHighToShow; i++) {
                result += highValues[i] + " ";
            }

            result += "\nPivot: ";
            int[] pivotValues = complex.getPivotValues();
            int maxPivotToShow = Math.min(30, pivotValues.length);
            for (int i = 0; i < maxPivotToShow; i++) {
                result += pivotValues[i] + " ";
            }

            result += "\nRecursive calls: " + util.Utility.format(complex.getRecursiveCalls());
        } else if (algorithm.equals("radixSort")) {
            result += "\nCounter Array: ";
            int[] counter = complex.getCounterRadix();
            if (counter != null) {
                for (int i = 0; i < counter.length; i++) {
                    result += counter[i] + ", ";
                }
            }
        } else if (algorithm.equals("mergeSort")) {
            // Mostrar solo los primeros 30 valores de low y high
            result += "\nLow: ";
            int[] lowValues = complex.getLowValues();
            int maxLowToShow = Math.min(30, lowValues.length);
            for (int i = 0; i < maxLowToShow; i++) {
                result += lowValues[i] + " ";
            }

            result += "\nHigh: ";
            int[] highValues = complex.getHighValues();
            int maxHighToShow = Math.min(30, highValues.length);
            for (int i = 0; i < maxHighToShow; i++) {
                result += highValues[i] + " ";
            }

            result += "\nRecursive calls: " + util.Utility.format(complex.getRecursiveCalls());
        } else if (algorithm.equals("shellSort")) {
            // Mostrar los valores de gap (k=n/2)
            result += "\nGap (n/2): ";
            int[] gapValues = complex.getGapValues();
            for (int i = 0; i < complex.getGapCount(); i++) {
                result += gapValues[i] + " ";
            }

            // Mostrar los subarreglos para cada gap
            int[][] subArrays = complex.getGapSubArrays();
            for (int i = 0; i < 3 && i < subArrays.length; i++) {
                result += "\nGap subarray " + (i+1) + ": ";
                for (int j = 0; j < subArrays[i].length && subArrays[i][j] != 0; j++) {
                    result += subArrays[i][j] + " ";
                }
            }
        }

        return result;
    }

}//END SORTING TEST CLASS