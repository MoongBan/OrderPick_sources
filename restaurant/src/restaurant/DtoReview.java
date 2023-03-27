package restaurant;

public class DtoReview {
	private int reNum;
	private String reWriter;
	private String reContent;
	private String reDate;
	public String getReDate() {
		return reDate;
	}
	public void setReDate(String reDate) {
		this.reDate = reDate;
	}
	public String getReMenu() {
		return reMenu;
	}
	public void setReMenu(String reMenu) {
		this.reMenu = reMenu;
	}
	private String reMenu;
	public int getReNum() {
		return reNum;
	}
	public void setReNum(int reNum) {
		this.reNum = reNum;
	}
	public String getReWriter() {
		return reWriter;
	}
	public void setReWriter(String reWriter) {
		this.reWriter = reWriter;
	}
	public String getReContent() {
		return reContent;
	}
	public void setReContent(String reContent) {
		this.reContent = reContent;
	}
	
}
