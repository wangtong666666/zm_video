package cn.controller;


/*import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;*/
import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.entity.BaseResult;
import cn.result.ResultEnum;
import cn.result.ResultObject;
import cn.result.ReturnResult;
import cn.util.FileUtil;
import cn.util.PropertiesLoader;
import cn.util.VideoFormat;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
//	@Value("#{configProperties['video_space']}")
//	private String videoSpace;
	
	@ResponseBody
	
	
	@RequestMapping(value="/uploadFile.action",method=RequestMethod.POST,produces = "text/html;charset=utf-8")
    public BaseResult testUpload(HttpServletRequest request,@RequestParam(value="filePath",required=false) String filePath,@RequestParam(value="file") CommonsMultipartFile file) throws Exception{
		String videoSpace = new PropertiesLoader("file.properties").getProperty("video_space");
		
		if(file == null){
			return new BaseResult("0","file is null",null);
		} 
		
		if(filePath == null || filePath.isEmpty()){
			filePath = "other";
		}
		
		//源文件路径
        File file1 = new File(videoSpace+"video_in/"+filePath);
        //播放文件路径
        File file2 = new File(videoSpace+"video_out/"+filePath);
        if(!file1.exists()){
    		System.out.print("正在创建源文件目录:["+videoSpace+"video_in/"+filePath+"]-");
            file1.mkdirs();
            System.out.println("success");
        }
        if(!file2.exists()){
        	System.out.print("正在创建转码文件目录:["+videoSpace+"video_in/"+filePath+"]-");
            file2.mkdirs();
            System.out.println("success");
        }
        OutputStream out;
        InputStream in;
  
        
        String fileName = file.getOriginalFilename();	
        
        fileName = fileName.substring(0,fileName.indexOf(".")) + "-" + new Date().getTime() + fileName.substring(fileName.indexOf("."),fileName.length());
        
        
        out = new FileOutputStream(new File(videoSpace+"video_in/"+filePath+"/"+fileName));
        in = file.getInputStream();
        
        
        
		System.out.print("正在上传源文件:["+videoSpace+"video_in/"+filePath+"/"+fileName+"]-");
        IOUtils.copy(in, out);
        System.out.println("success");
        
//      filePath = "C:\\Users\\Administrator\\Desktop\\VideoUtil\\video_space\\null";
//      fileName = "24-1558595934299-code.mp4";
       
        String videoInCodePath = videoSpace+"video_in/"+filePath+"/"+fileName.substring(0,fileName.lastIndexOf("."))+".lc";
        System.out.print("正在生成加密文件:["+videoInCodePath+"]-");
        FileUtil.copyFileEncryption(new FileInputStream(new File(videoSpace+"video_in/"+filePath+"/"+fileName)),new File(videoInCodePath));
        System.out.println("success");
        
        out.close();
        in.close();
        
        Integer duration = ReadVideoTime(new File(videoSpace+"video_in/"+filePath+"/"+fileName));
        
        
        //videoSpace+"video_out/"+filePath+"/"+fileName+"Fragment"
        
        String videoOutPath = "video_out/"+filePath+"/"+fileName.substring(0,fileName.lastIndexOf("."))+".leacol";
        System.out.print("正在转码:["+videoOutPath+"]-");
        boolean status = VideoFormat.process(videoSpace+"video_in/"+filePath+"/"+fileName, videoSpace+videoOutPath, "fragmentMp4");
      
        new File(videoSpace+"video_in/"+filePath+"/"+fileName).delete();
        
        System.out.println("上传完成-"+duration+"秒");
        
        Map<String,Object> map = new HashMap<String,Object>();
		map.put("url",videoOutPath);
		map.put("duration",duration);
        
        return new BaseResult("1","上传成功！",map);
    }
	
	private Integer ReadVideoTime(File source) {
		Encoder encoder = new Encoder();
		Integer s = null;
		try {
			MultimediaInfo m = encoder.getInfo(source);
			s = (int) (m.getDuration() / 1000);
/*			System.out.println("视频时长:" + s+"秒");*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	@RequestMapping(value="/watchVideo",method=RequestMethod.GET)
    public void watchVideo(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="filePath",required=false) String filePath) throws Exception{
        
		String videoSpace = new PropertiesLoader("file.properties").getProperty("video_space");
		
		
		File f = new File(videoSpace + filePath);
		System.out.print("正在加载:["+f.getPath()+"]-");
		
		
		String fileName = f.getName();
		//导出文件
		String agent = request.getHeader("User-Agent").toUpperCase();
		InputStream fis = null;
		OutputStream os = null;
		try {
		
			
		    fis = new BufferedInputStream(new FileInputStream(f.getPath()));
		    byte[] buffer;
		    buffer = new byte[fis.available()];
		    fis.read(buffer);
		    response.reset();
		    //由于火狐和其他浏览器显示名称的方式不相同，需要进行不同的编码处理
		    if(agent.indexOf("FIREFOX") != -1){//火狐浏览器
		    	response.addHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("GB2312"),"ISO-8859-1"));
		    }else{//其他浏览器
		    	response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		    }
		    //设置response编码
		    response.setCharacterEncoding("UTF-8");
		    response.addHeader("Content-Length", "" + f.length());
		    response.setHeader("Access-Control-Allow-Origin", "*"); 		   
		    //设置输出文件类型
		    response.setContentType("video/mpeg4");
		    //获取response输出流
		    os = response.getOutputStream();
		    
		    
		    //输出文件
		    os.write(buffer);
		
			
	        fis.close();
            os.close();
            System.out.println("success");
		}catch(Exception e){
			e.printStackTrace();
		    System.out.println(e.getMessage());
		} 
		
		
    }
	
	@RequestMapping(value="/downloadFile",method=RequestMethod.GET)
    public void downloadFile(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="filePath",required=false) String filePath) throws Exception{
        System.out.println("filePath:"+filePath);
		String videoSpace = new PropertiesLoader("file.properties").getProperty("video_space");
		
		//video_out/test/test1/123-1558682052050-code.mp4Fragment
		filePath = filePath.replace("video_out/","video_in/");
		filePath = filePath.replace(".leacol", ".lc");
		//创建 fragmentMp4 文件对象
		File lcFile = new File(videoSpace + filePath);
		 
		
		String downFilePath = videoSpace + filePath.substring(0,filePath.lastIndexOf("."))+".mp4";
	    FileUtil.copyFileEncryption(new FileInputStream(lcFile),new File(downFilePath));
		
		File f = new File(downFilePath);
		
		System.out.print("正在下载:["+f.getPath()+"]-");
		
		
		String fileName = f.getName();
		//导出文件
		String agent = request.getHeader("User-Agent").toUpperCase();
		InputStream fis = null;
		OutputStream os = null;
		try {
		
			
		    fis = new BufferedInputStream(new FileInputStream(f.getPath()));
		    byte[] buffer;
		    buffer = new byte[fis.available()];
		    fis.read(buffer);
		    response.reset();
		    //由于火狐和其他浏览器显示名称的方式不相同，需要进行不同的编码处理
		    if(agent.indexOf("FIREFOX") != -1){//火狐浏览器
		    	response.addHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("GB2312"),"ISO-8859-1"));
		    }else{//其他浏览器
		    	response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		    }
		    //设置response编码
		    response.setCharacterEncoding("UTF-8");
		    response.addHeader("Content-Length", "" + f.length());
		    response.setHeader("Access-Control-Allow-Origin", "*"); 		   
		    //设置输出文件类型
		    response.setContentType("video/mpeg4");
		    //获取response输出流
		    os = response.getOutputStream();
		    
		    
		    //输出文件
		    os.write(buffer);
		
			
	        fis.close();
            os.close();
            f.delete();
            System.out.println("success");
		}catch(Exception e){
			e.printStackTrace();
		    System.out.println(e.getMessage());
		} 
		
		
		
    }
	
	
	@RequestMapping(value="/deleteFile",method=RequestMethod.GET)
    public void deleteFile(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="filePath",required=false) String filePath) throws Exception{
        
		String videoSpace = new PropertiesLoader("file.properties").getProperty("video_space");
		
		//video_out/test/test1/123-1558682052050-code.mp4Fragment
		String filePath2 = filePath;
		filePath2 = filePath2.replace("video_out/","video_in/");
		filePath2 = filePath2.replace(".leacol", ".lc");
		
		
		File lcFile = new File(videoSpace + filePath);
		File leacolFile = new File(videoSpace + filePath2);
		
		System.out.print("正在删除:["+videoSpace + filePath +"]-");
		lcFile.delete();
		System.out.println("success");
		
		
		System.out.print("正在删除:["+videoSpace + filePath2 +"]-");
		leacolFile.delete();
		System.out.println("success");
		
    }
	
	
	
	
}
