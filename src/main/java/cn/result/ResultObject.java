package cn.result;

import java.sql.Timestamp;

public class ResultObject<T> {
	private Integer status;
	private String message;
	private T data;
	private Timestamp timestamp = new  Timestamp(System.currentTimeMillis());
	
	
	
	public ResultObject() {
	}



	public ResultObject(ResultEnum resultEnum) {
		super();
		this.status = resultEnum.getStatus();
		this.message = resultEnum.getMessage();
	}



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



	public T getData() {
		return data;
	}



	public void setData(T data) {
		this.data = data;
	}
	
	
	

}
