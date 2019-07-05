package cn.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.result.ResultEnum;
import cn.result.ResultObject;
import cn.result.ReturnResult;
import cn.util.MD5;

@Controller
@RequestMapping("/attestation")
public class attestationController {

	@ResponseBody
	@RequestMapping("/getAttestation")
	public ResultObject getAttestation(){
		String time = new Date().getTime()+""; 
		String encode = null;
		try {
			encode = MD5.md5(time,"leacol");
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnResult.error(ResultEnum.ERROR);
		}
		
	
		return ReturnResult.success(time+"-"+encode);
	}
	
}
