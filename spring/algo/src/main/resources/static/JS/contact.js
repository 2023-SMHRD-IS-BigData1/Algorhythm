// Elements
const el = {
    signUpHome: document.getElementById('sign-up'),
    signInHome: document.getElementById('sign-in'),
    btnHome: document.querySelector('.btn-back'),
    pageMain: document.querySelector('.main'),
    pageHome: document.querySelector('.home'),
    pageSignUp: document.querySelector('.sign-up'),
    formArea: document.querySelector('.form-area'),
    sideSignLeft: document.querySelector('.signup-left'),
    sideSignRight: document.querySelector('.signup-right'),
    formSignUp: document.querySelector('.form-area-signup'),
    formSignIn: document.querySelector('.form-area-signin'),
    linkUp: document.querySelector('.link-up'),
    linkIn: document.querySelector('.link-in'),
    btnSignUp: document.querySelector('.btn-up'),
    btnSignIn: document.querySelector('.btn-in'),
    labels: document.getElementsByTagName('label'),
    inputs: document.getElementsByTagName('input'),
};


// ADD Events
// Show the default page (Sign In) when the application loads
document.addEventListener('DOMContentLoaded', function() {
    showSign(null, 'signin'); // Assuming 'signin' is the ID for the sign-in page
});

// Show the page Sign Up
//el.signUpHome.addEventListener('click', function(e) {
//    showSign(e, 'signup');
//});
el.linkUp.addEventListener('click', function(e) {
    showSign(e, 'signup');
});

// Show the page sign in
el.signInHome.addEventListener('click', function(e) {
    showSign(e, 'signin');
});
el.linkIn.addEventListener('click', function(e) {
    showSign(e, 'signin');
});
el.btnSignUp.addEventListener('click', function(e) {
    showSign(e, 'signin');
});

// Show the page Home
el.btnHome.addEventListener('click', showHome);

// Functions Events
// function to show screen Home
function showHome(event) {


    setTimeout(function() {
        el.sideSignLeft.style.padding = '0';
        el.sideSignLeft.style.opacity = '0';
        el.sideSignRight.style.opacity = '0';
        el.sideSignRight.style.backgroundPositionX = '235%';

        el.formArea.style.opacity = '0';
        setTimeout(function() {
            el.pageSignUp.style.opacity = '0';
            el.pageSignUp.style.display = 'none';
            for (input of el.inputs)  {
                input.value = '';
            }
        }, 900);

    }, 100);

    setTimeout(function() {
        el.pageHome.style.display = 'flex';
    },1100);

    setTimeout(function() {
        el.pageHome.style.opacity = '1';
    }, 1200);

}
// function to show screen Sign up/Sign in
function showSign(event, sign) {

    if (sign === 'signup') {
        el.formSignUp.style.display = 'flex';
        el.formSignIn.style.opacity = '0';
        setTimeout(function() {
            el.formSignUp.style.opacity = '1';
        }, 100);
        el.formSignIn.style.display = 'none';

    } else {
        el.formSignIn.style.display = 'flex';
        el.formSignUp.style.opacity = '0';
        setTimeout(function() {
            el.formSignIn.style.opacity = '1';
        }, 100);
        el.formSignUp.style.display = 'none';
    }

    el.pageHome.style.opacity = '0';
    setTimeout(function() {
        el.pageHome.style.display = 'none';
    }, 700);

    setTimeout(function() {
        el.pageSignUp.style.display = 'flex';
        el.pageSignUp.style.opacity = '1';

        setTimeout(function() {
            el.sideSignLeft.style.padding = '20px';
            el.sideSignLeft.style.opacity = '1';
            el.sideSignRight.style.opacity = '1';
            el.sideSignRight.style.backgroundPositionX = '230%';

            el.formArea.style.opacity = '1';
        }, 10);

    }, 900);
}

