package br.ufrn.imd.domain;

public class ItemType {

    private String title;
    private String total;
    private int color;
 
    public ItemType(String title, String total, int color) {
        this.setTitle(title);
        this.setTotal(total);
        this.setColor(color);
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}
