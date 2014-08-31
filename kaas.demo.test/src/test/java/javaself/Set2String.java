package javaself;

import java.util.HashSet;
import java.util.Set;

public class Set2String {
	public static void main(String args[]) {
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(2);
		
		System.out.println(set.toString());
	}
}
