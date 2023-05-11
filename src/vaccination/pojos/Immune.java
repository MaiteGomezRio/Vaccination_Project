package vaccination.pojos;

import java.io.Serializable;

public class Immune implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int dis_id;
	private int p_id;
	
	public Immune(int p_id,int dis_id) {
		super();
		this.p_id=p_id;
		this.dis_id=dis_id;
	}
	
	public int getP_Id() {
		return p_id;
	}
	
	public void setP_Id(int p_id) {
		this.p_id=p_id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}
	
	public int getDis_Id() {
		return dis_id;
	}
	public void setDis_Id(int dis_id) {
		this.dis_id=dis_id;
	}
	
}
