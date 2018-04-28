package cn.mldn.util.web.validator;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;

/**
 * 根据指定的规则进行指定的数据验证处理操作，该操作会将错误信息返回
 * @author mldn
 */
public class ActionValidationUtil {
	private HttpServletRequest request ;	// Request对象
	private String rule ;	// 验证规则
	private MessageSource messageSource ;
	// 保存所有的错误信息，其中key为参数名称，而value是错误信息对象
	private Map<String,String> errors = new HashMap<String,String>() ;
	/**
	 * 必须明确的传递要进行验证的Action信息以及具体的验证规则处理
	 * @param actionObject Action程序类
	 * @param request 请求对象
	 * @param rule 规则
	 * @param messageSource 资源文件读取
	 */
	public ActionValidationUtil(HttpServletRequest request,String rule,MessageSource messageSource) {
		this.request = request ;
		this.rule = rule ;
		this.messageSource = messageSource ;
		this.handleValidator(); // 构造方法就直接进行验证了
	}
	/**
	 * 实现数据的验证检测，对外部而言，此方法可以不需要知道，因为所有的错误信息最终保存在Map集合之中
	 */
	private void handleValidator() {
		String ruleResult [] = this.rule.split("\\|") ;	
		for (int x = 0 ; x < ruleResult.length ; x ++) {
			String temp [] = ruleResult[x].split(":") ;
			// 根据参数的名称取得参数的内容，这个内容不管输入验证的检测类都可以检测出来
			String parameterValue = this.request.getParameter(temp[0]) ;
			switch(temp[1]) {
				case "int" :
					if (!ValueRuleValidator.isInt(parameterValue)) {	// 验证不通过
						// key为参数名称、value为messages中定义的提示文字信息
						this.errors.put(temp[0], this.messageSource.getMessage("validation.int.msg", null, null)) ;
					}
					break ;
				case "long" :
					if (!ValueRuleValidator.isLong(parameterValue)) {	// 验证不通过
						// key为参数名称、value为messages中定义的提示文字信息
						this.errors.put(temp[0], this.messageSource.getMessage("validation.long.msg", null, null)) ;
					}
					break ;
				case "double" :
					if (!ValueRuleValidator.isDouble(parameterValue)) {	// 验证不通过
						// key为参数名称、value为messages中定义的提示文字信息
						this.errors.put(temp[0], this.messageSource.getMessage("validation.double.msg", null, null)) ;
					}
					break ;
				case "string" :
					if (!ValueRuleValidator.isString(parameterValue)) {	// 验证不通过
						// key为参数名称、value为messages中定义的提示文字信息
						this.errors.put(temp[0], this.messageSource.getMessage("validation.string.msg", null, null)) ;
					}
					break ;
				case "date" :
					if (!ValueRuleValidator.isDate(parameterValue)) {	// 验证不通过
						// key为参数名称、value为messages中定义的提示文字信息
						this.errors.put(temp[0], this.messageSource.getMessage("validation.date.msg", null, null)) ;
					}
					break ;
				case "datetime" :
					if (!ValueRuleValidator.isDatetime(parameterValue)) {	// 验证不通过
						// key为参数名称、value为messages中定义的提示文字信息
						this.errors.put(temp[0], this.messageSource.getMessage("validation.datetime.msg", null, null)) ;
					}
					break ;
				case "rand" :
					if (!ValueRuleValidator.isRand(parameterValue,(String)this.request.getSession().getAttribute("rand"))) {	// 验证不通过
						// key为参数名称、value为messages中定义的提示文字信息
						this.errors.put(temp[0], this.messageSource.getMessage("validation.rand.msg", null, null)) ;
					}
					break ;
				case "boolean" :
					if (!ValueRuleValidator.isBoolean(parameterValue)) {	// 验证不通过
						// key为参数名称、value为messages中定义的提示文字信息
						this.errors.put(temp[0], this.messageSource.getMessage("validation.boolean.msg", null, null)) ;
					}
					break ;
			}
		}
	}
	/**
	 * 取得所有错误信息
	 * @return 返回集合，如果有错误信息则集合长度大于0
	 */
	public Map<String,String> getErrors() {	// 获取所有的错误信息
		return this.errors ;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
