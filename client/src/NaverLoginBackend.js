import React from "react";
import axios from "axios";

export default function NaverLoginBackend() {
  const handleNaverLogin = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/login/oauth2/code/naver",
        // 'http://localhost:8080/oauth2/authorization/naver',
        { withCredentials: true }
      );
      console.log("Frontend에서 제공되는 콘솔 로그");
      console.log(response.data);
    } catch (error) {
      console.error("Naver Login error : " + error);
    }
  };
  return (
    <div>
      <button onClick={handleNaverLogin}>Naver Login</button>
    </div>
  );
}
