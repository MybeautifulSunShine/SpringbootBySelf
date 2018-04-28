package cn.mldn.util.web.validator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

/**
 * 专门负责文件上传类型的检验
 * 
 * @author mldn
 */
public class ActionMIMEValidationUtil {
	private HttpServletRequest request;
	private MultipartResolver multipartResolver;
	private String key; // 进行资源读取的key信息
	private MessageSource messageSource;
	private Map<String, String> mimeErrors = new HashMap<String, String>();
	public ActionMIMEValidationUtil(HttpServletRequest request, String key,
			MessageSource messageSource) {
		this.multipartResolver = new CommonsMultipartResolver(); // 通过此类来判断是否有上传文件
		this.request = request;
		this.messageSource = messageSource;
		this.validateMime();
	}
	private void validateMime() { // 进行MIME类型的检测
		if (this.multipartResolver.isMultipart(request)) { // 表单被封装了，那么就意味着有可能需要上传
			String rule = null; // 规则读取的时候需要考虑没有资源信息的情况
			try {
				rule = this.messageSource.getMessage(this.key + ".mime.rule",
						null, null);
			} catch (Exception e) {
				rule = this.messageSource.getMessage("mime.rule", null, null);
			}
			if (request instanceof DefaultMultipartHttpServletRequest) {
				// 如果要想接收所有的上传文件数据信息，必须将Request进行强制转换
				MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
				Map<String, MultipartFile> fileMap = mrequest.getFileMap(); // 这所有的上传文件
				if (fileMap.size() > 0) { // 有表单数据，但是有可能没有选择
					Iterator<Map.Entry<String, MultipartFile>> iter = fileMap
							.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry<String, MultipartFile> me = iter.next();
						if (me.getValue().getSize() > 0) { // 现在有上传文件，那么就需要进行mime的验证处理
							if (!ValueRuleValidator.isMIME(rule,
									me.getValue().getContentType())) {
								this.mimeErrors.put(me.getKey(),
										this.messageSource.getMessage(
												"validation.mime.msg", null,
												null));
							}
						}
					}
				}
			}
		}
	}
	public Map<String, String> getMimeErrors() {
		return this.mimeErrors;
	}
}
