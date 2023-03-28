package Assignment;

import java.util.*;
import java.util.Random;
/**
 *
 * @author Sim Jia Yang, B210094B
 */
public class test {
    /**
     *
     */
    public static final String ANSI_YELLOW = "\u001B[33m";
    
    public static void main(String[] args) {
        //Problem
        //Order ..
        //cannot repeat ....
        //Get back the sequence...
        //Because when the arrival time between the process is too long,
        //the queue will be null as well

//        String[] name = {"p1","p2","p3","p4"};
//        int[] burstTime = {5,4,2,1};
//        int[] arrivalTime = {0,1,2,4};
//        int quantum = 2;

//        String[] name = {"p1", "p2", "p3", "p4", "p5", "p6"};
//        int[] burstTime = {5, 6, 7, 9, 2, 3};
//        int[] arrivalTime = {5, 4, 3, 1, 2, 6};
//        int quantum = 3;

//        String[] name = {"p1","p2","p3"};
//        int[] burstTime = {10,5,8};
//        int[] arrivalTime = {0,0,0};
//        int quantum = 2;

        String[] name = {"p1", "p2", "p3", "p4", "p5", "p6"};
        int[] burstTime = new Random().ints(name.length,1,10).toArray();
        int quantum = new Random().nextInt(1,9);
        int[] arrivalTime = new Random().ints(name.length,0,20).toArray();


        System.out.println("Round Robin Algorithm Stimulation");
        System.out.println("--------------Basic Information--------------");
        System.out.println("Arrival Time Generate: " + Arrays.toString(arrivalTime));
        System.out.println("Burst Time Generate: " + Arrays.toString(burstTime));
        System.out.println("Quantum Generate: " + quantum);
        
        System.out.println(ANSI_YELLOW + "---------------Before Sorting-----------------");
        System.out.println(ANSI_YELLOW + "pID\tAT\tBT");
        for (int i = 0; i < name.length; i++) {
            System.out.println(name[i] + "\t" + arrivalTime[i] + "\t" +burstTime[i] + "\t");
        }
        System.out.println("---------------------------------------------");

        roundrobin  r1 = new roundrobin (name, burstTime, arrivalTime, quantum);
        r1.rrs();
        System.out.println("---------------------------------------------");
    }
}
