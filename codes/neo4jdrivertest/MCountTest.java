package neo4jdrivertest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import algo.MotifMatch;
import neo4jdriver.MCount;

public class MCountTest {
	public static void main(String[]args) throws IOException {
		MCount mc = new MCount();
		String nodes[] = {"Disease","HostProtein","Symptom","VirusProtein","Strain","Location"};
		List<List<Integer>>motifIns = mc.mcount("3221", "HostProtein,Symptom,Drug,Virus");
		//System.out.println(s);
		ArrayList<Integer> drugs = new ArrayList<Integer>();
		ArrayList<Integer> drugFreq = new ArrayList();
		for (int i = 0; i < motifIns.size(); i++) {
			int drug = motifIns.get(i).get(2);
			if (!drugs.contains(drug))
				drugs.add(drug);
			//drugs.add(motifIns.get(i).get(2));
			drugFreq.add(0);
		}
		for (int i = 0; i < drugs.size(); i++) {
			int drug = drugs.get(i);
			for (int j = 0; j < motifIns.size(); j++) {
				if (motifIns.get(j).contains(drug))
					drugFreq.set(i, drugFreq.get(i)+1);
			}
		}
		
		//System.out.println(motifIns);
		System.out.println(motifIns.size());
		for (int i = 0; i < drugs.size(); i++) {
			System.out.println("DrugID: " + drugs.get(i) + ", Frequency: " + drugFreq.get(i));
		}
	}
}
