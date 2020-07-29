package textcompare;

public class CellPair {
	private String JPText;
	private String PTText;
	
	public String getJPText() {
		return this.JPText;
	}
	public void setJPText(String jPText) {
		this.JPText = jPText;
	}
	public String getPTText() {
		return this.PTText;
	}
	public void setPTText(String pTText) {
		this.PTText = pTText;
	}
	
	public CellPair(String jPText, String pTText) {
		this.JPText = jPText;
		this.PTText = pTText;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + ((this.JPText == null) ? 0 : this.JPText.hashCode());
		result = prime * result + ((this.PTText == null) ? 0 : this.PTText.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;

		CellPair other = (CellPair) obj;
		
		if (this.JPText == null && other.JPText != null) return false;
		if (this.PTText == null && other.PTText != null) return false;
		if (!this.JPText.equals(other.JPText) || !this.PTText.equals(other.PTText)) return false;
		return true;
	}
	
	

}
