import React from "react";
import NaverLogin from "react-naver-login";
//npm install react-naver-login
const NaverApp = () => {
  const clientId = "Z4rTscYPETXXWJp5C4Jm";
  const NaverLoginSuccess = (response) => {
    console.log(response);
  };

  const NaverLoginFailure = (err) => {
    console.error(err);
  };

  //react-naver-login
  //render prop : 사용자가 버튼을 클릭하는 것을 나타내는 추가 구문

  return (
    <NaverLogin
      clientId={clientId}
      callbackUrl="http://localhost:3000/naverLogin"
      onSuccess={NaverLoginSuccess}
      onFailure={NaverLoginFailure}
      render={(props) => (
        <button onClick={props.onClick}>네이버로 로그인</button>
      )}
    />
  );
};

export default NaverApp;
