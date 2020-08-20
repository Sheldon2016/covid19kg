package drugrec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import algo.MotifMatch;
import datapre.covid19kg;
import tools.Motif;

public class DrugPredict {
	
	MotifMatch mm = null;
	
	public DrugPredict() throws IOException{
		mm = new MotifMatch();
		covid19kg kg = new covid19kg("C:\\Users\\Sheldon\\Documents\\GitHub\\covid19kg\\data\\HPO\\");
		Motif[]mfs = new Motif[10];
		int[]ls = new int[10];
		
		//to creat a motif that is related to drug and virus
		mfs[1] = new Motif();
		mfs[1].get3Star();
		int[]labels = {2, 1, 4}; // order of the label should be in degree DESC 
		//hostprotein, drug, virus
		mfs[1].assignLabels(labels);
		
		ArrayList<ArrayList<Integer>>motifIns = mm.match(kg,mfs[1]);
		
		HashMap<Integer, Integer>drugCount = new HashMap();
		for(int i=0;i<motifIns.size();i++) {
			ArrayList<Integer> ins = motifIns.get(i);
			//hostprotein, drug, virus
			
			int drugID = ins.get(1);
			if(drugCount.containsKey(drugID)) {
				drugCount.put(drugID, drugCount.get(drugID)+1);
			}else {
				drugCount.put(drugID, 1);
			}	
		}
		
		Set<Integer>drugs = drugCount.keySet();
		Iterator<Integer> dit = drugs.iterator();
		while(dit.hasNext()) {
			int did = dit.next();
			int fre = drugCount.get(did);
			String dname = kg.nodeName[1].get(did);
			System.out.println("Frequency of drug "+dname +" is "+fre);
		}
		
		//<ele_1, ele_2, ..., ele_n> - motif feature vector for each drug
		//n: the number of motifs that contain drug-virus
		
		//manually: 1. the set of motifs are interesting 2. the drug to be recommended is (almost) frequent in each ele_i
		//automatically: 1. collect all motifs 2. train a classifier with motif feature vectors, to predict the drugs for covid-19 virus
		
		// to find interesting drugs from significant motifs
		int drugID = 19;
		
		//System.out.println(score(mfs, ls, drugID, kg));
		
		
		//0:"Disease"
		//1:"Drug"
		//2:"HostProtein"
		//3:"HPO"
		//4:"Virus"
		//5:"VirusProtein"
		//6:"Strain"
		//7:"Location"
	}
	public double score (Motif[]ms, int[]labelIDs, int id, covid19kg kg) {
		double res = 0;
		for(int i=0;i<ms.length;i++) {
			Motif m = ms[i];
			int labelID = labelIDs[i];
			int mnum = getInstanceNumSpecified(id, labelIDs[i], kg, ms[i]);
			res += mnum;
		}
		return res/ms.length;
	}
	
	public int getInstanceNumSpecified(int id, int labelID, covid19kg kg, Motif mf) {
		ArrayList<ArrayList<Integer>> res = mm.match(kg, mf);
		int mcounter = 0;
		for(int i=0;i<res.size();i++) {
			ArrayList<Integer>mins = res.get(i);
			//mins: a motif instance labeled A,B,C,D in the order of mf.motifLabels
			int nid = mins.get(labelID);
			if(nid == id) {
				mcounter ++;
			}
		}
		return mcounter;
	}
	
}
