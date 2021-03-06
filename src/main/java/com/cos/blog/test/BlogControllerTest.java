package com.cos.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Local (master) 로 develop을 Merge - 2021.03.08 11:19
// GitHub && 로컬 Merge 완료. - 2021.03.08 10:33
// 스프링이 com.cos.blog패키지 이하를 스캔해서 모든파일을 메노리에 new하는것은 아니구요.
// 특정 어노테이션이 붙어있는 클래스 파일들이 new헤서 (IoC) 스프링 컨테이너에 관리해줍니다.  
@RestController                     
public class BlogControllerTest {

	// http://localhost:8080/test/hello    
	@GetMapping("/test/hello")
	public String hello( ) {
		return "<h1>hello spring boot</h1>";
	}
}
