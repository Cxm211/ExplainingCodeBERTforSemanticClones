import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.IGenerator;

import java.util.ArrayList;
import java.util.List;

public class CombinationTester {

    public static void main(String args[])
    {
        int size = 4;
        ArrayList<Integer> indices= new ArrayList<Integer>();
        for(int a=0; a <= size - 1; a++)
        {
            indices.add(a);
        }

        for(int a=1; a <= size - 1; a++)
        {
            IGenerator<List<Integer>> statementIndices = Generator.combination(indices)
                    .simple(a);
            for (List<Integer> combination : statementIndices) {
                System.out.println(combination);
                //in this for loop, read the integer indices in list and set isprocessing for these statements in codeparts list
            }
            //System.out.println(statementIndices[7].toString());
        }

    }
}
