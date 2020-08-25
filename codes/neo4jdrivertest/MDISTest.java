package neo4jdrivertest;


import java.io.IOException;
import java.util.List;

import neo4jdriver.MDIS;


public class MDISTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		MDIS a = new MDIS();
		String labels = "A,B,C,D", kStr = 4 +"";
		List<List<String>> res = a.mdis(labels, kStr); // , 5+"", "A"
		//System.out.println(res);
	}

}
