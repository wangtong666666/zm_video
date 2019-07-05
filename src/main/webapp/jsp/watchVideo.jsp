<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
 <title>PostbirdMp4ToBlob</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/postbird-mp4-to-blob.min.js"></script>
     <style>
        *{
            margin:0;
            padding:0;
            box-sizing: border-box;
        }
        html,body{
            
        }
        video{
            width:200px;
        }
        .video-container{
            text-align: center;
        }
        small{
            color:#aaaaaa;
        }
    </style>    
</head>
<body>
 <div class="container">
        <div class="row">
            <div class="col-md-12"> 
                <h2>演示效果：</h2>
            </div>
            <div class="col-md-6">
              
                <div class="video-container">
                        <video id="video" webkit-playsinline="true" controls playsinline="true" type="video/mp4"  x5-video-player-type="h5" >
                        </video>
                </div>
            </div>
           
        </div>
    </div>
</body>

<script>

	var url = 'http://192.168.1.152:8081/zm_video/upload/watchVideo?filePath=null\123-1558596412661-code.mp4Fragment';
//	var url = 'http://192.168.1.87:8080/CGW_upload/upload/watchVideo.action?filePath=教学相关视频/测试课程001/测试课程0011812/教学视频/教师01/人物-1545127473771-code.cgw';
    var mimeCodec = 'video/mp4; codecs="avc1.640028, mp4a.40.2"';
    PostbirdMp4ToBlob.init('#video',url,mimeCodec); 
</script>
</html>