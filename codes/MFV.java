package neo4jdriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.procedure.Name;
import org.neo4j.procedure.UserFunction;

import algo.MotifMatch;
import datapre.covid19kg;
import tools.CONF;
import tools.Motif;

public class MFV {
	
    public List<Integer> mfv(@Name("value") String[]degVec, @Name("value") String[]labels, @Name("value") int snid, @Name("value") String slabel) throws IOException {
    	covid19kg kg = new covid19kg(CONF.mainDir);
    	
    	List<Integer> fres = new ArrayList();
    	for(int i=0;i<degVec.length;i++) {
    		fres.add(0);
    	}
    	
    	int slabelid = kg.getLabelID(slabel);
    	int sid = kg.node[slabelid].get(snid);
    	
    	for(int i=0;i<degVec.length;i++) {
    		MCount mc = new MCount();
    		List<List<Integer>> res = mc.mcount(degVec[i], labels[i]);
    		
    		for(int j=0;j<res.size();j++) {
    			List<Integer> ins = res.get(j);
    			if(ifcontains(ins, sid, labels[i], slabelid, kg))
    				fres.set(i, fres.get(i)+1);
    		}
    		
    	}
    	
		return fres;
    }
    
    private boolean ifcontains(List<Integer> ins, int snid, String labels, int slabelid, covid19kg kg) {
		int[]labelsInt = kg.getlabelsfromString(labels);
		for(int i=0;i<ins.size();i++) {
			if(labelsInt[i]==slabelid&&ins.get(i)==snid) {
				return true;
			}
		}
		return false;
	}

    @UserFunction
	public List<Integer> mfv(@Name("value") String degVecStr, @Name("value") String labelsStr, @Name("value") String snidStr,  @Name("value")String slabel, @Name("value") String tnidStr,  @Name("value")String tlabel) throws IOException {
    	covid19kg kg = new covid19kg(CONF.mainDir);
    	List<Integer> fres = new ArrayList();
    	
    	String degVec[] = degVecStr.split("[|]"), labels[] = labelsStr.split("[|]");
    	int snid = Integer.parseInt(snidStr), tnid = Integer.parseInt(tnidStr);
    	
    	for(int i=0;i<degVec.length;i++) {
    		fres.add(0);
    	}
    	
    	int slabelid = kg.getLabelID(slabel), tlabelid = kg.getLabelID(tlabel);
    	//int sid = kg.node[slabelid].get(snid), tid = kg.node[tlabelid].get(tnid);
    	
    	for(int i=0;i<degVec.length;i++) {
    		MCount mc = new MCount();
    		List<List<Integer>> res = mc.mcount(degVec[i], labels[i]);
    		// note that mcount returns the list of NIDs
    		
    		//System.out.println(res.toString());
    		
    		for(int j=0;j<res.size();j++) {
    			List<Integer> ins = res.get(j);
    			
    			if(ifcontains(ins, snid, labels[i], slabelid, kg)&&ifcontains(ins, tnid, labels[i], tlabelid, kg))
    				fres.set(i, fres.get(i)+1);
    		}
    		
    	}
    	
    	return fres;
    }
}