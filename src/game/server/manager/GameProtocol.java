package game.server.manager;

public class GameProtocol {
	private String contentType;
	private Object data;
	
	public GameProtocol(String contentType, Object data) {
		this.contentType = contentType;
		this.data = data;
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
