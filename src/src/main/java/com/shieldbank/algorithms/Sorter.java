package com.shieldbank.algorithms;

import com.shieldbank.model.Operation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Sorter {

    private Sorter() {
    }

    public static void bubbleSort(List<Operation> list,
                                  Comparator<Operation> comparator) {
        int n = list.size();
        
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            
            for (int j = 0; j < n - 1 - i; j++) {
                
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    swap(list, j, j + 1);
                    swapped = true;
                }
                
            }
            
            if (!swapped) {
                break;
            }
        }
    }


    public static void mergeSort(List<Operation> list,
                                 Comparator<Operation> comparator) {
        
        if (list.size() < 2) {
            return;
        }
        
        int mid = list.size() / 2;
        List<Operation> left = new ArrayList<>(list.subList(0, mid));
        List<Operation> right = new ArrayList<>(list.subList(mid, list.size()));
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        merge(list, left, right, comparator);
    }

    private static void merge(List<Operation> target, List<Operation> left,
                              List<Operation> right,
                              Comparator<Operation> comparator) {
        int i = 0;
        int j = 0;
        int k = 0;
        
        while (i < left.size() && j < right.size()) {
            
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                target.set(k, left.get(i));
                i++;
                
            } else {
                target.set(k, right.get(j));
                j++;
            }
            k++;
        }
        
        while (i < left.size()) {
            target.set(k, left.get(i));
            i++;
            k++;
        }
        
        while (j < right.size()) {
            target.set(k, right.get(j));
            j++;
            k++;
        }
    }

    private static void swap(List<Operation> list, int i, int j) {
        Operation temporary = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temporary);
    }
}
