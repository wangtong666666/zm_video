package cn.result;

public enum ResultEnum {
	SUCCESS(200,"处理成功"),
	NOFIND(0,"无数据"),
	NOACCESS(403,"服务器拒绝请求"),
	ERROR(500,"服务器内部错误"),
	ERROR_TOKEN(502,"用户Token错误"),
	ERROR_TIMEOUT(503,"服务器处理超时"),
	ERROR_UNKNOW(555,"未知错误");
	
	private Integer status;
	private String message;
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private ResultEnum(Integer status, String message) {
		this.status = status;
		this.message = message;
	}
		
	
	
}
