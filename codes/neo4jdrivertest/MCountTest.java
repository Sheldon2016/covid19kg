package neo4jdrivertest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import algo.MotifMatch;
import neo4jdriver.MCount;

public class MCountTest {
	public static void main(String[]args) throws IOException {
		MCount mc = new MCount();
		
		List<List<Integer>>motifIns = mc.mcount("1122", "A,B,B,A");
		
		System.out.println(motifIns.size());
	}
}
