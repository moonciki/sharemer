package sharemer.business.api.master.po;

import java.util.Date;

public class Picture implements java.io.Serializable {

	private static final long serialVersionUID = 5591784180950551554L;

	private Long id;

	private String hash;

	private String name;

	private long size;

	private int width;

	private int height;

	private String url;

	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public String getHash() {
		return hash;
	}

	public int getHeight() {
		return height;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public String getUrl() {
		return url;
	}

	public int getWidth() {
		return width;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
