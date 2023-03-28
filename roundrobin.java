package Assignment;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Sim Jia Yang, B210094B
 */
/*
Reference:
https://www.gatevidyalay.com/round-robin-round-robin-scheduling-examples/ - Verify
https://blog.csdn.net/YtdxYHZ/article/details/52760960 - Content
https://www.geeksforgeeks.org/program-round-robin-scheduling-set-1/ - Verify
https://docs.oracle.com/javase/7/docs/api/java/util/Queue.html - Queue API
https://www.scaler.com/topics/round-robin-scheduling-in-os/ - Verify
 */
public final class roundrobin{
    public String[] name;
    public int[] arrivalTime;
    public int[] burstTime;
    public int quantum = 0;

    public String[] temp_name;
    public int[] temp_at;
    public int[] temp_bt;

    public int[] waitingTime;
    public int[] turnAroundTime;
    public int[] completionTime;
    
    /**
     *
     */
    public static final String ANSI_YELLOW = "\u001B[33m";

    public roundrobin (String[] process, int[] bur, int[] arr, int quantum) {
        burstTime = new int[process.length];
        arrivalTime = new int[process.length];
        name = new String[process.length];
        
        temp_name = new String[process.length];
        temp_at = new int[process.length];
        temp_bt = new int[process.length];         
        
        if (arr == null || bur == null || arr.length != bur.length) {
            System.out.println("Wrong data length");
            System.exit(0);
        }
        
        selectionSort(process, arr, bur);
        this.quantum = quantum;
    }

    public void rrs() {

        Queue<process> queue = new LinkedList<>();
        int index = 0;
        int currentTime = 0;
        waitingTime = new int[name.length];
        turnAroundTime = new int[name.length];
        completionTime = new int[name.length];

        
        while (!queue.isEmpty() || index < name.length) {

            if (!queue.isEmpty()) {
                
                process temP = queue.poll();

                //totalWaitingTime += currentTime - temP.arrivalTime;
                if (temP.burstTime > quantum) {
                    currentTime += quantum;
                } else if (temP.burstTime <= quantum) {
                    currentTime += temP.burstTime;
                    completionTime[getProcess(temP.processID)] += currentTime;
                    turnAroundTime[getProcess(temP.processID)] += currentTime - temP.arrivalTime;
                    waitingTime[getProcess(temP.processID)] += (turnAroundTime[getProcess(temP.processID)])
                            - burstTime[getProcess(temP.processID)];
                }

                for (; index < name.length && arrivalTime[index] <= currentTime; index++) {
                    queue.add(new process(burstTime[index], arrivalTime[index], getProcessID(index)));
                }

                if (temP.burstTime > quantum) {
                    queue.add(new process(temP.burstTime - quantum, temP.arrivalTime, temP.processID));
                }

            } else {
                //Queue null
                queue.add(new process(burstTime[index], arrivalTime[index], getProcessID(index)));
                currentTime = arrivalTime[index];
                index++;
            }
        }
        
        double avgTurnAroundTime = (getSumTime(turnAroundTime) / name.length);
        double avgWaitingTime = (getSumTime(waitingTime) / name.length);
        
        //Recovery the order of the process
        recovery(completionTime, turnAroundTime, waitingTime);
        
        System.out.println("Completion time = " + currentTime);
        System.out.println("Average turn around time = " + (String.format("%.02f",avgTurnAroundTime)));
        System.out.println("Average waiting time = " + (String.format("%.02f",avgWaitingTime)));
        System.out.println("-----------------Result---------------------");
        System.out.println(ANSI_YELLOW + "pID\tAT\tBT\tCT\tTT\tWT\t");
        for (int i = 0; i < name.length; i++) {
            System.out.println(name[i] + "\t" + arrivalTime[i] + "\t" +burstTime[i] + "\t" +
                    completionTime[i] + "\t" + turnAroundTime[i] + "\t" + waitingTime[i]);
        }
        System.out.println("---------------------------------------------");

    }
    
    //Sorting Algorithm
    public void selectionSort(String[] name1, int[] arr1, int[] burst1){
    temp_name = name1.clone();
    temp_at = arr1.clone();
    temp_bt = burst1.clone();
    
    String[] n = name1.clone();
    int[] a = arr1.clone();
    int[] b = burst1.clone();
    int out, in, min;
        for(out=0; out<name.length; out++){
            min = out;
            for(in=out+1; in<name.length; in++){ // in=1
                if(a[in] < a[min] ){ // a[1] < a[0]
                    int temp = a[min];
                    String tempS = n[min];
                    int tempB = b[min];
                    
                    a[min] = a[in];
                    n[min] = n[in];
                    b[min] = b[in];
                    
                    a[in] = temp;
                    n[in] = tempS;
                    b[in] = tempB;   
                }
            }
        }
        this.name = n.clone();
        this.arrivalTime = a.clone();
        this.burstTime = b.clone();        
        
        System.out.println("---------------After Sorting-----------------");
        System.out.println(ANSI_YELLOW + "pID\tAT\tBT");
        for (int i = 0; i < name.length; i++) {
            System.out.println(name[i] + "\t" + arrivalTime[i] + "\t" +burstTime[i] + "\t");
        }
        System.out.println("---------------------------------------------");  
    }     
    
    //Recovery
    public void recovery(int[] c, int[] t, int[]w){
        for(int i=0;i<name.length;i++){
            for(int j=0;j<name.length;j++){
                if(name[j] == temp_name[i]){
                    String tempS = "";
                    int tempB = 0;
                    int tempA = 0;
                    int tempC = 0;
                    int tempW = 0;
                    int tempT = 0;
                    
                    tempS = name[i];
                    tempB = burstTime[i];
                    tempA = arrivalTime[i];
                    tempC = completionTime[i];
                    tempW = waitingTime[i];
                    tempT = turnAroundTime[i];
                    
                    name[i] = temp_name[i];
                    burstTime[i] = temp_bt[i];
                    arrivalTime[i] = temp_at[i];
                    completionTime[i] = completionTime[j];
                    waitingTime[i] = waitingTime[j];
                    turnAroundTime[i] = turnAroundTime[j];
                    
                    name[j] = tempS;
                    burstTime[j] = tempB;
                    arrivalTime[j] = tempA;
                    completionTime[j] = tempC;
                    waitingTime[j] = tempW;
                    turnAroundTime[j] = tempT;
                }
            }    
        }
    }
    
    public int getProcess(String a) {
        int id = 0;
        for (int i = 0; i < name.length; i++) {
            if (name[i] == a) {
                id = i;
            }
        }
        return id;
    }

    public String getProcessID(int a) {
        String id = new String();
        for (int i = 0; i < name.length; i++) {
            id = name[a];
        }
        return id;
    }
    
    
    //----------------------------------------//
    public double getSumTime(int[] a) {
        double tat = 0.0;
        for (int a2 : a) {
            tat += a2;
        }
        return tat;
    }
    
}
