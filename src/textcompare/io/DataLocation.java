package textcompare.io;

//This class stores the sheet, column and row of each retrieved value
public class DataLocation {

	private String sheet;
	private int jpColumn;
	private int ptColumn;
	private int row;
	
	public int getJPColumn() {
		return jpColumn;
	}
	
	public int getPTColumn() {
		return ptColumn;
	}

	public void setJPColumn(int jpColumn) {
		this.jpColumn = jpColumn;
	}
	
	public void setPTColumn(int ptColumn) {
		this.ptColumn = ptColumn;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getSheet() {
		return sheet;
	}

	public void setSheet(String sheet) {
		this.sheet = sheet;
	}
	
	public DataLocation(String sheet, int jpColumn, int ptColumn, int row) {
		this.sheet = sheet;
		this.jpColumn = jpColumn;
		this.ptColumn = ptColumn;
		this.row = row;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + jpColumn;
		result = prime * result + ptColumn;
		result = prime * result + row;
		result = prime * result + ((sheet == null) ? 0 : sheet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		DataLocation other = (DataLocation) obj;
		return sheet == other.sheet &&
				jpColumn == other.jpColumn &&
				ptColumn == other.ptColumn &&
				row == other.row;
	}
	
}
