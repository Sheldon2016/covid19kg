

import java.io.IOException;
import java.util.ArrayList;

import archive.MotifMatchOld;
import datapre.covid19kg;
import tools.Motif;

public class MPPRTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		MPPR m = new MPPR();
		System.out.println(m.mppr("1122", "A,B,C,D", "5", "A", "D", 10000+"", 0.85+""));
	}

}
