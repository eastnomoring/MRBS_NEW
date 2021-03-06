package com.yzy.mrbs.base;

/**
 * Created by ZhiYuan on 2016/4/26.
 */
public class C {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // core settings (important)

    public static final class dir {
        public static final String base				= "/sdcard/mrbs";
        public static final String faces			= base + "/faces";
        public static final String images			= base + "/images";
    }

    public static final class api {
        public static final String base				= "http://192.168.1.106:80/PhalApi/Public";
        public static final String index			= "/index/index";
        public static final String login			= "/index/login";
        public static final String logout			= "/index/logout";
        public static final String faceView 		= "/image/faceView";
        public static final String faceList 		= "/image/faceList";
        public static final String commentCreate	= "/comment/commentCreate";
        public static final String customerView		= "/customer/customerView";
        public static final String customerEdit		= "/customer/customerEdit";
        public static final String notice			= "/notify/notice";
    }

    public static final class task {
        public static final int index				= 1001;
        public static final int login				= 1002;
        public static final int logout				= 1003;
        public static final int faceView			= 1004;
        public static final int faceList			= 1005;
        public static final int blogList			= 1006;
        public static final int blogView			= 1007;
        public static final int blogCreate			= 1008;
        public static final int commentList			= 1009;
        public static final int commentCreate		= 1010;
        public static final int customerView		= 1011;
        public static final int customerEdit		= 1012;
        public static final int fansAdd				= 1013;
        public static final int fansDel				= 1014;
        public static final int notice				= 1015;
    }

    public static final class err {
        public static final String network			= "网络错误";
        public static final String message			= "消息错误";
        public static final String jsonFormat		= "消息格式错误";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // intent & action settings

    public static final class intent {
        public static final class action {
            public static final String EDITTEXT		= "com.yzy.mrbs.EDITTEXT";
        }
    }

    public static final class action {
        public static final class edittext {
            public static final int CONFIG			= 2001;
            public static final int COMMENT			= 2002;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // additional settings

    public static final class web {
        public static final String base				= "http://192.168.1.106:8002";
        public static final String index			= base + "/index.php";
        public static final String gomap			= base + "/gomap.php";
    }
}
