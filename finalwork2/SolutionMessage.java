package finalwork2;

public class SolutionMessage implements Message{
	  private boolean mIsProblemSolved;

	    public SolutionMessage(boolean isProblemSolved) {
	        mIsProblemSolved = isProblemSolved;
	    }


	    public boolean isSolutionSolved() {
	        return mIsProblemSolved;
	    }

}
