package br.com.authorizationserver.oauth.pkce;

public class CodeChallengeError extends Exception {
  public CodeChallengeError() {
    super("PKCE: Code  challenge failed");
  }
}
