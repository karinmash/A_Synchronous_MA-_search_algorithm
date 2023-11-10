package finalwork2;

import java.util.ArrayList;
import java.util.HashMap;

public class Results {
	private int CCs, Ass, BTs;
    private final ArrayList<Integer> Solution = new ArrayList<>();
    
	public int getCCs() {
		return CCs;
	}

	
	public int getAss() {
		return Ass;
	}

	
	public int getBTs() {
		return BTs;
	}

	
	public ArrayList<Integer> getmSolution() {
		return Solution;
	}
	
    public void incrementCCs() {
        this.CCs++;
    }

    public void incrementBTs() {
        this.BTs++;
    }

    public void clearSolution() {
    	Solution.clear();
    }



    public void incrementAss() {
        this.Ass++;
    }

}
