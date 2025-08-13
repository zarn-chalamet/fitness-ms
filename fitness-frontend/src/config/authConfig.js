export const authConfig = {
  clientId: "fitness-ai-client",
  authorizationEndpoint:
    "http://localhost:8181/realms/fitness-ms/protocol/openid-connect/auth",
  tokenEndpoint:
    "http://localhost:8181/realms/fitness-ms/protocol/openid-connect/token",
  redirectUri: "http://localhost:5173",
  logoutEndpoint:
    "http://localhost:8181/realms/fitness-ms/protocol/openid-connect/logout",
  scope: "openid profile email offline_access",
  onRefreshTokenExpire: (event) => event.logIn(),
};
