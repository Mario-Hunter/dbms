package eg.edu.alexu.csd.oop.Utilities.reflections;

import eg.edu.alexu.csd.oop.draw.Shape;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReflectionInterfaceScannerTest extends TestCase {
    public ReflectionInterfaceScannerTest() {
        super();
    }



     int removeCandidateOfLength(int size , ArrayList<ArrayList<Integer>>
             lists){
        Iterator<ArrayList<Integer>> iter = lists.iterator();
        int index =0;
        while(iter.hasNext()){
            int currSize =iter.next().size();
            if(currSize <size){
                index++;
            }
            if( currSize == size){
                iter.remove();
            }
        }
        return index;
    }

     int findLIS(int[] s) {

        ArrayList<ArrayList<Integer>> candidates = new ArrayList<>();
        for(int i=0;i<s.length;i++){
            boolean smallest = true;
            for(int j= candidates.size() -1 ; j >=0;j--){
                int lastElement = candidates.get(j).get(candidates.get(j).size()-1);
                if(lastElement == s[i]) {
                    smallest = false;
                    break;
                }
                if(lastElement < s[i] ){
                    ArrayList<Integer> longerSeq = (ArrayList<Integer>) candidates.get(j).clone();
                    longerSeq.add(s[i]);
                    int index = removeCandidateOfLength(longerSeq.size(),
                            candidates);

                    candidates.add(index,longerSeq);
                    smallest = false;
                    break;
                }

            }
            if(smallest){
                ArrayList<Integer> newList = new ArrayList();
                newList.add(s[i]);
                candidates.add(0, newList);
            }
        }
        return candidates.get(candidates.size()-1).size();

    }


    @Test
    public void testName() throws Exception {
         findLIS(new int[]{1,4,5,2,6});
    }

    @Test
    public void testClassesImplementingShape() {
        ReflectionInterfaceScanner<Shape> utils = new ReflectionInterfaceScanner<>(Shape.class);
        List<Class<? extends Shape>> clsses = utils
                .loadClassFrom(
                "src/eg/edu/alexu/csd/oop/draw/cs49/models");
        assertNotEquals(0,clsses.size());

    }
}