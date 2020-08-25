package neo4jdrivertest;


import java.io.IOException;

import neo4jdriver.MAM;

public class MAMTest {

	public static void main(String[] args) throws IOException {
		
		MAM a = new MAM();
		System.out.println(a.mam("112", "A,B,C"));
	}

}