// Behavior of the inputs and labels
for (input of el.inputs) {
    console.log(input)
    input.addEventListener('keydown', function() {
        this.labels[0].style.top = '10px';
    });
    input.addEventListener('blur', function() {
        if (this.value === '') {
            this.labels[0].style.top = '25px';
        }
    })
}
function registerCheck() {
    // Get the entered ID value
    var userId = document.getElementById("userId").value;

    // Perform the duplicate check logic (replace this with your actual logic)
    if (userId === "existingId") {
        alert("중복된 아이디입니다. 다른 아이디를 입력해주세요.");
    } else {
        alert("사용 가능한 아이디입니다.");
    }
}
// 아이디 중복체크
function registerCheck(){
    var memId = $("userId").val();

    $.ajax({
        url : "${contextPath}/registerCheck.do",
        type : "get",
        data : {"userId" : USERId},
        success : function(data){
            if(data == 0){
                $("#checkMessage").text("사용가능한 아이디입니다.");
                // id = checkId -> class = "modal-content panel-primary"
                $("#checkId").attr("class", "modal-content panel-primary");
            }else {
                $("#checkMessage").text("사용할 수 없는 아이디입니다.");
                $("#checkId").attr("class", "modal-content panel-danger");
                // id=checkId -> class ="modal-content panel-primary"
            }
            $("#myModal").modal("show");
        },
        error : function(){alert("error")}
    });


}

function checkPw(){
    // 비밀번호가 일치하는지 확인
    var userPw1 = $("#userPw").val();
    var userPw2 = $("#userPw2").val();

    if(userPw1 != userPw2){
        //비밀번호 일치하지 않습니다.
        $("#PwCheckMessage").text("비밀번호가 일치하지 않습니다.");
    }else{
        // 비밀번호 일치o
        $("#PwCheckMessage").text("");
        $("#userPw").val(userPw);
    }
}
// function sample6_execDaumPostcode() {
//        new daum.Postcode({
//            oncomplete: function(data) {
//                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
//
//                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
//                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
//                var addr = ''; // 주소 변수
//                var extraAddr = ''; // 참고항목 변수
//
//                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
//                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
//                    addr = data.roadAddress;
//                } else { // 사용자가 지번 주소를 선택했을 경우(J)
//                    addr = data.jibunAddress;
//                }
//
//                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
//                if(data.userSelectedType === 'R'){
//                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
//                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
//                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
//                        extraAddr += data.bname;
//                    }
//                    // 건물명이 있고, 공동주택일 경우 추가한다.
//                    if(data.buildingName !== '' && data.apartment === 'Y'){
//                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
//                    }
//                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
//                    if(extraAddr !== ''){
//                        extraAddr = ' (' + extraAddr + ')';
//                    }
//                    // 조합된 참고항목을 해당 필드에 넣는다.
//                    document.getElementById("sample6_extraAddress").value = extraAddr;
//
//                } else {
//                    document.getElementById("sample6_extraAddress").value = '';
//                }
//
//                // 우편번호와 주소 정보를 해당 필드에 넣는다.
//                document.getElementById('sample6_postcode').value = data.zonecode;
//                document.getElementById("sample6_address").value = addr;
//                // 커서를 상세주소 필드로 이동한다.
//                document.getElementById("sample6_detailAddress").focus();
//            }
//        }).open();
//    }

//  function checkUsernameAvailability() {
//        var userId = document.getElementById("userId").value;
//        var availabilityMessage = document.getElementById("availability-message");
//
//        // 비동기 부타드립니다
//        // 백엔드 확인부탁드립니다 AJAX 사용
//
//        // 서버 확인 시뮬레이션
//        setTimeout(function() {
//            var isUsernameAvailable = /* 실제 확인 결과로 대체 */ true;
//
//            if (isUsernameAvailable) {
//                availabilityMessage.innerHTML = "사용 가능한 아이디입니다.";
//                availabilityMessage.style.color = "green";
//                // 제출 버튼을 활성화합니다.
//                document.getElementById("join-form").submit.disabled = false;
//            } else {
//                availabilityMessage.innerHTML = "이미 존재하는 아이디입니다.";
//                availabilityMessage.style.color = "red";
//                // 제출 버튼을 비활성화합니다.
//                document.getElementById("join-form").submit.disabled = true;
//            }
//        }, 1000); // 시간 지연 시뮬레이션, 실제 비동기 확인
//    }

    function validateForm() {
            var password1 = document.getElementById("userPw").value;
            var password2 = document.getElementById("userPw2").value;
            var matchMessage = document.getElementById("pw-match-message");

            if (password1 !== password2) {
                matchMessage.innerHTML = "비밀번호가 일치하지 않습니다.";
                // 회원가입 버튼 비활성화
                return false;
            } else {
                matchMessage.innerHTML = "";
                // 회원가입 버튼 활성화
                return true;
            }
        }

