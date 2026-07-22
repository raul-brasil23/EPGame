package Utils;

public enum State {
	INACTIVE(0),
	ACTIVE(1),
	EXPLODING(2);
	
	private final int state;
	
	State (int state) {
		this.state = state;
	}
	
	public int getState () {
		return state;
	}
	
}