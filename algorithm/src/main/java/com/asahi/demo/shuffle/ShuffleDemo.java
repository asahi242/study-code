package com.asahi.demo.shuffle;

import java.util.Random;

/**
 * @title 洗牌算法
 * @author asahi
 * @date 2020-11-23 10:55:00
 */
public class ShuffleDemo {
    public static void main(String[] args) {
        System.out.println("Fisher-Yates Shuffle:");
        int[] a = {1,2,3,4,5,6,7,8,9,10};
        int[] b = Fisher_Yates_Shuffle(a);
        for (int j:b){
            System.out.printf(j+" ");
        }
        System.out.println("\nKnuth-Durstenfeld Shuffle:");
        int[] a1 = {1,2,3,4,5,6,7,8,9,10};
        Knuth_Durstenfeld_Shuffle(a1);
        for (int j:a1){
            System.out.printf(j+" ");
        }
        System.out.println("\nInside-Out Algorithm:");
        int[] a2 = {1,2,3,4,5,6,7,8,9,10};
        int[] c = Inside_Out_Shuffle(a2);
        for (int j:c){
            System.out.printf(j+" ");
        }
    }
    /*
        通过随机数将a中元素存进b数组中，访问过的元素置为0
        时间复杂度：O(n*n)  空间复杂度：O(n)
     */
    public static int[] Fisher_Yates_Shuffle(int[] a){
        int[] b = new int[a.length];
        Random random = new Random();
        for (int k=0;k<b.length;){
            int i = random.nextInt(a.length);
            if(a[i]!=0){
                b[k] = a[i];
                a[i]=0;
                k++;
            }
        }
        return b;
    }
    /*
        每次从未处理的数据中随机取出一个数字，然后把该数字放在数组的尾部
        时间复杂度：O(n)  空间复杂度：O(1)
     */
    public static void Knuth_Durstenfeld_Shuffle(int[] a){
        Random random = new Random();
        int temp;
        for (int i=a.length-1;i>0;i--){
            int rand = random.nextInt(i+1);
            temp = a[i];
            a[i] = a[rand];
            a[rand] = temp;
        }
    }
    /*
        复制a数组到b数组并从前向后扫描数据，把b中当前位置或之前随机一个位置的元素和当前位置元素替换
        时间复杂度：O(n)  空间复杂度：O(n)
     */
    public static int[] Inside_Out_Shuffle(int[] a){
        int[] b = new int[a.length];
        System.arraycopy(a,0,b,0,a.length);
        Random random = new Random();
        for (int i=0;i<a.length;i++){
            int rand = random.nextInt(i+1);
            b[i] = b[rand];
            b[rand] = a[i];
        }
        return b;
    }
}
