import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Q2P1 {
    public static void main(String[] args) {
        //Priority Scheduler
        Schedule s = new Schedule();
        PriorityScheduler p = new PriorityScheduler();
        while(!s.list.isEmpty()){
            p.Insert(s.list.remove(0));
        }
        p.StartProcess();
    }

}
class NodesP1{
    NodesP1 next;
    String name;
    int priority;
    double BurstTime;
    double WaitingTime;
    double TurnaroundTime;
    double ArrivalTime;
    double rrCounter;
    //for round robin

    public NodesP1(String name, int priority, double burstTime, double arrivalTime) {
        this.name = name;
        this.priority = priority;
        BurstTime = burstTime;
        ArrivalTime = arrivalTime;
    }

    public NodesP1(NodesP1 temp){
        this.name = temp.name;
        this.priority = temp.priority;
        BurstTime = temp.BurstTime;
        ArrivalTime = temp.ArrivalTime;
        WaitingTime = temp.WaitingTime;
    }

}


class PriorityScheduler{
    NodesP1 head;

    public void Insert(NodesP1 n){
        if (head == null){
            //if first element
            NodesP1 newNode = new NodesP1(n.name,n.priority,n.BurstTime,n.priority);
            head = newNode;
        } else {
            NodesP1 temp = head;
            NodesP1 prev = null;
            while (temp.next != null && temp.priority > n.priority){
                //loop continues till end of list or new process priority is less than current processes in list
                //linked list scnz
                prev = temp;
                temp = temp.next;
            }
            if (temp.next == null){
                // if in end of list
                if (temp.priority < n.priority){
                    //if new process priority is greater than last process in list
                    prev.next = n;
                    n.next = temp;
                }else {
                    //if new process has the lowest priority
                    temp.next = n;
                }
            }else if(head != temp){
                //insertion in middle of list
                n.next = temp;
                prev.next = n;
            }else{
                //insertion at head, new process has highest priority
                n.next = temp;
                head = n;
            }
        }
    }

    public void Remove(NodesP1 n){
        //moves processes after removing the current one and bring the next one in
        head = n.next;
    }

    public boolean isEmpty(){
        if (head == null){
            return true;
        }
        return false;
    }

    public void StartProcess(){
        int i = 0;
        NodesP1 temp = head;
        while (!isEmpty()){
            //increases waiting time of next process
            AddWaiting(temp.next);
            i++;
            //i checks if burst time is completed
            if (temp.BurstTime - i == 0){
                i = 0;
                //calculation of turnaround time
                temp.TurnaroundTime += temp.BurstTime + temp.WaitingTime;
                System.out.println("---------------");
                System.out.println("Process:     " + temp.name);
                System.out.println("Priority:   " + temp.priority);
                System.out.println("Arrival Time:   0");
                System.out.println("Burst Time:     " + temp.BurstTime);
                System.out.println("Waiting Time:   " + temp.WaitingTime);
                System.out.println("Turnaround Time:    " + temp.TurnaroundTime);
                Remove(temp);
                //removes head and makes nest process the head
                temp = head;
            }
        }
    }

    public void Print(){
        NodesP1 temp = head;
        while (temp!=null){
            System.out.println(temp.name);
            temp = temp.next;
        }
    }

    public void AddWaiting(NodesP1 n){
        NodesP1 temp = n;
        //increases waiting times of every process
        while (n != null){
            n.WaitingTime++;
            n = n.next;
        }
    }
}
