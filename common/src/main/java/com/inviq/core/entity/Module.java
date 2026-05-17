package com.inviq.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 17/04/2026
 */
@Getter
@AllArgsConstructor
public enum Module {
  USER("USER", "User"),
  LEVEL("LEVEL", "Level"),
  SUBJECT("SUBJECT", "Subject"),
  LEVEL_SUBJECTS("LEVEL_SUBJECTS", "Level Subjects"),
  MATERIAL("MATERIAL", "Material"),
  LEVEL_SUBJECT_MATERIALS("LEVEL_SUBJECT_MATERIALS", "Level Subject Materials"),
  TEN_SET_MATERIALS("TEN_SET_MATERIALS", "Ten Set Materials"),
  LEVEL_SUBJECT_CHAPTERS("LEVEL_SUBJECT_CHAPTERS", "Level Subject Chapters"),
  AFFILIATE("AFFILIATE", "Affiliate"),
  AFFILIATE_TYPE("AFFILIATE_TYPE", "Affiliate Type"),
  AFFILIATE_REFERRAL("AFFILIATE_REFERRAL", "Affiliate Referral"),
  QUESTION("QUESTION", "Question"),
  ANSWER("ANSWER", "Answer"),
  ;

  private final String code;
  private final String displayName;
}
