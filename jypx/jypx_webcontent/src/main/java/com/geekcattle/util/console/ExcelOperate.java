package com.geekcattle.util.console;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.export.ExcelExportServer;

public class ExcelOperate {
	protected static final String HSSF         = ".xls";
    protected static final String XSSF         = ".xlsx";
    private static final String   CONTENT_TYPE = "text/html;application/vnd.ms-excel";
    
	public static void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String codedFileName = "临时文件";
		Workbook workbook = null;
		if (model.containsKey(NormalExcelConstants.MAP_LIST)) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) model.get(NormalExcelConstants.MAP_LIST);
			if (list.size() == 0) {
				throw new RuntimeException("MAP_LIST IS NULL");
			}
			workbook = ExcelExportUtil.exportExcel((ExportParams) list.get(0).get(NormalExcelConstants.PARAMS),
					(Class<?>) list.get(0).get(NormalExcelConstants.CLASS),
					(Collection<?>) list.get(0).get(NormalExcelConstants.DATA_LIST));
			for (int i = 1; i < list.size(); i++) {
				new ExcelExportServer().createSheet(workbook,
						(ExportParams) list.get(i).get(NormalExcelConstants.PARAMS),
						(Class<?>) list.get(i).get(NormalExcelConstants.CLASS),
						(Collection<?>) list.get(i).get(NormalExcelConstants.DATA_LIST));
			}
		} else {
			workbook = ExcelExportUtil.exportExcel((ExportParams) model.get(NormalExcelConstants.PARAMS),
					(Class<?>) model.get(NormalExcelConstants.CLASS),
					(Collection<?>) model.get(NormalExcelConstants.DATA_LIST));
		}
		if (model.containsKey(NormalExcelConstants.FILE_NAME)) {
			codedFileName = (String) model.get(NormalExcelConstants.FILE_NAME);
		}
		if (workbook instanceof HSSFWorkbook) {
			codedFileName += HSSF;
		} else {
			codedFileName += XSSF;
		}
		if (isIE(request)) {
			codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF8");
		} else {
			codedFileName = new String(codedFileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		response.setContentType(CONTENT_TYPE);		
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}
	
	protected static boolean isIE(HttpServletRequest request) {
        return (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0
                || request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0
                || request.getHeader("USER-AGENT").toLowerCase().indexOf("edge") > 0) ? true
                    : false;
    }
}
