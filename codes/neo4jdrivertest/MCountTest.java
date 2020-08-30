package neo4jdrivertest;


import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;

import algo.MotifMatch;
import neo4jdriver.MCount;

public class MCountTest {
	public static void main(String[]args) throws IOException {
		MCount mc = new MCount();
		String nodes[] = {"Disease","HostProtein","Symptom","VirusProtein","Strain","Location"};
		List<List<Integer>>motifIns = mc.mcount("3221", "HostProtein,Symptom,Drug,Virus");
		//System.out.println(s);
		HashMap<Integer, Integer> drugs = new HashMap<Integer, Integer>();
		for (int i = 0; i < motifIns.size(); i++) {
			int drug = motifIns.get(i).get(2);
			drugs.put(drug, 0);
		}
		for (Integer i: drugs.keySet()) {
			int drug = i;
			for (int j = 0; j < motifIns.size(); j++) {
				if (motifIns.get(j).contains(drug))
					drugs.put(drug, drugs.get(i)+1);
			}
		}
		//System.out.println(motifIns);
		System.out.println(motifIns.size());
		
		for (Integer i: drugs.keySet()) {
			System.out.println("DrugID: " + i + ", Frequency: " + drugs.get(i));
		}
	}
}
