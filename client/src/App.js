// npm i @react-oauth/google@latest

import KakaoLogin from "./KakaoApp";

import GoogleApp from "./GoogleApp";
import NaverApp from "./NaverApp";
import NaverLoginBackend from "./NaverLoginBackend";

const App = () => {
  return (
    <div>
      <GoogleApp />
      <NaverApp />
      <KakaoLogin />
      <NaverLoginBackend />
    </div>
  );
};

export default App;
