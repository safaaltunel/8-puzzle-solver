
public class Node implements Comparable<Node>{

	Node parent;
	int[] target;
	int[] current;
	int h;
	int f;
	int g;
	String id;
	String stringCurrent = "";
	int tableSize;
	int indexOfZero;
	
	public Node(int tableSize, String id, int[] target) {
		this.tableSize = tableSize;
		this.id = id;
		current = new int[tableSize * tableSize];
		parent = null;
		this.target = target;
	}
	
	public void updateF() {
		f = g + h;
	}
	
	public void calculateH() {
		h = 0;
		for(int i = 0; i < current.length; i++) {
			int row = Math.abs(i / tableSize - indexOf(target,current[i]) / tableSize);
			int column = Math.abs(i % tableSize - indexOf(target,current[i]) % tableSize);
			h += row + column;
		}
	}
	
	public int calculateDistance(int currentIndex, int targetIndex) {
		return Math.abs((currentIndex / tableSize) - (targetIndex / tableSize)) + Math.abs((currentIndex % tableSize) - (targetIndex % tableSize));
	}
	
	public static int indexOf(int[] arr, int target) {
		int ans = -1;
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == target)
				return i;
		}
		return ans;
	}

	@Override
	public int compareTo(Node node) {
		if(this.f > node.f) return 1;
		else if(this.f < node.f) return -1;
		return 0;
	}
	
	public void convertCurrentToString() {
		for(int i = 0; i < current.length; i++) stringCurrent += current[i];
	}
	
	
}
