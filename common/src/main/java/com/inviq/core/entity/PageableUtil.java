package com.inviq.core.entity;

/**
 * @author ripesh created on 18/04/2025
 */
public class PageableUtil {

  public static int getPageNumberForPagination(int pageNumber) {
    return Math.max(pageNumber - 1, 0);
  }
}
