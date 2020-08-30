package drugrec;

import java.io.IOException;
import java.util.List;

import datapre.covid19kg;
import neo4jdriver.MDISF;
import tools.CONF;

public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		DrugFreqVec a = new DrugFreqVec(new covid19kg(CONF.mainDir));
		String labels = "Disease,Drug,HostProtein,Symptom,Virus,VirusProtein,Strain,Location", kStr = 4 +"";
		List<List<String>> res = a.getDrugFreqVec(labels, kStr); // , 5+"", "A"
		//System.out.println(res);
	}

}
