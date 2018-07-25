package com.imooc.handlers;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import com.imooc.exceptions.CheckException;

import reactor.core.publisher.Mono;

@Component
//默认有多个异常处理的handler 
//数值越小优先级越高
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {
	//当方法抛出异常的时候进入到这里面
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();
		// 设置响应头400
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		// 设置返回类型 可以返回json 也可以方法字符串
		response.getHeaders().setContentType(MediaType.TEXT_PLAIN);

		// 异常信息
		String errorMsg = toStr(ex);
		
		DataBuffer db = response.bufferFactory().wrap(errorMsg.getBytes());

		return response.writeWith(Mono.just(db));
	}

	private String toStr(Throwable ex) {
		// 已知异常
		if (ex instanceof CheckException) {
			CheckException e = (CheckException) ex;
			return e.getFieldName() + ": invalid value " + e.getFieldValue();
		}
		// 未知异常, 需要打印堆栈, 方便定位
		else {
			ex.printStackTrace();
			return ex.toString();
		}

	}

}
