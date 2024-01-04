//npm i react-kakao-login

//JavaScript 키 : 4a3d0a721ac6023b6d68e0883fb29e90

import React from "react";
import KakaoLogin from "react-kakao-login";

const KakaoApp = () => {
  const KakaoLoginSuccess = (res) => {
    console.log(res);
  };
  const KakaoLoginFailure = (err) => {
    console.error(err);
  };

  return (
    <div>
      <KakaoLogin
        token="4a3d0a721ac6023b6d68e0883fb29e90"
        onSuccess={KakaoLoginSuccess}
        onFailure={KakaoLoginFailure}
        // getProfile={true}
        render={(props) => (
          <button onClick={props.onClick}>카카오 로그인</button>
        )}
      />
    </div>
  );
};

export default KakaoApp;
