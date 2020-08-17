package algo;

import java.io.IOException;
import java.util.ArrayList;

public class DrugPredict {
	
	MotifMatch mm = null;
	
	public DrugPredict() throws IOException{
		mm = new MotifMatch();
		covid19kg kg = new covid19kg("C:\\Users\\Sheldon\\Documents\\GitHub\\covid19kg\\data\\toyKG\\");
		Motif[]mfs = new Motif[10];
		int[]ls = new int[10];
		
		mfs[1] = new Motif();
		mfs[1].getDiamond();
		ls[1] = 0; // label A is matched to Drug
		
		
		int drugID = 19;
		
		System.out.println(score(mfs, ls, drugID, kg));
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
