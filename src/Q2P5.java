import java.util.Random;

public class Q2P5 {
    public static void main(String[] args) {
        //Round Robin
        Schedule s = new Schedule();
        s.Sort();
        RoundRobin rr = new RoundRobin();
        while(!s.list.isEmpty()){
            rr.Insert(s.list.remove(0));
        }
        //rr.Print();
        System.out.println("Quantum Time :  " + rr.qt);
        rr.StartProcess();
    }
}

class RoundRobin{
    NodesP1 head;
    NodesP1 tail;
    int size = 0;
    int qt = 1;

    public void Insert(NodesP1 temp){
        size ++;
        //circular list
        if (head == null){
            head = temp;
            tail = temp;
            head.next = temp;
            tail.next = temp;
        }else{
            tail.next = temp;
            temp.next = head;
            tail = temp;
        }
    }

    public void Print(){
        NodesP1 n = head;
        do {
            System.out.println(n.name);
            n = n.next;
        }while (n != head);
    }

    public void Remove(NodesP1 n, NodesP1 prev){
        prev.next = n.next;
        if (n.next == n){
            head = null;
        }
    }

    public void StartProcess(){
        double i = 0, j = 0;
        NodesP1 n = head;
        NodesP1 prev = null;
        while (!isEmpty()){
            //j is like system clock
            //increasing j with quantam time
            j += qt;
            if (j >= n.ArrivalTime){
                //if process has arrived in waiting list
                //counter for each processes round robin burst times
                n.rrCounter += qt;
                if ((n.BurstTime - n.rrCounter) <= 0){
                    //if burst time has been completed
                    //this is done in case the subtraction of burst time and round robin counter is less than 0
                    //resets each value in such a way that subtraction result will be equal to 0
                    n.rrCounter += n.BurstTime - n.rrCounter;
                    j += n.BurstTime - n.rrCounter;
                    n.WaitingTime = j - n.rrCounter - n.ArrivalTime;
                    n.TurnaroundTime += n.BurstTime + n.WaitingTime;
                    System.out.println("---------------");
                    System.out.println("Process:     " + n.name);
                    System.out.println("Arrival Time:   " + (n.ArrivalTime));
                    System.out.println("Burst Time:     " + n.BurstTime);
                    System.out.println("Waiting Time:   " + (n.WaitingTime) );
                    System.out.println("Turnaround Time:    " + n.TurnaroundTime);
                    System.out.println("j " + j);
                    //moves to next process when burst time completed
                    prev.next = n.next;
                    n = n.next;
                    //i = 0;
                    if (n == n.next && (n.BurstTime - n.rrCounter) <= 0){
                        //if last process in list
                        break;
                    }
                }else{
                    //moves to next process when no quantam time is up
                    prev = n;
                    n = n.next;
                }
            }
        }
    }


    public boolean isEmpty(){
        if (head == null){
            return true;
        }
        return false;
    }
}
