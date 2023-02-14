let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{ // function(){}, ()=>{} this를 바인딩하기 위해서
			this.save();
		});
		$("#btn-update").on("click", ()=>{ 
			this.update();
		});
	}
	
	, save: function(){
		//alert('user의 save 함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		//console.log(data);
		
		//ajax호출시 default가 비동기호출 
		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		//aiax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바오브젝트로 변환
		$.ajax({ //회원가입 수행 요청
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), //http body 데이터. JSON 문자열
			contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
			dataType: "json" //요청을 서버로해서 응답이 왔을 때, 기본적으로 모든것이 문자열(생긴게 json이라면) => javascript오브젝트로 변경해줌
		}).done(function(resp){ //응답결과가 정상일때 done 실행
			alert("회원가입이 완료되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error){ //응답결과가 비정상일때 fail 실행
			alert(JSON.stringify(error));
		});
	}	
	
	, update: function(){
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(resp){ 
			alert("회원수정이 완료되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}	
}

index.init();

/*
ajax 쓰는 두 가지 이유
1. 웹프로그램을 사용하든지 앱 프로그램을 사용하든지 서버를 하나만 쓰기 위해
2. 비동기 통신을 하기 위해서
*/
