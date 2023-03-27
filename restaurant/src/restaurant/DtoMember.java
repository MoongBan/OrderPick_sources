package restaurant;

public class DtoMember {
	private String mid;
	private String mpw;
	private String mname;
	private int mbalance;
	
	public DtoMember() {
	}
	
	public DtoMember(String mid, String mpw, String mname, int mbalance) {
		super();
		this.mid = mid;
		this.mpw = mpw;
		this.mname = mname;
		this.mbalance = mbalance;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMpw() {
		return mpw;
	}
	public void setMpw(String mpw) {
		this.mpw = mpw;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public int getMbalance() {
		return mbalance;
	}
	public void setMbalance(int mbalance) {
		this.mbalance = mbalance;
	}
	
	
}
