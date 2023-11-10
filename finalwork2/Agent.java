package finalwork2;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

public class Agent implements Runnable {
	private int id;
	private int domainSize, assignment =-1, agents;
	private Mailer mailer;
	private Results result;
	private boolean IsAlive = true;


	
	public boolean IsAlive() {
		return IsAlive;
	}

	private HashMap<Integer, ConsTable> constraints;
	private HashMap<Integer, Integer> assignments = new HashMap<Integer, Integer>();
	
	
	/*
	 * constructor parameters -
	 * agent's id
	 * a reference to mailer
	 * a reference to csp
	 */
	public Agent(int id, Mailer mailer,Results metrics, HashMap<Integer, ConsTable> constraints, int n, int d) {
		this.id = id;
		this.mailer = mailer;
		this.result=metrics;
		this.constraints = constraints;
		this.domainSize = d;
		this.agents = n;
	/*	
		Random r = new Random();
		assignment = r.nextInt(domainSize);
		*/

	 
 }		        
	
	
	 public boolean isSuccessfulConstraint(int firstAgent, int secondAgent, int firstAgentAssigment, int secondAgentAssigment,Entry<Integer,ConsTable>e) {
    			int assignments1 = -1, assignments2 = -1;
    			if (secondAgent < firstAgent) {
    				assignments1 = secondAgentAssigment;
    				assignments2 = firstAgentAssigment;
    			}
    			else {
    				assignments2 =  secondAgentAssigment;
    				assignments1 =  firstAgentAssigment;
    			}
    			return (e.getValue().check(assignments1, assignments2)) ;	  
    			
	 }
 
		 
	  private boolean isProblemSolved(CPAMessage cpa, int i) {
		for( Entry<Integer, ConsTable> e: constraints.entrySet()) {
			if(cpa.getAssignments().get(e.getKey())==-1) {
				continue;
			}
			if (! isSuccessfulConstraint(this.id,  e.getKey(), i, cpa.getAssignments().get(e.getKey()),e)) {
	                return false;
			}     
		     
	  }
	       return true;
}
	   private void handleFirstAgent() {
		   assignment = 0;
	        CPAMessage cpa = new CPAMessage(agents);
	        cpa.assign(id, 0);
	        assignments.put(id,0);
	        result.incrementAss();
	        mailer.send(1, cpa);
	    }
	  private void killAgent() {
	        IsAlive = false;
	    }

	@Override
	public void run() {
		  if (this.id == 0) {
			  handleFirstAgent();
		  }
		  
		  while (IsAlive) {
			  Message msg = mailer.readOne(this.id);
			  
	            if (msg instanceof SolutionMessage) {
	                if (((SolutionMessage) msg).isSolutionSolved()) {
	                } else {   
	                }
	                killAgent();
	                break;
	            } else if (msg instanceof CPAMessage) {
	            	
	            	
	                CPAMessage cpa = (CPAMessage) msg;
	                boolean hasValidSolution = false;
	                if (cpa.isHeaderBackTrack()) {
	                	assignment++;
	                } else {
	                	assignment = 0;
	                }
	                for (int i = assignment; i < domainSize; i++) {
	                	result.incrementCCs();
	                    if (isProblemSolved(cpa, i)) {
	                        hasValidSolution = true;
	                        assignment = i;
	                        break;
	                    }
	                }

	                if (!hasValidSolution) {
	                    if (this.id == 0) {
	                        noSolution();
	                        return;
	                    } else {
	                    	assignment = -1;
	                        backtrack(cpa);
	                        result.incrementBTs();
	                    }
	                } else {
	                    cpa.assign(this.id, assignment);
	                    result.incrementAss();
	                    if (id == agents - 1) {
	                        hasSolution(cpa);
	                        return;
	                    } else {
	                        mailer.send((id + 1) % agents, cpa);
	                    }
	                }
	            }
	        }
	} 
	 public void backtrack(CPAMessage cpa) {
	        cpa.backtrack(id);
	        mailer.send((id - 1) % agents, cpa);
	    }

	    public void hasSolution(CPAMessage cpa) {
	        SolutionMessage s = new SolutionMessage(true);
	        for (int i = 0; i < agents; i++) {
	            mailer.send(i, s);
	        }
	    }

	    public void noSolution() {
	        SolutionMessage ns = new SolutionMessage(false);
	        for (int i = 0; i <agents; i++) {
	            mailer.send(i, ns);
	        }
	    }


}



