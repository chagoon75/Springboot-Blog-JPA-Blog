let index = {
	init: function(){
		$("#btn-save").on("click", ()=> { // function(){}, ()=>{} this를 바인딩하기 위해서 !!
			this.save();
		});
	},
	
	save: function(){
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		//console.log(data);
		
		// ajax호출시 default가 비동기 호출
		// ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!!
		$.ajax({
			// 회원가입 수행 요청 (100초 가정)
			type: "POST",
			url: "/blog/api/user",
			data: JSON.stringify(data), // http body데이터
			contentType: "application/json;charset=utf-8", // body데어터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 문자열(생긴게 json이라면) => javascript object로 변환			
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
			location.href = "/blog";			
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	}
}

index.init();