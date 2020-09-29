
public class GameTree {

	Node root;
	int tableSize;
	int[] target;
	
	public GameTree(int tableSize, int[] target) {
		this.tableSize = tableSize;
		root = new Node(tableSize,"",target);
		this.target = target;
	}
	
	public int[] swap(int[] arr,int a, int b) {
		int[] newArray = new int[arr.length];
		for(int i = 0; i < arr.length; i++) newArray[i] = arr[i];
		int temp = newArray[a];
		newArray[a] = newArray[b];
		newArray[b] = temp;
		return newArray;
	}
	
	public Node createNode(Node parent, String rotation, int[] target) {
		Node newNode = new Node(this.tableSize,rotation,target);
		newNode.parent = parent;
		int indexOfZero = parent.indexOfZero;
		if(rotation.equals("R")) {
			newNode.current = swap(parent.current,indexOfZero,indexOfZero+1);
			newNode.indexOfZero = indexOfZero + 1;

		}
		else if(rotation.equals("L")) {
			newNode.current = swap(parent.current,indexOfZero,indexOfZero-1);
			newNode.indexOfZero = indexOfZero - 1;

		}
		else if(rotation.equals("U")) {
			newNode.current = swap(parent.current,indexOfZero,indexOfZero - tableSize);
			newNode.indexOfZero = indexOfZero - tableSize;

		}
		else if(rotation.equals("D")){
			newNode.current = swap(parent.current,indexOfZero,indexOfZero+tableSize);
			newNode.indexOfZero = indexOfZero + tableSize;

		}
		newNode.h = parent.h + (newNode.calculateDistance(newNode.indexOfZero, target.length-1) + newNode.calculateDistance(indexOfZero, target[newNode.current[indexOfZero]-1]-1)
		- newNode.calculateDistance(indexOfZero, target.length-1) - newNode.calculateDistance(newNode.indexOfZero, target[newNode.current[indexOfZero]-1]-1));
		newNode.convertCurrentToString();
		newNode.g = parent.g + 1;
		newNode.updateF();
		return newNode;
	}
	
	public static int indexOf(int[] arr, int target) {
		int ans = 0;
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == target)
				return i;
		}
		return ans;
	}
	
}
