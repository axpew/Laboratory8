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
        result += "Algorithm: " + algorithm + " - Test\n";
        result += "Original Array: ";

        //En caso de que el array sea menor a n
        if (array.length < n) {
            //Imprime el arreglo original
            for (int i = 0; i < array.length; i++)
                result += array[i] + ", ";

        } else {
            //Imprime el arreglo original
            for (int i = 0; i <= n; i++)
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
            for (int i = 0; i <= n; i++)
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
        result += "Algorithm: " + algorithm + " - Test\n";
        result += "Original Array: ";

        // Mostrar el arreglo original
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

        // Mostrar el arreglo ordenado
        for (int i = 0; i < elementsToShow; i++) {
            result += array[i] + ", ";
        }

        // Mostrar información adicional
        if (algorithm.equals("quickSort")) {
            result += "\nLow: ";
            for (int value : complex.getLowValues()) {
                result += value + " ";
            }

            result += "\nHigh: ";
            for (int value : complex.getHighValues()) {
                result += value + " ";
            }

            result += "\nPivot: ";
            for (int value : complex.getPivotValues()) {
                result += value + " ";
            }

            result += "\nRecursive calls: " + util.Utility.format(complex.getRecursiveCalls());
        } else if (algorithm.equals("radixSort")) {
            result += "\nCounter Array: ";
            int[] counter = complex.getCounterRadix();
            for (int i = 0; i < counter.length; i++) {
                result += counter[i] + ", ";
            }
        } else if (algorithm.equals("mergeSort")) {
            result += "\nLow: ";
            for (int value : complex.getLowValues()) {
                result += value + " ";
            }

            result += "\nHigh: ";
            for (int value : complex.getHighValues()) {
                result += value + " ";
            }

            result += "\nRecursive calls: " + util.Utility.format(complex.getRecursiveCalls());
        } else if (algorithm.equals("shellSort")) {
            int[][] subArrays = complex.getGapSubArrays();
            for (int i = 0; i < subArrays.length; i++) {
                result += "\nGap (n/2) subArray" + (i+1) + ": ";
                for (int j = 0; j < subArrays[i].length; j++) {
                    result += subArrays[i][j] + " ";
                }
            }
        }

        return result;
    }

}//END ELEMENTARY TEST CLASS