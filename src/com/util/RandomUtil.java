package com.util;

import java.util.Random;

public class RandomUtil
{
    public static int[] getRandomArray(int arraySize, int maxValue)
    {
        int[] init = new int[maxValue];
        int[] res = new int[arraySize];
        for (int i = 0; i < maxValue; i++)
        {
            init[i] = i;
        }
        Random random = new Random();
        int top = maxValue-1;
        for (int i = 0; i < arraySize; i++)
        {
            int idx = 0;
            if(top!=0)
            {
                idx = Math.abs(random.nextInt()%top);
            }
            res[i] = init[idx];
            init[idx] = init[top];
            top--;
        }
        return res;
    }
//    public static void main(String[] args)
//    {
//        int[] res = RandomArrayUtil.getRandomArray(10, 10);
//        for(int i=0;i<10;i++)
//        {
//            System.out.print(res[i]+" ");
//        }
//    }
}
