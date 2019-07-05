package cn.result;

public class ReturnResult {
	
	public static ResultObject success(){
		return success(null);
	}
	
	public static ResultObject success(Object o){
		ResultObject resultObject = new ResultObject(ResultEnum.SUCCESS);
		resultObject.setData(o);
		return resultObject;
	}
	
	public static ResultObject error(ResultEnum resultEnum){
		return new ResultObject(resultEnum);
	}
}
 