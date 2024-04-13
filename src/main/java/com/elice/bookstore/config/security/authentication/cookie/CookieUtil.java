package com.elice.bookstore.config.security.authentication.cookie;

import jakarta.servlet.http.Cookie;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 * cookie utility.
 */
@Component
public class CookieUtil {

  public static final int REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 24;

  /**
   * set cookie.

   * @param key .
   * @param value .
   * @param expiry .
   *
   * @return cookie.
   */
  public Cookie createCookie(String key, String value, int expiry) {
    Cookie cookie = new Cookie(key, value);
    cookie.setMaxAge(expiry);
    cookie.setPath("/");
    cookie.setHttpOnly(true);

    return cookie;
  }

  /**
   * delete cookie.
   */
  public Cookie deleteCookie(String key) {
    Cookie cookie = new Cookie(key, null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    return cookie;
  }

  /**
   * get cookie value from key.

   * @param cookies .
   * @param value .
   *
   * @return value.
   */
  public String getValue(Cookie[] cookies, String value) {

    String find = null;

    if (Objects.isNull(cookies)) {

      return null;
    }

    for (var e : cookies) {
      if (e.getName().equals(value)) {
        find = e.getValue();
      }
    }

    return find;
  }
}
