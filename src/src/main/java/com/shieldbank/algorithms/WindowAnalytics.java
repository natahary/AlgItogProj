package com.shieldbank.algorithms;

public final class WindowAnalytics {

    private WindowAnalytics() {
    }

    public static long maxWindowSum(long[] values, int k) {
        
        if (k <= 0 || values.length < k) {
            return -1;
        }
        
        long sum = 0;
        
        for (int i = 0; i < k; i++) {
            sum += values[i];
        }
        
        long max = sum;
        
        for (int i = k; i < values.length; i++) {
            sum += values[i] - values[i - k];
            
            if (sum > max) {
                max = sum;
            }
            
        }
        return max;
    }

    public static long[] findPair(long[] sorted, long target) {
        int left = 0;
        int right = sorted.length - 1;
        
        while (left < right) {
            long sum = sorted[left] + sorted[right];
            
            if (sum == target) {
                return new long[]{sorted[left], sorted[right]};
            }
            
            if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return null;
    }
}
