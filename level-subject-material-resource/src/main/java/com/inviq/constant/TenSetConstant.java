package com.inviq.constant;

import com.inviq.core.constants.NovaLmsEnvConstants;
import com.inviq.core.util.SystemUtil;

/**
 * @author ripesh created on 06/05/2026
 */
public class TenSetConstant {
  public static final String TEN_SET_FILE_PATH =
      SystemUtil.getEnv(NovaLmsEnvConstants.HOME_DIRECTORY) + "/tenset/uploads";
}
