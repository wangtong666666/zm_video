package cn.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VideoFormat {


	//输出视频地址

		        //所在位置(本地配置)
	//		private static String ffmpegPath = "C:\\\Users\\Administrator\\Desktop\\转码文件\\ffmpeg.exe";
	//		private static String mencoderPath = "C:\\Users\\Administrator\\Desktop\\转码文件\\mencoder.exe";

			 public static void main(String args[]) throws IOException {

			       String inputPath = "C:\\Users\\Administrator\\Desktop\\转码文件\\input\\3.avi";
			       String outputPath = "C:\\Users\\Administrator\\Desktop\\转码文件\\output\\3.mp4";

			    /*   if (process(inputPath,outputPath,"fmp4")) {
			            System.out.println("ok");
			       }*/


			        if (!checkfile(inputPath)) {
			            System.out.println(inputPath + " is not file");
			            return;
			        }else{
			        	 if (process(inputPath,outputPath,"mp4")) {
					            System.out.println("ok");
					     }
			        }

			    }
			    public static void getPath() {
			        File diretory = new File("");
			        try {
			            String currPath = diretory.getAbsolutePath();
			            System.out.println(currPath);
			        }
			        catch (Exception e) {
			            System.out.println("getPath出错");
			        }
			    }

			    public static boolean process(String inputPath,String outputPath,String fileType) {
			        int type = checkContentType(inputPath);


			        boolean status = false;
			        if("mp4".equals(fileType)){
			        	status = processMp4(inputPath,inputPath,outputPath);
			        }
			        if("flv".equals(fileType)){
			        	status = processFlv(inputPath,inputPath,outputPath);
			        }
			        if("fragmentMp4".equals(fileType)){
			        	status = processFragmentedMp4(inputPath,outputPath);
			        }

			        return status;
			    }

			    private static int checkContentType(String inputPath) {
			    	//截取格式字符串
			        String type = inputPath.substring(inputPath.lastIndexOf(".") + 1, inputPath.length())
			                .toLowerCase();
			        if (type.equals("avi")) {
			            return 0;
			        } else if (type.equals("mpg")) {
			            return 0;
			        } else if (type.equals("wmv")) {
			            return 0;
			        } else if (type.equals("3gp")) {
			            return 0;
			        } else if (type.equals("mov")) {
			            return 0;
			        } else if (type.equals("mp4")) {
			            return 0;
			        } else if (type.equals("asf")) {
			            return 0;
			        } else if (type.equals("asx")) {
			            return 0;
			        } else if (type.equals("flv")) {
			            return 0;
			        }
			        // 先用mencoder转成avi格式.
			        else if (type.equals("wmv9")) {
			            return 1;
			        } else if (type.equals("rm")) {
			            return 1;
			        } else if (type.equals("rmvb")) {
			            return 1;
			        }
			        return 9;
			    }

			    public static boolean checkfile(String path) {
			        File file = new File(path);
			        if (!file.isFile()) {
			            return false;
			        }
			        return true;
			    }
		/*	    //不能转的先用mencoder转成avi格式
			    private static String processAVI(int type,String inputPath,String outputPath) {
			        List<String> commend = new ArrayList<String>();
			        commend.add(mencoderPath);
			        commend.add(inputPath);
			        commend.add("-oac");
			        commend.add("lavc");
			        commend.add("-lavcopts");
			        commend.add("acodec=mp3:abitrate=64");
			        commend.add("-ovc");
			        commend.add("xvid");
			        commend.add("-xvidencopts");
			        commend.add("bitrate=600");
			        commend.add("-of");
			        commend.add("mp4");
			        commend.add("-o");
			        commend.add(outputPath + "a.AVI");
			        try {
			            ProcessBuilder builder = new ProcessBuilder();
			            Process process = builder.command(commend).redirectErrorStream(true).start();
			            new PrintStream(process.getInputStream());
			            new PrintStream(process.getErrorStream());
			            process.waitFor();
			            return outputPath + "a.AVI";
			        } catch (Exception e) {
			            e.printStackTrace();
			            return null;
			        }
			    }*/

			    //不能转的先用mencoder转成avi格式
			    private static String processAVI(int type, String inputFile,String outputPath) {
			    	String mencoderPath = new PropertiesLoader("file.properties").getProperty("mencoder_path");
					File file = new File(outputPath + ".avi");
					if (file.exists())
						file.delete();
					List<String> commend = new ArrayList<String>();
					commend.add(mencoderPath);
					commend.add(inputFile);
					commend.add("-oac");
					commend.add("mp3lame");
					commend.add("-lameopts");
					commend.add("preset=64");
					commend.add("-ovc");
					commend.add("xvid");
					commend.add("-xvidencopts");
					commend.add("bitrate=600");
					commend.add("-of");
					commend.add("avi");
					commend.add("-o");
					commend.add(outputPath + ".avi");
					StringBuffer test = new StringBuffer();
					for (int i = 0; i < commend.size(); i++) {
						test.append(commend.get(i) + " ");
					}

					System.out.println(test);
					try {
						ProcessBuilder builder = new ProcessBuilder();
						builder.command(commend);
						Process p = builder.start();

						final InputStream is1 = p.getInputStream();
						final InputStream is2 = p.getErrorStream();
						new Thread() {
							public void run() {
								BufferedReader br = new BufferedReader(
										new InputStreamReader(is1));
								try {
									String lineB = null;
									while ((lineB = br.readLine()) != null) {
										if (lineB != null)
											System.out.println(lineB);
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}.start();
						new Thread() {
							public void run() {
								BufferedReader br2 = new BufferedReader(
										new InputStreamReader(is2));
								try {
									String lineC = null;
									while ((lineC = br2.readLine()) != null) {
										if (lineC != null)
											System.out.println(lineC);
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}.start();

						// 等Mencoder进程转换结束，再调用ffmepg进程
						p.waitFor();
						System.out.println("who cares");
						return outputPath + ".avi";
					} catch (Exception e) {
						System.err.println(e);
						return null;
					}
				}

			    private static boolean processFlv(String oldfilepath,String inputPath,String outputPath) {
			    	String ffmpegPath = new PropertiesLoader("file.properties").getProperty("ffmpeg_path");

			        if (!checkfile(inputPath)) {
			            System.out.println(oldfilepath + " is not file");
			            return false;
			        }
			        List<String> command = new ArrayList<String>();
			        command.add(ffmpegPath);
			        command.add("-i");
			        command.add(oldfilepath);
			        command.add("-ab");
			        command.add("56");
			        command.add("-ar");
			        command.add("22050");
			        command.add("-qscale");
			        command.add("8");
			        command.add("-r");
			        command.add("15");
			        command.add("-s");
			        command.add("1920x1080");
			        command.add(outputPath + ".flv");
			        try {

			            Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();

			            new PrintStream(videoProcess.getErrorStream()).start();

			            new PrintStream(videoProcess.getInputStream()).start();

			            videoProcess.waitFor();

			            return true;
			        } catch (Exception e) {
			            e.printStackTrace();
			            return false;
			        }
			    }






			private static boolean processMp4(String oldfilepath,String inputPath,String outputPath) {

				String ffmpegPath = new PropertiesLoader("file.properties").getProperty("ffmpeg_path");

			    if (!checkfile(inputPath)) {
			        System.out.println(oldfilepath + " is not file");
			        return false;
			    }

	//		    ffmpeg -i 1.wmv -c:v libx264 -crf 23 -c:a libfaac -q:a 100 1.mp4
	//		    ffmpeg -i input.wmv -c:v libx264 -crf 23 -profile:v high -r 30 -c:a libfaac -q:a 100 -ar 48000 output.mp4
	//			ffmpeg -i sample.wmv -vcodec libx264 -acodec aac out.mp4
	//			ffmpeg -i sample.wmv -vcodec copy -vbsf h264_mp4toannexb -an out.h264
	//			ffmpeg -i source_video.avi -b 300K -s 320x240 -vcodec xvid -ab 32K -ar 24000 -acodec aac final_video.mp4
			    List<String> command = new ArrayList<String>();

			    command.add(ffmpegPath);
			    command.add("-i");
			    command.add(inputPath);

			    // NO.1
			   /* command.add("-vcodec");
			    command.add("copy");*/

			    //NO.2

			    command.add("-ab");
				command.add("56");
				command.add("-ar");
				command.add("22050");
				command.add("-qscale");
				command.add("8");
				command.add("-r");
				command.add("15");
				command.add("-s");
				command.add("1920X1080");



			    command.add(outputPath);



			    try {
			        Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();

			        new PrintStream(videoProcess.getErrorStream()).start();

			        new PrintStream(videoProcess.getInputStream()).start();

			        videoProcess.waitFor();

			        return true;
			    } catch (Exception e) {
			        e.printStackTrace();
			        return false;
			    }
			}


			/**
			 * mp4 转 fragmented mp4
			 * @param inputPath
			 * @param outputPath
			 * @return
			 */
			private static boolean processFragmentedMp4(String inputPath,String outputPath) {
			//	String fragmentedPath =   "G:\\Tomcat\\apache-tomcat-7.0.6-2\\webapps\\CGW_upload\\VideoUtil\\fragmented\\bin\\.\\mp4fragment.exe";//  new PropertiesLoader("file.properties").getProperty("mp4Fragmented_path");
				
			//	inputPath = "C:\\Users\\Administrator\\Desktop\\VideoUtil\\赵云-1558495043907-nocode.mp4";
			//	outputPath = "C:\\Users\\Administrator\\Desktop\\VideoUtil\\赵云-1558495043907-nocode.fmp4";
				
			//	String fragmentedPath = "C:\\Users\\Administrator\\Desktop\\VideoUtil\\fragmented\\bin\\.\\mp4fragment.exe";
				
				String fragmentedPath = new PropertiesLoader("file.properties").getProperty("mp4Fragmented_path");
				
				System.out.print("fragmentedpath:+");
			    if (!checkfile(inputPath)) {
			        System.out.println(inputPath + " is not file");
			        return false;
			    }
			    List<String> command = new ArrayList<String>();

			    command.add(fragmentedPath);
			    command.add(inputPath);
			    command.add(outputPath);


			    try {
			        Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
			        new PrintStream(videoProcess.getErrorStream()).start();

			        new PrintStream(videoProcess.getInputStream()).start();

			        videoProcess.waitFor();

			        return true;
			    } catch (Exception e) {
			        e.printStackTrace();
			        return false;
			    }
			}

			}
			class PrintStream extends Thread
			{
			    java.io.InputStream __is = null;
			    public PrintStream(java.io.InputStream is)
			    {
			        __is = is;
			    }

			    public void run()
			    {
			        try
			        {
			            while(this != null)
			            {
			                int _ch = __is.read();
			                if(_ch != -1)
			                    System.out.print((char)_ch);
			                else break;
			            }
			        }
			        catch (Exception e)
			        {
			            e.printStackTrace();
			        }
			    }

}
