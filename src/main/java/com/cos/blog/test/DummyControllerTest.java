package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 로컬수정 - 2021.03.08 10:02
// html파일이 아니라 date를 리터해주는 controller = RestController
@RestController
public class DummyControllerTest {
	
	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		return "삭제되었습니다.  id : " + id;
	}

	
	// save함수는 id를 전달하지 않으면 insertㄹ르 해주소
	// save함수는 id를 전달하면 해당id에 대한 데이터가 있으면 updaet해주소
	// save함수는 id를 전달하면 해당id에 대한 데이터가 없으면 insert를 해요.
	
	// email, password update
	// http://localhost:8000/blog/dummy/user/3
	//@Transactional // 함수 종료시에 자동 commit이 됨. - 아래 전체조회가 안되는 현상 null return하는 현상이 생겨서 주석처리하고 userRepository.save 처리함.
	@PutMapping("/dummy/user/{id}")
	public User updateUser( @PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		user.setUsername(requestUser.getUsername());
		
		userRepository.save(user);
		// 더티체킹 : @Transactional 붙임으로써 영속성 context에 있는 데이터가 변경이 감지되면 자동으로 db에 commit을 하는 현상  
		return user;
	}
	
	// http://localhost:8000/blog/dummy/users/
	@GetMapping("/dummy/users")
	private List<User> list() {
		return userRepository.findAll();
	}
	
	// 한페이지당 2건에 데이터를 리턴받아 볼 예정
	// http://localhost:8000/blog/dummy/user?page=0
	// http://localhost:8000/blog/dummy/user?page=1
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable){
		Page<User> pageingUser =  userRepository.findAll(pageable);
		
		// 위의 pageingUser는 페이지정보까지 모두 return
		// 아래  getContents() 는 데이터 정보만 get한다.
		List<User> users = pageingUser.getContent();
		return pageingUser;
	}
	
	// {id} 주소로 파라미터를 전달 받을수 있음.
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id ) {
		// user/4을 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될것 아냐?
		// 그럼 return null이 리턴이 되나나.. 그럼 프로그램에 문제가 있지 않겠니?
		// Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해!!
		
		// 람다식
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 사용자가 없습니다.");			
//		});
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {			
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 사용자가 없습니다.");				
			}
		});
		// 요청 : 웹브라우져
		// user 객체 = 자바오브젝트
		// 변환 (웹브라우저가 이해할 수 있는 데이터) -> json (Gson라이브러리)
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동작동
		// 만약에 자바오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서 
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		return user;
	}
	
	// http://localhost:8000/blog/dummy/join (요청)
	// http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("id : "+user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : "+user.getRole());
		System.out.println("createDate : "+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다  .";
	}

}
