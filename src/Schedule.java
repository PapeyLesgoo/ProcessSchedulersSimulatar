import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Schedule{

    ArrayList<NodesP1> list = new ArrayList<NodesP1>();

    public Schedule() {
        //takes process from textfile and stores them in nodes
        File file = new File("Process.txt");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine())!=null){
                String arr[] = line.split(", ");
                int temp1 = Integer.parseInt(arr[1]);
                double temp2 = Double.parseDouble(arr[2]);
                NodesP1 temp3 = new NodesP1(arr[0],temp1,temp2,temp1);
                list.add(temp3);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //sorts the values on arrival time
    public void Sort(){
        for (int i = 0; i < list.size() - 1; i++){
            for (int j = 0; j < list.size() - i - 1; j++){
                if (list.get(j).ArrivalTime > list.get(j+1).ArrivalTime){
                    NodesP1 temp = list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1,temp);
                }
            }
        }
    }
}