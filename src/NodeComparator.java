import java.util.Comparator;


public class NodeComparator implements Comparator<Packet> {
	
	/* This is for the sort function in CSMA
	 *  
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	
	public int compare(Packet o1, Packet o2) {
		// TODO Auto-generated method stub
		return (o1.CurTime - o2.CurTime);
	}

}
