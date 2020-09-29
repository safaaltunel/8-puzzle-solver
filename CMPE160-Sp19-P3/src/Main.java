import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Main {
	
	public static int[] encode45(String str, int tableSize) {
		int[] arr = new int[tableSize*tableSize];
		int i = 0;
		int count = 0;
		String temp = "";
		while(count != arr.length-1) {
			if(str.charAt(i) == '-') {
				arr[count] = Integer.parseInt(temp);
				count++;
				temp = "";
			}
			else {
				temp += str.charAt(i);
			}
			i++;
		}
		arr[arr.length-1] = Integer.parseInt(str.substring(i));
		return arr;
	}
	
	public static int indexOf(int[] arr, int target) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == target)
				return i;
		}
		return -1;
	}

	public static Node run(Node root, int[] target, GameTree gt) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(root);
		Set<String> processed = new HashSet<>();
		processed.add(root.stringCurrent);
		int indexOfZeroTarget = indexOf(target,0);
		while(!pq.isEmpty()) {
			Node currentNode = pq.poll();
			int indexOfZero = currentNode.indexOfZero;
			if(indexOfZeroTarget == indexOfZero && isEqual(currentNode.current,target)) {
				return currentNode;
			}
			if(!currentNode.id.equals("R") && indexOfZero % gt.tableSize != 0) {
				Node leftNode = gt.createNode(currentNode, "L",target);
				if(!processed.contains(leftNode.stringCurrent)) {
					pq.add(leftNode);
					processed.add(leftNode.stringCurrent);
				}
			}
			if(!currentNode.id.equals("L") && indexOfZero % gt.tableSize != gt.tableSize - 1) {
				Node rightNode = gt.createNode(currentNode, "R",target);
				if(!processed.contains(rightNode.stringCurrent)) {
					pq.add(rightNode);
					processed.add(rightNode.stringCurrent);
				}
				
			}
			if(!currentNode.id.equals("D") && indexOfZero >= gt.tableSize) {
				Node upNode = gt.createNode(currentNode, "U",target);
				if(!processed.contains(upNode.stringCurrent)) {
					pq.add(upNode);
					processed.add(upNode.stringCurrent);
				}
			}
			if(!currentNode.id.equals("U") && indexOfZero < gt.tableSize*(gt.tableSize-1)) {
				Node downNode = gt.createNode(currentNode, "D",target);
				if(!processed.contains(downNode.stringCurrent)) {
					pq.add(downNode);
					processed.add(downNode.stringCurrent);
				}
			}
		}
		return null;
	}
	
	public static String tempAns(Node leaf) {
		String temp = "";
		while(leaf != null) {
			temp += leaf.id;
			leaf = leaf.parent;
		}
		return temp;
	}
	
	public static String ans(String str) {
		if(str.length() == 0) return "N";
		String ans = "";
		for(int i = str.length()-1; i >= 0; i--) {
			ans += str.charAt(i);
		}
		return ans;
	}
	
	public static boolean isEqual(int[] current, int[] target) {
		for(int i = 0; i < current.length; i++) {
			if(current[i] != target[i]) return false;
		}
		return true;
	}
	
	public static int tableSize(String str) {
		int maxNum = 0;
		String temp = "";
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == '-') {
				int candidate = Integer.parseInt(temp);
				maxNum = Math.max(maxNum, candidate);
				temp = "";
			}
			else temp += str.charAt(i);
		}
		int candidate = Integer.parseInt(temp);
		maxNum = Math.max(maxNum, candidate);
		return (int) Math.sqrt(maxNum+1);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		File inputFile = new File(args[0]);
		Scanner sc = new Scanner(inputFile);
		String input = sc.nextLine();
		int tableSize = tableSize(input);
		int[] target = new int[tableSize*tableSize];
		for(int i = 0; i < target.length-1; i++) {
			target[i] = i+1;
		}
		PrintStream printStream = new PrintStream(new File(args[1]));
		GameTree gt = new GameTree(tableSize, target);
		gt.root.current = encode45(input,tableSize);
		gt.root.convertCurrentToString();
		gt.root.calculateH();
		gt.root.g = 0;
		gt.root.updateF();
		gt.root.indexOfZero = indexOf(gt.root.current,0);
		Node leaf = null;
		leaf = run(gt.root,target,gt);
		String tempAns = tempAns(leaf);
		String ans = ans(tempAns);
		printStream.println(ans);
		printStream.close();
		sc.close();
		return;
	}

}
