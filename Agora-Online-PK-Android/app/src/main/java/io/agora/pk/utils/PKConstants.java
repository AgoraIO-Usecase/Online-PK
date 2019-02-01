package io.agora.pk.utils;

import io.agora.rtc.video.VideoEncoderConfiguration;

public class PKConstants {

    public final static String BUNDLE_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String USER_CLIENT_ROLE = "USER_CLIENT_ROLE";
    public final static VideoEncoderConfiguration VIDEO_CONFIGURATION = new VideoEncoderConfiguration(VideoEncoderConfiguration.VD_640x480,
            VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15, VideoEncoderConfiguration.STANDARD_BITRATE,
            VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT);

    public final static int MAX_PK_COUNT = 2;

    public final static int LIVE_TRANSCODING_WIDTH = 360;
    public final static int LIVE_TRANSCODING_HEIGHT = 640;
    public final static int LIVE_TRANSCODING_FPS = 15;
    public final static int LIVE_TRANSCODING_BITRATE = 1200;

    public final static String PUBLISH_URL = "rtmp://vid-218.push.chinanetcenter.broadcastapp.agora.io/live/";
    public final static String PUBLISH_PULL_URL = "rtmp://vid-218.pull.chinanetcenter.broadcastapp.agora.io/live/";
}
