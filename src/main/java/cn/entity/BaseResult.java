package cn.entity;

public class BaseResult {

    public String code;            // 访问成功的标�?
    public String msg = "";          //提示性语�?
    public Object result;

    
    
    
    
    public BaseResult() {
		super();
	}

	public BaseResult(String code, String msg, Object result) {
		super();
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
