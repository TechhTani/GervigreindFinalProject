import java.util.LinkedList;

public class CSPCell {
	public int value;
	public LinkedList<Integer> domain;
	
	public CSPCell() {

		value = 0;
		domain = new LinkedList<Integer>();
		domain.add(1);
		domain.add(2);
		domain.add(3);
		domain.add(4);
		domain.add(5);
		domain.add(6);
		domain.add(7);
		domain.add(8);
		domain.add(9);
	}
	
	public CSPCell(int value, LinkedList<Integer> domain) {
		this.value = value;
		this.domain = (LinkedList<Integer>) domain.clone();
	}
	
	public int domainSize() {
		return domain.size();
	}
}
