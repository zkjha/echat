package com.ecard.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.config.StaticValue;
import com.ecard.util.WebSessionUtil;
/**
 * 文件上传控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/file")
public class FileController {
	
	@Resource
	private WebSessionUtil webSessionUtil;
	
	/**
	 * 图片后缀名
	 */
	public static final String [] SUFFIX_ARR = {"jpg","png"};
	
	/**
	 * 上传商家LOGO图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadImage")
	public String uploadImage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "文件不能为空", null);
		}
		String fileName = file.getOriginalFilename();
		try {
			if (!checkIsImage(fileName, file)) {
				return DataTool.constructResponse(ResultCode.IS_NOT_PICTURE, "上传文件不是图片类型", null);
			}
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			// 文件上传路径
			String filePath = StaticValue.FILE_UPLOAD_URL;
			// 解决中文问题，liunx下中文路径，图片显示问题
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			String folder = df.format(new Date());
			fileName = folder + "/" + DataTool.getUUID() + suffixName;
			File destination = new File(filePath + fileName);
			// 检测是否存在目录
			if (!destination.getParentFile().exists()) {
				destination.getParentFile().mkdirs();
			}

			file.transferTo(destination);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("strImgrootpath", StaticValue.IMAGE_ROOT_PATH);
			resultMap.put("strImgpath", fileName);
			return DataTool.constructResponse(ResultCode.OK, "文件上传成功", resultMap);

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "文件上传失败", null);
	}
	
	/**
	 * 
	 * @描述：检查上传文件是否为指定的类型
	 * @param fileName
	 * @param file
	 * @return
	 * @throws IOException
	 */
	
	private boolean checkIsImage(String fileName,MultipartFile file) throws IOException {
		BufferedImage sourceImg = ImageIO.read(file.getInputStream());
		if(sourceImg==null){
			return false;
		}
		//默认不是图片
		boolean flag = false;
		String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		for(String suffix : SUFFIX_ARR) {
			if(suffix.equals(suffixName)) {
				flag = true;
				break;
			} else {
				continue;
			}
		}
		return flag;
	}
	
}
