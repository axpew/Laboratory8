package domain;

import java.util.ArrayList;

public class Elementary {

    // bubbleSort, improvedBubbleSort, selectionSort, countingSort.

    private static int totalIteractions;
    private static int totalChanges;
    private static int count[];

    // Arreglos para guardar los valores mínimos e índices por iteración
    public static ArrayList<Integer> minValues = new ArrayList<>();
    public static ArrayList<Integer> minIndexes = new ArrayList<>();

    public static int getTotalIteractions() {
        return totalIteractions;
    }

    public static void setTotalIteractions(int totalIteractions) {
        Elementary.totalIteractions = totalIteractions;
    }

    public static int getTotalChanges() {
        return totalChanges;
    }

    public static void setTotalChanges(int totalChanges) {
        Elementary.totalChanges = totalChanges;
    }

    public static int[] getCount() {
        return count;
    }

    public static void setCount(int[] count) {
        Elementary.count = count;
    }

    //Bubble Sort
    public static void bubbleSort(int a[]){
        totalIteractions = 0; //clearing content
        totalChanges = 0; //clearing content

        for(int i=1;i<a.length;i++) {
            totalIteractions++;
            for (int j = 0; j < a.length - i; j++) {
                if (a[j] > a[j + 1]) {
                    int aux = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = aux;
                    totalChanges++;
                }//if
            }//for j
        }//for i
    }

    //Bubble Sort Improved
    public static void improvedBubbleSort(int a[]){
        totalIteractions = 0; //clearing content
        totalChanges = 0; //clearing content
        boolean swapped = true; //intercambiado
        for(int i=1;swapped;i++){
            swapped = false;
            totalIteractions++;
            for(int j=0;j<a.length-i;j++){
                if(a[j]>a[j+1]){
                    int aux=a[j];
                    a[j]=a[j+1];
                    a[j+1]=aux;
                    swapped = true;
                    totalChanges++;
                }//if
            }//for j
        }//for i
    }

    //Selection Sort
    public static void selectionSort(int a[]){
        totalIteractions = 0; //clearing content
        totalChanges = 0; //clearing content
        minIndexes.clear();   // clearing content
        minValues.clear();    // clearing content

        for(int i=0;i<a.length-1;i++){
            int min=a[i];
            int minIndex=i;
            for(int j=i+1;j<a.length;j++){
                if(a[j]<min){
                    min=a[j];
                    minIndex=j;
                    totalChanges++;
                }//if
            }//for j

            // Guardar los valores mínimo e índice de esta iteración
            minValues.add(min);
            minIndexes.add(minIndex);

            a[minIndex]=a[i];
            a[i]=min;
            totalIteractions++;
        }//for i
    }

    //Counting Sort
    public static void countingSort(int a[]) {

        int max = util.Utility.maxArray(a); //va de 0 hasta el elemento maximo
        setCount(new int[max + 1]);

        // create buckets
        int counter[] = new int[max + 1];

        for (int element : a) {
            counter[element]++;//incrementa el num de ocurrencias del elemento
        }
        // sort array
        // fill buckets
        int index = 0;
        count = util.Utility.copyArray(counter, counter.length); //Copia el array counter

        for (int i = 0; i < counter.length; i++) {
            while (counter[i]>0) { //significa q al menos hay un elemento (q existe)
                a[index++] = i;
                counter[i]--;
            }
        }//for i
    }
}