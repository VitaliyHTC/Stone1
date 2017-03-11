package com.vitaliyhtc.stone1;

public abstract class Config {
    public Config() {
        throw new AssertionError();
    }

    public static final String LCBO_API_KEY = "MDowMDhjMGRmMi0wNThhLTExZTctYjc2ZS1iYmQyNmI5NzJjM2Y6Nk9peTlNM1JIMWpuM3BaSzlGM2JnSWROVkJxMW5wWndxbEh5";
    public static final String LCBO_API_BASE_URL = "https://lcboapi.com/";

    public static final int PRODUCTS_PER_PAGE = 24;
    public static final String PRODUCTS_WHERE_NOT = "is_dead";

    public static final String TAG_BROADCAST_RECEIVER = "timer-service-event";
    public static final String TAG_TIMER_SERVICE_IMAGE = "image_url";
    public static final String TAG_TIMER_SERVICE_TIME = "time_string";
}
