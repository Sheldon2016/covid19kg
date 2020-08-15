package algo;

import java.io.IOException;
import java.util.ArrayList;

public class RunMotifMatch {
	public static void main(String[] args) throws IOException {

		MotifMatchJoey mm = new MotifMatchJoey();
		covid19kg kg = new covid19kg("C:\\Users\\Sheldon\\Documents\\GitHub\\covid19kg\\data\\toyKG\\");
		Motif mf = new Motif();
		mf.get4Path();
		
		ArrayList<ArrayList<Integer>>motifIns = mm.match(kg,mf);
		mm.motifIns2String(motifIns, mf, kg);
		
	}
}
