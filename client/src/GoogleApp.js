// npm i @react-oauth/google@latest

import { GoogleLogin } from "@react-oauth/google";
import { GoogleOAuthProvider } from "@react-oauth/google";

const GoogleLoginButton = () => {
  const clientId =
    "484985427980-v3j166954btcfgo9l2s7o3qbssime88t.apps.googleusercontent.com";

  return (
    <div>
      <GoogleOAuthProvider clientId={clientId}>
        <GoogleLogin
          onSuccess={(res) => {
            console.log(res);
          }}
          onFailure={(err) => {
            console.log(err);
          }}
        />
      </GoogleOAuthProvider>
    </div>
  );
};

export default GoogleLoginButton;
