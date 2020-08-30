package neo4jdrivertest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import algo.MotifMatch;
import neo4jdriver.MCOUNTF;

public class MCountTest {
	public static void main(String[]args) throws IOException {
		MCOUNTF mc = new MCOUNTF();
		
		List<List<Integer>>motifIns = mc.MCOUNT("3221", "Drug,HostProtein,VirusProtein,Symptom");
		
		System.out.println(motifIns);
	}
}
