package com.shankephone.mi.common.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.util.FileDownLoad;
import com.shankephone.mi.util.FilePath;
import com.shankephone.mi.util.ResultVOUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 下拉列表数据加载
 * @author fengql
 * @date 2018年6月22日 上午10:32:32
 */
@Controller
@RequestMapping("/method")
public class MethodController
{

	/***
	 * 导入模板下载
	 * @return
	 */
	////@RequiresPermissions("yhgl")
	@ResponseBody
	@RequestMapping(value = "/downfileUrl", method = RequestMethod.POST)
	public ResultVO downfileurl(HttpServletRequest request, HttpServletResponse response)
	{
		try{
			String savePath = FilePath.getExcelTplPath();
			String filename=  request.getParameter("filename");
			// return ResultVOUtil.success(patch);
			return   FileDownLoad.downMubanload(response,savePath+filename,filename);

		}
		catch (Exception ex)
		{
			return ResultVOUtil.error(ex,"模板下载失败");
		}
	}

}
