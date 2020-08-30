package drugrec;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.procedure.Name;
import org.neo4j.procedure.UserFunction;

import datapre.covid19kg;
import neo4jdriver.MCOUNTF;
import tools.BitGenerator;
import tools.CONF;
import tools.Motif;

public class DrugFreqVec {

	static covid19kg kg = null;
	static List<List<Integer>> motifInsForMMC = null;
	static int bar = 0;
	
	public DrugFreqVec(covid19kg kg2) throws IOException {
		kg = kg2;
		//the labels given by the user MUST NOT have duplicates
	}
	
	@UserFunction
	public List<List<String>> getDrugFreqVec(@Name("value") String labels, @Name("value") String kStr) throws IOException, InterruptedException {
		List<List<String>> res = new ArrayList();//a list of <degVec-label, count> pairs
		int k = Integer.parseInt(kStr);
		if(k>4)
			k = 4;
		String[]label = labels.split(",");
		
		String[]degvecs = Motif.degVecs;
		double[]drugfreq = new double[kg.nodeNID[1].size()];
		for(int i=0;i<degvecs.length;i++) {
			if(degvecs[i].length()>k)
				continue;
			
			//assign labels for degvecs[i] and count it
			//generate the permutation A(degvecs[i].length, label.length)
			ArrayList<String>bitvec = BitGenerator.getBits(degvecs[i].length(), label.length);
			for(int j=0;j<bitvec.size();j++) {
				String bits = bitvec.get(j);
				String labelStr = getLabelStrFromBit(label, bits);
				if(!labelStr.contains("Drug,")||!labelStr.contains("Virus,")) 
					continue;
				List<List<Integer>>motifIns = new MCOUNTF(kg).MCOUNT(degvecs[i], labelStr);
				ArrayList<Integer>drugpos = getPos("Drug", labelStr);
				for(int p=0;p<motifIns.size();p++) {
					List<Integer>ins = motifIns.get(p);
					for(int q=0;q<drugpos.size();q++) {
						int dnid = ins.get(drugpos.get(q));
						int did = kg.node[1].get(dnid);
						drugfreq[did]++;
					}
				}
				int count = motifIns.size();
				if(count>bar)
					res.add(getARes(degvecs[i], labelStr, count));
			}
			
			
		}
		
		String[]drugnames = new String[drugfreq.length];
		for(int i=0;i<drugfreq.length;i++) {
			drugnames[i] = kg.nodeName[1].get(i);
		}
		for(int i=0;i<drugfreq.length;i++) {
			for(int j=i+1;j<drugfreq.length;j++) {
				if(drugfreq[i]<drugfreq[j]) {
					double tem = drugfreq[i];
					drugfreq[i] = drugfreq[j];
					drugfreq[j] = tem;
					String tems = drugnames[i];
					drugnames[i] = drugnames[j];
					drugnames[j] = tems;
				}
			}
			
			if(drugfreq[i]>0)
			System.out.println(drugnamedeal(drugnames[i])+"\t"+drugfreq[i]);
		}
		
		
		return res;
	}

	private String drugnamedeal(String string) {
		String[]tem = string.split("[|]");
		return tem[0];
	}

	private ArrayList<Integer> getPos(String string, String labelStr) {
		String[]tem = labelStr.split(",");
		ArrayList<Integer>res = new ArrayList();
		for(int i=0;i<tem.length;i++) {
			if(tem[i].equals(string))
				res.add(i);
		}
		return res;
	}

	private List<String> getARes(String degvec, String labelStr, int count) {
		ArrayList<String> ares = new ArrayList();
		ares.add(degvec);
		ares.add(labelStr);
		ares.add(count+"");
		
		System.out.println(ares.toString());
		return ares;
	}
	
	private String getLabelStrFromBit(String[] label, String bits) {
		String s = "";
		for(int i=0;i<bits.length()-1;i++) {
			int bit = Integer.parseInt(bits.charAt(i)+"");
			s+=(label[bit]+",");
		}
		int bit = Integer.parseInt(bits.charAt(bits.length()-1)+"");
		s+=label[bit];
		return s;
	}


	private String getLabelStrFrom1Label(String label, int length) {
		String s = "";
		for(int i=0;i<length-1;i++) {
			s+=(label+",");
		}
		s+=label;
		return s;
	}
	
	
}
