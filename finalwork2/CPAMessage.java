package finalwork2;

import java.util.HashMap;

public class CPAMessage implements Message {
	 private String header;
	 private HashMap<Integer, Integer> assignments;
	

	  public CPAMessage(int n) {
	  assignments = new HashMap<>(n);
	  for (int i = 0; i < n; i++) {
	      assignments.put(i, -1);
	     
         }
	 }

	

		public HashMap<Integer, Integer> getAssignments() {
			return assignments;
		}
		

		public void assign(int id, int assignment) {
			header = "";
	        assignments.put(id, assignment);
	    }

	    public void backtrack(int id) {
	    	header = "backtrack";
	        assignments.put(id, -1);
	    }


	    public boolean isHeaderBackTrack() {
	        return header != null && header.equals("backtrack");
	    }

}
