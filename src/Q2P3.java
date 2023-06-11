public class Q2P3 {
    public static void main(String[] args) {
        //Shortest Job First Non-Preemptive
        System.out.println("Shortest Job First Non-Preemptive");
        Schedule s = new Schedule();
        s.Sort();
        SJFNonPre sjf = new SJFNonPre();
        while(!s.list.isEmpty()){
            sjf.Insert(s.list.remove(0));
        }
        sjf.StartProcess();
    }
}
class SJFNonPre{
    NodesP1 head;
    int size = 0;

    public void Insert(NodesP1 n){
        size++;
        if (head == null){
            //insertion of firwst element
            NodesP1 newNode = new NodesP1(n.name,n.priority,n.BurstTime,n.priority);
            head = newNode;
        } else {
            double c = head.ArrivalTime;
            //n.ArrivalTime -= c;
            NodesP1 temp = head;
            NodesP1 prev = null;
            while (temp.next != null && temp.ArrivalTime < n.ArrivalTime){
                //loop continues till end of list or new processes arrival time is greater than arrival times of processes in list
                prev = temp;
                temp = temp.next;
            }
            if (temp.next == null){
                // if in end of list
                if (temp.ArrivalTime > n.ArrivalTime){
                    //if new process arrival time is less than arrival time of last process in list
                    prev.next = n;
                    n.next = temp;
                }else {
                    //if new process has the greatest arrival time
                    temp.next = n;
                }
            }else if(head != temp){
                //if in middle of list
                n.next = temp;
                prev.next = n;
            }else{
                //if new process time is less than processes in list
                n.next = temp;
                head = n;
            }
        }
    }

    public void Remove(NodesP1 temp){
        //makes the next process the head
        head = temp.next;
        size--;
    }


    public void AddWaiting(NodesP1 n, int x){
        NodesP1 temp = n;
        while (temp != null){
            if (x >= temp.ArrivalTime){
                temp.WaitingTime++;
            }
            temp = temp.next;
        }
    }

    public void DecWaiting(NodesP1 n, int x){
        NodesP1 temp = n;
        while (temp != null){
            if (x >= temp.ArrivalTime){
                temp.WaitingTime--;
            }
            temp = temp.next;
        }
    }

    public boolean isEmpty(){
        if (head == null){
            return true;
        }
        return false;
    }


    public void StartProcess(){
        int i = 0, j = 0;
        NodesP1 temp = head;
        while (!isEmpty()){
            //j is sort of system clock
            j++;
            if (j >= temp.ArrivalTime){
                //if process arrival time is greater than or equal to j
                //i checks the completion of burst time
                i++;
                if (temp.BurstTime - i == 0){
                    //calcs waiting time by subtracting burst time and arrival time from j
                    temp.WaitingTime = j - i - temp.ArrivalTime + 1;
                    //calcs turnaround time
                    temp.TurnaroundTime += temp.BurstTime + temp.WaitingTime;
                    System.out.println("---------------");
                    System.out.println("Process:     " + temp.name);
                    System.out.println("Arrival Time:   " + (temp.ArrivalTime));
                    System.out.println("Burst Time:     " + temp.BurstTime);
                    System.out.println("Waiting Time:   " + temp.WaitingTime );
                    System.out.println("Turnaround Time:    " + temp.TurnaroundTime);
                    Remove(temp);
                    //makes next process head
                    temp = head;
                    i = 0;
                    Sort(j,temp);
                    //sorts to see which process has shortest burst time and makes it head
                }
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

    //swap function for sorting
    public void Swap(NodesP1 s1, NodesP1 s2){
        NodesP1 temp = new NodesP1(s1);
        s1.ArrivalTime = s2.ArrivalTime;
        s1.BurstTime = s2.BurstTime;
        s1.priority = s2.priority;
        s1.TurnaroundTime = s2.TurnaroundTime;
        s1.WaitingTime = s2.WaitingTime;
        s1.name = s2.name;

        s2.ArrivalTime = temp.ArrivalTime;
        s2.BurstTime = temp.BurstTime;
        s2.priority = temp.priority;
        s2.TurnaroundTime = temp.TurnaroundTime;
        s2.WaitingTime = temp.WaitingTime;
        s2.name = temp.name;
    }

    //sorting to find shortest job
    public void Sort(int time, NodesP1 temp1){
        for (int i = 0; i < size - 1; i++){
            NodesP1 temp2 = temp1;
            for (int j = 0 ; j < size - i - 1; j++){
                if (time>=temp2.ArrivalTime && temp2.BurstTime>temp2.next.BurstTime){
                    Swap(temp2,temp2.next);
                }
                temp2 = temp2.next;
            }
        }
    }
}

